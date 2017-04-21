package ch.unibe.scg.metrics.szz;

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

import ch.unibe.scg.metrics.szz.domain.SZZBugRepository;
import ch.unibe.scg.metrics.szz.domain.SZZRepository;

public class SZZ {
	private SCMRepository repository;
	private CommitRange range;
	private int threads = 1;
	private int everyNthCommit = 1;
	private String firstRef;
	private List<String> commitList;
	
	private SZZBugRepository bugRepository;
	
	private Logger logger = Logger.getLogger(SZZ.class);
	
	public SZZ(String gitUrl) {
		// First clone Repo and then set path
		clone(gitUrl);
		initDefaults();
	}
	
	public SZZ(Path path) {
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
	
	public SZZRepository analyze(List<String> commits) {
		SZZStudy study;
		study = new SZZStudy(repository, Commits.list(commits), threads);
		study.setBugRepository(bugRepository);
		
		new RepoDriller().start(study);
		return study.getRepositoryInfo();
	}
	
	public SZZRepository analyze() {
		SZZStudy study;
		study = new SZZStudy(repository, range, threads);
		study.setBugRepository(bugRepository);
		
		new RepoDriller().start(study);
		
		SZZStudy2 study2;
		study2 = new SZZStudy2(repository, range, threads, study.getRepositoryInfo());
		
		new RepoDriller().start(study2);
		
		return study2.getRepositoryInfo();
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

	public SZZBugRepository getBugRepository() {
		return bugRepository;
	}

	public void setBugRepository(SZZBugRepository bugRepository) {
		this.bugRepository = bugRepository;
	}
}
