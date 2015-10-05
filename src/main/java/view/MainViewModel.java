package view;

import java.io.File;
import java.io.IOException;

import org.xml.sax.SAXException;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TreeItem;
import javafx.stage.FileChooser;
import main.Main;
import node.FXMLNode;
import node.FXMLReader;

public class MainViewModel implements ViewModel {

	public TreeItem<String> rootItem;
	public SimpleStringProperty fileName = new SimpleStringProperty();
	// rootItem is not visible in TreeView in MainView

	FXMLNode rootNode;

	public MainViewModel() {
		rootItem = new TreeItem<String>("root");
	}

	public void readFXML() throws SAXException, IOException {
		rootItem.getChildren().clear();

		// get fxml file //TODO make sure only .fxml files are acceptable
		FileChooser filechooser = new FileChooser();
		File file = filechooser.showOpenDialog(Main.scene.getWindow());
		fileName.set(file.getName());

		// Read FXML file
		FXMLReader fxmlreader = new FXMLReader();
		rootNode = fxmlreader.readFXML(file);

		// add Items to rootNode
		addTreeItemsFromNode(rootNode, rootItem);
	}

	// recursive method to get to the leaves -> will create an Item[original
	// fxml] with other Items
	private void addTreeItemsFromNode(FXMLNode node, TreeItem<String> leafItem) {
		TreeItem<String> item = new TreeItem<String>();
		for (FXMLNode n : node.getNodesList())
			addTreeItemsFromNode(n, item);// loop

		System.out.println(node.getName() + " (Children:" + node.getNodesList().size() + ") " + " ; "
		        + node.getController() + " ; " + node.getPath());
		item.setValue(node.getName());
		if (item.isLeaf()) // adds * if item is a leaf (end of branch)
			item.setValue("*" + item.getValue());

		leafItem.getChildren().add(item);
		leafItem.setExpanded(true);
	}
}
