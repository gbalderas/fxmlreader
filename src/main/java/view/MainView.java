package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.xml.sax.SAXException;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;

public class MainView implements FxmlView<MainViewModel>, Initializable {

	@FXML
	private TreeView<String> treeView;

	@FXML
	private Label labelName;

	@FXML
	private Button buttonReadFXML;

	@InjectViewModel
	private MainViewModel viewModel;

	@FXML
	void readFXML() throws SAXException, IOException {
		viewModel.readFXML();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		treeView.setRoot(viewModel.rootItem);
		treeView.setShowRoot(false);
		labelName.textProperty().bindBidirectional(viewModel.fileName);
	}

}
