package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class window extends Application {
	
	public void start(Stage primaryStage) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainMenu.fxml"));

		Scene mainScene = new Scene(root);

		primaryStage.setTitle("Moding Utilities");
		primaryStage.setScene(mainScene);
		primaryStage.show();
		
	}
	//Start Program
	public static void main(String[] args) {
		launch(args);
	}
	
	
}

