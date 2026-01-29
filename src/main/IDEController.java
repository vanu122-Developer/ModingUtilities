package main;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import main.utils.FileTreeBuilder;
import main.utils.GetFullPath;
import main.utils.OpenFileInEditor;
import main.utils.ProjectConfig;

public class IDEController {

	@FXML
	private AnchorPane FolderProjects;
	
	@FXML 
	private TextArea editorArea;
	
	//File save
	private File currentFile;
	
	private ProjectConfig config;
	
	public void setProjectConfig(ProjectConfig config) {
		this.config = config;
		loadProjectTree();
	}
	
	public void loadProjectTree() {
		if (config == null) return;
		
		TreeItem<File> root = FileTreeBuilder.buildTree(new File(config.getModPath()));
		TreeView<File> tree = new TreeView<>(root);
		
		AnchorPane.setTopAnchor(tree, 0.0);
		AnchorPane.setBottomAnchor(tree, 0.0);
		AnchorPane.setLeftAnchor(tree, 0.0);
		AnchorPane.setRightAnchor(tree, 0.0);
		
		FolderProjects.getChildren().clear();
		FolderProjects.getChildren().add(tree);
		
		//Double click on file
		tree.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				//Debug
				System.out.println("Double click!");
				
				TreeItem<File> selected = tree.getSelectionModel().getSelectedItem();
				if (selected == null) return;
				
				File file = selected.getValue();
				
				if (file.isFile()) {
					new OpenFileInEditor().openFileEditor(file, editorArea);
				}
				
			}
		});
		tree.setCellFactory(tv -> new javafx.scene.control.TreeCell<File>() {
		    @Override
		    protected void updateItem(File file, boolean empty) {
		        super.updateItem(file, empty);

		        if (empty || file == null) {
		            setText(null);
		        } else {
		            setText(file.getName()); // <-- только имя файла
		        }
		    }
		});

		
	}
	
	@FXML
	public void initialize() {
		//Debug
		System.out.println("IDE loaded");
		
		System.out.println("editorArea = " + editorArea);

	}
}
