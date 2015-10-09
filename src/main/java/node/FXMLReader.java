package node;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * FXMLReader is an Object that can parse an FXML file and retrieve some
 * information from it in the form of an {@link FXMLNode}. Uses the SAX parser
 * XMLReader.
 * 
 * @author gerardo.balderas
 *
 * @see FXMLNode
 * @see XMLReader
 */
public class FXMLReader {

	private static String CONTROLLER;
	private static ArrayList<FXMLNode> list;

	/**
	 * Returns an FXMLNode from the given FXML file. This method implements a
	 * recursive method which follows a Depth-first search to find all included
	 * FXML files within their FXML files.
	 * 
	 * @param fxml
	 *            Root FXML file to be read.
	 * @return The root node of the FXML file as an FXMLNode.
	 * @throws SAXException
	 * @throws IOException
	 * @see FXMLNode
	 */
	public static FXMLNode readFXML(File fxml) throws SAXException, IOException { // TODO
	                                                                       // rename
		// creates the root FXMLNode and parses through it
		FXMLNode rootNode = new FXMLNode();
		XMLReader reader = XMLReaderFactory.createXMLReader();

		rootNode.setName(fxml.getName());
		rootNode.setPath(fxml.getPath());
		rootNode.setController(parseForController(reader, fxml.getPath()));
		rootNode.setChildren(parseForIncludes(reader, fxml.getPath()));

		// parses through included FXML files.
		lookForNodes(rootNode, reader);
		return rootNode;
	}

	/**
	 * Recursive method to parse all included FXMLs and add them to its parent.
	 * Implements a Depth-first search to find all included FXML files.
	 * 
	 * @param node
	 *            Parent FXMLNode, it's children will be parsed.
	 * @param reader
	 *            XMLReader to parse the FXML file.
	 * @throws IOException
	 * @throws SAXException
	 */
	private static void lookForNodes(FXMLNode node, XMLReader reader) throws IOException, SAXException {

		for (FXMLNode child : node.getChildren()) {
			child.setParent(node); // TODO make method for setting path
			                       // [currently:
			// FXMLNode.setPath(String path)]
			child.setPath(child.getName());
			child.setController(parseForController(reader, child.getPath()));
			child.setChildren(parseForIncludes(reader, child.getPath()));
			lookForNodes(child, reader); // loop
		}

	}

	/**
	 * Returns the controllers name from the given FXML file's path. Sets the
	 * content handler to the custom DefaultHandler {@link #controllerHandler}.
	 * 
	 * @param reader
	 *            XMLReader to parse the FXML file.
	 * @param pathToFXML
	 *            Path to FXML file.
	 * @return The name of the controller, if no controller is found it returns
	 *         null.
	 * @throws IOException
	 * @throws SAXException
	 */
	private static String parseForController(XMLReader reader, String pathToFXML) throws IOException, SAXException {
		CONTROLLER = null;
		reader.setContentHandler(controllerHandler);
		reader.parse(pathToFXML);
		return CONTROLLER;
	}

	/**
	 * Returns an ArrayList of included FXMLNodes of parsed FXML file. Sets the
	 * content handler to the custom DefaultHandler {@link #includeHandler}.
	 * 
	 * @param reader
	 *            XMLReader to parse the FXML file.
	 * @param pathToFXML
	 *            Path to FXML file.
	 * @return An ArrayList containing included FXMLNodes
	 * @throws IOException
	 * @throws SAXException
	 */
	private static ArrayList<FXMLNode> parseForIncludes(XMLReader reader, String pathToFXML) throws IOException, SAXException {
		list = new ArrayList<>();
		reader.setContentHandler(includeHandler);
		reader.parse(pathToFXML);
		return list;
	}

	/**
	 * Looks for the fx:controller attribute in the FXML file that is being read
	 * and sets the returning controller name.
	 * 
	 * @see #parseForController(XMLReader, String)
	 */
	private static DefaultHandler controllerHandler = new DefaultHandler() {
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

	/**
	 * Looks for all &ltfx:include&gt elements in the FXML file. It creates a
	 * node for each include and adds it to the returning list
	 * 
	 * @see #parseForIncludes(XMLReader, String)
	 */
	private static DefaultHandler includeHandler = new DefaultHandler() {
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

	/**
	 * Custom Exception to stop parsing. Used when Controller is found, since
	 * there can only be one controller class.
	 * 
	 * @author gerardo.balderas
	 *
	 */
	private static class StopParsingException extends SAXException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 6802692101026207305L;

		// empty StackTrace
		@Override
		public synchronized Throwable fillInStackTrace() {
			return this;
		}

	}

}