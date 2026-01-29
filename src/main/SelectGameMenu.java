package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import main.utils.SteamFinder;

//Code SelectGameMenu

public class SelectGameMenu {
	@FXML
	private ListView<String> SetGameName;
	
	@FXML Button nextButton;
	
	@FXML
	public void initialize() {
		//List games
		ObservableList<String> data = FXCollections.observableArrayList(
				"Zomboid",
				"Source Game"
				);
		
		SetGameName.setItems(data);
		
		//Button disable in default
		nextButton.setDisable(true);
		
		//On button when select game
		SetGameName.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
			if ("Zomboid".equals(newVal)) {
				nextButton.setDisable(false);
				openZomboidWindow();
			}
			else if ("Source Game".equals(newVal)) {
				nextButton.setDisable(false);
			}
			else {
				nextButton.setDisable(true);
			}
		});
	}
	
	private void openZomboidWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ZomboidMenu.fxml"));
			Parent root = loader.load();
			
			//Controllers
			ZomboidMenu controller = loader.getController();
			//Use find  Path on label
			controller.setGamePath(SteamFinder.findZomboidPath());
			//Use find  Path document on label
			controller.setDocumentPath(SteamFinder.findZomboidPathdocument());
			controller.setDocumentPathTextField(SteamFinder.findZomboidPathdocument());
			
			Stage stage = new Stage();
			stage.setTitle("Zomboid mod setting");
			stage.setScene(new Scene(root));
			stage.show();
			
			((Stage) SetGameName.getScene().getWindow()).close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
