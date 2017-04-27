package ch.unibe.scg.metrics.sourcemetrics;

import org.repodriller.domain.Commit;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

import com.github.mauricioaniche.ck.CK;
import com.github.mauricioaniche.ck.CKNumber;
import com.github.mauricioaniche.ck.CKReport;

import ch.unibe.scg.metrics.sourcemetrics.domain.SMCommit;
import ch.unibe.scg.metrics.sourcemetrics.domain.SMFile;
import ch.unibe.scg.metrics.sourcemetrics.domain.SMRepository;


public class SMProcessor implements CommitVisitor {

	private SMRepository repository;
	
	public SMProcessor(SMRepository repository) {
		this.repository = repository;
	}
	
	public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
		
		try {
			repo.getScm().checkout(commit.getHash());
			String repoPath = repo.getPath() + "\\";
			
			CKReport report = new CK().calculate(repo.getPath());
			
			SMCommit c = new SMCommit(commit);
			repository.addCommit(c);
			
			for(CKNumber result : report.all()) {
				SMFile f = new SMFile(result);
				//System.out.println(repoPath);
				//System.out.println(result.getFile());
				//System.out.println(result.getFile().replace(repoPath, "").replace("\\", "/"));
				f.setFile(result.getFile().replace(repoPath, "").replace("\\", "/"));
				c.addFile(f);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			repo.getScm().reset();
		}
	}

	public String name() {
		return "sm-processor";
	}	
}
