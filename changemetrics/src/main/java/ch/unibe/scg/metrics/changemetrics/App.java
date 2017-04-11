package ch.unibe.scg.metrics.changemetrics;

import java.nio.file.Paths;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.repodriller.filter.range.CommitRange;

import ch.unibe.scg.metrics.changemetrics.domain.CMRepository;

public class App 
{
	static Logger logger = Logger.getLogger(App.class);
	
    public static void main( String[] args )
    {
    	logger.debug("test");
        System.out.println( "Testing scg-metrics.changemetrics" );
        
        String path = "C:\\eclipse\\target\\repositories\\flume";
        ChangeMetrics cm = new ChangeMetrics(Paths.get(path));
        
        //cm.setRange("188c3104ab6030c40d652595a2274527a4ad4105", "73d87444013a656f763feb38ce20c43670dc6230");
        
        // cm.setWeeksBack(12);
        cm.setEveryNthCommit(20);
        cm.setThreads(40);
        
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
