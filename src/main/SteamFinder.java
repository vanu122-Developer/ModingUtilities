package main;

import java.nio.file.*;
import java.util.*;

public class SteamFinder {

	public static String findZomboidPathdocument() {
		String userHome = System.getProperty("user.home");
		String os = System.getProperty("os.name").toLowerCase();
		
		List<Path> possiblePaths = new ArrayList<>();
		
		if (os.contains("win")) {
			possiblePaths.add(Paths.get(userHome, "Zomboid"));
			possiblePaths.add(Paths.get(userHome, "Documents", "Zomboid"));
		} else {
			possiblePaths.add(Paths.get(userHome, "Zomboid"));
			possiblePaths.add(Paths.get(userHome, "Documents", "Zomboid"));
		}
		
		for (Path p : possiblePaths) {
			if (Files.exists(p)) {
				return p.toString();
			}
		}
		return null;
	}
	
    public static String findZomboidPath() {
        List<Path> possibleLibraryFiles = new ArrayList<>();

        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            // Windows
            possibleLibraryFiles.add(Paths.get(System.getenv("PROGRAMFILES(X86)"), "Steam", "steamapps", "libraryfolders.vdf"));
            possibleLibraryFiles.add(Paths.get("C:\\Steam\\steamapps\\libraryfolders.vdf"));
        } else {
            // Linux
            possibleLibraryFiles.add(Paths.get(System.getProperty("user.home"), ".steam", "steam", "steamapps", "libraryfolders.vdf"));
            possibleLibraryFiles.add(Paths.get(System.getProperty("user.home"), ".local", "share", "Steam", "steamapps", "libraryfolders.vdf"));
        }

        for (Path libraryFile : possibleLibraryFiles) {
            if (Files.exists(libraryFile)) {
                String result = searchInLibraryFile(libraryFile);
                if (result != null) return result;
            }
        }

        return null; // не нашли
    }

    private static String searchInLibraryFile(Path libraryFile) {
        try {
            List<String> lines = Files.readAllLines(libraryFile);

            for (String line : lines) {
                line = line.trim();

                if (line.contains("\"path\"")) {
                    String path = line.split("\"")[3];

                    Path zomboidPath = Paths.get(path, "steamapps", "common", "ProjectZomboid");

                    if (Files.exists(zomboidPath)) {
                        return zomboidPath.toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

