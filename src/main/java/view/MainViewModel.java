package view;

import java.io.File;
import java.io.IOException;

import org.xml.sax.SAXException;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TreeItem;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import main.Main;
//import node.FXMLNode;
//import node.FXMLReader;
import node.FXMLNode;
import node.FXMLReader;

public class MainViewModel implements ViewModel {

	public SimpleStringProperty fileName = new SimpleStringProperty();
	// rootItem is not visible in TreeView in MainView
	public TreeItem<String> rootItem;

	public MainViewModel() {
		rootItem = new TreeItem<String>("root");
	}

	public void readFXML() throws SAXException, IOException {
		rootItem.getChildren().clear();

		File file = getFXML();
		fileName.set(file.getName());

		// Read FXML file
		FXMLNode rootNode = FXMLReader.parseFXML(file.getPath());

		// add Items to rootNode
		addTreeItemsFromNode(rootNode, rootItem);
	}

	// recursive method to get to the leaves -> will create an Item[original
	// fxml] with other Items
	private void addTreeItemsFromNode(FXMLNode node, TreeItem<String> treeItem) {
		TreeItem<String> item = new TreeItem<String>();
		for (FXMLNode n : node.getChildren())
			addTreeItemsFromNode(n, item);// loop

		System.out.println(node.getName() + " (Children:" + node.getChildren().size() + ") " + " ; "
		        + node.getController() + " ; " + node.getPath());
		item.setValue(node.getName());
		if (item.isLeaf()) // adds * if item is a leaf (end of branch)
			item.setValue("*" + item.getValue());

		treeItem.getChildren().add(item);
		treeItem.setExpanded(true);
	}

	// FileChooser for FXML files
	private File getFXML() {
		FileChooser filechooser = new FileChooser();
		filechooser.getExtensionFilters().add(new ExtensionFilter("FXML files (*.fxml)", "*.fxml"));
		File file = filechooser.showOpenDialog(Main.scene.getWindow());
		return file;
	}
}
