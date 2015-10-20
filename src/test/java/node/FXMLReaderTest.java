package node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

public class FXMLReaderTest {

	FXMLNode origin;
	String pathToOrigin;

	@Test
	public void noIncludesTest() {
		String pathToViews = Paths.get("src/test/resources/fxmls/views/views.fxml").toString();
		FXMLNode views = FXMLReader.parseFXML(pathToViews);

		assertEquals("Path should be " + pathToViews, pathToViews, views.getPath());
		assertEquals("Name should be views.fxml", "views.fxml", views.getName());
		assertEquals("controller should be views.Controller", "views.Controller", views.getController());
		assertTrue("Children should be empty", views.getChildren().isEmpty());
	}

	@Test
	public void originFXMLTest() {
		assertEquals("Path should be " + pathToOrigin, pathToOrigin, origin.getPath());
		assertEquals("Name should be origin.fxml", "origin.fxml", origin.getName());
		assertEquals("controller should be origin.Controller", "origin.Controller", origin.getController());
		assertEquals("Children should be 2", 2, origin.getChildren().size());
	}

	@Test
	public void pickersFXMLTest() {
		String pathToPickersFXML = Paths.get("src/test/resources/fxmls/pickers.fxml").toString();
		FXMLNode pickers = origin.getChild(0);

		assertEquals("Path should be " + pathToPickersFXML, pathToPickersFXML, pickers.getPath());
		assertEquals("Name should be toolbar.fxml", "pickers.fxml", pickers.getName());
		assertEquals("controller should be pickers.Controller", "pickers.Controller", pickers.getController());
		assertEquals("Children should be 1", 1, pickers.getChildren().size());
		assertEquals("Child should be toolbar.fxml", "toolbar.fxml", pickers.getChild(0).getName());
	}

	@Before
	public void setup() {
		pathToOrigin = Paths.get("src/test/resources/fxmls/origin.fxml").toString();
		origin = FXMLReader.parseFXML(pathToOrigin);
	}

	@Test
	public void toolbarFXMLTest() {
		String pathToToolbarFXML = Paths.get("src/test/resources/fxmls/toolbar.fxml").toString();
		FXMLNode toolbar = origin.getChild(0).getChild(0);

		assertEquals("Path should be " + pathToToolbarFXML, pathToToolbarFXML, toolbar.getPath());
		assertEquals("Name should be toolbar.fxml", "toolbar.fxml", toolbar.getName());
		assertEquals("controller should be toolbar.Controller", "toolbar.Controller", toolbar.getController());
		assertEquals("Children should be 1", 1, toolbar.getChildren().size());
		assertEquals("Child should be views.fxml", "views.fxml", toolbar.getChild(0).getName());
	}

	@Test
	public void viewsFXMLTest() {
		String pathToViewsFXML = Paths.get("src/test/resources/fxmls/views/views.fxml").toString();
		FXMLNode views = origin.getChild(1).getChild(0);

		assertEquals("Path should be " + pathToViewsFXML, pathToViewsFXML, views.getPath());
		assertEquals("Name should be views.fxml", "views.fxml", views.getName());
		assertEquals("controller should be views.Controller", "views.Controller", views.getController());
		assertTrue("Children should be empty", views.getChildren().isEmpty());

	}

}
