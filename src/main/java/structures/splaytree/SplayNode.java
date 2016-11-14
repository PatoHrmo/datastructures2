package structures.splaytree;



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
	/**
	 * Porovná k¾úè tejto nódy s inım k¾úèom
	 * @param key k¾úè ktorı bude porovnanı s k¾úèom tejto nódy
	 * @return záporné, kladné èislo alebo nulu
	 */
	protected int compareKeys(K key) {
		return this.key.compareTo(key)*(-1);
	}
	/**
	 * 
	 * @return dáta v tejto nóde
	 */
	public T getData() {
		return data;
	}
	/**
	 * nastaví dáta v tejto nóde
	 * @param data
	 */
	public void setData(T data) {
		this.data = data;
	}
	/**
	 * 
	 * @return k¾úè tejto nódy
	 */
	public K getKey() {
		return key;
	}
	/**
	 * nastavı k¾úè tejto nódy
	 * @param key
	 */
	public void setKey(K key) {
		this.key = key;
	}
	/**
	 * 
	 * @return poèet synov tejto nódy
	 */
	int getNumberofKids() {
		int numberofKids = 0;
		if(this.left!=null) numberofKids++;
		if(this.right!=null) numberofKids++;
		return numberofKids;
		
	}
	
}
