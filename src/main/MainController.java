package main;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.utils.ProjectConfig;
import main.utils.ProjectManager;

public class MainController {
	
	@FXML
	private Button CreateMod;
	
	@FXML
	private Button openProject;
	
	@FXML
	private ListView<String> projectList;
	
	private ProjectManager projectManager;
	
	@FXML
	public void initialize() {
		projectManager = new ProjectManager();
		
		System.out.println("Loaded projects: " + projectManager.getProjects().size());
		
		for (ProjectConfig config : projectManager.getProjects()) {
			System.out.println("Project: " + config.getProjectName());
			projectList.getItems().add(config.getProjectName());
		}
	}
	
	public void onOpenProject() throws IOException{
		String selected = projectList.getSelectionModel().getSelectedItem();
		
		if (selected == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Select a project first");
			alert.show();
			return;
		}
		
		//Load Project
		ProjectConfig config = projectManager.getProjects().stream()
				.filter(p -> p.getProjectName().equals(selected))
				.findFirst()
				.orElse(null);
		
		if (config == null) return;
		
		//Open IDE.fxml
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/IDE.fxml"));
		Parent root = loader.load();
		
		// Give controller IDE
		IDEController ideController = loader.getController();
		
		//transfer selected project
		ideController.setProjectConfig(config);
		
		Stage stage = new Stage();
		stage.setTitle("IDE - " + config.getProjectName());
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	public void onQuit(ActionEvent event) {
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Exit");
		alert.setHeaderText("Do you really want to quit?");
		alert.setContentText("Press OK to exit, or Cancel to stay");
		
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.isPresent() && result.get() == ButtonType.OK) {
			Platform.exit();
		}
	}
	
	public void onNew(ActionEvent event) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SelectGameMenu.fxml"));
		Parent root = loader.load();
		
		Stage newWindow = new Stage();
		newWindow.setTitle("Create Project");
		newWindow.setScene(new Scene(root));;
		newWindow.show();
		
	}
	
	public void onCreateModClick() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SelectGameMenu.fxml"));
		Parent root = loader.load();
		
		Stage newWindow = new Stage();
		newWindow.setTitle("Create Project");
		newWindow.setScene(new Scene(root));;
		newWindow.show();
		//Close window
		Stage currentStage = (Stage) CreateMod.getScene().getWindow();
		currentStage.close();
	}
}
