package ch.unibe.scg.metrics.szz;

import java.nio.file.Paths;

import org.apache.log4j.Logger;

import ch.unibe.scg.metrics.szz.domain.SZZCommit;
import ch.unibe.scg.metrics.szz.domain.SZZFile;
import ch.unibe.scg.metrics.szz.domain.SZZRepository;


public class App 
{
static Logger logger = Logger.getLogger(App.class);
	
    public static void main( String[] args )
    {
    	logger.debug("test");
        System.out.println( "Testing scg-metrics.szz" );
        
        //String path = "C:\\eclipse\\target\\repositories\\flume";
        String path = "src/main/resources/szz_testrepo";
        SZZ szz = new SZZ(Paths.get(path));
        
        /*szz.setRange("188c3104ab6030c40d652595a2274527a4ad4105", "73d87444013a656f763feb38ce20c43670dc6230");
        szz.setEveryNthCommit(10);
        szz.generateCommitList();*/
        
        //String firstRef = cm.getFirstRef();
        
        //logger.debug("first commit (should be 188c3104ab6030c40d652595a2274527a4ad4105): "+cm.getFirstRef());
        
        
        
        //List<String> commits = cm.getCommitList();
        
        /*for(String ref : commits) {
        	cm.setRange(firstRef, ref);
        	CMRepository repo = cm.analyze();
        	
        	logger.debug(repo.all());
        	
        	 ChangeMetricsExporter exporter = new ChangeMetricsExporter(repo);
        	 String outputPath = Paths.get("").toAbsolutePath().toString();
             exporter.toCSV(outputPath + "/export_"+ref+".csv");
        }*/
        
        SZZRepository repo = szz.analyze();
        
        for(SZZFile f : repo.all()) {
        	for(SZZCommit c : f.getCommits()) {
        		System.out.println(c);	
        	}
        }
        
        //logger.debug(repo.all());
       
    }
}
