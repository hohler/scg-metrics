package ch.unibe.scg.metrics.sourcemetrics.domain;

import java.util.HashMap;
import java.util.Map;

import org.repodriller.domain.Commit;

public class SMCommit {

	private String msg;
	private String hash;
	private Map<String, SMFile> files;
	
	public SMCommit() {
		files = new HashMap<>();
	}
	
	public SMCommit(String hash) {
		this.hash = hash;
		files = new HashMap<>();
	}
	
	public SMCommit(Commit commit) {
		this.hash = commit.getHash();
		this.msg = commit.getHash();
		files = new HashMap<>();
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
	

	public Map<String, SMFile> getFiles() {
		return files;
	}

	public void setFiles(Map<String, SMFile> files) {
		this.files = files;
	}
	
	public void addFile(SMFile file) {
		files.put(file.getFile(), file);
	}
	
	public SMFile removeFile(SMFile file) {
		return files.remove(file.getFile());
	}

	public String toString() {
		return "[SZZCommit] Hash: "+hash+" Msg: "+msg;
	}
}
