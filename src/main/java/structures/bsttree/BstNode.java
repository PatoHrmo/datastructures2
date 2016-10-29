package structures.bsttree;

public class BstNode<K extends Comparable<K>,T> {
	private K key;
    private T nodeData;
	protected BstNode<K,T> left;
	protected BstNode<K,T> right;
	
	public BstNode(K key, T data) {
		this.key = key;
		this.nodeData = data;
		left = null;
		right = null;
	}
	protected int compareNodes(BstNode<K,T> node) {
		return this.key.compareTo(node.key);
	}
	protected int compareKeys(K key) {
		return this.key.compareTo(key);
	}
	public T getData() {
		return nodeData;
	}
	int getNumberofKids() {
		int numberofKids = 0;
		if(this.left!=null) numberofKids++;
		if(this.right!=null) numberofKids++;
		return numberofKids;
		
	}
	@Override
	public String toString () {
		return nodeData.toString();
	}
	
	
}
