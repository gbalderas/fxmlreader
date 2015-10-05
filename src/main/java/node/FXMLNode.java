package node;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FXMLNode {

	private String name;
	private String controller;
	private String path;
	private ArrayList<FXMLNode> nodesList = new ArrayList<>();
	private FXMLNode parent;

	public FXMLNode getParent() {
		return this.parent;
	}

	public void setParent(FXMLNode parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		if (this.parent != null) { // TODO do this in FXMLReader
			Path paths = Paths.get(parent.getPath().replace(parent.getName(), "") + path).normalize();
			path = paths.toString();
			this.name = paths.getFileName().toString();
		}
		this.path = path;
	}

	public ArrayList<FXMLNode> getNodesList() {
		return nodesList;
	}

	public void setNodesList(ArrayList<FXMLNode> nodesList) {
		this.nodesList = nodesList;
	}

}