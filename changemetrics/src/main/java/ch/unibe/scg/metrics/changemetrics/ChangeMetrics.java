package ch.unibe.scg.metrics.changemetrics;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.repodriller.RepoDriller;
import org.repodriller.domain.ChangeSet;
import org.repodriller.filter.range.CommitRange;
import org.repodriller.filter.range.Commits;
import org.repodriller.scm.GitRemoteRepository;
import org.repodriller.scm.GitRepository;
import org.repodriller.scm.SCMRepository;

import ch.unibe.scg.metrics.changemetrics.domain.CMRepository;

public class ChangeMetrics {

	private SCMRepository repository;
	private CommitRange range;
	private int threads = 1;
	private int everyNthCommit = 1;
	private String firstRef;
	private List<String> commitList;
	
	private Logger logger = Logger.getLogger(ChangeMetrics.class);
	
	public ChangeMetrics(String gitUrl) {
		// First clone Repo and then set path
		clone(gitUrl);
		initDefaults();
	}
	
	public ChangeMetrics(Path path) {
		this.repository = GitRepository.singleProject(path.toString());
		initDefaults();
	}
	
	private void initDefaults() {
		this.range = Commits.all();
	}
	
	private void clone(String gitUrl) {
		this.repository = GitRemoteRepository
		.hostedOn(gitUrl)
		.buildAsSCMRepository();
	}
	
	public void setFirstRef(String firstRef) {
		this.firstRef = firstRef;
	}
	
	public String getFirstRef() {
		return firstRef;
	}
	
	public void setRange(Calendar from, Calendar to) {
		this.range = Commits.betweenDates(from, to);
	}
	
	/**
	 * 
	 * @param firstRef first Commit (oldest)
	 * @param lastRef last commit (newest)
	 * if firstRef is null, Head is used.
	 */
	public void setRange(String firstRef, String lastRef) {
		this.range = Commits.range(firstRef, lastRef);
	}
	
	public void setThreads(int threads) {
		this.threads = threads;
	}
	
	public void setEveryNthCommit(int n) {
		everyNthCommit = n;
	}
	
	public List<String> getCommitList() {
		return new ArrayList<String>(commitList);
	}
	
	public CMRepository analyze(List<String> commits) {
		ChangeMetricsStudy study;
		study = new ChangeMetricsStudy(repository, Commits.list(commits), threads);
		
		new RepoDriller().start(study);
		return study.getRepositoryInfo();
	}
	
	public CMRepository analyze() {
		ChangeMetricsStudy study;		
		study = new ChangeMetricsStudy(repository, range, threads);
		
		new RepoDriller().start(study);
		return study.getRepositoryInfo();
	}

	public void generateCommitList() {
		ArrayList<String> results = new ArrayList<>();
		int counter = 0;
		List<ChangeSet> changeSets;
		
		if(range != null) {
			changeSets = range.get(repository.getScm());
			logger.debug("[changeSet with range!=null]:"+changeSets);
		} else {
			logger.debug("[Range is null]");
			changeSets = repository.getScm().getChangeSets();
		}
		
		logger.debug("change set amount: "+changeSets.size());
		
		firstRef = changeSets.get(changeSets.size()-1).getId();
		
		for(ChangeSet cs : changeSets) {
			if(counter % everyNthCommit == 0 && counter != 0) {
				results.add(cs.getId());
			}
			counter++;
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("[everyNthCommit] value: "+everyNthCommit);
			logger.debug(results);
		}
		
		commitList = results;
	}
}
