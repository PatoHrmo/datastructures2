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
	 * Porovn� k��� tejto n�dy s in�m k���om
	 * @param key k��� ktor� bude porovnan� s k���om tejto n�dy
	 * @return z�porn�, kladn� �islo alebo nulu
	 */
	protected int compareKeys(K key) {
		return this.key.compareTo(key)*(-1);
	}
	/**
	 * 
	 * @return d�ta v tejto n�de
	 */
	public T getData() {
		return data;
	}
	/**
	 * nastav� d�ta v tejto n�de
	 * @param data
	 */
	public void setData(T data) {
		this.data = data;
	}
	/**
	 * 
	 * @return k��� tejto n�dy
	 */
	public K getKey() {
		return key;
	}
	/**
	 * nastav� k��� tejto n�dy
	 * @param key
	 */
	public void setKey(K key) {
		this.key = key;
	}
	/**
	 * 
	 * @return po�et synov tejto n�dy
	 */
	int getNumberofKids() {
		int numberofKids = 0;
		if(this.left!=null) numberofKids++;
		if(this.right!=null) numberofKids++;
		return numberofKids;
		
	}
	
}
