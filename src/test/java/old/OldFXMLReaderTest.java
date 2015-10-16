package old;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import old.OldFXMLNode;
import old.OldFXMLReader;

@Deprecated
public class OldFXMLReaderTest {

	String path;
	OldFXMLNode root;

	@Test // will be changed
	public void parseForInformationTest() {
		OldFXMLNode pickers = root.getChildren().get(0);
		pickers.parseForInformation();
		assertEquals("should contain one include", 1, pickers.getChildren().size());
		assertEquals("include file should be toolbar.fxml", "toolbar.fxml", pickers.getChildren().get(0).getName());
		assertEquals("controller should be null", "not.a.Controller", pickers.getController());
	}

	@Test
	public void parsingTest() {
		OldFXMLNode views = root.getChildren().get(0).getChildren().get(0).getChildren().get(0);
		assertEquals("node should be named views.fxml", "views.fxml", views.getName());
		assertTrue("node should have no children", views.getChildren().isEmpty());
		assertTrue("node should have a controller", views.getController() != null);
		assertEquals("controller should be views.Controller", "views.Controller", views.getController());
		OldFXMLNode views2 = root.getChildren().get(1).getChildren().get(0);
		assertEquals("nodes should have same name", views.getName(), views2.getName());
		assertEquals("nodes should have same controller", views.getController(), views2.getController());
	}

	@Test
	public void rootTest() {
		assertEquals("root children should be 2", 2, root.getChildren().size());
		assertEquals("controller should be controller.Controller", "controller.Controller", root.getController());
		assertEquals("name should be fxml.fxml", "fxml.fxml", root.getName());
		assertEquals("path should be \"src/main/resources/fxmlparser/fxml.fxml\"", path, root.getPath());

		assertEquals("first child name is pickers.fxml", "pickers.fxml", root.getChildren().get(0).getName());
		assertEquals("first child controller is null", "not.a.Controller", root.getChildren().get(0).getController());
	}

	@Before
	public void setup() {
		path = "src/test/resources/fxmlparser/fxml.fxml";
		root = OldFXMLReader.parse(path);
	}

}
