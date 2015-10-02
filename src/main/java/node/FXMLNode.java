package node;

import java.util.ArrayList;

public class FXMLNode {

	private String name;
	private String controller;
	private String path;
	private ArrayList<FXMLNode> nodesList = new ArrayList<>();

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
		this.path = path;
	}

	public ArrayList<FXMLNode> getNodesList() {
		return nodesList;
	}

	public void setNodesList(ArrayList<FXMLNode> nodesList) {
		this.nodesList = nodesList;
	}

	public void addNodesToList(FXMLNode node) {
		this.nodesList.add(node);
	}

}