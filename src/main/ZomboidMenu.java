package main;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class ZomboidMenu {
	@FXML
	private Label PathLabel;
	@FXML
	private Label PathLabelDocument;
	@FXML
	private TextField DocumentFolder;
	@FXML
	private TextField NameModeFolder;
	@FXML
	private Button DoneButton;
	
	
	//Save folder debug
	public void setDocumentPath(String path) {
		if (path == null) {
			PathLabelDocument.setText("Document folder not found");
		} else {
			PathLabelDocument.setText(path);
		}
	}
	
	//Save folder for text field 
	public void setDocumentPathTextField(String path) {
		if (path == null) {
			DocumentFolder.setText("Document folder not found");
		} else {
			DocumentFolder.setText(path + "/Workshop");
		}
	}
	
	//Game folder debug
	public void setGamePath(String path) {
		if (path == null) {
			PathLabel.setText("Game folder not found");
		} else {
			PathLabel.setText(path);
		}
	}
	@FXML
	private void ActionButtoneDone() {
		try {
			//Folder document text
			String basePath = DocumentFolder.getText().trim();
			//Name folder mode
			String folderName = NameModeFolder.getText().trim();
			
			if (!isFolderNameValid(folderName)) {
			    showAlert("Error", "Folder name contains invalid characters");
			    return;
			}
			
			if (basePath.isEmpty() || folderName.isEmpty()) {
				showAlert("Error", "Path or folder name is empty");
				return;
			}
			
			Path newFolder = Paths.get(basePath, folderName);
			
			if (!Files.exists(newFolder)) {
				Files.createDirectories(newFolder);
				showAlert("Success", "Folder created:\n" + newFolder);
			} else {
				showAlert("Info", "Folder already exists:\n" + newFolder);
			}
		} catch (IOException e) {
			showAlert("Error", "Failed to create folder:\n" + e.getMessage());
		}
	}
	
	private boolean isFolderNameValid(String name) {
	    String invalid = "\\/:*?\"<>|";

	    if (name == null || name.isBlank())
	        return false;

	    for (char c : invalid.toCharArray()) {
	        if (name.indexOf(c) >= 0)
	            return false;
	    }

	    return true;
	}

	

	
	private void showAlert(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.show();
	}
	
}
