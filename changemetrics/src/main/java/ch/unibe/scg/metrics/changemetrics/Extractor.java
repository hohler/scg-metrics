package ch.unibe.scg.metrics.changemetrics;

import java.nio.file.Path;

public class Extractor {

	private Path path;
	
	public Extractor(String gitUrl) {
		// First clone Repo and then set path
	}
	
	public Extractor(Path path) {
		this.path = path;
	}
}
