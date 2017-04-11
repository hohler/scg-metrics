package ch.unibe.scg.metrics.szz;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.repodriller.domain.Commit;
import org.repodriller.domain.Modification;
import org.repodriller.domain.ModificationType;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.BlamedLine;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

import ch.unibe.scg.metrics.szz.domain.SZZCommit;
import ch.unibe.scg.metrics.szz.domain.SZZFile;
import ch.unibe.scg.metrics.szz.domain.SZZRepository;


public class SZZProcessor2 implements CommitVisitor {

	private SZZRepository repository;
	
	public SZZProcessor2(SZZRepository repository) {
		this.repository = repository;
	}
	
	public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
		for(Modification modification : commit.getModifications()) {
			if(!modification.fileNameEndsWith(".java")) continue;
			/*if(modification.getType() == ModificationType.RENAME) {
				repository.rename(modification);
			}*/

			SZZFile file = repository.get(modification);
			if(file == null) continue;


			
			// check if bug fix commit
			// TODO link to issue things from BiCo
			String msg = commit.getMsg();
			boolean bugfix = false;
			if(msg.contains("fix") && !msg.contains("postfix") && !msg.contains("prefix")) bugfix = true;
			else if(msg.contains("bug")) bugfix = true;
			
			if(bugfix) {
				SZZCommit szzC = new SZZCommit(commit, modification);
				
				/*
				List<BlamedLine> blames = repo.getScm().blame(modification.getFileName(), commit.getHash(), false);
				Iterator<BlamedLine> it = blames.iterator();
				while(it.hasNext()) {
					BlamedLine l = it.next();
					if(l.getLine().matches("^( *)([*]+|[//]|[*\/])+(.*)$")) it.remove();
					if(l.getLine().length() == 0) it.remove();
					if(l.getLine().matches("^(import |package )(.*)$")) it.remove();
				}
				*/
				
				file.addBugfixCommit(szzC);
			}
			
			//file.addCommit(commit, modification);
			file.update(commit, modification);
		}
	}

	public String name() {
		return "szz-processor";
	}	
}
