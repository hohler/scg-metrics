package org.repodriller.filter.range;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.repodriller.domain.ChangeSet;
import org.repodriller.scm.SCM;

public class BetweenDatesExtended implements CommitRange {

	private List<String> excludeCommits;
	
	private Calendar from;
	private Calendar to;
	
	public BetweenDatesExtended(Calendar from, Calendar to, List<String> excludeCommits) {
		this.from = from;
		this.to = to;
		this.excludeCommits = excludeCommits;
	}
	
	@Override
	public List<ChangeSet> get(SCM scm) {
		
		List<ChangeSet> all = scm.getChangeSets();
		
		LinkedList<ChangeSet> filtered = new LinkedList<ChangeSet>();
		
		for(ChangeSet cs : all) {
			if(isInTheRange(cs) && !isCommitExcluded(cs.getId())) {
				filtered.addLast(cs);
			}
		}
		
		return filtered;
	}
	
	private boolean isInTheRange(ChangeSet cs) {
		return from.before(cs.getTime()) && to.after(cs.getTime());
	}

	private boolean isCommitExcluded(String ref) {
		if(excludeCommits == null) return false;
		return excludeCommits.contains(ref);
	}
}
