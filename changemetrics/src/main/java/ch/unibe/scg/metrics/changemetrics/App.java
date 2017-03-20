package ch.unibe.scg.metrics.changemetrics;

import java.nio.file.Paths;

import ch.unibe.scg.metrics.changemetrics.domain.CMRepository;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Testing scg-metrics.changemetrics" );
        
        String path = "C:\\eclipse\\target\\repositories\\flume";
        ChangeMetrics cm = new ChangeMetrics(Paths.get(path));
        CMRepository repo = cm.analyze();
       
        ChangeMetricsExporter exporter = new ChangeMetricsExporter(repo);
        exporter.toCSV();
    }
}
