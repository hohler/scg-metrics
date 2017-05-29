package ch.unibe.scg.metrics.sourcemetrics;

import java.util.Arrays;

import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.domain.ChangeSet;
import org.repodriller.filter.commit.OnlyInMainBranch;
import org.repodriller.filter.commit.OnlyModificationsWithFileTypes;
import org.repodriller.filter.range.CommitRange;
import org.repodriller.scm.SCMRepository;

import ch.unibe.scg.metrics.sourcemetrics.domain.SMRepository;

public class SMStudy implements Study {

	private SCMRepository repository;
	private CommitRange range;
	private SMRepository repoInfo;
	
	public SMStudy(SCMRepository repository, CommitRange range) {
		this.repository = repository;
		this.range = range;
	}
	
	public void execute() {
		repoInfo = new SMRepository();
		new RepositoryMining()
		.in(repository)
		.through(range)
		.process(new SMProcessor(repoInfo))
		.filters(new OnlyModificationsWithFileTypes(Arrays.asList(".java")), new OnlyInMainBranch())
		.mine();
	}
	
	public SMRepository getRepositoryInfo() {
		return repoInfo;
	}

}
