# sourcemetrics

Miner for source code metrics of Java projects.

Structure based on https://github.com/mauricioaniche/change-metrics

## Source metrics

- *CBO (Coupling between objects)*: Counts the number of dependencies a class has.
The tools checks for any type used in the entire class (field declaration, method
return types, variable declarations, etc). It ignores dependencies to Java itself
(e.g. java.lang.String).

- *DIT (Depth Inheritance Tree)*: It counts the number of "fathers" a class has.
All classes have DIT at least 1 (everyone inherits java.lang.Object).
In order to make it happen, classes must exist in the project (i.e. if a class
depends upon X which relies in a jar/dependency file, and X depends upon other
classes, DIT is counted as 2). 

- *NOC (Number of Children)*: Counts the number of children a class has.

- *NOF (Number of fields)*: Counts the number of fields in a class, no matter
its modifiers.

- *NOPF (Number of public fields)*: Counts only the public fields.

- *NOSF*: Counts only the static fields.

- *NOM (Number of methods)*: Counts the number of methods, no matter its
modifiers.

- *NOPM (Number of public methods)*: Counts only the public methods.

- *NOSM (Number of static methods):* Counts only the static methods.

- *NOSI (Number of static invocations)*: Counts the number of invocations
to static methods. It can only count the ones that can be resolved by the
JDT.


- *RFC (Response for a Class)*: Counts the number of unique method
invocations in a class. As invocations are resolved via static analysis,
this implementation fails when a method has overloads with same number of parameters,
but different types.

- *WMC (Weight Method Class)* or *McCabe's complexity*. It counts the number
of branch instructions in a class.

- *LOC (Lines of code)*: It counts the lines of count, ignoring
empty lines.

- *LCOM (Lack of Cohesion of Methods)*: Calculates LCOM metric. This is the very first
version of metric, which is not reliable. LCOM-HS can be better (hopefully, you will
send us a pull request). 

- *NOCB (Number of Catch Blocks)*: Calculates number of catch blocks

- *NONC (Number of Null Checks)*: Calculates number of null checks

- *NONA (Number of Null Assignments*: Calculates number of null assignments

- *NOMWMOP (Number of Methods with more than one Parameter)*: Calculates the number of methods with more than one parameter

## Functions

**App.java** is a playground.

**SourceMetrics.java**

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

## License

This software is licensed under the Apache 2.0 License.

## How to build

	mvn install