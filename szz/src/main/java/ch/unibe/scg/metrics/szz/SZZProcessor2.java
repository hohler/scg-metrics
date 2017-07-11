package ch.unibe.scg.metrics.szz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.wickedsource.diffparser.api.UnifiedDiffParser;
import org.wickedsource.diffparser.api.model.Diff;
import org.wickedsource.diffparser.api.model.Hunk;
import org.wickedsource.diffparser.api.model.Line;

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
			
			Set<String> buggedCommits = new HashSet<>();
			// perhaps use https://github.com/stkent/githubdiffparser has some bugfixes!
			UnifiedDiffParser parser = new UnifiedDiffParser();
			List<Diff> diffs = parser.parse((modification.getDiff()+"\n").getBytes());
			
			for(Diff diff : diffs) {
				
				Map<Integer, Line> removed = new HashMap<>();
				Map<Integer, Line> added = new HashMap<>();
				Map<Integer, Line> neutral = new HashMap<>();
				
				Map<Hunk, List<Line>> oldLines = new HashMap<>(); // used for calculating line numbers
				Map<Hunk, List<Line>> newLines = new HashMap<>();
				
				for(Hunk hunk : diff.getHunks()) {
					oldLines.put(hunk, new ArrayList<>());
					newLines.put(hunk, new ArrayList<>());
					for(Line line : hunk.getLines()) {
						if(line.getLineType() == Line.LineType.FROM || line.getLineType() == Line.LineType.NEUTRAL) {
							oldLines.get(hunk).add(line);
						}
						if(line.getLineType() == Line.LineType.TO || line.getLineType() == Line.LineType.NEUTRAL) {
							newLines.get(hunk).add(line);
						}
					}					
				}
				
				for(Hunk hunk : diff.getHunks()) {
					
					for(Line line : hunk.getLines()) {
						if(line.getContent().trim().length() == 0) continue;
						//if(line.getContent().trim().matches("^(import\\s.*|\\}|\\{)$")) continue;
						if(line.getContent().trim().matches("^(import|package)\\s.*$")) continue;
						//if(line.getContent().trim().startsWith("import ")) continue;
						
						if(line.getLineType() == Line.LineType.FROM) {
							int fromLineNumber = oldLines.get(hunk).indexOf(line) + hunk.getFromFileRange().getLineStart();
							removed.put(fromLineNumber, line);
						} else
						if(line.getLineType() == Line.LineType.TO) {
							int toLineNumber = newLines.get(hunk).indexOf(line) + hunk.getToFileRange().getLineStart();
							added.put(toLineNumber, line);
						} else
						if(line.getLineType() == Line.LineType.NEUTRAL) {
							int lineNumber = oldLines.get(hunk).indexOf(line) + hunk.getFromFileRange().getLineStart();
							neutral.put(lineNumber, line);
						}
					}
				}
				
				if(removed.size() == 0 && added.size() == 0) continue;

				String filePathToBlame = modification.getNewPath()!=null && !modification.getNewPath().equals("/dev/null") ? modification.getNewPath() : modification.getOldPath();
				
				// if no line has been removed
				if(removed.size() == 0) {
					// blame prior to this commit and check line numbers with neutral lines to find the bug introducing commit
					try {
						List<BlamedLine> blames = repo.getScm().blame(filePathToBlame, commit.getHash(), true);
						Iterator<BlamedLine> it = blames.iterator();
						while(it.hasNext()) {
							BlamedLine l = it.next();
							
							Line neutralLine = neutral.get(l.getLineNumber());
							if(neutralLine != null && !neutralLine.getContent().trim().equals("}")) {
								buggedCommits.add(l.getCommit());
								break;
							}
						}
					} catch(RuntimeException e) {
						System.err.println("File could not be blamed: " + commit.getHash() + " -" + filePathToBlame);
						//e.printStackTrace();
					}
				} else {
				// if  lines have been removed (and added)
					// blame prior to this commit and check line numbers
					try {
						List<BlamedLine> blames = repo.getScm().blame(filePathToBlame, commit.getHash(), true);
						Iterator<BlamedLine> it = blames.iterator();
						while(it.hasNext()) {
							BlamedLine l = it.next();
							
							Line removedLine = removed.get(l.getLineNumber());
							if(removedLine != null) {
								buggedCommits.add(l.getCommit());
							}
						}
					} catch(RuntimeException e) {
						System.err.println("File could not be blamed: " + commit.getHash() + " -" + filePathToBlame);
						// e.printStackTrace();
					}
				}
				
			}
			
			List<String> alreadyIncreasedCommits = new ArrayList<>();
						
			for(String hash : buggedCommits) {	
				CommitRange cr = Commits.range(hash, commit.getHash());
				List<ChangeSet> sets = cr.get(repo.getScm());
				
				for(ChangeSet s : sets) {
					if(s.getId().equals(commit.getHash())) continue; // introduced in current commit
					if(alreadyIncreasedCommits.contains(s.getId())) continue; // commit was already increased
					SZZCommit c = file.getCommit(s.getId());
					if(c == null) {
						// the file was not present in that commit, so create a new one for that file
						c = new SZZCommit();
						c.setHash(s.getId());
						file.addCommit(c);
					}
					c.increaseBugs(1);
					alreadyIncreasedCommits.add(c.getHash());
				}
			}
		}
		logger.debug("2: "+commit.getHash());
	}

	public String name() {
		return "szz-processor";
	}	
}
