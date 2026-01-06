package main;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class MainController {
	
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
	}
}
