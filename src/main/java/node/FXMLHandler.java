package node;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class FXMLHandler extends DefaultHandler {

	private String path;
	private String pathNoFileName;
	private FXMLNode node;

	private String includedPath;
	private String controller;
	private ArrayList<FXMLNode> includes = new ArrayList<>();;

	public FXMLHandler(String path) {
		this.path = path;
		this.pathNoFileName = path.replace(Paths.get(path).getFileName().toString(), "");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		if (attributes.getValue("fx:controller") != null)
			controller = attributes.getValue("fx:controller");
		if (qName.equalsIgnoreCase("fx:include"))
			if (attributes.getValue("source") != null) {
				String source = attributes.getValue("source");
				includedPath = Paths.get(pathNoFileName + source).normalize().toString();

				try {
					// creates a new FXMLHanlder instance for each include
					FXMLHandler fxmlHandler = new FXMLHandler(includedPath);
					XMLReader reader = XMLReaderFactory.createXMLReader();
					reader.setContentHandler(fxmlHandler);
					reader.parse(includedPath); // loop / looks for innermost
					                            // include

					// adds found include to the include list (will become this'
					// node children)
					if (fxmlHandler.getNode() != null) // no children for
					                                   // innermost include
						includes.add(fxmlHandler.getNode());

				} catch (IOException | SAXException e) {
					System.out.println("No FXML file at " + includedPath);
				}
			}
	}

	@Override
	public void endDocument() throws SAXException {
		// creates an FXMLNode after parsing the document
		node = new FXMLNode(path, controller, includes);
	}

	public FXMLNode getNode() {
		return node; // returns an FXMLNode of the read document
	}

}
