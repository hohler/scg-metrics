package ch.unibe.scg.metrics.changemetrics;

import java.nio.file.Path;
import java.util.Calendar;

import org.repodriller.RepoDriller;
import org.repodriller.filter.range.CommitRange;
import org.repodriller.filter.range.Commits;
import org.repodriller.scm.GitRemoteRepository;
import org.repodriller.scm.GitRepository;
import org.repodriller.scm.SCMRepository;

import ch.unibe.scg.metrics.changemetrics.domain.CMRepository;

public class ChangeMetrics {

	private SCMRepository repository;
	private CommitRange range;
	
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
	
	public void setRange(Calendar from, Calendar to) {
		this.range = Commits.betweenDates(from, to);
	}
	
	public void setRange(String firstRef, String lastRef) {
		this.range = Commits.range(firstRef, lastRef);
	}
	
	public CMRepository analyze() {
		ChangeMetricsStudy study = new ChangeMetricsStudy(repository, range);
		new RepoDriller().start(study);
		return study.getRepositoryInfo();
	}
	
}
