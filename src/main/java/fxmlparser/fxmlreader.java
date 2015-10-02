package fxmlparser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

public class fxmlreader extends Application {
	static ArrayList<String> source;
	Stage primarystage = new Stage();

	String path;
	String[] pathBase = new String[100];

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
	}

	public void parsefxml() {
		try {
			source = new ArrayList<>();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			FileChooser filechooser = new FileChooser();
			File file = filechooser.showOpenDialog(primarystage.getScene().getWindow());

			path = file.getAbsolutePath().replace(file.getName(), "");
			pathBase[0] = path;

			System.out.println(pathBase[0]);
			source.add(file.getName());
			saxParser.parse(path + source.get(0), handler);

		} catch (Exception e) {
			System.exit(-1);
		}
	}

	DefaultHandler handler = new DefaultHandler() {
		int sourceCounter = 0;
		int baseCounter = 0;

		@Override
		public void startDocument() throws SAXException {
			System.out.println("+++ now parsing: " + source.get(sourceCounter));
		}

		@Override
		public void endDocument() throws SAXException {
			System.out.println("--- end parsing: " + source.get(sourceCounter));
			if (source.get(sourceCounter).contains("../")) {
				baseCounter--;
				System.out.println("##### RETURN TO OLD BASE: " + pathBase[baseCounter]);
			}
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
	            throws SAXException {

			if (attributes.getValue("fx:controller") != null)
				System.out.println("~~~ Controller found!" + attributes.getValue("fx:controller"));

			if (qName.equalsIgnoreCase("fx:include") || uri.equalsIgnoreCase("fx:include"))
				if (attributes.getValue("source") != null) {
					System.out.println("******* include found in " + source.get(sourceCounter));

					sourceCounter++;
					source.add(attributes.getValue("source"));

					Path pathNormalized = Paths.get(pathBase[baseCounter] + source.get(sourceCounter)).normalize();

					if (source.get(sourceCounter).contains("../")) {
						baseCounter++;
						pathBase[baseCounter] = pathNormalized.getParent().toString() + "/";
						System.out.println("##### NEW BASE: " + pathBase[baseCounter]);
					}

					try {
						SAXParserFactory.newInstance().newSAXParser().parse(pathNormalized.toString(), handler);
						source.remove(source.get(sourceCounter));
						sourceCounter--;
					} catch (IOException | ParserConfigurationException e) {
						e.printStackTrace();
					}
				}
		}

	};
}
