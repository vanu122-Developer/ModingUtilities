package main.utils;

import java.io.File;
import javafx.scene.control.TreeItem;

public class FileTreeBuilder {

    public static TreeItem<File> buildTree(File rootFile) {
        TreeItem<File> root = new TreeItem<>(rootFile);

        File[] files = rootFile.listFiles();
        if (files == null) return root;

        for (File file : files) {
            TreeItem<File> item = new TreeItem<>(file);

            if (file.isDirectory()) {
                item.getChildren().addAll(buildTree(file).getChildren());
            }

            root.getChildren().add(item);
        }

        return root;
    }
}
