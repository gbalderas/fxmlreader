package node;

import java.nio.file.Paths;
import java.util.ArrayList;

public final class FXMLNode {

	private final ArrayList<FXMLNode> children;
	private final String controller;
	private final String name;
	private final String path;

	public FXMLNode(String path, String controller, ArrayList<FXMLNode> children) {
		this.path = path;
		this.name = Paths.get(path).getFileName().toString();
		this.controller = controller;
		this.children = children;
	}

	public ArrayList<FXMLNode> getChildren() {
		return children;
	}

	public FXMLNode getChild(int position) {
		return this.children.get(position);
	}

	public String getController() {
		return controller;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}
}
