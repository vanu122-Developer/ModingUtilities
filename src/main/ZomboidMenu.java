package main;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
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
//				Files.createDirectories(newFolder);
//				showAlert("Success", "Folder created:\n" + newFolder);
				createWorkshopStructure(Paths.get(basePath), folderName);
				showAlert("Success", "Workshop mod created:\n"+ basePath + "/" + newFolder);
			} else {
				showAlert("Info", "Folder already exists:\n" + newFolder);
			}
		} catch (IOException e) {
			showAlert("Error", "Failed to create folder:\n" + e.getMessage());
		}
	}
	
	//select folder for create mod
	@FXML
	private void onChooseFolderClick() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Select folder");
		
		//Open window selecter
		File selectedDirectory = directoryChooser.showDialog(new Stage());
		
		if (selectedDirectory != null) {
			//Select to TextField
			DocumentFolder.setText(selectedDirectory.getAbsolutePath());
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

	//Stryctory Modification
	private void createWorkshopStructure(Path basePath, String modName) throws IOException {
	    Path workshopRoot = basePath.resolve(modName);

	    // 1. Корневая папка Workshop-мода
	    Files.createDirectories(workshopRoot);

	    // 2. preview.png
	    Path preview = workshopRoot.resolve("preview.png");
	    if (!Files.exists(preview)) {
	        Files.createFile(preview);
	    }

	    // 3. workshop.txt
	    Path workshopTxt = workshopRoot.resolve("workshop.txt");
	    if (!Files.exists(workshopTxt)) {
	        Files.writeString(workshopTxt,
	                "version=1\n" +
	                "title=" + modName + "\n" +
	                "description=\n" +
	                "tags=\n" +
	                "visibility=public\n"
	        );
	    }

	    // 4. Contents/mods/<ModName>/
	    Path modRoot = workshopRoot.resolve("Contents/mods/" + modName);
	    Files.createDirectories(modRoot);

	    // 5. mod.info
	    Path modInfo = modRoot.resolve("mod.info");
	    if (!Files.exists(modInfo)) {
	        Files.writeString(modInfo,
	                "name=" + modName + "\n" +
	                "id=" + modName + "\n" +
	                "description=\n" +
	                "poster=poster.png\n"
	        );
	    }

	    // 6. poster.png
	    Path poster = modRoot.resolve("poster.png");
	    if (!Files.exists(poster)) {
	        Files.createFile(poster);
	    }

	    // 7. media/ структура
	    Path media = modRoot.resolve("media");
	    Files.createDirectories(media.resolve("lua/client"));
	    Files.createDirectories(media.resolve("lua/server"));
	    Files.createDirectories(media.resolve("lua/shared"));
	    Files.createDirectories(media.resolve("lua/ui"));
	    Files.createDirectories(media.resolve("scripts"));
	    Files.createDirectories(media.resolve("textures"));
	    Files.createDirectories(media.resolve("sound"));
	    Files.createDirectories(media.resolve("models"));
	    Files.createDirectories(media.resolve("animations"));
	}


	
	private void showAlert(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.show();
	}
	
}
