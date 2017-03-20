package ch.unibe.scg.metrics.changemetrics;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;

import ch.unibe.scg.metrics.changemetrics.domain.CMFile;
import ch.unibe.scg.metrics.changemetrics.domain.CMRepository;

public class ChangeMetricsExporter {

	private CMRepository repository;
	private CSVFile csv;
	
	public ChangeMetricsExporter(CMRepository repository) {
		this.repository = repository;
	}
	
	public void toCSV() {
		String outputPath = Paths.get("").toAbsolutePath().toString();
		outputPath += (outputPath.endsWith("/") ? "" : "/") + "export.csv";
		csv = new CSVFile(outputPath);
		processCSV();
	}
	
	public void toCSV(String path) {
		csv = new CSVFile(path);
		processCSV();
	}
	
	private void processCSV() {
		this.csv.setSeparator(CSVFile.SeparatorType.SEMICOLON);
		printCSVHead(csv);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		for(CMFile info : repository.all()) {
			csv.write(
					info.getFile().replace(",", ""),
					info.getRevisions(),
					info.getRefactorings(),
					info.getBugfixes(),
					info.getUniqueAuthorsQuantity(),
					info.getLocAdded(),
					info.getLocRemoved(),
					info.getMaxLocAdded(),
					info.getMaxLocRemoved(),
					info.getAvgLocAdded(),
					info.getAvgLocRemoved(),
					info.getCodeChurn(),
					info.getMaxChangeset(),
					info.getAvgChangeset(),
					sdf.format(info.getFirstCommit().getTime()),
					sdf.format(info.getLastCommit().getTime()),
					info.getWeeks()
			);
			
		}
	}
	
	private void printCSVHead(CSVFile csv) {
		csv.write(
				"file",
				"revisions",
				"refactorings",
				"bugfixes",
				"authors",
				"locAdded",
				"locRemoved",
				"maxLocAdded",
				"maxLocRemoved",
				"avgLocAdded",
				"avgLogRemoved",
				"codeChurn",
				"maxChangeset",
				"avgChangeset",
				"firstCommit",
				"lastCommit",
				"weeks"
		);
	}
}
