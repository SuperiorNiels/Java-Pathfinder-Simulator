
public class Node {
	private Node parent;
	private int[] node;
	public Node(int[] node, Node parent) {
		this.setParent(parent);
		this.setNode(node);
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public int[] getNode() {
		return node;
	}
	public void setNode(int[] node) {
		this.node = node;
	}
}
