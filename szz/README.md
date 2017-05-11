# szz

SZZ algorithm for Java projects.

This algorithm will calculate the possible bugs in each revision.

Structure based on https://github.com/mauricioaniche/change-metrics

## Functions

**App.java** is a playground.

**SZZ.java**

`void setFirstRef(String commitRef)`

`void setRange(Date start, Date end)`

`void setRange(String firstRef, String lastRef)`

These three methods define the start/end of the examined commits.

`void setEveryNthCommit(int n)`
Only every nth commit in the repository will be put in the list by *generateCommitList

`void generateCommitList()`
This generates based on the configuration (range, etc.) the list with commits that meet the conditions.

`SMRepository analyze(List<String> commits)`
This function will start the process and takes a list of commits to examine.

`CMRepository analyze()`
This function will start the process and generates the commit list by itself with the given configuration.

### CSV Exporter

**SourceMetricsExporter**

Exports the mined source code metrics to a CSV file.

## Domain

**SZZBugRepository**

A repository containing commit refs to bug fix commits

**SZZRepository**

This holds all the mined information, repository for commits

**SZZFile**

One file in the project, containing commits where it appears

**SZZCommit**

One commit of a file, contains the bug count of a specific revision.

## Usage

	String path = "C:\eclipse\target\repositories\flume";
	SZZ szz = new SZZ(Paths.get(path));
	szz.generateCommitList();
        
	SZZRepository repo = szz.analyze();
    

## Major dependencies

* org.repodriller.repodriller (1.2.1)

## License

This software is licensed under the Apache 2.0 License.