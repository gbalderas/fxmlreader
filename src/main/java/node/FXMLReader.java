package node;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class FXMLReader {

	private String CONTROLLER;
	private ArrayList<FXMLNode> list;

	// makes first node (rootNode) from an fxml file, then parses it and looks
	// for fx:controller and fx:includes
	public FXMLNode readFXML(File fxml) throws SAXException, IOException {
		FXMLNode rootNode = new FXMLNode();
		XMLReader reader = XMLReaderFactory.createXMLReader();

		rootNode.setName(fxml.getName());
		rootNode.setPath(fxml.getPath());
		rootNode.setController(getFXMLController(reader, fxml.getPath()));
		rootNode.setNodesList(getListOfFXMLIncludes(reader, fxml.getPath()));

		lookForNodes(rootNode, reader);
		return rootNode;
	}

	// recursive method, looks in the included nodes for more included nodes
	private void lookForNodes(FXMLNode node, XMLReader reader) throws IOException, SAXException {

		for (FXMLNode n : node.getNodesList()) {
			n.setParent(node); // TODO make method for setting path [currently:
			                   // FXMLNode.setPath(String path)]
			n.setPath(n.getName());
			n.setController(getFXMLController(reader, n.getPath()));
			n.setNodesList(getListOfFXMLIncludes(reader, n.getPath()));
			lookForNodes(n, reader); // loop
		}

	}

	// returns controller string of parsed FXML file, using controllerHandler
	// for SAXParsing
	private String getFXMLController(XMLReader reader, String pathToFXML) throws IOException, SAXException {
		CONTROLLER = "no controller found"; // default, will return if no
		                                    // controller is found
		reader.setContentHandler(controllerHandler);
		reader.parse(pathToFXML);
		return CONTROLLER;
	}

	// makes a list of FXMLNodes of parsed FXML file, using includeHandler for
	// SAXParsing
	private ArrayList<FXMLNode> getListOfFXMLIncludes(XMLReader reader, String pathToFXML)
	        throws IOException, SAXException {
		list = new ArrayList<>();
		reader.setContentHandler(includeHandler);
		reader.parse(pathToFXML);
		return list;
	}

	// looks for fx:controller attribute in FXML file
	private DefaultHandler controllerHandler = new DefaultHandler() {
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) {
			if (attributes.getValue("fx:controller") != null) {
				CONTROLLER = attributes.getValue("fx:controller");
				try {
					throw new StopParsingException();
				} catch (StopParsingException e) {
				}
			}
		}
	};

	// looks for all fx:include elements in FXML file, creates a node for each
	// include
	private DefaultHandler includeHandler = new DefaultHandler() {
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) {
			if (qName.equalsIgnoreCase("fx:include"))
				if (attributes.getValue("source") != null) {
					FXMLNode node = new FXMLNode();
					String source = attributes.getValue("source");
					node.setName(source);
					list.add(node);
				}
		}

	};

	// Custom exception to stop parsing
	@SuppressWarnings("serial")
	class StopParsingException extends SAXException {
		// empty StackTrace
		@Override
		public synchronized Throwable fillInStackTrace() {
			return this;
		}

	}

}