package node;

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
public class FXMLNode {

	/**
	 * String with the name of this FXMLNode.
	 */
	private String name;
	/**
	 * String with the package and name of this FXMLNode's Controller.
	 */
	private String controller;
	/**
	 * String with the path of this FXMLNode's FXML file.
	 * <p>
	 * Example: package.Controller
	 */
	private String path;
	/**
	 * ArrayList containing the children from this FXMLNode.
	 */
	private ArrayList<FXMLNode> children = new ArrayList<>();
	/**
	 * The parent of this FXMLNode.
	 */
	private FXMLNode parent;

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
	 * Sets the {@link #name} of this FXMLNode.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
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
	 * Sets the {@link #controller} name to this FXMLNode.
	 * 
	 * @param controller
	 *            Location and name of the controller.
	 *            <p>
	 *            Example: package.Controller
	 */
	public void setController(String controller) {
		this.controller = controller;
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
	 * Sets the {@link #path} of the FXML file to this FXMLNode. If FXML is
	 * included, the path will be normalized and be relative to its parent's.
	 * 
	 * @param path
	 *            The relative path to the FXML file.
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Returns the {@link #children} of this FXMLNode. ArrayList contains
	 * FXMLNodes from included FXMLs in the FXML file.
	 * 
	 * @return An ArrayList of FXMLNodes.
	 */
	public ArrayList<FXMLNode> getChildren() {
		return children;
	}

	/**
	 * Sets an ArrayList of FXMLNodes to this FXMLNode's {@link #children}. The
	 * ArrayList is a list of included FXMLs.
	 * 
	 * @param listOfChildren
	 *            An ArrayList of FXMLNodes.
	 */
	public void setChildren(ArrayList<FXMLNode> listOfChildren) {
		this.children = listOfChildren;
	}

	/**
	 * Returns the {@link #parent} from this FXMLNode.
	 * 
	 * @return FXMLNode's parent.
	 */
	public FXMLNode getParent() {
		return this.parent;
	}

	/**
	 * Sets the {@link #parent} of this FXMLNode.
	 * 
	 * @param parent
	 *            The {@link FXMLNode} to be set as {@link #parent}.
	 */
	public void setParent(FXMLNode parent) {
		this.parent = parent;
	}

}