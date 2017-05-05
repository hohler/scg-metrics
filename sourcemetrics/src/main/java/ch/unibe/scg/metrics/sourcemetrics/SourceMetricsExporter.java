package ch.unibe.scg.metrics.sourcemetrics;

import java.nio.file.Paths;

import ch.unibe.scg.metrics.sourcemetrics.domain.SMCommit;
import ch.unibe.scg.metrics.sourcemetrics.domain.SMFile;

public class SourceMetricsExporter {

	private SMCommit commit;
	private CSVFile csv;
	
	public SourceMetricsExporter(SMCommit commit) {
		this.commit = commit;
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
		
		for(SMFile info : commit.getFiles().values()) {
			csv.write(
					info.getFile().replace(",", ""),
					info.getCbo(),
					info.getDit(),
					info.getNoc(),
					info.getNof(),
					info.getNopf(),
					info.getNosf(),
					info.getNom(),
					info.getNopm(),
					info.getNosm(),
					info.getNosi(),
					info.getRfc(),
					info.getWmc(),
					info.getLoc(),
					info.getLcom(),
					info.getNocb(),
					info.getNonc(),
					info.getNona(),
					info.getNomwmop()
			);
			
		}
	}
	
	private void printCSVHead(CSVFile csv) {
		csv.write(
				"file",
				"cbo",
				"dit",
				"noc",
				"nof",
				"nopf",
				"nosf",
				"nom",
				"nopm",
				"nosm",
				"nosi",
				"rfc",
				"wmc",
				"loc",
				"lcom",
				"nocb",
				"nonc",
				"nona",
				"nomwmop"
		);
	}
}
