# scg-changemetrics

This project is based on change-metrics by Mauricio Aniche [https://github.com/mauricioaniche/change-metrics](https://github.com/mauricioaniche/change-metrics)

It's a miner for change metrics of a Java project.

## Change metrics

```
"file": the full file path,
"revisions": quantity of commits,
"refactorings": quantity of refactorings that occured (if said in commit msg),
"bugfixes": quantity of bugs that file has had (if said in commit msg),
"authors": quantity of different authors,
"locAdded": total of LOC added,
"locRemoved": total of LOC removed,
"maxLocAdded": maximum number of LOC added,
"maxLocRemoved": maximum number of LOC removed,
"avgLocAdded": average of LOC added,
"avgLocRemoved": average of LOC removed,
"codeChurn": sum of all LOC added and removed,
"maxChangeset": max number of files committed together with this file,
"avgChangeset": average number of files committed together,
"firstCommit": date of the first commit,
"lastCommit": date of the last commit,
"weeks": difference in weeks from the last commit - first commit.
```

## Functions

**App.java** is a playground.

**ChangeMetrics.java**

`void setFirstRef(String commitRef)`

`void setRange(Date start, Date end)`

`void setRange(String firstRef, String lastRef)`

These three methods define the start/end of the examined commits.

`void setEveryNthCommit(int n)`
Only every nth commit in the repository will be put in the list by *generateCommitList

`void setThreads(int n)`
How many threads should be used to mine

`void setBugRepository(CMBugRepository bugRepo)`
Set the repository containing bug fix commits. 

`void generateCommitList()`
This generates based on the configuration (range, etc.) the list with commits that meet the conditions.

`Map<String, CommitRange> generateCommitListWithWeeks(int weeksBack)`
for each commit get a range that is from the commit to x weeks back

`CMRepository analyze(List<String> commits)`
This function will start the process and takes a list of commits to examine.

`CMRepository analyze()`
This function will start the process and generates the commit list by itself with the given configuration.


### CSV Exporter

**ChangeMetricsExporter**

Exports the mined change code metrics to a CSV file.

## Domain

**CMBugRepository**

A repository containing commit refs to bug fix commits

**CMRepository**

This holds all the mined information, repository for files

**CMFile**

One file in the repository, containing the change metrics

## Usage

	String path = "C:\eclipse\target\repositories\flume";
	ChangeMetrics cm = new ChangeMetrics(Paths.get(path));
	cm.setThreads(40);
        
        
    CMBugRepository bugRepo = new CMBugRepository();
    
    String[] commits = {
        "791f443fae173054cf29ac52fee8e9cf7fe70dc7",
        "2e1b7c23c4964b8860b876cc5c8c3642c4d74ab9",
        "ba0b2685b96747a60a3e318b1e2fc2c9c02bcea4",
        "fa1ee05af38bcf08ed18ff36d4284e68836a9054",
        "eb7eab6593e241d9f67308298871f5586734b657",
        "a085eb653768cbde4f89f9182c31ebc2074bca72",
        "d20c94ca61103632de2cd941a716dbd4d9c6d719",
        "aad551d3f45687193ef3323ba6a4584c15c6ad53"
    };
    
    bugRepo.setBugCommits(new HashSet<String>(Arrays.asList(commits)));
    
    cm.setBugRepository(bugRepo);
    
    Map<String, CommitRange> list = cm.generateCommitListWithWeeks(52);
    
    for(Entry<String, CommitRange> e : list.entrySet()) {
    	String ref = e.getKey();
    	cm.setRange(e.getValue());
    	CMRepository repo = cm.analyze();
    	ChangeMetricsExporter exporter = new ChangeMetricsExporter(repo);
    	String outputPath = Paths.get("").toAbsolutePath().toString();
    	exporter.toCSV(outputPath + "/testing/export_"+ref+".csv");
    }

## Major dependencies

* org.repodriller.repodriller (1.2.1)

## License

This software is licensed under the Apache 2.0 License.

## How to build

	mvn install