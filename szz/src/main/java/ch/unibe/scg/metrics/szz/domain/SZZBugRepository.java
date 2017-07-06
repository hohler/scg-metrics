package ch.unibe.scg.metrics.szz.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * used for external services that can provide their information about commits (from issue trackers, etc.)
 * Set automatically removes duplicates
 */
public class SZZBugRepository {

	private Set<String> bugCommits;
	
	public SZZBugRepository() {
		bugCommits = new HashSet<>();
	}

	public Set<String> getBugCommits() {
		return bugCommits;
	}

	public void setBugCommits(Set<String> bugCommits) {
		this.bugCommits = bugCommits;
	}
	
	public void addBugCommit(String commitHash) {
		if(this.bugCommits == null) return;
		this.bugCommits.add(commitHash);
	}
	
	public boolean removeBugCommit(String commitHash) {
		if(this.bugCommits == null) return false;
		return this.bugCommits.remove(commitHash);
	}
	
	public boolean isBugfixCommit(String commitHash) {
		if(this.bugCommits == null) return false;
		return bugCommits.contains(commitHash);
	}
}