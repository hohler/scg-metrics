package ch.unibe.scg.metrics.sourcemetrics;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.unibe.scg.metrics.sourcemetrics.domain.SMCommit;
import ch.unibe.scg.metrics.sourcemetrics.domain.SMFile;
import ch.unibe.scg.metrics.sourcemetrics.domain.SMRepository;

public class MetricsTest {

	private static String COMMIT = "ac964792db4e999edf2df1fd7521c0d968f005cc";
	private static Path path;
	private static SourceMetrics sourceMetrics;
	
	private static SMRepository repository;
	
	@BeforeClass
	public static void readPath() throws FileNotFoundException {
		String p = (MetricsTest.class.getResource("/").getPath() + "../../test-repo/AcmeStore-master").replaceFirst("/","").replace("/", "//");
		path = Paths.get(p);
		
		sourceMetrics = new SourceMetrics(path);
		sourceMetrics.setEveryNthCommit(0);
		sourceMetrics.setRange(COMMIT, COMMIT);
		sourceMetrics.generateCommitList();
		repository = sourceMetrics.analyze(sourceMetrics.getCommitList());
	}
	
	@Test
	public void testRepository() {
		Assert.assertEquals(1, repository.all().size());
		Assert.assertNotNull(repository.getCommit(COMMIT));
	}
	
	@Test
	public void testCommit() {
		
		SMCommit commit = repository.getCommit(COMMIT);
		
		Assert.assertNotNull(commit);
		
		Assert.assertEquals(COMMIT, commit.getHash());
		
		Assert.assertEquals("some unnecessary additions to cover the metrics", commit.getMsg());		
	}
	
	@Test
	public void testFiles() {
		SMCommit commit = repository.getCommit(COMMIT);
		Map<String, SMFile> files = commit.getFiles();
		
		Assert.assertEquals(files.size(), 13);
		
		for(Map.Entry<String, SMFile> s : files.entrySet()) {
			SMFile file = s.getValue();
			Assert.assertEquals(file.getFile(), s.getKey());
		}
		
	}
	
