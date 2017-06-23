package ch.unibe.scg.metrics.szz;

import java.util.List;
import org.wickedsource.diffparser.api.*;
import org.wickedsource.diffparser.api.model.*;

public class App2 {

	public static void main( String[] args ) {
		UnifiedDiffParser parser = new UnifiedDiffParser();
		
		String diff = ""
				+"--- a/src/model/CD.java\n"
				+"+++ b/src/model/CD.java\n"
				+"@@ -43,6 +43,6 @@\n"
				+"\n"
				+" 	@Override\n"
				+" 	public void setYear(int year) {\n"
				+"-		year = year;\n"
				+"+		this.year = year;\n"
				+" 	}\n"
				+"}\n"
				+"\n";
		
		List<Diff> diffs = parser.parse(diff.getBytes());
		System.out.println(diffs);
    }
}
