package ch.unibe.scg.metrics.changemetrics;

import org.repodriller.domain.Commit;
import org.repodriller.domain.Modification;
import org.repodriller.domain.ModificationType;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

import ch.unibe.scg.metrics.changemetrics.domain.CMFile;
import ch.unibe.scg.metrics.changemetrics.domain.CMRepository;

public class ChangeMetricsProcessor implements CommitVisitor {

	private CMRepository repository;
	public ChangeMetricsProcessor(CMRepository repository) {
		this.repository = repository;
	}
	
	public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
		for(Modification modification : commit.getModifications()) {
			if(!modification.fileNameEndsWith(".java")) continue;
			if(modification.getType() == ModificationType.RENAME) {
				repository.rename(modification);
			}

			CMFile file = repository.saveOrGet(modification);
			file.update(commit, modification);
		}
	}

	public String name() {
		return "change-metrics-processor";
	}

}
