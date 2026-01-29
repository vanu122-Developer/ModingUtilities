package main.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javafx.scene.control.TextArea;

public class OpenFileInEditor {
	public void openFileEditor(File file, TextArea editorArea) {
		try {
			String text = Files.readString(file.toPath());
			editorArea.setText(text);
		} catch (IOException e) {
			editorArea.setText("Error read file:\n" + e.getMessage());
		}
	}
}
