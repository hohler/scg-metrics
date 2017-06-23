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
        String path = "C:\\Users\\Andi\\Documents\\scg-metrics\\szz\\test-repo\\AcmeStore-master";
        //String path = "C:\\eclipse\\target\\repositories\\defects4j-lang";
        
        SZZ szz = new SZZ(Paths.get(path));
        
        /*szz.setRange("188c3104ab6030c40d652595a2274527a4ad4105", "73d87444013a656f763feb38ce20c43670dc6230");
        szz.setEveryNthCommit(10);
        szz.generateCommitList();*/
        
        //szz.setThreads(40);
        
        SZZBugRepository bugRepo = new SZZBugRepository();
        
        /*String[] commits = {
	        "96a4c30f29e1e66f9a5351ec1130eda6789ea7c9",
	        //"a6726ddd15cd048cec1765500675e2aa9a5432d2",
	        "b2928f282707e5af03a91ff7cc237496223799ee",
	        "517426d8aaeddec3bace226aaf6ff8a50702c61e"
	        //"34e52bafc4c91abf45b75f8c688058e23f956740"
        };*/
        
        //bugRepo.setBugCommits(new HashSet<String>(Arrays.asList(commits)));
        
        //szz.setBugRepository(bugRepo);
        
        String[] commits = {
        		"5de615507d5ab9909c6e418376b87502f0688eb9",
        		"5acbc10a0c2700b5555963b5790bd8b440b0bcf4",
        		"7652bc0fcc45850b9155b37c012e339802728a82",
        		"267c6ebb927e12c10aa5d777acc0409b19210ec3",
        		"6de5173cf6a91b638381669c013121b59ac94255",
        		"131d40ce7961785bcf17e1480af7558e87374e41",
        		"463091a2257fb4e3ab7b12a41cea84053d3cb709",
        		"854e27c7e2edbf4886dc2541743e8c9119392b5c",
        		"bea14e6c2d0263932f626972aedb67287b4516e1",
        		"00a6ce533e96e956b05e1b3b1249a1b1d74b25ed",
        		"a100580cbd16bace6aa3bafc95aa1ea495632818",
        		"088382ba8eb73b9c6dcff3f9ddb8276c866aa5fa",
        		"5f0f919e3945c1251b8d562c4d510ca1523a6ce0",
        		"abbaf768592f5b2bcb4b61b7ec3c4f5890831049"
        };
        
        //szz.excludeCommits(Arrays.asList(commits));
        
        szz.generateCommitList();
        SZZRepository repo = szz.analyze(szz.getCommitList());
        
        /*for(SZZFile f : repo.all()) {
        	for(SZZCommit c : f.getCommits()) {
        		System.out.println(c);	
        	}
        }*/
        
        //logger.debug(repo.all());
       
    }
}
