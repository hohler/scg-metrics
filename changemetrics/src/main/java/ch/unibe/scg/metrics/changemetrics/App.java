package ch.unibe.scg.metrics.changemetrics;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.repodriller.filter.range.CommitRange;

import ch.unibe.scg.metrics.changemetrics.domain.CMBugRepository;
import ch.unibe.scg.metrics.changemetrics.domain.CMRepository;

public class App 
{
	static Logger logger = Logger.getLogger(App.class);
	
    public static void main( String[] args )
    {
    	logger.debug("test");
        System.out.println( "Testing scg-metrics.changemetrics" );
        
        String path = "C:\\eclipse\\target\\repositories\\flume";
        //String path = "src/main/resources/cm_testrepo";
        ChangeMetrics cm = new ChangeMetrics(Paths.get(path));
        
        //cm.setRange("188c3104ab6030c40d652595a2274527a4ad4105", "73d87444013a656f763feb38ce20c43670dc6230");
        
        // cm.setWeeksBack(12);
        cm.setEveryNthCommit(1);
        cm.setThreads(40);
        
        
        CMBugRepository bugRepo = new CMBugRepository();
        
        // flume
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
        // cm.generateCommitList();
        
        for(Entry<String, CommitRange> e : list.entrySet()) {
        	String ref = e.getKey();
        	
        	cm.setRange(e.getValue());
        	CMRepository repo = cm.analyze();
        	
        	//logger.debug(repo.all());
        	
        	ChangeMetricsExporter exporter = new ChangeMetricsExporter(repo);
       	 	String outputPath = Paths.get("").toAbsolutePath().toString();
            exporter.toCSV(outputPath + "/testing/export_"+ref+".csv");
        }
        
        
        
        /*for(String ref : commits) {
        	firstRef = cm.calculateFirstRefWithWeeks(ref);
        	cm.setRange(firstRef, ref);
        	CMRepository repo = cm.analyze();
        	
        	logger.debug(repo.all());
        	
        	 ChangeMetricsExporter exporter = new ChangeMetricsExporter(repo);
        	 String outputPath = Paths.get("").toAbsolutePath().toString();
             exporter.toCSV(outputPath + "/export_"+ref+".csv");
        }*/
       
    }
}
