package ch.unibe.scg.metrics.szz;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.unibe.scg.metrics.szz.domain.SZZCommit;
import ch.unibe.scg.metrics.szz.domain.SZZFile;
import ch.unibe.scg.metrics.szz.domain.SZZRepository;

public class MetricsTest {

	private static Path path;
	private static SZZ szz;
	private static SZZRepository repository;
	
	@BeforeClass
	public static void readPath() throws FileNotFoundException {
		String p = (MetricsTest.class.getResource("/").getPath() + "../../test-repo/AcmeStore-master").replaceFirst("/","").replace("/", "//");
		path = Paths.get(p);
		
		szz = new SZZ(path);
		
		repository = szz.analyze();
	}
	
	@Test
	public void testRepository() {
		Assert.assertEquals(13,  repository.all().size());
	}
	
	/*@Test
	public void testFiles() {
		
		for(SZZFile f : repository.getFileByName()) {
        	
        	for(SZZCommit c : f.getCommits()) {
        		//System.out.println(c);
        		
        		sz.setFile(f.getFile());
        		sz.setBugs(c.getBugs());
        		sz.setCommit(commit);
        		sz.setBugfix(c.isBugfix());
        		sz.setDeleted(c.isDeleted());
        		
        		toPersist.add(sz);
        		
        		contribution.incrementWriteCount(1);
        	}
        	
        	szzMetricService.addAll(toPersist);
        	
        	chunkContext.getStepContext().getStepExecution().incrementCommitCount();
        }
	}*/

	@Test
	public void testFile_Store() {
		SZZFile f = repository.getFileByPath("src/Store.java");
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/Store.java", f.getFile());
		
		Collection<SZZCommit> commits = f.getCommits();
		Assert.assertNotNull(commits);
		Assert.assertEquals(5, commits.size());
		
		Map<String, SZZCommit> map = new HashMap<>();
		for(SZZCommit c : commits) {
			map.put(c.getHash(), c);
		}
		
		SZZCommit c = map.get("463091a2257fb4e3ab7b12a41cea84053d3cb709");
		Assert.assertNotNull(c);
		Assert.assertEquals(1, c.getBugs());
		Assert.assertFalse(c.isBugfix());
		Assert.assertFalse(c.isDeleted());
		
		
		SZZCommit c2 = map.get("854e27c7e2edbf4886dc2541743e8c9119392b5c");
		Assert.assertNotNull(c2);
		Assert.assertEquals(1, c2.getBugs());
		Assert.assertFalse(c2.isBugfix());
		Assert.assertFalse(c2.isDeleted());
		
		SZZCommit c3 = map.get("bea14e6c2d0263932f626972aedb67287b4516e1");
		Assert.assertNotNull(c3);
		Assert.assertEquals(0, c3.getBugs());
		Assert.assertTrue(c3.isBugfix());
		Assert.assertFalse(c3.isDeleted());
		
		SZZCommit c4 = map.get("a100580cbd16bace6aa3bafc95aa1ea495632818");
		Assert.assertNotNull(c4);
		Assert.assertEquals(0, c4.getBugs());
		Assert.assertFalse(c4.isBugfix());
		Assert.assertFalse(c4.isDeleted());
		
		SZZCommit c5 = map.get("ac964792db4e999edf2df1fd7521c0d968f005cc");
		Assert.assertNotNull(c5);
		Assert.assertEquals(0, c5.getBugs());
		Assert.assertFalse(c5.isBugfix());
		Assert.assertFalse(c5.isDeleted());
	}
	
	@Test
	public void testFile_fixtures_Books() {
		SZZFile f = repository.getFileByPath("src/fixtures/Books.java");
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/fixtures/Books.java", f.getFile());
		
		Collection<SZZCommit> commits = f.getCommits();
		Assert.assertNotNull(commits);
		Assert.assertEquals(2, commits.size());
		
		Map<String, SZZCommit> map = new HashMap<>();
		for(SZZCommit c : commits) {
			map.put(c.getHash(), c);
		}
		
		SZZCommit c = map.get("a100580cbd16bace6aa3bafc95aa1ea495632818");
		Assert.assertNotNull(c);
		Assert.assertEquals(0, c.getBugs());
		Assert.assertFalse(c.isBugfix());
		Assert.assertFalse(c.isDeleted());
		
		
		SZZCommit c2 = map.get("7b993ef69f73f0cea351c4e5df93154ed6c86b0d");
		Assert.assertNotNull(c2);
		Assert.assertEquals(0, c2.getBugs());
		Assert.assertFalse(c2.isBugfix());
		Assert.assertFalse(c2.isDeleted());
	}
	
