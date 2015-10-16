package node;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * FXMLNode is an Object that contains information about an FXML file and its
 * included FXMLs. Information includes: its {@link #path}, its {@link #name},
 * its {@link #controller} name from the fx:controller attribute, and a list of
 * other included FXML files ({@link #children}) from the &ltfx:include&gt tag.
 * Included FXMLs will also have information about its {@link #parent}.
 * 
 * @author gerardo.balderas
 *
 */
@Deprecated
public class OldFXMLNode {
	// TODO make class immutable
	// TODO make variables final, except for children
	/**
	 * ArrayList containing the children from this FXMLNode.
	 */
	private ArrayList<OldFXMLNode> children = new ArrayList<>();

	/**
	 * String with the package and name of this FXMLNode's Controller.
	 */
	private String controller;
	/**
	 * String with the filename of this FXMLNode.
	 */
	private String name;
	/**
	 * The parent of this FXMLNode.
	 */
	// TODO remove parent -> try to find relative path to fxml without parent
	private OldFXMLNode parent;
	/**
	 * String with the path of this FXMLNode's FXML file.
	 * <p>
	 * Example: package.Controller
	 */
	private String path;

	/**
	 * Constructor. Sets the path and the file name of the FXML file.
	 * 
	 * @param pathToFXML
	 *            String with the path to the FXML file.
	 */
	// TODO add all info on constructor, except for children
	public OldFXMLNode(String pathToFXML) {
		this.path = pathToFXML;
		this.name = Paths.get(pathToFXML).getFileName().toString();
	}

	public OldFXMLNode(String pathToFXML, String controller) {
		this.path = pathToFXML;
		this.name = Paths.get(pathToFXML).getFileName().toString();
		this.controller = controller;
	}

	public OldFXMLNode(String pathToFXML, String controller, ArrayList<OldFXMLNode> children) {
		this.path = pathToFXML;
		this.name = Paths.get(pathToFXML).getFileName().toString();
		this.controller = controller;
		this.children = children;
	}

	/**
	 * Adds an FXMLNode to this FXMLNode's children. Also sets parent, path and
	 * name.
	 * 
	 * @param child
	 *            FXMLNode to be added.
	 */
	public void addChild(OldFXMLNode child) {
		this.children.add(child);
		child.setCoreData(this);
	}

	/**
	 * Returns the {@link #children} of this FXMLNode. ArrayList contains
	 * FXMLNodes from included FXMLs in the FXML file.
	 * 
	 * @return An ArrayList of FXMLNodes.
	 */
	public ArrayList<OldFXMLNode> getChildren() {
		return children;
	}

	/**
	 * Returns the {@link #controller} of this FXMLNode.
	 * <p>
	 * Example: package.Controller
	 * 
	 * @return A String of the location of the Controller and its name. If there
	 *         is no controller it returns null.
	 */
	public String getController() {
		return controller;
	}

	/**
	 * Returns the {@link #name} of this FXMLNode, normally the file name of the
	 * FXML. file.
	 * 
	 * @return A String with the name of this FXMLNode.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the {@link #parent} from this FXMLNode.
	 * 
	 * @return FXMLNode's parent.
	 */
	public OldFXMLNode getParent() {
		return this.parent;
	}

	/**
	 * Returns the {@link #path} of its FXML file.
	 * 
	 * @return The path of this FXMLNode FXML file.
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Parses this FXMLNode and sets, if found, its Controller and Children.
	 */
	// TODO will be removed
	public void parseForInformation() {
		this.controller = OldFXMLReader.getController(this.getPath());
		this.setChildren(OldFXMLReader.getChildren(this.getPath()));
	}

	/**
	 * Sets an ArrayList of FXMLNodes to this FXMLNode's {@link #children}. The
	 * ArrayList is a list of included FXMLs.
	 * 
	 * @param listOfChildren
	 *            An ArrayList of FXMLNodes.
	 */
	// TODO to be removed
	private void setChildren(ArrayList<OldFXMLNode> listOfChildren) {
		listOfChildren.forEach(n -> {
			n.setCoreData(this);
		});
		this.children = listOfChildren;
	}

	/**
	 * Sets the Core Data of an FXMLNode without the need to Parse the FXML
	 * file. Requires to know which is the parent of this FXMLNode. Will throw a
	 * NullPointerException if the parent is itself.
	 * <p>
	 * Sets the name, path and its parent. The path is normalized to that of the
	 * parent's.
	 * 
	 * @param parent
	 *            The parent of this FXMLNode.
	 */
	// TODO to be removed
	private void setCoreData(OldFXMLNode parent) {
		// normalizes path of FXML, sets name and parent
		Path path = Paths.get(parent.getPath().replace(parent.getName(), "") + this.getPath()).normalize();
		this.parent = parent;
		this.name = path.getFileName().toString();
		this.path = path.toString(); // sets new normalized path
	}

}