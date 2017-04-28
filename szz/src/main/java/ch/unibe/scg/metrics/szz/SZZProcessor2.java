package ch.unibe.scg.metrics.szz;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.repodriller.domain.ChangeSet;
import org.repodriller.domain.Commit;
import org.repodriller.domain.Modification;
import org.repodriller.domain.ModificationType;
import org.repodriller.filter.range.CommitRange;
import org.repodriller.filter.range.Commits;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.BlamedLine;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

import ch.unibe.scg.metrics.szz.domain.SZZCommit;
import ch.unibe.scg.metrics.szz.domain.SZZFile;
import ch.unibe.scg.metrics.szz.domain.SZZRepository;


public class SZZProcessor2 implements CommitVisitor {

	private static final int MAX_MODIFICATIONS = 10;
	
	private SZZRepository repository;
	
	private Logger logger = Logger.getLogger(SZZProcessor2.class);
	
	public SZZProcessor2(SZZRepository repository) {
		this.repository = repository;
	}
	
	public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
		for(Modification modification : commit.getModifications()) {
			if(!modification.fileNameEndsWith(".java")) continue;
			if(modification.getType() == ModificationType.RENAME) {
				repository.rename(modification);
			}

			SZZFile file = repository.getFile(modification);
			if(file == null) continue;

			SZZCommit szzC = file.getCommit(commit.getHash());
			if(szzC == null) continue;
			
			if(modification.getType() == ModificationType.DELETE) {
				szzC.setDeleted(true);
			}
			
			if(!szzC.isBugfix()) continue;
			
			
			
			if(commit.getModifications().size() > MAX_MODIFICATIONS) continue; // to much modifications for a bugfix commit
			
			// Get lines that have been changed here
			
			
			List<BlamedLine> blames = repo.getScm().blame(modification.getFileName(), commit.getHash(), false);
			Iterator<BlamedLine> it = blames.iterator();
			while(it.hasNext()) {
				BlamedLine l = it.next();
				if(!l.getCommit().equals(commit.getHash())) {
					it.remove();
				}
				//if(l.getLine().matches("^( *)([*]+|[//]|[*/])+(.*)$")) it.remove();
				//if(l.getLine().length() == 0) it.remove();
				//if(l.getLine().matches("^(import |package )(.*)$")) it.remove();
			}
			
			
			try {
				List<BlamedLine> blames2 = repo.getScm().blame(modification.getFileName(), commit.getHash(), true);
				
				Iterator<BlamedLine> it2 = blames2.iterator();
				while(it2.hasNext()) {
					BlamedLine l = it2.next();
					boolean exists = blames.stream().anyMatch(e -> e.getLineNumber() == l.getLineNumber());
					if(!exists) it2.remove();
				}
				
				it2 = blames2.iterator();
				List<String> alreadyIncreasedCommits = new ArrayList<>();
				while(it2.hasNext()) {
					BlamedLine l = it2.next();
					
					CommitRange cr = Commits.range(l.getCommit(), commit.getHash());
					List<ChangeSet> sets = cr.get(repo.getScm());
					
					for(ChangeSet s : sets) {
						if(s.getId().equals(commit.getHash())) continue; // introduced in current commit
						if(alreadyIncreasedCommits.contains(s.getId())) continue; // commit was already increased
						SZZCommit c = file.getCommit(s.getId());
						if(c == null) continue; // file is not present in the changeset s
						c.increaseBugs(1);
						alreadyIncreasedCommits.add(c.getHash());
					}	
				}
				logger.debug(blames2);
			} catch(RuntimeException e) {}
		}
	}

	public String name() {
		return "szz-processor";
	}	
}
