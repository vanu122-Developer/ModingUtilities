package main.utils;

import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Path;


public class ProjectConfig {
	
	private String projectName;
	private String gameName;
	private String gamePath;
	private String modPath;
	
	public ProjectConfig(String projectName, String gameName, String gamePath, String modPath) {
		this.projectName = projectName;
		this.gameName = gameName;
		this.gamePath = gamePath;
		this.modPath = modPath;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public String getGameName() {
		return gameName;
	}
	
	public String getGamePath() {
		return gamePath;
	}
	
	public String getModPath() {
		return modPath;
	}
	
	//
	// SAVE .PROJ FILE
	//
	public void save(Path file) throws IOException {
		String json = """
				{
				"projectName": "%s",
				"gameName": "%s",
				"paths": {
					"gamePath": "%s",
					"modPath": "%s"
					}
				}
				""".formatted(projectName, gameName, gamePath, modPath);
		
		Files.writeString(file, json);
	}
	
	//
	// LOAD .PROJ FILE
	//
	public static ProjectConfig load(Path file) throws IOException {
		String json = Files.readString(file);
		
		String projectName = extract(json, "projectName");
		String gameName = extract(json, "gameName");
		String gamePath = extract(json, "gamePath");
		String modPath = extract(json, "modPath");
		
		ProjectConfig config = new ProjectConfig(projectName, gameName, gamePath, modPath);
		
		return config;
	}
	
	//
	// SIMPLE JSON FIELD EXTRACTOR
	//
	public static String extract(String json, String key) {
	    String pattern = "\"" + key + "\"";
	    int keyIndex = json.indexOf(pattern);
	    if (keyIndex == -1) return null;

	    int colon = json.indexOf(":", keyIndex);
	    int firstQuote = json.indexOf("\"", colon + 1);
	    int secondQuote = json.indexOf("\"", firstQuote + 1);

	    if (firstQuote == -1 || secondQuote == -1) return null;

	    return json.substring(firstQuote + 1, secondQuote);
	}

}
