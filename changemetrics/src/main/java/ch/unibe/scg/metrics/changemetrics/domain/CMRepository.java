package ch.unibe.scg.metrics.changemetrics.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.repodriller.domain.Modification;

public class CMRepository {

	private Map<String, CMFile> files;
	
	public CMRepository() {
		files = new HashMap<>();
	}
	
	
	public CMFile saveOrGet(Modification m) {
		
		String fileName = m.getNewPath();
		
		if(!files.containsKey(fileName)) {
			CMFile f = new CMFile(fileName);
			files.put(fileName, f);
			return f;
		}
		
		return files.get(fileName);
	}

	public void rename(Modification m) {
		String oldPath = m.getOldPath();
		String newPath = m.getNewPath();
		
		CMFile file = files.remove(oldPath);
		
		if(file != null) {
			file.rename(newPath);
			files.put(newPath, file);
		}
	}

	public Collection<CMFile> all() {
		return files.values();
	}

}