	@Test
	public void testFile_fixtures_CDs() {
		SZZFile f = repository.getFileByPath("src/fixtures/CDs.java");
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/fixtures/CDs.java", f.getFile());
		
		Collection<SZZCommit> commits = f.getCommits();
		Assert.assertNotNull(commits);
		Assert.assertEquals(1, commits.size());
		
		Map<String, SZZCommit> map = new HashMap<>();
		for(SZZCommit c : commits) {
			map.put(c.getHash(), c);
		}
		
		SZZCommit c = map.get("a100580cbd16bace6aa3bafc95aa1ea495632818");
		Assert.assertNotNull(c);
		Assert.assertEquals(0,  c.getBugs());
		Assert.assertFalse(c.isBugfix());
		Assert.assertFalse(c.isDeleted());
	}
	

	@Test
	public void testFile_fixtures_DVDs() {
		SZZFile f = repository.getFileByPath("src/fixtures/DVDs.java");
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/fixtures/DVDs.java", f.getFile());
		
		Collection<SZZCommit> commits = f.getCommits();
		Assert.assertNotNull(commits);
		Assert.assertEquals(1, commits.size());

		Map<String, SZZCommit> map = new HashMap<>();
		for(SZZCommit c : commits) {
			map.put(c.getHash(), c);
		}
		
		SZZCommit c = map.get("a100580cbd16bace6aa3bafc95aa1ea495632818");
		Assert.assertNotNull(c);
		Assert.assertEquals(0,  c.getBugs());
		Assert.assertFalse(c.isBugfix());
		Assert.assertFalse(c.isDeleted());
	}
	
	
	@Test
	public void testFile_fixtures_Customers() {
		SZZFile f = repository.getFileByPath("src/fixtures/Customers.java");
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/fixtures/Customers.java", f.getFile());
		
		Collection<SZZCommit> commits = f.getCommits();
		Assert.assertNotNull(commits);
		Assert.assertEquals(2, commits.size());
		
		Map<String, SZZCommit> map = new HashMap<>();
		for(SZZCommit c : commits) {
			map.put(c.getHash(), c);
		}
		
		SZZCommit c = map.get("c7c5f4bb49a1de1715da9e933a3331cc4a6836a8");
		Assert.assertNotNull(c);
		Assert.assertEquals(1,  c.getBugs());
		Assert.assertFalse(c.isBugfix());
		Assert.assertFalse(c.isDeleted());
		
		
		SZZCommit c2 = map.get("15c4843dbc7263489ac1a1ec15728ef1e967e0e3");
		Assert.assertNotNull(c2);
		Assert.assertEquals(0, c2.getBugs());
		Assert.assertTrue(c2.isBugfix());
		Assert.assertFalse(c2.isDeleted());
	}

	@Test
	public void testFile_fixtures_IFixture() {
		SZZFile f = repository.getFileByPath("src/fixtures/IFixture.java");
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/fixtures/IFixture.java", f.getFile());
		
		Collection<SZZCommit> commits = f.getCommits();
		Assert.assertNotNull(commits);
		Assert.assertEquals(1, commits.size());

		Map<String, SZZCommit> map = new HashMap<>();
		for(SZZCommit c : commits) {
			map.put(c.getHash(), c);
		}
		
		SZZCommit c = map.get("a100580cbd16bace6aa3bafc95aa1ea495632818");
		Assert.assertNotNull(c);
		Assert.assertEquals(0,  c.getBugs());
		Assert.assertFalse(c.isBugfix());
		Assert.assertFalse(c.isDeleted());
	}

	@Test
	public void testFile_model_Article() {
		SZZFile f = repository.getFileByPath("src/model/Article.java");
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/model/Article.java", f.getFile());
		
		Collection<SZZCommit> commits = f.getCommits();
		Assert.assertNotNull(commits);
		Assert.assertEquals(1, commits.size());

		Map<String, SZZCommit> map = new HashMap<>();
		for(SZZCommit c : commits) {
			map.put(c.getHash(), c);
		}
		
		SZZCommit c = map.get("9fcf637d46f11bd6556f2bfc49b598dccefd45fb");
		Assert.assertNotNull(c);
		Assert.assertEquals(0,  c.getBugs());
		Assert.assertFalse(c.isBugfix());
		Assert.assertFalse(c.isDeleted());
	}

