package ch.unibe.scg.metrics.szz;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.log4j.Logger;

import ch.unibe.scg.metrics.szz.domain.SZZBugRepository;
import ch.unibe.scg.metrics.szz.domain.SZZRepository;


public class App 
{
static Logger logger = Logger.getLogger(App.class);
	
    public static void main( String[] args )
    {
    	logger.debug("test");
        System.out.println( "Testing scg-metrics.szz" );
        
        //String path = "C:\\eclipse\\target\\repositories\\flume";
        //String path = "src/main/resources/szz_testrepo";
        //String path = "C:\\Users\\Andi\\Documents\\scg-metrics\\szz\\test-repo\\AcmeStore-master";
        String path = "C:\\eclipse\\target\\repositories\\defects4j-lang";
        
        SZZ szz = new SZZ(Paths.get(path));
        
        /*szz.setRange("188c3104ab6030c40d652595a2274527a4ad4105", "73d87444013a656f763feb38ce20c43670dc6230");
        szz.setEveryNthCommit(10);
        szz.generateCommitList();*/
        
        //szz.setThreads(40);
        
        SZZBugRepository bugRepo = new SZZBugRepository();
        
        String[] commits = {
	        "96a4c30f29e1e66f9a5351ec1130eda6789ea7c9",
	        //"a6726ddd15cd048cec1765500675e2aa9a5432d2",
	        "b2928f282707e5af03a91ff7cc237496223799ee",
	        "517426d8aaeddec3bace226aaf6ff8a50702c61e"
	        //"34e52bafc4c91abf45b75f8c688058e23f956740"
        };
        
        //bugRepo.setBugCommits(new HashSet<String>(Arrays.asList(commits)));
        
        //szz.setBugRepository(bugRepo);
        
        SZZRepository repo = szz.analyze();
        
        /*for(SZZFile f : repo.all()) {
        	for(SZZCommit c : f.getCommits()) {
        		System.out.println(c);	
        	}
        }*/
        
        //logger.debug(repo.all());
       
    }
}
