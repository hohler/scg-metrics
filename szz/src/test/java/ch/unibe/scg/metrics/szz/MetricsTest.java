package ch.unibe.scg.metrics.szz;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

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
	
	/*@Test
	public void testRepository() {
		Assert.assertEquals(1, ranges.size());
		Assert.assertNotNull(ranges.keySet().contains(COMMIT));
	}
	
	@Test
	public void testFiles() {
		Assert.assertEquals(13,  repository.all().size());
	}

	@Test
	public void testFile_Store() {
		CMFile f = repository.allAsMap().get("src/Store.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/Store.java", f.getFile());
		Assert.assertEquals(5, f.getRevisions());
		Assert.assertEquals(0, f.getRefactorings());
		Assert.assertEquals(1, f.getBugfixes());
		Assert.assertEquals(1, f.getUniqueAuthorsQuantity());
		Assert.assertEquals(50, f.getLocAdded()); // verified
		Assert.assertEquals(3, f.getLocRemoved()); // verified
		Assert.assertEquals(22, f.getMaxLocAdded()); // verified
		Assert.assertEquals(2, f.getMaxLocRemoved()); // verified
		Assert.assertEquals(10, f.getAvgLocAdded(), 0.01); // verified: 50 loc total / 5 commits
		Assert.assertEquals(0.6, f.getAvgLocRemoved(), 0.01); // verified: 3 loc total / 5 commits
		Assert.assertEquals(53, f.getCodeChurn()); // verified: LOC added 50 + LOC deleted 3 = 53
		Assert.assertEquals(23, f.getMaxCodeChurn()); // verified: 22 added + 1 deleted = 23
		Assert.assertEquals(10.6, f.getAvgCodeChurn(), 0.01); // verified: 53 / 5 = 10.6
		Assert.assertEquals(8, f.getMaxChangeset()); // verified: max files commited together: a100580cbd16bace6aa3bafc95aa1ea495632818
		Assert.assertEquals(2.4, f.getAvgChangeset(), 0.01); // total changeset / revisions = 12/5 (5 commits where that file is included) 
		Assert.assertEquals(0, f.getWeightedAge(), 0.01); // 0 because files are modified within 1-2 days
		Assert.assertEquals(0, f.getWeeks()); // 0 because files are modified within 1-2 days
		
		Calendar f1 = f.getFirstCommit();
		Assert.assertEquals(9, f1.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(0, f1.get(Calendar.MINUTE));
		Assert.assertEquals(13, f1.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f1.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f1.get(Calendar.YEAR));
		
		Calendar f2 = f.getLastCommit();
		Assert.assertEquals(16, f2.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(20, f2.get(Calendar.MINUTE));
		Assert.assertEquals(13, f2.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f2.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f2.get(Calendar.YEAR));
	}
	
	@Test
	public void testFile_fixtures_Books() {
		CMFile f = repository.allAsMap().get("src/fixtures/Books.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/fixtures/Books.java", f.getFile());
		Assert.assertEquals(2, f.getRevisions());
		Assert.assertEquals(0, f.getRefactorings());
		Assert.assertEquals(0, f.getBugfixes());
		Assert.assertEquals(1, f.getUniqueAuthorsQuantity());
		Assert.assertEquals(30, f.getLocAdded()); // verified
		Assert.assertEquals(3, f.getLocRemoved()); // verified
		Assert.assertEquals(27, f.getMaxLocAdded()); // verified
		Assert.assertEquals(3, f.getMaxLocRemoved()); // verified
		Assert.assertEquals(15, f.getAvgLocAdded(), 0.01); // verified: 30 loc total / 2 commits
		Assert.assertEquals(1.5, f.getAvgLocRemoved(), 0.01); // verified: 3 loc total / 2 commits
		Assert.assertEquals(33, f.getCodeChurn()); // verified: LOC added 30 + LOC deleted 3 = 33
		Assert.assertEquals(27, f.getMaxCodeChurn()); // verified: 27 added + 0 deleted = 27
		Assert.assertEquals(16.5, f.getAvgCodeChurn(), 0.01); // verified: 33 / 2 = 16.5
		Assert.assertEquals(8, f.getMaxChangeset()); // verified: max files commited together: a100580cbd16bace6aa3bafc95aa1ea495632818
		Assert.assertEquals(5, f.getAvgChangeset(), 0.01); // total changeset / revisions = 10/2 (2 commits where that file is included) 
		Assert.assertEquals(0, f.getWeightedAge(), 0.01); // 0 because files are modified within 1-2 days
		Assert.assertEquals(0, f.getWeeks()); // 0 because files are modified within 1-2 days
		
		Calendar f1 = f.getFirstCommit();
		Assert.assertEquals(9, f1.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(34, f1.get(Calendar.MINUTE));
		Assert.assertEquals(13, f1.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f1.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f1.get(Calendar.YEAR));
		
		Calendar f2 = f.getLastCommit();
		Assert.assertEquals(10, f2.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(8, f2.get(Calendar.MINUTE));
		Assert.assertEquals(13, f2.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f2.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f2.get(Calendar.YEAR));
	}
	
	@Test
	public void testFile_fixtures_CDs() {
		CMFile f = repository.allAsMap().get("src/fixtures/CDs.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/fixtures/CDs.java", f.getFile());
		Assert.assertEquals(1, f.getRevisions());
		Assert.assertEquals(0, f.getRefactorings());
		Assert.assertEquals(0, f.getBugfixes());
		Assert.assertEquals(1, f.getUniqueAuthorsQuantity());
		Assert.assertEquals(28, f.getLocAdded()); // verified
		Assert.assertEquals(0, f.getLocRemoved()); // verified
		Assert.assertEquals(28, f.getMaxLocAdded()); // verified
		Assert.assertEquals(0, f.getMaxLocRemoved()); // verified
		Assert.assertEquals(28, f.getAvgLocAdded(), 0.01); // verified: 28 loc total / 1 commits
		Assert.assertEquals(0, f.getAvgLocRemoved(), 0.01); // verified: 0 loc total / 1 commits
		Assert.assertEquals(28, f.getCodeChurn()); // verified: LOC added 28 + LOC deleted 0 = 28
		Assert.assertEquals(28, f.getMaxCodeChurn()); // verified: 28 added + 0 deleted = 28
		Assert.assertEquals(28, f.getAvgCodeChurn(), 0.01); // verified: 28 / 1 = 28
		Assert.assertEquals(8, f.getMaxChangeset()); // verified: max files commited together: a100580cbd16bace6aa3bafc95aa1ea495632818
		Assert.assertEquals(8, f.getAvgChangeset(), 0.01); // total changeset / revisions = 8/1 (1 commit where that file is included)
		Assert.assertEquals(0, f.getWeightedAge(), 0.01); // 0 because files are modified within 1-2 days
		Assert.assertEquals(0, f.getWeeks()); // 0 because files are modified within 1-2 days
		
		Calendar f1 = f.getFirstCommit();
		Assert.assertEquals(9, f1.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(34, f1.get(Calendar.MINUTE));
		Assert.assertEquals(13, f1.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f1.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f1.get(Calendar.YEAR));
		
		Calendar f2 = f.getLastCommit();
		Assert.assertEquals(9, f2.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(34, f2.get(Calendar.MINUTE));
		Assert.assertEquals(13, f2.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f2.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f2.get(Calendar.YEAR));
	}
	

	@Test
	public void testFile_fixtures_DVDs() {
		CMFile f = repository.allAsMap().get("src/fixtures/DVDs.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/fixtures/DVDs.java", f.getFile());
		Assert.assertEquals(1, f.getRevisions());
		Assert.assertEquals(0, f.getRefactorings());
		Assert.assertEquals(0, f.getBugfixes());
		Assert.assertEquals(1, f.getUniqueAuthorsQuantity());
		Assert.assertEquals(27, f.getLocAdded()); // verified
		Assert.assertEquals(0, f.getLocRemoved()); // verified
		Assert.assertEquals(27, f.getMaxLocAdded()); // verified
		Assert.assertEquals(0, f.getMaxLocRemoved()); // verified
		Assert.assertEquals(27, f.getAvgLocAdded(), 0.01); // verified: 28 loc total / 1 commits
		Assert.assertEquals(0, f.getAvgLocRemoved(), 0.01); // verified: 0 loc total / 1 commits
		Assert.assertEquals(27, f.getCodeChurn()); // verified: LOC added 28 + LOC deleted 0 = 28
		Assert.assertEquals(27, f.getMaxCodeChurn()); // verified: 28 added + 0 deleted = 28
		Assert.assertEquals(27, f.getAvgCodeChurn(), 0.01); // verified: 28 / 1 = 28
		Assert.assertEquals(8, f.getMaxChangeset()); // verified: max files commited together: a100580cbd16bace6aa3bafc95aa1ea495632818
		Assert.assertEquals(8, f.getAvgChangeset(), 0.01); // total changeset / revisions = 8/1 (1 commit where that file is included)
		Assert.assertEquals(0, f.getWeightedAge(), 0.01); // 0 because files are modified within 1-2 days
		Assert.assertEquals(0, f.getWeeks()); // 0 because files are modified within 1-2 days
		
		Calendar f1 = f.getFirstCommit();
		Assert.assertEquals(9, f1.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(34, f1.get(Calendar.MINUTE));
		Assert.assertEquals(13, f1.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f1.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f1.get(Calendar.YEAR));
		
		Calendar f2 = f.getLastCommit();
		Assert.assertEquals(9, f2.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(34, f2.get(Calendar.MINUTE));
		Assert.assertEquals(13, f2.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f2.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f2.get(Calendar.YEAR));
	}
	
	
	@Test
	public void testFile_fixtures_Customers() {
		CMFile f = repository.allAsMap().get("src/fixtures/Customers.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/fixtures/Customers.java", f.getFile());
		Assert.assertEquals(2, f.getRevisions());
		Assert.assertEquals(0, f.getRefactorings());
		Assert.assertEquals(1, f.getBugfixes());
		Assert.assertEquals(1, f.getUniqueAuthorsQuantity());
		Assert.assertEquals(26, f.getLocAdded()); // verified
		Assert.assertEquals(1, f.getLocRemoved()); // verified
		Assert.assertEquals(26, f.getMaxLocAdded()); // verified
		Assert.assertEquals(1, f.getMaxLocRemoved()); // verified
		Assert.assertEquals(13, f.getAvgLocAdded(), 0.01); // verified: 26 loc total / 2 commits
		Assert.assertEquals(0.5, f.getAvgLocRemoved(), 0.01); // verified: 1 loc total / 2 commits
		Assert.assertEquals(27, f.getCodeChurn()); // verified: LOC added 26 + LOC deleted 1 = 27
		Assert.assertEquals(26, f.getMaxCodeChurn()); // verified: 26 added + 0 deleted = 26
		Assert.assertEquals(13.5, f.getAvgCodeChurn(), 0.01); // verified: 27 / 2 = 13.5
		Assert.assertEquals(1, f.getMaxChangeset()); // verified: max files commited together: 15c4843dbc7263489ac1a1ec15728ef1e967e0e3 or c7c5f4bb49a1de1715da9e933a3331cc4a6836a8
		Assert.assertEquals(1, f.getAvgChangeset(), 0.01); // total changeset / revisions = 2/2 (2 commits where that file is included)
		Assert.assertEquals(0, f.getWeightedAge(), 0.01); // 0 because files are modified within 1-2 days
		Assert.assertEquals(0, f.getWeeks()); // 0 because files are modified within 1-2 days
		
		Calendar f1 = f.getFirstCommit();
		Assert.assertEquals(9, f1.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(54, f1.get(Calendar.MINUTE));
		Assert.assertEquals(13, f1.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f1.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f1.get(Calendar.YEAR));
		
		Calendar f2 = f.getLastCommit();
		Assert.assertEquals(9, f2.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(55, f2.get(Calendar.MINUTE));
		Assert.assertEquals(13, f2.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f2.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f2.get(Calendar.YEAR));
	}

	@Test
	public void testFile_fixtures_IFixture() {
		CMFile f = repository.allAsMap().get("src/fixtures/IFixture.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/fixtures/IFixture.java", f.getFile());
		Assert.assertEquals(1, f.getRevisions());
		Assert.assertEquals(0, f.getRefactorings());
		Assert.assertEquals(0, f.getBugfixes());
		Assert.assertEquals(1, f.getUniqueAuthorsQuantity());
		Assert.assertEquals(9, f.getLocAdded()); // verified
		Assert.assertEquals(0, f.getLocRemoved()); // verified
		Assert.assertEquals(9, f.getMaxLocAdded()); // verified
		Assert.assertEquals(0, f.getMaxLocRemoved()); // verified
		Assert.assertEquals(9, f.getAvgLocAdded(), 0.01); // verified: 9 loc total / 1 commits
		Assert.assertEquals(0, f.getAvgLocRemoved(), 0.01); // verified: 0 loc total / 1 commits
		Assert.assertEquals(9, f.getCodeChurn()); // verified: LOC added 9 + LOC deleted 0 = 9
		Assert.assertEquals(9, f.getMaxCodeChurn()); // verified: 9 added + 0 deleted = 9
		Assert.assertEquals(9, f.getAvgCodeChurn(), 0.01); // verified: 9 / 1 = 9
		Assert.assertEquals(8, f.getMaxChangeset()); // verified: max files commited together: a100580cbd16bace6aa3bafc95aa1ea495632818
		Assert.assertEquals(8, f.getAvgChangeset(), 0.01); // total changeset / revisions = 8/1 (1 commit where that file is included)
		Assert.assertEquals(0, f.getWeightedAge(), 0.01); // 0 because files are modified within 1-2 days
		Assert.assertEquals(0, f.getWeeks()); // 0 because files are modified within 1-2 days
		
		Calendar f1 = f.getFirstCommit();
		Assert.assertEquals(9, f1.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(34, f1.get(Calendar.MINUTE));
		Assert.assertEquals(13, f1.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f1.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f1.get(Calendar.YEAR));
		
		Calendar f2 = f.getLastCommit();
		Assert.assertEquals(9, f2.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(34, f2.get(Calendar.MINUTE));
		Assert.assertEquals(13, f2.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f2.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f2.get(Calendar.YEAR));
	}

	@Test
	public void testFile_model_Article() {
		CMFile f = repository.allAsMap().get("src/model/Article.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/model/Article.java", f.getFile());
		Assert.assertEquals(1, f.getRevisions());
		Assert.assertEquals(1, f.getRefactorings());
		Assert.assertEquals(0, f.getBugfixes());
		Assert.assertEquals(1, f.getUniqueAuthorsQuantity());
		Assert.assertEquals(57, f.getLocAdded()); // verified
		Assert.assertEquals(0, f.getLocRemoved()); // verified
		Assert.assertEquals(57, f.getMaxLocAdded()); // verified
		Assert.assertEquals(0, f.getMaxLocRemoved()); // verified
		Assert.assertEquals(57, f.getAvgLocAdded(), 0.01); // verified: 57 loc total / 1 commits
		Assert.assertEquals(0, f.getAvgLocRemoved(), 0.01); // verified: 0 loc total / 1 commits
		Assert.assertEquals(57, f.getCodeChurn()); // verified: LOC added 57 + LOC deleted 0 = 57
		Assert.assertEquals(57, f.getMaxCodeChurn()); // verified: 57 added + 0 deleted = 57
		Assert.assertEquals(57, f.getAvgCodeChurn(), 0.01); // verified: 57 / 1 = 57
		Assert.assertEquals(4, f.getMaxChangeset()); // verified: max files commited together: 9fcf637d46f11bd6556f2bfc49b598dccefd45fb
		Assert.assertEquals(4, f.getAvgChangeset(), 0.01); // total changeset / revisions = 4/1 (1 commit where that file is included)
		Assert.assertEquals(0, f.getWeightedAge(), 0.01); // 0 because files are modified within 1-2 days
		Assert.assertEquals(0, f.getWeeks()); // 0 because files are modified within 1-2 days
		
		Calendar f1 = f.getFirstCommit();
		Assert.assertEquals(16, f1.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(7, f1.get(Calendar.MINUTE));
		Assert.assertEquals(13, f1.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f1.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f1.get(Calendar.YEAR));
		
		Calendar f2 = f.getLastCommit();
		Assert.assertEquals(16, f2.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(7, f2.get(Calendar.MINUTE));
		Assert.assertEquals(13, f2.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f2.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f2.get(Calendar.YEAR));
	}
	
	@Test
	public void testFile_model_Book() {
		CMFile f = repository.allAsMap().get("src/model/Book.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/model/Book.java", f.getFile());
		Assert.assertEquals(9, f.getRevisions());
		Assert.assertEquals(1, f.getRefactorings());
		Assert.assertEquals(1, f.getBugfixes());
		Assert.assertEquals(1, f.getUniqueAuthorsQuantity());
		Assert.assertEquals(97, f.getLocAdded()); // verified
		Assert.assertEquals(56, f.getLocRemoved()); // verified
		Assert.assertEquals(32, f.getMaxLocAdded()); // verified
		Assert.assertEquals(49, f.getMaxLocRemoved()); // verified
		Assert.assertEquals(10.777777, f.getAvgLocAdded(), 0.01); // verified: 97 loc total / 9 commits
		Assert.assertEquals(6.222222, f.getAvgLocRemoved(), 0.01); // verified: 56 loc total / 9 commits
		Assert.assertEquals(153, f.getCodeChurn()); // verified: LOC added 97 + LOC deleted 56 = 153
		Assert.assertEquals(54, f.getMaxCodeChurn()); // verified: 5 added + 49 deleted = 54
		Assert.assertEquals(17, f.getAvgCodeChurn(), 0.01); // verified: 153 / 9 = 17
		Assert.assertEquals(8, f.getMaxChangeset()); // verified: max files commited together: a100580cbd16bace6aa3bafc95aa1ea495632818
		Assert.assertEquals(3.666666, f.getAvgChangeset(), 0.01); // total changeset / revisions = 33/9 (9 commit where that file is included)
		Assert.assertEquals(0, f.getWeightedAge(), 0.01); // 0 because files are modified within 1-2 days
		Assert.assertEquals(0, f.getWeeks()); // 0 because files are modified within 1-2 days
		
		Calendar f1 = f.getFirstCommit();
		Assert.assertEquals(8, f1.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(44, f1.get(Calendar.MINUTE));
		Assert.assertEquals(13, f1.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f1.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f1.get(Calendar.YEAR));
		
		Calendar f2 = f.getLastCommit();
		Assert.assertEquals(16, f2.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(7, f2.get(Calendar.MINUTE));
		Assert.assertEquals(13, f2.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f2.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f2.get(Calendar.YEAR));
	}
	
	@Test
	public void testFile_model_CD() {
		CMFile f = repository.allAsMap().get("src/model/CD.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/model/CD.java", f.getFile());
		Assert.assertEquals(9, f.getRevisions());
		Assert.assertEquals(1, f.getRefactorings());
		Assert.assertEquals(2, f.getBugfixes());
		Assert.assertEquals(1, f.getUniqueAuthorsQuantity());
		Assert.assertEquals(85, f.getLocAdded()); // verified
		Assert.assertEquals(55, f.getLocRemoved()); // verified
		Assert.assertEquals(38, f.getMaxLocAdded()); // verified
		Assert.assertEquals(50, f.getMaxLocRemoved()); // verified
		Assert.assertEquals(9.444444, f.getAvgLocAdded(), 0.01); // verified: 85 loc total / 9 commits
		Assert.assertEquals(6.111111, f.getAvgLocRemoved(), 0.01); // verified: 55 loc total / 9 commits
		Assert.assertEquals(140, f.getCodeChurn()); // verified: LOC added 85 + LOC deleted 55 = 140
		Assert.assertEquals(55, f.getMaxCodeChurn()); // verified: 5 added + 50 deleted = 55
		Assert.assertEquals(15.555555, f.getAvgCodeChurn(), 0.01); // verified: 140 / 9 = 15.55
		Assert.assertEquals(8, f.getMaxChangeset()); // verified: max files commited together: a100580cbd16bace6aa3bafc95aa1ea495632818
		Assert.assertEquals(3.666666, f.getAvgChangeset(), 0.01); // total changeset / revisions = 33/9 (9 commit where that file is included)
		Assert.assertEquals(0, f.getWeightedAge(), 0.01); // 0 because files are modified within 1-2 days
		Assert.assertEquals(0, f.getWeeks()); // 0 because files are modified within 1-2 days
		
		Calendar f1 = f.getFirstCommit();
		Assert.assertEquals(8, f1.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(38, f1.get(Calendar.MINUTE));
		Assert.assertEquals(13, f1.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f1.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f1.get(Calendar.YEAR));
		
		Calendar f2 = f.getLastCommit();
		Assert.assertEquals(16, f2.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(07, f2.get(Calendar.MINUTE));
		Assert.assertEquals(13, f2.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f2.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f2.get(Calendar.YEAR));
	}
	
	@Test
	public void testFile_model_DVD() {
		CMFile f = repository.allAsMap().get("src/model/DVD.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/model/DVD.java", f.getFile());
		Assert.assertEquals(7, f.getRevisions());
		Assert.assertEquals(1, f.getRefactorings());
		Assert.assertEquals(0, f.getBugfixes());
		Assert.assertEquals(1, f.getUniqueAuthorsQuantity());
		Assert.assertEquals(72, f.getLocAdded()); // verified
		Assert.assertEquals(55, f.getLocRemoved()); // verified
		Assert.assertEquals(33, f.getMaxLocAdded()); // verified
		Assert.assertEquals(50, f.getMaxLocRemoved()); // verified
		Assert.assertEquals(10.285714, f.getAvgLocAdded(), 0.01); // verified: 72 loc total / 7 commits
		Assert.assertEquals(7.857142, f.getAvgLocRemoved(), 0.01); // verified: 55 loc total / 7 commits
		Assert.assertEquals(127, f.getCodeChurn()); // verified: LOC added 72 + LOC deleted 55 = 127
		Assert.assertEquals(55, f.getMaxCodeChurn()); // verified: 5 added + 50 deleted = 50
		Assert.assertEquals(18.142857, f.getAvgCodeChurn(), 0.01); // verified: 127 / 7 = 18.142857
		Assert.assertEquals(8, f.getMaxChangeset()); // verified: max files commited together: a100580cbd16bace6aa3bafc95aa1ea495632818
		Assert.assertEquals(4.285714, f.getAvgChangeset(), 0.01); // total changeset / revisions = 30/7 (7 commit where that file is included)
		Assert.assertEquals(0, f.getWeightedAge(), 0.01); // 0 because files are modified within 1-2 days
		Assert.assertEquals(0, f.getWeeks()); // 0 because files are modified within 1-2 days
		
		Calendar f1 = f.getFirstCommit();
		Assert.assertEquals(8, f1.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(40, f1.get(Calendar.MINUTE));
		Assert.assertEquals(13, f1.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f1.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f1.get(Calendar.YEAR));
		
		Calendar f2 = f.getLastCommit();
		Assert.assertEquals(16, f2.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(7, f2.get(Calendar.MINUTE));
		Assert.assertEquals(13, f2.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f2.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f2.get(Calendar.YEAR));
	}

	@Test
	public void testFile_model_Customer() {
		CMFile f = repository.allAsMap().get("src/model/Customer.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/model/Customer.java", f.getFile());
		Assert.assertEquals(4, f.getRevisions());
		Assert.assertEquals(0, f.getRefactorings());
		Assert.assertEquals(0, f.getBugfixes());
		Assert.assertEquals(1, f.getUniqueAuthorsQuantity());
		Assert.assertEquals(68, f.getLocAdded()); // verified
		Assert.assertEquals(5, f.getLocRemoved()); // verified
		Assert.assertEquals(32, f.getMaxLocAdded()); // verified
		Assert.assertEquals(2, f.getMaxLocRemoved()); // verified
		Assert.assertEquals(17, f.getAvgLocAdded(), 0.01); // verified: 68 loc total / 4 commits
		Assert.assertEquals(1.25, f.getAvgLocRemoved(), 0.01); // verified: 5 loc total / 4 commits
		Assert.assertEquals(73, f.getCodeChurn()); // verified: LOC added 68 + LOC deleted 5 = 73
		Assert.assertEquals(32, f.getMaxCodeChurn()); // verified: 32 added + 0 deleted = 32 or 30 added + 2 deleted = 32
		Assert.assertEquals(18.25, f.getAvgCodeChurn(), 0.01); // verified: 73 / 4 = 18.25
		Assert.assertEquals(5, f.getMaxChangeset()); // verified: max files commited together: 4ee76ac608c07d3c77cd283546e658bf3ea91adc
		Assert.assertEquals(2.25, f.getAvgChangeset(), 0.01); // total changeset / revisions = 9/4 (4 commit where that file is included)
		Assert.assertEquals(0, f.getWeightedAge(), 0.01); // 0 because files are modified within 1-2 days
		Assert.assertEquals(0, f.getWeeks()); // 0 because files are modified within 1-2 days
		
		Calendar f1 = f.getFirstCommit();
		Assert.assertEquals(9, f1.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(43, f1.get(Calendar.MINUTE));
		Assert.assertEquals(13, f1.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f1.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f1.get(Calendar.YEAR));
		
		Calendar f2 = f.getLastCommit();
		Assert.assertEquals(10, f2.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(2, f2.get(Calendar.MINUTE));
		Assert.assertEquals(13, f2.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f2.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f2.get(Calendar.YEAR));
	}

	@Test
	public void testFile_model_IArticle() {
		CMFile f = repository.allAsMap().get("src/model/IArticle.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/model/IArticle.java", f.getFile());
		Assert.assertEquals(4, f.getRevisions());
		Assert.assertEquals(0, f.getRefactorings());
		Assert.assertEquals(0, f.getBugfixes());
		Assert.assertEquals(1, f.getUniqueAuthorsQuantity());
		Assert.assertEquals(21, f.getLocAdded()); // verified
		Assert.assertEquals(4, f.getLocRemoved()); // verified
		Assert.assertEquals(11, f.getMaxLocAdded()); // verified
		Assert.assertEquals(3, f.getMaxLocRemoved()); // verified
		Assert.assertEquals(5.25, f.getAvgLocAdded(), 0.01); // verified: 21 loc total / 4 commits
		Assert.assertEquals(1, f.getAvgLocRemoved(), 0.01); // verified: 4 loc total / 4 commits
		Assert.assertEquals(25, f.getCodeChurn()); // verified: LOC added 21 + LOC deleted 4 = 25
		Assert.assertEquals(11, f.getMaxCodeChurn()); // verified: 11 added + 0 deleted = 11
		Assert.assertEquals(6.25, f.getAvgCodeChurn(), 0.01); // verified: 25 / 4 = 6.25
		Assert.assertEquals(4, f.getMaxChangeset()); // verified: max files commited together: several
		Assert.assertEquals(3.5, f.getAvgChangeset(), 0.01); // total changeset / revisions = 14/4 (4 commit where that file is included)
		Assert.assertEquals(0, f.getWeightedAge(), 0.01); // 0 because files are modified within 1-2 days
		Assert.assertEquals(0, f.getWeeks()); // 0 because files are modified within 1-2 days
		
		Calendar f1 = f.getFirstCommit();
		Assert.assertEquals(8, f1.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(38, f1.get(Calendar.MINUTE));
		Assert.assertEquals(13, f1.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f1.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f1.get(Calendar.YEAR));
		
		Calendar f2 = f.getLastCommit();
		Assert.assertEquals(9, f2.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(36, f2.get(Calendar.MINUTE));
		Assert.assertEquals(13, f2.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f2.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f2.get(Calendar.YEAR));
	}
	
	@Test
	public void testFile_model_Order() {
		CMFile f = repository.allAsMap().get("src/model/Order.java");
		
		Assert.assertNotNull(f);
		
		Assert.assertEquals("src/model/Order.java", f.getFile());
		Assert.assertEquals(2, f.getRevisions());
		Assert.assertEquals(0, f.getRefactorings());
		Assert.assertEquals(0, f.getBugfixes());
		Assert.assertEquals(1, f.getUniqueAuthorsQuantity());
		Assert.assertEquals(45, f.getLocAdded()); // verified
		Assert.assertEquals(0, f.getLocRemoved()); // verified
		Assert.assertEquals(40, f.getMaxLocAdded()); // verified
		Assert.assertEquals(0, f.getMaxLocRemoved()); // verified
		Assert.assertEquals(22.5, f.getAvgLocAdded(), 0.01); // verified: 45 loc total / 2 commits
		Assert.assertEquals(0, f.getAvgLocRemoved(), 0.01); // verified: 0 loc total / 2 commits
		Assert.assertEquals(45, f.getCodeChurn()); // verified: LOC added 45 + LOC deleted 0 = 45
		Assert.assertEquals(40, f.getMaxCodeChurn()); // verified: 40 added + 0 deleted = 40
		Assert.assertEquals(22.5, f.getAvgCodeChurn(), 0.01); // verified: 45 / 2 = 22.5
		Assert.assertEquals(5, f.getMaxChangeset()); // verified: max files commited together: several
		Assert.assertEquals(3.5, f.getAvgChangeset(), 0.01); // total changeset / revisions = 7/2 (2 commit where that file is included)
		Assert.assertEquals(0, f.getWeightedAge(), 0.01); // 0 because files are modified within 1-2 days
		Assert.assertEquals(0, f.getWeeks()); // 0 because files are modified within 1-2 days
		
		Calendar f1 = f.getFirstCommit();
		Assert.assertEquals(9, f1.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(43, f1.get(Calendar.MINUTE));
		Assert.assertEquals(13, f1.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f1.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f1.get(Calendar.YEAR));
		
		Calendar f2 = f.getLastCommit();
		Assert.assertEquals(10, f2.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(2, f2.get(Calendar.MINUTE));
		Assert.assertEquals(13, f2.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(6, f2.get(Calendar.MONTH)+1);
		Assert.assertEquals(2017, f2.get(Calendar.YEAR));
	}*/
	
}