	@Test
	public void testFile_model_Book() {
		SZZFile f = repository.getFileByPath("src/model/Book.java");
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/model/Book.java", f.getFile());
		
		Collection<SZZCommit> commits = f.getCommits();
		Assert.assertNotNull(commits);
		Assert.assertEquals(9, commits.size());
		
		Map<String, SZZCommit> map = new HashMap<>();
		for(SZZCommit c : commits) {
			map.put(c.getHash(), c);
		}
		
		SZZCommit c = map.get("7652bc0fcc45850b9155b37c012e339802728a82");
		Assert.assertNotNull(c);
		Assert.assertEquals(0, c.getBugs());
		Assert.assertFalse(c.isBugfix());
		Assert.assertFalse(c.isDeleted());
		
		
		SZZCommit c2 = map.get("267c6ebb927e12c10aa5d777acc0409b19210ec3");
		Assert.assertNotNull(c2);
		Assert.assertEquals(0, c2.getBugs());
		Assert.assertFalse(c2.isBugfix());
		Assert.assertFalse(c2.isDeleted());
		
		SZZCommit c3 = map.get("131d40ce7961785bcf17e1480af7558e87374e41");
		Assert.assertNotNull(c3);
		Assert.assertEquals(0, c3.getBugs());
		Assert.assertFalse(c3.isBugfix());
		Assert.assertFalse(c3.isDeleted());
		
		SZZCommit c4 = map.get("a100580cbd16bace6aa3bafc95aa1ea495632818");
		Assert.assertNotNull(c4);
		Assert.assertEquals(0, c4.getBugs());
		Assert.assertFalse(c4.isBugfix());
		Assert.assertFalse(c4.isDeleted());
		
		SZZCommit c5 = map.get("088382ba8eb73b9c6dcff3f9ddb8276c866aa5fa");
		Assert.assertNotNull(c5);
		Assert.assertEquals(1, c5.getBugs());
		Assert.assertFalse(c5.isBugfix());
		Assert.assertFalse(c5.isDeleted());
		
		SZZCommit c6 = map.get("08e461a49ff2e7649ce2a337c3a740413613db61");
		Assert.assertNotNull(c6);
		Assert.assertEquals(0, c6.getBugs());
		Assert.assertTrue(c6.isBugfix());
		Assert.assertFalse(c6.isDeleted());
		
		SZZCommit c7 = map.get("4ee76ac608c07d3c77cd283546e658bf3ea91adc");
		Assert.assertNotNull(c7);
		Assert.assertEquals(0, c7.getBugs());
		Assert.assertFalse(c7.isBugfix());
		Assert.assertFalse(c7.isDeleted());
		
		SZZCommit c8 = map.get("7b993ef69f73f0cea351c4e5df93154ed6c86b0d");
		Assert.assertNotNull(c8);
		Assert.assertEquals(0, c8.getBugs());
		Assert.assertFalse(c8.isBugfix());
		Assert.assertFalse(c8.isDeleted());
		
		SZZCommit c9 = map.get("9fcf637d46f11bd6556f2bfc49b598dccefd45fb");
		Assert.assertNotNull(c9);
		Assert.assertEquals(0, c9.getBugs());
		Assert.assertFalse(c9.isBugfix());
		Assert.assertFalse(c9.isDeleted());
	}

