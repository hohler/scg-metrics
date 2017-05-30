package ch.unibe.scg.metrics.changemetrics;

import java.util.Arrays;

import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.commit.OnlyInMainBranch;
import org.repodriller.filter.commit.OnlyModificationsWithFileTypes;
import org.repodriller.filter.range.CommitRange;
import org.repodriller.scm.SCMRepository;

import ch.unibe.scg.metrics.changemetrics.domain.CMBugRepository;
import ch.unibe.scg.metrics.changemetrics.domain.CMRepository;

public class ChangeMetricsStudy implements Study {

	private SCMRepository repository;
	private CommitRange range;
	private CMRepository repoInfo;
	private int threads;
	private CMBugRepository bugRepository;
	
	public ChangeMetricsStudy(SCMRepository repository, CommitRange range, int threads) {
		this.repository = repository;
		this.range = range;
		this.threads = threads;
	}
	
	public void execute() {
		
		repoInfo = new CMRepository();
		
		new RepositoryMining()
		.in(repository)
		.through(range)
		.withThreads(threads)
		.process(new ChangeMetricsProcessor(repoInfo, bugRepository))
		.filters(/*new OnlyModificationsWithFileTypes(Arrays.asList(".java")), */new OnlyInMainBranch())
		.mine();

	}
	
	public CMRepository getRepositoryInfo() {
		return this.repoInfo;
	}
	
	public void setBugRepository(CMBugRepository bugRepo) {
		this.bugRepository = bugRepo;
	}

}
