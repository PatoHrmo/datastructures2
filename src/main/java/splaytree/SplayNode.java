package splaytree;



public class SplayNode<K extends Comparable<K>,T> {
	SplayNode<K,T> parent;
	SplayNode<K,T> left;
	SplayNode<K,T> right;
	private K key;
	private T data;
	public SplayNode(K key, T data) {
		this.setKey(key);
		this.setData(data);
		parent = null;
		left = null;
		right = null;
	}
	public SplayNode(K key, T data, SplayNode<K,T> parent) {
		this.setKey(key);
		this.setData(data);
		this.parent = parent;
		left = null;
		right = null;
	}
	protected int compareNodes(SplayNode<K,T> node) {
		return this.key.compareTo(node.key);
	}
	protected int compareKeys(K key) {
		return this.key.compareTo(key)*(-1);
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public K getKey() {
		return key;
	}
	public void setKey(K key) {
		this.key = key;
	}
	int getNumberofKids() {
		int numberofKids = 0;
		if(this.left!=null) numberofKids++;
		if(this.right!=null) numberofKids++;
		return numberofKids;
		
	}
	
}
