package ch.unibe.scg.metrics.changemetrics;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

import ch.unibe.scg.metrics.changemetrics.domain.CMBugRepository;
import ch.unibe.scg.metrics.changemetrics.domain.CMRepository;

public class ChangeMetrics {

	private SCMRepository repository;
	private CommitRange range;
	private int threads = 1;
	private int everyNthCommit = 1;
	private String firstRef;
	private List<String> commitList;
	
	private CMBugRepository bugRepository;
	
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
	
	public void setRange(CommitRange range) {
		this.range = range;
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
		study.setBugRepository(this.bugRepository);
		
		new RepoDriller().start(study);
		return study.getRepositoryInfo();
	}
	
	public CMRepository analyze() {
		ChangeMetricsStudy study;		
		study = new ChangeMetricsStudy(repository, range, threads);
		study.setBugRepository(this.bugRepository);
		
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
			if((counter % everyNthCommit == 0 && counter != 0) || counter == 0) {
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
	
	/**
	 * for each commit get a range that is from the commit to x weeks back
	 * @param weeksBack (if 999999: this means it should go to the very beginning of the project (2005, git release))
	 * @return Map with CommitHash mapped to CommitRange
	 */
	public Map<String, CommitRange> generateCommitListWithWeeks(int weeksBack) {
		Map<String, CommitRange> results = new LinkedHashMap<>();
		SCM scm = repository.getScm();
		List<ChangeSet> changeSets;
		
		if(range != null) changeSets = range.get(scm);
		else changeSets = scm.getChangeSets();
		
		
		/*List<ChangeSet> changeSets = changeSetsTemp.stream()
                .filter(set -> !scm.getCommit(set.getId()).isInMainBranch())
                .collect(Collectors.toList());*/
		
		
		int counter = 0;
		for(ChangeSet cs : changeSets) {
			
			/*String ref = cs.getId();
			Commit c = scm.getCommit(ref);
			
			if(!c.isInMainBranch()) continue; // only MAIN branch!*/
			
			//if(counter % everyNthCommit == 0 || everyNthCommit == 1) {
			if((counter % everyNthCommit == 0 && counter != 0) || counter == 0 || everyNthCommit == 1) {
				String ref = cs.getId();
				Commit c = scm.getCommit(ref);
				
				if(!c.isInMainBranch()) continue; // only MAIN branch!
				
				Calendar end = Calendar.getInstance();
				end.setTimeInMillis(c.getDate().getTimeInMillis());
				end.add(Calendar.SECOND, 10); // So that this commit will be in range
				
				Calendar start = Calendar.getInstance();
				start.setTimeInMillis(c.getDate().getTimeInMillis());
				if(weeksBack == 999999) {
					start.set(Calendar.YEAR, 1999); // release of first git version
				} else {
					start.add(Calendar.WEEK_OF_YEAR, -weeksBack);
				}
				start.add(Calendar.SECOND, -10);

				CommitRange range = Commits.betweenDates(start,  end);
				
				results.put(ref, range);
			}
			counter++;
		}
		return results;
	}
	
	
	public void setBugRepository(CMBugRepository bugRepo) {
		this.bugRepository = bugRepo;
	}
	
	public SCM getScm() {
		return this.repository.getScm();
	}
}
