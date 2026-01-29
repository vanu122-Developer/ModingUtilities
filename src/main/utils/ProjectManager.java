package main.utils;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectManager {
	
	private final Path projectsFolder;
	private final List<ProjectConfig> projects = new ArrayList<>();
	
	public ProjectManager() {
		this.projectsFolder = Path.of(System.getProperty("user.home"), "Documents", "ModingUtilities");
		loadProjects();
	}
	
	public List<ProjectConfig> getProjects() {
		return projects;
	}
	
	private void loadProjects() {
		projects.clear();
		
		if (!Files.exists(projectsFolder)) {
			return;
		}
		
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(projectsFolder, "*.proj")) {
			for (Path file : stream) {
				try {
					ProjectConfig config = ProjectConfig.load(file);
					projects.add(config);
				} catch (IOException e) {
					System.err.println("Failed to load project: " + file);
				}
			}
		} catch (IOException e) {
			System.err.println("Failed to scan project folder");
		}
	}
}
