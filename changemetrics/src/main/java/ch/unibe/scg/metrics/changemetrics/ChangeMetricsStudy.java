package ch.unibe.scg.metrics.changemetrics;

import java.util.Arrays;

import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.commit.OnlyModificationsWithFileTypes;
import org.repodriller.filter.range.CommitRange;
import org.repodriller.scm.SCMRepository;

import ch.unibe.scg.metrics.changemetrics.domain.CMRepository;

public class ChangeMetricsStudy implements Study {

	private SCMRepository repository;
	private CommitRange range;
	private CMRepository repoInfo;
	
	public ChangeMetricsStudy(SCMRepository repository, CommitRange range) {
		this.repository = repository;
		this.range = range;
	}
	
	public void execute() {
		
		repoInfo = new CMRepository();
		
		new RepositoryMining()
		.in(repository)
		.through(range)
		.process(new ChangeMetricsProcessor(repoInfo))
		.filters(new OnlyModificationsWithFileTypes(Arrays.asList(".java")))
		.mine();

	}
	
	public CMRepository getRepositoryInfo() {
		return this.repoInfo;
	}

}
