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
	private Button buttonReadFXML;

	@FXML
	private Label labelName;

	@FXML
	private TreeView<String> treeView;

	@InjectViewModel
	private MainViewModel viewModel;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		treeView.setRoot(viewModel.rootItem);
		treeView.setShowRoot(false);
		labelName.textProperty().bindBidirectional(viewModel.fileName);
	}

	@FXML
	void readFXML() throws SAXException, IOException {
		viewModel.readFXML();
	}

}
