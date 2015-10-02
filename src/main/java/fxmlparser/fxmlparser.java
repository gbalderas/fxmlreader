package fxmlparser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class fxmlparser extends Application {
	static ArrayList<String> source;
	Stage primarystage = new Stage();
	File file;
	String path;

	public static void main(String args[]) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		primarystage = stage;
		VBox vbox = new VBox();
		Scene scene = new Scene(vbox);
		Button button = new Button();
		button.setOnAction((ActionEvent) -> {
			parsefxml();
		});
		vbox.getChildren().add(button);

		primarystage.setScene(scene);
		primarystage.show();

		parsefxml();
	}

	public void parsefxml() {
		try {
			source = new ArrayList<>();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			FileChooser filechooser = new FileChooser();
			file = filechooser.showOpenDialog(primarystage.getScene().getWindow());

			String path = file.getAbsolutePath();
			source.add(file.getCanonicalPath());
			saxParser.parse(path, handler);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		parsefxml();

	}

	DefaultHandler handler = new DefaultHandler() {
		int counter = 0;

		@Override
		public void startDocument() throws SAXException {
			System.out.println("now parsing: " + source.get(counter));
		}

		@Override
		public void endDocument() throws SAXException {
			System.out.println("end parsing: " + source.get(counter));
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
	            throws SAXException {

			if (attributes.getValue("fx:controller") != null)
				System.out.println("Controller found!" + attributes.getValue("fx:controller"));

			if (qName.equalsIgnoreCase("fx:include"))
				if (attributes.getValue("source") != null) {
					System.out.println("include found in " + source.get(counter));
					counter++;
					System.out.println(attributes.getValue("source"));
					try {

						file = new File(
	                            file.getCanonicalPath().replace(file.getName(), "") + attributes.getValue("source"));
						source.add(file.getCanonicalPath());
						SAXParserFactory.newInstance().newSAXParser().parse(file.getCanonicalPath(), handler);
						source.remove(source.get(counter));
						counter--;
						file = new File(source.get(counter));
					} catch (IOException | ParserConfigurationException e) {
						e.printStackTrace();
					}
				}
		}

	};
}
