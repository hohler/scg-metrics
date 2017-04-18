package ch.unibe.scg.metrics.szz.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.repodriller.domain.Commit;
import org.repodriller.domain.Developer;
import org.repodriller.domain.Modification;
import org.repodriller.domain.ModificationType;

public class SZZFile {

	String file;
	private int revisions;
	private int bugfixes;
	private Set<String> authors;
	private long locAdded;
	private long locRemoved;
	private long maxLocAdded;
	private long maxLocRemoved;
	private Calendar firstCommit;
	private Calendar lastCommit;
	
	private Map<String, SZZCommit> commits;
	
	// private Logger logger = Logger.getLogger(CMFile.class);

	public SZZFile(String file) {
		this.file = file;
		this.authors = new HashSet<>();
		this.commits = new LinkedHashMap<>();
	}
	
	public void update(Commit commit, Modification modification) {
		String msg = commit.getMsg().toLowerCase();

		countRevision(modification);
		// int oldBugfixes = this.bugfixes;
		countBugFixes(msg);
		// if(oldBugfixes != this.bugfixes) addBugfixCommit(commit, modification);
		addAuthor(commit.getAuthor());
		countLocAddedAndRemoved(modification);
		firstAndLastDates(commit, modification);
	}
	
	// TODO
	/*public void addBugfixCommit(SZZCommit szzC) {
		this.commits.put(szzC.getHash(), szzC);
	}*/

	private void firstAndLastDates(Commit commit, Modification modification) {
		if(modification.getType() != ModificationType.DELETE) {
		
			if(firstCommit == null) firstCommit = commit.getDate();
			else if(commit.getDate().before(firstCommit)) firstCommit = commit.getDate();
	
			if(lastCommit == null) lastCommit = commit.getDate();
			else if(commit.getDate().after(lastCommit)) lastCommit = commit.getDate();
		}
	}

	private void countLocAddedAndRemoved(Modification modification) {
		if(modification.getType() != ModificationType.DELETE) {
			int added = modification.getAdded();
			int removed = modification.getRemoved();

			locAdded += added;
			locRemoved += removed;

			maxLocAdded = Math.max(maxLocAdded, added);
			maxLocRemoved = Math.max(maxLocRemoved, removed);
		}
		
	}

	private void addAuthor(Developer developer) {
		authors.add(developer.getName());
	}

	// TODO: make regex and remove post/prefix
	private void countBugFixes(String msg) {
		if(msg.contains("fix") && !msg.contains("postfix") && !msg.contains("prefix")) bugfixes++;
		else if(msg.contains("bug")) bugfixes++;
	}

	private void countRevision(Modification modification) {
		if(modification.getType() != ModificationType.DELETE) revisions++;
	}

	public String getFile() {
		return file;
	}

	public int getRevisions() {
		return revisions;
	}


	public int getBugfixes() {
		return bugfixes;
	}


	public long getLocAdded() {
		return locAdded;
	}


	public long getLocRemoved() {
		return locRemoved;
	}


	public double getAvgLocAdded() {
		if(revisions == 0) return 0;
		return locAdded / (double) revisions;
	}


	public double getAvgLocRemoved() {
		if(revisions == 0) return 0;
		return locRemoved / (double) revisions;
	}


	public long getMaxLocAdded() {
		return maxLocAdded;
	}


	public long getMaxLocRemoved() {
		return maxLocRemoved;
	}


	public Calendar getFirstCommit() {
		return firstCommit;
	}


	public Calendar getLastCommit() {
		return lastCommit;
	}

	public int getUniqueAuthorsQuantity() {
		return authors.size();
	}

	public long getWeeks() {
		long end = lastCommit.getTimeInMillis();
	    long start = firstCommit.getTimeInMillis();
	    return TimeUnit.MILLISECONDS.toDays(Math.abs(end - start)) / 7;
	}
	

	public void rename(String newPath) {
		this.file = newPath;
	}
	
	public SZZCommit saveOrGetCommit(Commit commit, Modification modification) {
		String hash = commit.getHash();
		
		if(!commits.containsKey(hash)) {
			commits.put(hash, new SZZCommit(commit, modification));
		}
		
		return commits.get(hash);
	}
	
	/*public void addCommit(SZZCommit commit) {
		commits.put(commit.getHash(), commit);
	}*/
	
	public SZZCommit getCommit(String hash) {
		return commits.get(hash);
	}
	
	public Collection<SZZCommit> getCommits() {
		return commits.values();
	}
	
	public String toString() {
		return "SZZFile ["+file+", revisions: "+revisions+", bugfixes: "+bugfixes+", authors: "
				+String.join(",", authors)+", locAdded: "+locAdded+", locRemoved: "+locRemoved;
	}
}
