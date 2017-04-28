package ch.unibe.scg.metrics.sourcemetrics;

import java.nio.file.Paths;

import org.apache.log4j.Logger;

import ch.unibe.scg.metrics.sourcemetrics.domain.SMFile;
import ch.unibe.scg.metrics.sourcemetrics.domain.SMCommit;
import ch.unibe.scg.metrics.sourcemetrics.domain.SMRepository;


public class App 
{
static Logger logger = Logger.getLogger(App.class);
	
    public static void main( String[] args )
    {
    	logger.debug("test");
        System.out.println( "Testing scg-metrics.sourcemetrics" );
        
        String path = "C:\\eclipse\\target\\repositories\\flume";
        //String path = "src/main/resources/szz_testrepo";
        SourceMetrics sm = new SourceMetrics(Paths.get(path));
        
       
        sm.setEveryNthCommit(0); // 0: only newest commit
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
        
        //logger.debug(repo.all());
       
    }
}
