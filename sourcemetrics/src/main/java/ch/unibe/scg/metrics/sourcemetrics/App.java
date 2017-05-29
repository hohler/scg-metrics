package ch.unibe.scg.metrics.sourcemetrics;

import java.nio.file.Paths;
import java.util.ArrayList;

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
        
        String path = "C:\\eclipse\\target\\repositories\\commons-lang";
        //String path = "src/main/resources/szz_testrepo";
        SourceMetrics sm = new SourceMetrics(Paths.get(path));
        
       
        sm.setEveryNthCommit(200); // 0: only newest commit
        sm.generateCommitList();
        
        System.out.println(sm.getCommitList());
        
        //System.exit(0);
        
        SMRepository repo = sm.analyze(sm.getCommitList());
        
        ArrayList<String> refs = new ArrayList<>();
        
        for(SMCommit c : repo.all()) {
        	refs.add(c.getHash());
        	SourceMetricsExporter exporter = new SourceMetricsExporter(c);
       	 	String outputPath = Paths.get("").toAbsolutePath().toString();
            exporter.toCSV(outputPath + "/testing/export_"+c.getHash()+".csv");
        	for(SMFile f : c.getFiles().values()) {
        		System.out.println(f);
        	}
        }
        
        System.out.println(refs);
        //logger.debug(repo.all());
       
    }
}
