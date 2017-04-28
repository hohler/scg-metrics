package ch.unibe.scg.metrics.szz.domain;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.repodriller.domain.Commit;
import org.repodriller.domain.Modification;

public class SZZFile {

	String file;	
	private Map<String, SZZCommit> commits;
	
	// private Logger logger = Logger.getLogger(CMFile.class);

	public SZZFile(String file) {
		this.file = file;
		this.commits = new LinkedHashMap<>();
	}
	
	public String getFile() {
		return file;
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
	
	public SZZCommit getCommit(String hash) {
		return commits.get(hash);
	}
	
	public Collection<SZZCommit> getCommits() {
		return commits.values();
	}
	
	public String toString() {
		return "SZZFile ["+file+", commits: "+ commits.size();
	}
}