	@Test
	public void testFile_Store() {
		SMCommit commit = repository.getCommit(COMMIT);
		SMFile f = commit.getFiles().get("src/Store.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("Store", f.getClassName());
		Assert.assertEquals("src/Store.java", f.getFile());
		Assert.assertEquals("class", f.getType());
		Assert.assertEquals(4, f.getCbo());
		Assert.assertEquals(1, f.getDit());
		Assert.assertEquals(4, f.getLcom());
		Assert.assertEquals(36, f.getLoc());
		Assert.assertEquals(0, f.getNoc());
		Assert.assertEquals(1, f.getNocb());
		Assert.assertEquals(2, f.getNof());
		Assert.assertEquals(5, f.getNom());
		Assert.assertEquals(0, f.getNomwmop());
		Assert.assertEquals(1, f.getNona());
		Assert.assertEquals(1, f.getNonc());
		Assert.assertEquals(2, f.getNopf());
		Assert.assertEquals(4, f.getNopm());
		Assert.assertEquals(1, f.getNosf());
		Assert.assertEquals(2, f.getNosi());
		Assert.assertEquals(1, f.getNosm());
		Assert.assertEquals(8, f.getRfc());
		Assert.assertEquals(8, f.getWmc());
		
	}
	
	@Test
	public void testFile_fixtures_Books() {
		SMCommit commit = repository.getCommit(COMMIT);
		SMFile f = commit.getFiles().get("src/fixtures/Books.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("fixtures.Books", f.getClassName());
		Assert.assertEquals("src/fixtures/Books.java", f.getFile());
		Assert.assertEquals("class", f.getType());
		Assert.assertEquals(3, f.getCbo());
		Assert.assertEquals(1, f.getDit());
		Assert.assertEquals(0, f.getLcom());
		Assert.assertEquals(18, f.getLoc());
		Assert.assertEquals(0, f.getNoc());
		Assert.assertEquals(0, f.getNocb());
		Assert.assertEquals(0, f.getNof());
		Assert.assertEquals(1, f.getNom());
		Assert.assertEquals(0, f.getNomwmop());
		Assert.assertEquals(0, f.getNona());
		Assert.assertEquals(0, f.getNonc());
		Assert.assertEquals(0, f.getNopf());
		Assert.assertEquals(1, f.getNopm());
		Assert.assertEquals(0, f.getNosf());
		Assert.assertEquals(0, f.getNosi());
		Assert.assertEquals(0, f.getNosm());
		Assert.assertEquals(1, f.getRfc());
		Assert.assertEquals(1, f.getWmc());
	}
	
	@Test
	public void testFile_fixtures_CDs() {
		SMCommit commit = repository.getCommit(COMMIT);
		SMFile f = commit.getFiles().get("src/fixtures/CDs.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("fixtures.CDs", f.getClassName());
		Assert.assertEquals("src/fixtures/CDs.java", f.getFile());
		Assert.assertEquals("class", f.getType());
		Assert.assertEquals(3, f.getCbo());
		Assert.assertEquals(1, f.getDit());
		Assert.assertEquals(0, f.getLcom());
		Assert.assertEquals(18, f.getLoc());
		Assert.assertEquals(0, f.getNoc());
		Assert.assertEquals(0, f.getNocb());
		Assert.assertEquals(0, f.getNof());
		Assert.assertEquals(1, f.getNom());
		Assert.assertEquals(0, f.getNomwmop());
		Assert.assertEquals(0, f.getNona());
		Assert.assertEquals(0, f.getNonc());
		Assert.assertEquals(0, f.getNopf());
		Assert.assertEquals(1, f.getNopm());
		Assert.assertEquals(0, f.getNosf());
		Assert.assertEquals(0, f.getNosi());
		Assert.assertEquals(0, f.getNosm());
		Assert.assertEquals(1, f.getRfc());
		Assert.assertEquals(1, f.getWmc());
	}
	
	@Test
	public void testFile_fixtures_DVDs() {
		SMCommit commit = repository.getCommit(COMMIT);
		SMFile f = commit.getFiles().get("src/fixtures/DVDs.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("fixtures.DVDs", f.getClassName());
		Assert.assertEquals("src/fixtures/DVDs.java", f.getFile());
		Assert.assertEquals("class", f.getType());
		Assert.assertEquals(3, f.getCbo());
		Assert.assertEquals(1, f.getDit());
		Assert.assertEquals(0, f.getLcom());
		Assert.assertEquals(18, f.getLoc());
		Assert.assertEquals(0, f.getNoc());
		Assert.assertEquals(0, f.getNocb());
		Assert.assertEquals(0, f.getNof());
		Assert.assertEquals(1, f.getNom());
		Assert.assertEquals(0, f.getNomwmop());
		Assert.assertEquals(0, f.getNona());
		Assert.assertEquals(0, f.getNonc());
		Assert.assertEquals(0, f.getNopf());
		Assert.assertEquals(1, f.getNopm());
		Assert.assertEquals(0, f.getNosf());
		Assert.assertEquals(0, f.getNosi());
		Assert.assertEquals(0, f.getNosm());
		Assert.assertEquals(1, f.getRfc());
		Assert.assertEquals(1, f.getWmc());
	}
	
	@Test
	public void testFile_fixtures_Customers() {
		SMCommit commit = repository.getCommit(COMMIT);
		SMFile f = commit.getFiles().get("src/fixtures/Customers.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("fixtures.Customers", f.getClassName());
		Assert.assertEquals("src/fixtures/Customers.java", f.getFile());
		Assert.assertEquals("class", f.getType());
		Assert.assertEquals(1, f.getCbo());
		Assert.assertEquals(1, f.getDit());
		Assert.assertEquals(0, f.getLcom());
		Assert.assertEquals(16, f.getLoc());
		Assert.assertEquals(0, f.getNoc());
		Assert.assertEquals(0, f.getNocb());
		Assert.assertEquals(0, f.getNof());
		Assert.assertEquals(1, f.getNom());
		Assert.assertEquals(0, f.getNomwmop());
		Assert.assertEquals(0, f.getNona());
		Assert.assertEquals(0, f.getNonc());
		Assert.assertEquals(0, f.getNopf());
		Assert.assertEquals(1, f.getNopm());
		Assert.assertEquals(0, f.getNosf());
		Assert.assertEquals(0, f.getNosi());
		Assert.assertEquals(0, f.getNosm());
		Assert.assertEquals(1, f.getRfc());
		Assert.assertEquals(1, f.getWmc());
	}
	
	@Test
	public void testFile_fixtures_IFixture() {
		SMCommit commit = repository.getCommit(COMMIT);
		SMFile f = commit.getFiles().get("src/fixtures/IFixture.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("fixtures.IFixture", f.getClassName());
		Assert.assertEquals("src/fixtures/IFixture.java", f.getFile());
		Assert.assertEquals("interface", f.getType());
		Assert.assertEquals(1, f.getCbo());
		Assert.assertEquals(1, f.getDit());
		Assert.assertEquals(0, f.getLcom());
		Assert.assertEquals(6, f.getLoc());
		Assert.assertEquals(0, f.getNoc());
		Assert.assertEquals(0, f.getNocb());
		Assert.assertEquals(0, f.getNof());
		Assert.assertEquals(1, f.getNom());
		Assert.assertEquals(0, f.getNomwmop());
		Assert.assertEquals(0, f.getNona());
		Assert.assertEquals(0, f.getNonc());
		Assert.assertEquals(0, f.getNopf());
		Assert.assertEquals(1, f.getNopm());
		Assert.assertEquals(0, f.getNosf());
		Assert.assertEquals(0, f.getNosi());
		Assert.assertEquals(0, f.getNosm());
		Assert.assertEquals(0, f.getRfc());
		Assert.assertEquals(1, f.getWmc());
	}
	
	@Test
	public void testFile_model_Article() {
		SMCommit commit = repository.getCommit(COMMIT);
		SMFile f = commit.getFiles().get("src/model/Article.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("model.Article", f.getClassName());
		Assert.assertEquals("src/model/Article.java", f.getFile());
		Assert.assertEquals("class", f.getType());
		Assert.assertEquals(1, f.getCbo());
		Assert.assertEquals(1, f.getDit());
		Assert.assertEquals(25, f.getLcom());
		Assert.assertEquals(45, f.getLoc());
		Assert.assertEquals(3, f.getNoc());
		Assert.assertEquals(0, f.getNocb());
		Assert.assertEquals(4, f.getNof());
		Assert.assertEquals(10, f.getNom());
		Assert.assertEquals(1, f.getNomwmop());
		Assert.assertEquals(0, f.getNona());
		Assert.assertEquals(0, f.getNonc());
		Assert.assertEquals(0, f.getNopf());
		Assert.assertEquals(10, f.getNopm());
		Assert.assertEquals(0, f.getNosf());
		Assert.assertEquals(0, f.getNosi());
		Assert.assertEquals(0, f.getNosm());
		Assert.assertEquals(0, f.getRfc());
		Assert.assertEquals(10, f.getWmc());
	}
	
	@Test
	public void testFile_model_Book() {
		SMCommit commit = repository.getCommit(COMMIT);
		SMFile f = commit.getFiles().get("src/model/Book.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("model.Book", f.getClassName());
		Assert.assertEquals("src/model/Book.java", f.getFile());
		Assert.assertEquals("class", f.getType());
		Assert.assertEquals(1, f.getCbo());
		Assert.assertEquals(2, f.getDit());
		Assert.assertEquals(0, f.getLcom());
		Assert.assertEquals(30, f.getLoc());
		Assert.assertEquals(0, f.getNoc());
		Assert.assertEquals(0, f.getNocb());
		Assert.assertEquals(2, f.getNof());
		Assert.assertEquals(7, f.getNom());
		Assert.assertEquals(1, f.getNomwmop());
		Assert.assertEquals(0, f.getNona());
		Assert.assertEquals(0, f.getNonc());
		Assert.assertEquals(0, f.getNopf());
		Assert.assertEquals(7, f.getNopm());
		Assert.assertEquals(0, f.getNosf());
		Assert.assertEquals(0, f.getNosi());
		Assert.assertEquals(0, f.getNosm());
		Assert.assertEquals(1, f.getRfc());
		Assert.assertEquals(7, f.getWmc());
	}
	
	@Test
	public void testFile_model_CD() {
		SMCommit commit = repository.getCommit(COMMIT);
		SMFile f = commit.getFiles().get("src/model/CD.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("model.CD", f.getClassName());
		Assert.assertEquals("src/model/CD.java", f.getFile());
		Assert.assertEquals("class", f.getType());
		Assert.assertEquals(1, f.getCbo());
		Assert.assertEquals(2, f.getDit());
		Assert.assertEquals(0, f.getLcom());
		Assert.assertEquals(23, f.getLoc());
		Assert.assertEquals(0, f.getNoc());
		Assert.assertEquals(0, f.getNocb());
		Assert.assertEquals(1, f.getNof());
		Assert.assertEquals(5, f.getNom());
		Assert.assertEquals(1, f.getNomwmop());
		Assert.assertEquals(0, f.getNona());
		Assert.assertEquals(0, f.getNonc());
		Assert.assertEquals(0, f.getNopf());
		Assert.assertEquals(5, f.getNopm());
		Assert.assertEquals(0, f.getNosf());
		Assert.assertEquals(0, f.getNosi());
		Assert.assertEquals(0, f.getNosm());
		Assert.assertEquals(1, f.getRfc());
		Assert.assertEquals(5, f.getWmc());
	}
	
	@Test
	public void testFile_model_DVD() {
		SMCommit commit = repository.getCommit(COMMIT);
		SMFile f = commit.getFiles().get("src/model/DVD.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("model.DVD", f.getClassName());
		Assert.assertEquals("src/model/DVD.java", f.getFile());
		Assert.assertEquals("class", f.getType());
		Assert.assertEquals(1, f.getCbo());
		Assert.assertEquals(2, f.getDit());
		Assert.assertEquals(3, f.getLcom());
		Assert.assertEquals(13, f.getLoc());
		Assert.assertEquals(0, f.getNoc());
		Assert.assertEquals(0, f.getNocb());
		Assert.assertEquals(0, f.getNof());
		Assert.assertEquals(3, f.getNom());
		Assert.assertEquals(1, f.getNomwmop());
		Assert.assertEquals(0, f.getNona());
		Assert.assertEquals(0, f.getNonc());
		Assert.assertEquals(0, f.getNopf());
		Assert.assertEquals(3, f.getNopm());
		Assert.assertEquals(0, f.getNosf());
		Assert.assertEquals(0, f.getNosi());
		Assert.assertEquals(0, f.getNosm());
		Assert.assertEquals(0, f.getRfc());
		Assert.assertEquals(3, f.getWmc());
	}
	
	@Test
	public void testFile_model_Customer() {
		SMCommit commit = repository.getCommit(COMMIT);
		SMFile f = commit.getFiles().get("src/model/Customer.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("model.Customer", f.getClassName());
		Assert.assertEquals("src/model/Customer.java", f.getFile());
		Assert.assertEquals("class", f.getType());
		Assert.assertEquals(0, f.getCbo());
		Assert.assertEquals(1, f.getDit());
		Assert.assertEquals(26, f.getLcom());
		Assert.assertEquals(51, f.getLoc());
		Assert.assertEquals(0, f.getNoc());
		Assert.assertEquals(0, f.getNocb());
		Assert.assertEquals(5, f.getNof());
		Assert.assertEquals(13, f.getNom());
		Assert.assertEquals(1, f.getNomwmop());
		Assert.assertEquals(0, f.getNona());
		Assert.assertEquals(0, f.getNonc());
		Assert.assertEquals(0, f.getNopf());
		Assert.assertEquals(13, f.getNopm());
		Assert.assertEquals(0, f.getNosf());
		Assert.assertEquals(0, f.getNosi());
		Assert.assertEquals(0, f.getNosm());
		Assert.assertEquals(0, f.getRfc());
		Assert.assertEquals(13, f.getWmc());
	}
	
	@Test
	public void testFile_model_IArticle() {
		SMCommit commit = repository.getCommit(COMMIT);
		SMFile f = commit.getFiles().get("src/model/IArticle.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("model.IArticle", f.getClassName());
		Assert.assertEquals("src/model/IArticle.java", f.getFile());
		Assert.assertEquals("interface", f.getType());
		Assert.assertEquals(0, f.getCbo());
		Assert.assertEquals(1, f.getDit());
		Assert.assertEquals(28, f.getLcom());
		Assert.assertEquals(11, f.getLoc());
		Assert.assertEquals(0, f.getNoc());
		Assert.assertEquals(0, f.getNocb());
		Assert.assertEquals(0, f.getNof());
		Assert.assertEquals(8, f.getNom());
		Assert.assertEquals(0, f.getNomwmop());
		Assert.assertEquals(0, f.getNona());
		Assert.assertEquals(0, f.getNonc());
		Assert.assertEquals(0, f.getNopf());
		Assert.assertEquals(8, f.getNopm());
		Assert.assertEquals(0, f.getNosf());
		Assert.assertEquals(0, f.getNosi());
		Assert.assertEquals(0, f.getNosm());
		Assert.assertEquals(0, f.getRfc());
		Assert.assertEquals(8, f.getWmc());
	}
	
	@Test
	public void testFile_model_Order() {
		SMCommit commit = repository.getCommit(COMMIT);
		SMFile f = commit.getFiles().get("src/model/Order.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("model.Order", f.getClassName());
		Assert.assertEquals("src/model/Order.java", f.getFile());
		Assert.assertEquals("class", f.getType());
		Assert.assertEquals(2, f.getCbo());
		Assert.assertEquals(1, f.getDit());
		Assert.assertEquals(4, f.getLcom());
		Assert.assertEquals(33, f.getLoc());
		Assert.assertEquals(0, f.getNoc());
		Assert.assertEquals(0, f.getNocb());
		Assert.assertEquals(2, f.getNof());
		Assert.assertEquals(8, f.getNom());
		Assert.assertEquals(0, f.getNomwmop());
		Assert.assertEquals(0, f.getNona());
		Assert.assertEquals(0, f.getNonc());
		Assert.assertEquals(0, f.getNopf());
		Assert.assertEquals(8, f.getNopm());
		Assert.assertEquals(0, f.getNosf());
		Assert.assertEquals(0, f.getNosi());
		Assert.assertEquals(0, f.getNosm());
		Assert.assertEquals(3, f.getRfc());
		Assert.assertEquals(9, f.getWmc());
	}
	
}
