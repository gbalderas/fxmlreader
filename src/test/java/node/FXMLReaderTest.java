package node;

import static org.junit.Assert.*;

import org.junit.Test;

public class FXMLReaderTest {

	FXMLNode root;

	@Test
	public void rootTest() {
		String path = "src/main/resources/fxmlparser/fxml.fxml";
		root = FXMLReader.parse(path);
		assertEquals("root children should be 4", 4, root.getChildren().size());
		assertEquals("controller should be controller.Controller", "controller.Controller", root.getController());
		assertEquals("name should be fxml.fxml", "fxml.fxml", root.getName());
		assertEquals("path should be \"src/main/resources/fxmlparser/fxml.fxml\"", path, root.getPath());
		assertNull(root.getParent());

		assertEquals("first child name is MainView.fxml","MainView.fxml", root.getChildren().get(0).getName());
		assertEquals("first child controller is view.MainView", "view.MainView", root.getChildren().get(0).getController());
	}
	
	
	

}