	@Test
	public void testFile_model_CD() {
		SZZFile f = repository.getFileByPath("src/model/CD.java");
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/model/CD.java", f.getFile());
		
		Collection<SZZCommit> commits = f.getCommits();
		Assert.assertNotNull(commits);
		Assert.assertEquals(9, commits.size());
		
		Map<String, SZZCommit> map = new HashMap<>();
		for(SZZCommit c : commits) {
			map.put(c.getHash(), c);
		}
		
		SZZCommit c = map.get("5de615507d5ab9909c6e418376b87502f0688eb9");
		Assert.assertNotNull(c);
		Assert.assertEquals(1, c.getBugs());
		Assert.assertFalse(c.isBugfix());
		Assert.assertFalse(c.isDeleted());
		
		
		SZZCommit c2 = map.get("267c6ebb927e12c10aa5d777acc0409b19210ec3");
		Assert.assertNotNull(c2);
		Assert.assertEquals(2, c2.getBugs());
		Assert.assertFalse(c2.isBugfix());
		Assert.assertFalse(c2.isDeleted());
		
		SZZCommit c3 = map.get("6de5173cf6a91b638381669c013121b59ac94255");
		Assert.assertNotNull(c3);
		Assert.assertEquals(1, c3.getBugs());
		Assert.assertTrue(c3.isBugfix());
		Assert.assertFalse(c3.isDeleted());
		
		SZZCommit c4 = map.get("131d40ce7961785bcf17e1480af7558e87374e41");
		Assert.assertNotNull(c4);
		Assert.assertEquals(1, c4.getBugs());
		Assert.assertFalse(c4.isBugfix());
		Assert.assertFalse(c4.isDeleted());
		
		SZZCommit c5 = map.get("00a6ce533e96e956b05e1b3b1249a1b1d74b25ed");
		Assert.assertNotNull(c5);
		Assert.assertEquals(0, c5.getBugs());
		Assert.assertTrue(c5.isBugfix());
		Assert.assertFalse(c5.isDeleted());
		
		SZZCommit c6 = map.get("a100580cbd16bace6aa3bafc95aa1ea495632818");
		Assert.assertNotNull(c6);
		Assert.assertEquals(0, c6.getBugs());
		Assert.assertFalse(c6.isBugfix());
		Assert.assertFalse(c6.isDeleted());
		
		SZZCommit c7 = map.get("088382ba8eb73b9c6dcff3f9ddb8276c866aa5fa");
		Assert.assertNotNull(c7);
		Assert.assertEquals(0, c7.getBugs());
		Assert.assertFalse(c7.isBugfix());
		Assert.assertFalse(c7.isDeleted());
		
		SZZCommit c8 = map.get("4ee76ac608c07d3c77cd283546e658bf3ea91adc");
		Assert.assertNotNull(c8);
		Assert.assertEquals(0, c8.getBugs());
		Assert.assertFalse(c8.isBugfix());
		Assert.assertFalse(c8.isDeleted());
		
		SZZCommit c9 = map.get("9fcf637d46f11bd6556f2bfc49b598dccefd45fb");
		Assert.assertNotNull(c9);
		Assert.assertEquals(0, c9.getBugs());
		Assert.assertFalse(c9.isBugfix());
		Assert.assertFalse(c9.isDeleted());
	}

	@Test
	public void testFile_model_DVD() {
		SZZFile f = repository.getFileByPath("src/model/DVD.java");
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/model/DVD.java", f.getFile());
		
		Collection<SZZCommit> commits = f.getCommits();
		Assert.assertNotNull(commits);
		Assert.assertEquals(7, commits.size());
		
		Map<String, SZZCommit> map = new HashMap<>();
		for(SZZCommit c : commits) {
			map.put(c.getHash(), c);
		}
		
		SZZCommit c = map.get("5acbc10a0c2700b5555963b5790bd8b440b0bcf4");
		Assert.assertNotNull(c);
		Assert.assertEquals(0, c.getBugs());
		Assert.assertFalse(c.isBugfix());
		Assert.assertFalse(c.isDeleted());
		
		
		SZZCommit c2 = map.get("267c6ebb927e12c10aa5d777acc0409b19210ec3");
		Assert.assertNotNull(c2);
		Assert.assertEquals(0, c2.getBugs());
		Assert.assertFalse(c2.isBugfix());
		Assert.assertFalse(c2.isDeleted());
		
		SZZCommit c3 = map.get("131d40ce7961785bcf17e1480af7558e87374e41");
		Assert.assertNotNull(c3);
		Assert.assertEquals(0, c3.getBugs());
		Assert.assertFalse(c3.isBugfix());
		Assert.assertFalse(c3.isDeleted());
		
		SZZCommit c4 = map.get("a100580cbd16bace6aa3bafc95aa1ea495632818");
		Assert.assertNotNull(c4);
		Assert.assertEquals(0, c4.getBugs());
		Assert.assertFalse(c4.isBugfix());
		Assert.assertFalse(c4.isDeleted());
		
		SZZCommit c5 = map.get("088382ba8eb73b9c6dcff3f9ddb8276c866aa5fa");
		Assert.assertNotNull(c5);
		Assert.assertEquals(0, c5.getBugs());
		Assert.assertFalse(c5.isBugfix());
		Assert.assertFalse(c5.isDeleted());
		
		SZZCommit c6 = map.get("4ee76ac608c07d3c77cd283546e658bf3ea91adc");
		Assert.assertNotNull(c6);
		Assert.assertEquals(0, c6.getBugs());
		Assert.assertFalse(c6.isBugfix());
		Assert.assertFalse(c6.isDeleted());
		
		SZZCommit c7 = map.get("9fcf637d46f11bd6556f2bfc49b598dccefd45fb");
		Assert.assertNotNull(c7);
		Assert.assertEquals(0, c7.getBugs());
		Assert.assertFalse(c7.isBugfix());
		Assert.assertFalse(c7.isDeleted());
	}

