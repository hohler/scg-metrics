package ch.unibe.scg.metrics.szz.domain;

import org.repodriller.domain.Commit;
import org.repodriller.domain.Modification;

public class SZZCommit {

	private String msg;
	private String hash;
	private String diff;
	private int bugs;
	private boolean bugfix;
	
	public SZZCommit() {
		
	}
	
	public SZZCommit(String hash) {
		this.hash = hash;
	}
	
	public SZZCommit(Commit commit) {
		this.hash = commit.getHash();
		this.msg = commit.getHash();
	}
	
	public SZZCommit(Commit commit, Modification modification) {
		this.msg = commit.getMsg();
		this.hash = commit.getHash();

		this.diff = modification.getDiff();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getDiff() {
		return diff;
	}

	public void setDiff(String diff) {
		this.diff = diff;
	}
	
	public void setBugs(int bugs) {
		this.bugs = bugs;
	}
	
	public int getBugs() {
		return this.bugs;
	}
	
	public void increaseBugs(int i) {
		this.bugs += i;
	}
	
	public boolean isBugfix() {
		return bugfix;
	}

	public void setBugfix(boolean bugfix) {
		this.bugfix = bugfix;
	}

	public String toString() {
		return "[SZZCommit] Hash: "+hash+" Msg: "+msg+" Bugs: " + bugs;
	}
}
