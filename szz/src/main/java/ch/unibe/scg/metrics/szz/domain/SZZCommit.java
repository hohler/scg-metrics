package ch.unibe.scg.metrics.szz.domain;

import org.repodriller.domain.Commit;
import org.repodriller.domain.Modification;

public class SZZCommit {

	private String msg;
	private String hash;
	private String diff;
	
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
	
}
