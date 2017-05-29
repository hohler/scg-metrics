package ch.unibe.scg.metrics.sourcemetrics;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.repodriller.RepoDriller;
import org.repodriller.domain.ChangeSet;
import org.repodriller.domain.Commit;
import org.repodriller.filter.range.CommitRange;
import org.repodriller.filter.range.Commits;
import org.repodriller.scm.GitRemoteRepository;
import org.repodriller.scm.GitRepository;
import org.repodriller.scm.SCM;
import org.repodriller.scm.SCMRepository;

import ch.unibe.scg.metrics.sourcemetrics.domain.SMRepository;

public class SourceMetrics {
	private SCMRepository repository;
	private CommitRange range;
	private int everyNthCommit = 1;
	private String firstRef;
	private List<String> commitList;
	
	private Logger logger = Logger.getLogger(SourceMetrics.class);
	
	public SourceMetrics(String gitUrl) {
		// First clone Repo and then set path
		clone(gitUrl);
		initDefaults();
	}
	
	public SourceMetrics(Path path) {
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
	
	public void setEveryNthCommit(int n) {
		everyNthCommit = n;
	}
	
	public List<String> getCommitList() {
		return new ArrayList<String>(commitList);
	}
	
	public SMRepository analyze(List<String> commits) {
		SMStudy study;
		study = new SMStudy(repository, Commits.list(commits));
		
		new RepoDriller().start(study);
		return study.getRepositoryInfo();
	}
	
	public SMRepository analyze() {
		SMStudy study;
		study = new SMStudy(repository, range);
		
		new RepoDriller().start(study);
		
		return study.getRepositoryInfo();
	}

	public void generateCommitList() {
		ArrayList<String> results = new ArrayList<>();
		int counter = 0;
		List<ChangeSet> changeSets;
		
		SCM scm = repository.getScm();
		
		if(range != null) {
			changeSets = range.get(scm);
			logger.debug("[changeSet with range!=null]:"+changeSets);
		} else {
			logger.debug("[Range is null]");
			changeSets = scm.getChangeSets();
		}
		
		/*List<ChangeSet> changeSets = changeSetsTemp.stream()
                .filter(set -> !scm.getCommit(set.getId()).isInMainBranch())
                .collect(Collectors.toList());*/
		
		if(everyNthCommit == 0) {
			if(changeSets.size() > 0) results.add(changeSets.get(0).getId());
			commitList = results;
			return;
		}
		
		logger.debug("change set amount: "+changeSets.size());
		
		firstRef = changeSets.get(changeSets.size()-1).getId();
		
		for(ChangeSet cs : changeSets) {
			
			if((counter % everyNthCommit == 0 && counter != 0) || counter == 0) {
				
				String ref = cs.getId();
				Commit c = scm.getCommit(ref);
				
				if(!c.isInMainBranch()) continue; // only MAIN branch!
				
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
