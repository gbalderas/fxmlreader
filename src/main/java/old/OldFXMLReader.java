package old;

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
 * information from it in the form of an {@link OldFXMLNode}. Uses the SAX
 * parser XMLReader.
 * 
 * @author gerardo.balderas
 *
 * @see OldFXMLNode
 * @see XMLReader
 */
@Deprecated
public class OldFXMLReader {
	// TODO parse for controllers and includes at the same time
	// TODO parse the include while fxml is parsing

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

	private static String CONTROLLER;

	/**
	 * Looks only for the fx:controller attribute in the FXML file that is being
	 * read and sets the returning controller name.
	 * <p>
	 * After controller is found it will mark it and end parsing.
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
	 * Looks only for all &ltfx:include&gt elements in the FXML file. It creates
	 * a node for each include and adds it to a returning list
	 * 
	 * @see #parseForIncludes(XMLReader, String)
	 */
	private static DefaultHandler includeHandler = new DefaultHandler() {
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) {
			if (qName.equalsIgnoreCase("fx:include"))
				if (attributes.getValue("source") != null) {

					String source = attributes.getValue("source");
					OldFXMLNode node = new OldFXMLNode(source);// sets
					// a
	                // relative
	                // path
	                // to
	                // the
	                // new
	                // FXMLNode
					list.add(node);
				}
		}

	};

	private static ArrayList<OldFXMLNode> list;

	/**
	 * Returns an ArrayList of FXMLNodes containing only the path to the FXML
	 * file. This method uses {@link #includeHandler} to find the Elements to
	 * the ArrayList.
	 * 
	 * @param pathToFXML
	 *            The path to the FXML file.
	 * @return An ArrayList of FXMLNodes.
	 */
	public static ArrayList<OldFXMLNode> getChildren(String pathToFXML) {
		XMLReader reader = null;
		try {
			reader = XMLReaderFactory.createXMLReader();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return parseForIncludes(reader, pathToFXML);
	}

	/**
	 * Returns a String with the Controllers package and name from the FXML
	 * file's path. If No Controller is found it returns null.
	 * <p>
	 * Example: package.ControllerClass
	 * 
	 * @param pathToFXML
	 *            The path to the FXML file.
	 * @return A String of the Controllers package and name.
	 */
	public static String getController(String pathToFXML) {
		XMLReader reader = null;
		try {
			reader = XMLReaderFactory.createXMLReader();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return parseForController(reader, pathToFXML);
	}

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
	 * @see OldFXMLNode
	 */
	public static OldFXMLNode parse(File fxml) throws SAXException, IOException {
		return parse(fxml.getPath());
	}

	/**
	 * Returns an FXMLNode from the given FXML String path. This method
	 * implements a recursive method which follows a Depth-first search to find
	 * all included FXML files within their FXML files.
	 * 
	 * @param pathToFXML
	 *            Path to the FXML in a String.
	 * @return The root node of the FXML String path as an FXMLNode.
	 * @throws SAXException
	 * @throws IOException
	 * @see OldFXMLNode
	 */
	public static OldFXMLNode parse(String pathToFXML) {
		OldFXMLNode rootNode = new OldFXMLNode(pathToFXML);
		// change, see: lookForNodes()
		rootNode.parseForInformation();

		lookForNodes(rootNode);
		return rootNode;
	}

	/**
	 * Recursive method to parse all included FXMLs and add them to its parent.
	 * Implements a Depth-first search to find all included FXML files.
	 * 
	 * @param node
	 *            Parent FXMLNode, it's children will be parsed.
	 * @throws IOException
	 * @throws SAXException
	 */
	private static void lookForNodes(OldFXMLNode node) {

		for (OldFXMLNode child : node.getChildren()) {
			// TODO change to parse from this class
			child.parseForInformation();

			lookForNodes(child); // loop
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
	private static String parseForController(XMLReader reader, String pathToFXML) {
		CONTROLLER = null;
		reader.setContentHandler(controllerHandler);
		try {
			reader.parse(pathToFXML);
		} catch (IOException | SAXException e) {
			e.printStackTrace();
		}
		String returningController = CONTROLLER;
		CONTROLLER = null;
		return returningController;
	}

	/**
	 * Returns an ArrayList of FXMLNodes of the parsed FXML file. Sets the
	 * content handler to the custom DefaultHandler {@link #includeHandler}.
	 * 
	 * @param reader
	 *            XMLReader to parse the FXML file.
	 * @param pathToFXML
	 *            Path to FXML file.
	 * @return An ArrayList containing FXMLNodes
	 * @throws IOException
	 * @throws SAXException
	 */
	private static ArrayList<OldFXMLNode> parseForIncludes(XMLReader reader, String pathToFXML) {
		list = new ArrayList<>();
		reader.setContentHandler(includeHandler);
		try {
			reader.parse(pathToFXML);
		} catch (IOException | SAXException e) {
			e.printStackTrace();
		}
		return list;
	}

}