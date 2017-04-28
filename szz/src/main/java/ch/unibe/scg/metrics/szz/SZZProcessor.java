package ch.unibe.scg.metrics.szz;

import org.repodriller.domain.Commit;
import org.repodriller.domain.Modification;
import org.repodriller.domain.ModificationType;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

import ch.unibe.scg.metrics.szz.domain.SZZBugRepository;
import ch.unibe.scg.metrics.szz.domain.SZZCommit;
import ch.unibe.scg.metrics.szz.domain.SZZFile;
import ch.unibe.scg.metrics.szz.domain.SZZRepository;


public class SZZProcessor implements CommitVisitor {

	// private static final int MAX_MODIFICATIONS = 10;
	
	private SZZRepository repository;
	private SZZBugRepository bugRepository;
	
	public SZZProcessor(SZZRepository repository) {
		this.repository = repository;	}
	
	public SZZProcessor(SZZRepository repository, SZZBugRepository bugRepository) {
		this.repository = repository;
		this.bugRepository = bugRepository;
	}
	
	public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
		//if(commit.getModifications().size() > MAX_MODIFICATIONS) return; // to much modifications for a bugfix commit
		
		for(Modification modification : commit.getModifications()) {
			if(!modification.fileNameEndsWith(".java")) continue;
			if(modification.getType() == ModificationType.RENAME) {
				repository.rename(modification);
			}
			
			SZZFile file = repository.saveOrGet(modification);
			SZZCommit szzC = file.saveOrGetCommit(commit, modification);
			
			if(modification.getType() == ModificationType.DELETE) {
				szzC.setDeleted(true);
			}
			
			// check if bug fix commit
			boolean bugfix = false;
			if(bugRepository == null) {
				String msg = commit.getMsg();	
				if(msg.contains("fix") && !msg.contains("postfix") && !msg.contains("prefix")) bugfix = true;
				else if(msg.contains("bug")) bugfix = true;
			} else {
				if(bugRepository.isBugfixCommit(commit.getHash())) bugfix = true;
			}

			
			szzC.setBugfix(bugfix);
		}
	}

	public String name() {
		return "szz-processor";
	}	
}
