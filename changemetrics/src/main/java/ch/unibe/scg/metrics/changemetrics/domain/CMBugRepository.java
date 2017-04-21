package ch.unibe.scg.metrics.changemetrics.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * used for external services that can provide their information about commits (from issue trackers, etc.)
 * Set automatically removes duplicates
 */
public class CMBugRepository {

	private Set<String> bugCommits;
	
	public CMBugRepository() {
		bugCommits = new HashSet<>();
	}

	public Set<String> getBugCommits() {
		return bugCommits;
	}

	public void setBugCommits(Set<String> bugCommits) {
		this.bugCommits = bugCommits;
	}
	
	public void addBugCommit(String commitHash) {
		this.bugCommits.add(commitHash);
	}
	
	public boolean removeBugCommit(String commitHash) {
		return this.bugCommits.remove(commitHash);
	}
	
	public boolean isBugfixCommit(String commitHash) {
		return bugCommits.contains(commitHash);
	}
}