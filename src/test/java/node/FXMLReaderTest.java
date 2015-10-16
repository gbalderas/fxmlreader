package node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FXMLReaderTest {

	@Test
	public void noIncludesTest() {
		String pathToRoot = "src\\test\\resources\\fxmls\\views\\views.fxml";
		FXMLNode root = FXMLReader.parseFXML(pathToRoot);
		assertEquals("Path should be " + pathToRoot, pathToRoot, root.getPath());
		assertEquals("Name should be views.fxml", "views.fxml", root.getName());
		assertEquals("controller should be views.Controller", "views.Controller", root.getController());
		assertTrue("Children should be empty", root.getChildren().isEmpty());
	}

	@Test
	public void originFXMLTest() {
		String pathToOriginFXML = "src/test\\resources\\fxmls/origin.fxml";
		FXMLNode origin = FXMLReader.parseFXML(pathToOriginFXML);
		assertEquals("Path should be " + pathToOriginFXML, pathToOriginFXML, origin.getPath());
		assertEquals("Name should be origin.fxml", "origin.fxml", origin.getName());
		assertEquals("controller should be origin.Controller", "origin.Controller", origin.getController());
		assertEquals("Children should be 2", 2, origin.getChildren().size());
	}

	@Test
	public void pickersFXMLTest() {
		String pathToOriginFXML = "src/test/resources/fxmls/origin.fxml";
		FXMLNode origin = FXMLReader.parseFXML(pathToOriginFXML);
		FXMLNode pickers = origin.getChild(0);

		String pathToPickersFXML = "src\\test\\resources\\fxmls\\pickers.fxml";
		assertEquals("Path should be " + pathToPickersFXML, pathToPickersFXML, pickers.getPath());
		assertEquals("Name should be toolbar.fxml", "pickers.fxml", pickers.getName());
		assertEquals("controller should be pickers.Controller", "pickers.Controller", pickers.getController());
		assertEquals("Children should be 1", 1, pickers.getChildren().size());
		assertEquals("Child should be toolbar.fxml", "toolbar.fxml", pickers.getChild(0).getName());
	}

	@Test
	public void toolbarFXMLTest() {
		String pathToOriginFXML = "src/test/resources/fxmls/origin.fxml";
		FXMLNode origin = FXMLReader.parseFXML(pathToOriginFXML);
		FXMLNode toolbar = origin.getChild(0).getChild(0);
		String pathToToolbarFXML = "src\\test\\resources\\fxmls\\toolbar.fxml";
		assertEquals("Path should be " + pathToToolbarFXML, pathToToolbarFXML, toolbar.getPath());
		assertEquals("Name should be toolbar.fxml", "toolbar.fxml", toolbar.getName());
		assertEquals("controller should be toolbar.Controller", "toolbar.Controller", toolbar.getController());
		assertEquals("Children should be 1", 1, toolbar.getChildren().size());
		assertEquals("Child should be views.fxml", "views.fxml", toolbar.getChild(0).getName());
	}

	@Test
	public void viewsFXMLTest() {
		String pathToOriginFXML = "src/test/resources/fxmls/origin.fxml";
		FXMLNode origin = FXMLReader.parseFXML(pathToOriginFXML);
		FXMLNode views = origin.getChild(1).getChild(0);
		String pathToViewsFXML = "src\\test\\resources\\fxmls\\views\\views.fxml";
		assertEquals("Path should be " + pathToViewsFXML, pathToViewsFXML, views.getPath());
		assertEquals("Name should be views.fxml", "views.fxml", views.getName());
		assertEquals("controller should be views.Controller", "views.Controller", views.getController());
		assertTrue("Children should be empty", views.getChildren().isEmpty());

	}

}
