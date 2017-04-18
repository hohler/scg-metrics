package ch.unibe.scg.metrics.szz.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.repodriller.domain.Commit;
import org.repodriller.domain.Modification;

public class SZZRepository {

	private Map<String, SZZFile> files;
	private Map<String, SZZCommit> commits;
	
	public SZZRepository() {
		files = new HashMap<>();
		commits = new HashMap<>();
	}
	
	
	public SZZFile saveOrGet(Modification m) {
		
		String fileName = m.getNewPath();
		
		if(!files.containsKey(fileName)) {
			files.put(fileName, new SZZFile(fileName));
		}
		
		return files.get(fileName);
	}
	
	public SZZFile getFile(Modification m) {
		if(!files.containsKey(m.getNewPath())) return null;
		return files.get(m.getNewPath());
	}
	
	public SZZCommit saveOrGetCommit(Commit c, Modification m) {
			String hash = c.getHash();
		
		if(!commits.containsKey(hash)) {
			commits.put(hash, new SZZCommit(c, m));
		}
		
		return commits.get(hash);
	}
	
	public SZZCommit getCommit(String hash) {
		return commits.get(hash);
	}

	public void rename(Modification m) {
		String oldPath = m.getOldPath();
		String newPath = m.getNewPath();
		
		SZZFile file = files.remove(oldPath);
		
		if(file != null) {
			file.rename(newPath);
			files.put(newPath, file);
		}
	}

	public Collection<SZZFile> all() {
		return files.values();
	}

}
