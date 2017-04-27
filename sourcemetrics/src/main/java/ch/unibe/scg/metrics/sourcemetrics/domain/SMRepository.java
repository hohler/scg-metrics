package ch.unibe.scg.metrics.sourcemetrics.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SMRepository {
	
	private Map<String, SMCommit> commits;
	
	public SMRepository() {
		commits = new HashMap<>();
	}

	public void addCommit(SMCommit c) {
		commits.put(c.getHash(), c);
	}
	
	public SMCommit getCommit(String hash) {
		return commits.get(hash);
	}

	public Collection<SMCommit> all() {
		return commits.values();
	}

}
