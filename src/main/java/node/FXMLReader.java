package node;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class FXMLReader {

	private String CONTROLLER;
	private ArrayList<FXMLNode> list;

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

	private void lookForNodes(FXMLNode node, XMLReader reader) throws IOException, SAXException {

		for (FXMLNode n : node.getNodesList()) {
			n.setParent(node);
			n.setPath(n.getName());
			n.setController(getFXMLController(reader, n.getPath()));
			n.setNodesList(getListOfFXMLIncludes(reader, n.getPath()));
			lookForNodes(n, reader); // loop
		}

	}

	private String getFXMLController(XMLReader reader, String pathToFXML) throws IOException, SAXException {
		CONTROLLER = "no controller found"; // default, will return if no
		                                    // controller is found
		reader.setContentHandler(controllerHandler);
		reader.parse(pathToFXML);
		return CONTROLLER;
	}

	private ArrayList<FXMLNode> getListOfFXMLIncludes(XMLReader reader, String pathToFXML)
	        throws IOException, SAXException {
		list = new ArrayList<>();
		reader.setContentHandler(includeHandler);
		reader.parse(pathToFXML);
		return list;
	}

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

	@SuppressWarnings("serial")
	class StopParsingException extends SAXException {
		// empty StackTrace
		@Override
		public synchronized Throwable fillInStackTrace() {
			return this;
		}

	}

}