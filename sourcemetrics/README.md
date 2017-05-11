# sourcemetrics

Miner for source code metrics of Java projects.

Structure based on https://github.com/mauricioaniche/change-metrics

## Functions

**App.java** is a playground.

**SourceMetrics.java**

`setFirstRef(String commitRef)`

`setRange(Date start, Date end)`

`setRange(String firstRef, String lastRef)`

These three methods define the start/end of the examined commits.

`setEveryNthCommit(int n)`
Only every nth commit in the repository will be put in the list by *generateCommitList

`generateCommitList()`
This generates based on the configuration (range, etc.) the list with commits that meet the conditions.

`analyze(List<String> commits)`
This function will start the process and takes a list of commits to examine.

### CSV Exporter

**SourceMetricsExporter**

Exports the mined source code metrics to a CSV file.

## Domain

**SMRepository**

This holds all the mined information, repository for commits

**SMCommit**

One commit in the repository, contains all source file of that specific revision.

**SMFile**

One file in a revision, containing the metrics

## Usage

	String path = "C:\eclipse\target\repositories\flume";
	SourceMetrics sm = new SourceMetrics(Paths.get(path));
	sm.generateCommitList();
        
    SMRepository repo = sm.analyze(sm.getCommitList());
    
    for(SMCommit c : repo.all()) {
    	SourceMetricsExporter exporter = new SourceMetricsExporter(c);
		String outputPath = Paths.get("").toAbsolutePath().toString();
		exporter.toCSV(outputPath + "/testing/export_"+c.getHash()+".csv");
		for(SMFile f : c.getFiles().values()) {
    		System.out.println(f);
    	}
    }

## Major dependencies

* org.repodriller.repodriller (1.2.1)
* modified com.github.mauricioaniche.ck (https://github.com/papagei9/ck), found in "local_maven_repository" directory.