package main.utils;

import java.io.File;

import javafx.scene.control.TreeItem;

public class GetFullPath {
	public static String getFullPath(TreeItem<String> item) {
		StringBuilder path = new StringBuilder(item.getValue());
		TreeItem<String> parent = item.getParent();
		
		while (parent != null) {
			path.insert(0, parent.getValue() + File.separator);
			parent = parent.getParent();
		}
		return path.toString();
	}
}
