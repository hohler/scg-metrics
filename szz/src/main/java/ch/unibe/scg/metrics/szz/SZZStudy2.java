package ch.unibe.scg.metrics.szz;

import java.util.Arrays;

import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.commit.OnlyInMainBranch;
import org.repodriller.filter.commit.OnlyModificationsWithFileTypes;
import org.repodriller.filter.range.CommitRange;
import org.repodriller.scm.SCMRepository;

import ch.unibe.scg.metrics.szz.domain.SZZRepository;

public class SZZStudy2 implements Study {

	private SCMRepository repository;
	private CommitRange range;
	private int threads;
	private SZZRepository repoInfo;
	
	public SZZStudy2(SCMRepository repository, CommitRange range, int threads, SZZRepository repoInfo) {
		this.repository = repository;
		this.range = range;
		this.threads = threads;
		this.repoInfo = repoInfo;
	}
	
	public void execute() {

		new RepositoryMining()
		.in(repository)
		.through(range)
		.withThreads(threads)
		.process(new SZZProcessor2(repoInfo))
		.filters(new OnlyModificationsWithFileTypes(Arrays.asList(".java")), new OnlyInMainBranch())
		.mine();
	}
	
	public SZZRepository getRepositoryInfo() {
		return repoInfo;
	}

}
