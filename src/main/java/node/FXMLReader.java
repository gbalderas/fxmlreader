package node;

import java.io.IOException;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class FXMLReader {

	public static FXMLNode parseFXML(String path) {

		FXMLNode root = null;
		try {
			FXMLHandler fxmlHandler = new FXMLHandler(path);
			XMLReader reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(fxmlHandler);
			reader.parse(path);
			root = fxmlHandler.getNode();
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		return root;
	}

}
