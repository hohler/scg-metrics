package ch.unibe.scg.metrics.changemetrics.domain;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.repodriller.domain.Commit;
import org.repodriller.domain.Developer;
import org.repodriller.domain.Modification;
import org.repodriller.domain.ModificationType;

public class CMFile {

	String file;
	private int revisions;
	private int refactorings;
	private int bugfixes;
	private Set<String> authors;
	private long locAdded;
	private long locRemoved;
	private long maxLocAdded;
	private long maxLocRemoved;
	private long codeChurn;
	private long maxCodeChurn;
	private int maxChangeset;
	private long totalChangeset;
	private Calendar firstCommit;
	private Calendar lastCommit;
	private double weightedAge;
	
	private Map<Integer, WeightedAgeHolder> weightedAgeRecords;

	public CMFile(String file) {
		this.file = file;
		this.authors = new HashSet<>();
		this.weightedAgeRecords = new HashMap<>();
	}
	
	public void update(Commit commit, Modification modification, CMBugRepository bugRepository) {
		String msg = commit.getMsg().toLowerCase();

		countRevision(modification);
		countRefactoring(msg);
		countBugFixes(msg, commit.getHash(), bugRepository);
		addAuthor(commit.getAuthor());
		countLocAddedAndRemoved(modification);
		countCodeChurn(modification);
		countChangeset(commit, modification);
		firstAndLastDates(commit, modification);
		calculateWeightedAge(modification);
	}

	private void firstAndLastDates(Commit commit, Modification modification) {
		if(modification.getType() != ModificationType.DELETE) {
		
			if(firstCommit == null) firstCommit = commit.getDate();
			else if(commit.getDate().before(firstCommit)) firstCommit = commit.getDate();
	
			if(lastCommit == null) lastCommit = commit.getDate();
			else if(commit.getDate().after(lastCommit)) lastCommit = commit.getDate();
		}
	}

	private void countChangeset(Commit commit, Modification modification) {
		if(modification.getType() != ModificationType.DELETE) {
			maxChangeset = Math.max(maxChangeset, commit.getModifications().size());
			totalChangeset += commit.getModifications().size();
		}
	}

	private void countCodeChurn(Modification modification) {
		if(modification.getType() != ModificationType.DELETE) {
			int churn = modification.getAdded() + modification.getRemoved();
			codeChurn += churn;
			
			maxCodeChurn = Math.max(maxCodeChurn, churn);
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
	private void countBugFixes(String msg, String hash, CMBugRepository bugRepository) {
		if(bugRepository == null) {
			if(msg.contains("fix") && !msg.contains("postfix") && !msg.contains("prefix")) bugfixes++;
			else if(msg.contains("bug")) bugfixes++;
		} else {
			if(bugRepository.isBugfixCommit(hash)) {
				bugfixes++;
			}
		}
	}

	private void countRevision(Modification modification) {
		if(modification.getType() != ModificationType.DELETE) revisions++;
	}
	
	private void countRefactoring(String msg) {
		if(msg.contains("refactor")) refactorings++;
	}
	
	private void calculateWeightedAge(Modification modification) {
		if(modification.getType() != ModificationType.DELETE) {
			WeightedAgeHolder w = new WeightedAgeHolder();
			w.locAdded = modification.getAdded();
			w.weeks = getWeeks();
			weightedAgeRecords.put(getRevisions(), w);
		}
		
		Iterator<Entry<Integer, WeightedAgeHolder>> it = weightedAgeRecords.entrySet().iterator();
		long numerator = 0;
		double denominator = 0.0;
		while(it.hasNext()) {
			Entry<Integer, WeightedAgeHolder> pair = it.next();
			WeightedAgeHolder w = (WeightedAgeHolder) pair.getValue();
			
			numerator += w.weeks * w.locAdded;
			denominator += w.locAdded;
		}
		
		if(numerator == 0 || denominator == 0) weightedAge = 0;
		else weightedAge = numerator / denominator;
		
	}

	public String getFile() {
		return file;
	}

	public int getRevisions() {
		return revisions;
	}


	public int getRefactorings() {
		return refactorings;
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


	public long getCodeChurn() {
		return codeChurn;
	}
	
	public long getMaxCodeChurn() {
		return maxCodeChurn;
	}
	
	public double getAvgCodeChurn() {
		if(revisions == 0) return 0;
		return codeChurn / (double) revisions;
	}


	public int getMaxChangeset() {
		return maxChangeset;
	}


	public double getAvgChangeset() {
		if(revisions == 0) return 0;
		return totalChangeset / (double) revisions;
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
	
	public double getWeightedAge() {
		return weightedAge;
	}

	public void rename(String newPath) {
		this.file = newPath;
	}
	
	public String toString() {
		return "CMFile ["+file+", revisions: "+revisions+", refactorings: "+refactorings+", bugfixes: "+bugfixes+", authors: "
				+String.join(",", authors)+", locAdded: "+locAdded+", locRemoved: "+locRemoved+" codeChurn: "+codeChurn+", totalChangeset: "+totalChangeset;
	}
	
	private class WeightedAgeHolder {
		long weeks;
		long locAdded;
	}
}