	@Test
	public void testFile_model_Customer() {
		SZZFile f = repository.getFileByPath("src/model/Customer.java");
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/model/Customer.java", f.getFile());
		
		Collection<SZZCommit> commits = f.getCommits();
		Assert.assertNotNull(commits);
		Assert.assertEquals(4, commits.size());
		
		Map<String, SZZCommit> map = new HashMap<>();
		for(SZZCommit c : commits) {
			map.put(c.getHash(), c);
		}
		
		SZZCommit c = map.get("5f0f919e3945c1251b8d562c4d510ca1523a6ce0");
		Assert.assertNotNull(c);
		Assert.assertEquals(0, c.getBugs());
		Assert.assertFalse(c.isBugfix());
		Assert.assertFalse(c.isDeleted());
		
		
		SZZCommit c2 = map.get("abbaf768592f5b2bcb4b61b7ec3c4f5890831049");
		Assert.assertNotNull(c2);
		Assert.assertEquals(0, c2.getBugs());
		Assert.assertFalse(c2.isBugfix());
		Assert.assertFalse(c2.isDeleted());
		
		SZZCommit c3 = map.get("212657a6779a6ad0648fb55b4659d82e9f119c71");
		Assert.assertNotNull(c3);
		Assert.assertEquals(0, c3.getBugs());
		Assert.assertFalse(c3.isBugfix());
		Assert.assertFalse(c3.isDeleted());
		
		SZZCommit c4 = map.get("4ee76ac608c07d3c77cd283546e658bf3ea91adc");
		Assert.assertNotNull(c4);
		Assert.assertEquals(0, c4.getBugs());
		Assert.assertFalse(c4.isBugfix());
		Assert.assertFalse(c4.isDeleted());
	}

	@Test
	public void testFile_model_IArticle() {
		SZZFile f = repository.getFileByPath("src/model/IArticle.java");
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/model/IArticle.java", f.getFile());
		
		Collection<SZZCommit> commits = f.getCommits();
		Assert.assertNotNull(commits);
		Assert.assertEquals(4, commits.size());
		
		Map<String, SZZCommit> map = new HashMap<>();
		for(SZZCommit c : commits) {
			map.put(c.getHash(), c);
		}
		
		SZZCommit c = map.get("5de615507d5ab9909c6e418376b87502f0688eb9");
		Assert.assertNotNull(c);
		Assert.assertEquals(0, c.getBugs());
		Assert.assertFalse(c.isBugfix());
		Assert.assertFalse(c.isDeleted());
		
		
		SZZCommit c2 = map.get("267c6ebb927e12c10aa5d777acc0409b19210ec3");
		Assert.assertNotNull(c2);
		Assert.assertEquals(0, c2.getBugs());
		Assert.assertFalse(c2.isBugfix());
		Assert.assertFalse(c2.isDeleted());
		
		SZZCommit c3 = map.get("131d40ce7961785bcf17e1480af7558e87374e41");
		Assert.assertNotNull(c3);
		Assert.assertEquals(0, c3.getBugs());
		Assert.assertFalse(c3.isBugfix());
		Assert.assertFalse(c3.isDeleted());
		
		SZZCommit c4 = map.get("088382ba8eb73b9c6dcff3f9ddb8276c866aa5fa");
		Assert.assertNotNull(c4);
		Assert.assertEquals(0, c4.getBugs());
		Assert.assertFalse(c4.isBugfix());
		Assert.assertFalse(c4.isDeleted());
	}

	@Test
	public void testFile_model_Order() {
		SZZFile f = repository.getFileByPath("src/model/Order.java");
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/model/Order.java", f.getFile());
		
		Collection<SZZCommit> commits = f.getCommits();
		Assert.assertNotNull(commits);
		Assert.assertEquals(2, commits.size());
		
		Map<String, SZZCommit> map = new HashMap<>();
		for(SZZCommit c : commits) {
			map.put(c.getHash(), c);
		}
		
		SZZCommit c = map.get("5f0f919e3945c1251b8d562c4d510ca1523a6ce0");
		Assert.assertNotNull(c);
		Assert.assertEquals(0, c.getBugs());
		Assert.assertFalse(c.isBugfix());
		Assert.assertFalse(c.isDeleted());
		
		
		SZZCommit c2 = map.get("4ee76ac608c07d3c77cd283546e658bf3ea91adc");
		Assert.assertNotNull(c2);
		Assert.assertEquals(0, c2.getBugs());
		Assert.assertFalse(c2.isBugfix());
		Assert.assertFalse(c2.isDeleted());
	}
}
