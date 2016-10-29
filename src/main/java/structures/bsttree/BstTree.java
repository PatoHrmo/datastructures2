package structures.bsttree;

import java.util.Stack;

public class BstTree<K extends Comparable<K>, T> {
	private BstNode<K, T> root;
	private int size;

	public BstTree() {
		root = null;
		size = 0;
	}

	public boolean insert(K key, T newData) {
		if (root == null) {
			root = new BstNode<>(key, newData);
			size++;
			return true;
		}
		BstNode<K, T> actualNode = root;
		while (true) {
			if (actualNode.compareKeys(key) < 0) {
				if (actualNode.left == null) {
					actualNode.left = new BstNode<>(key, newData);
					size++;
					return true;
				} else {
					actualNode = actualNode.left;
					continue;
				}
			}
			if (actualNode.compareKeys(key) > 0) {
				if (actualNode.right == null) {
					actualNode.right = new BstNode<>(key, newData);
					size++;
					return true;
				} else {
					
					actualNode = actualNode.right;
					continue;
				}
			}
			return false;
		}
	}

	public T find(K key) {
		if (root == null)
			return null;
		BstNode<K, T> actual = root;
		while (true) {
			if (actual.compareKeys(key) == 0)
				return actual.getData();
			if (actual.compareKeys(key) < 0) {
				if (actual.left == null)
					return null;
				else
					actual = actual.left;
			}
			if (actual.compareKeys(key) > 0) {
				if (actual.right == null)
					return null;
				else
					actual = actual.right;
			}
		}
	}

	public T delete(K key) {
		if (root == null)
			return null;
		BstNode<K, T> actual = root;
		BstNode<K, T> parent = actual;
		boolean isLeftKid = true;
		while (true) {
			if (actual.compareKeys(key) == 0) {
				if (actual.getNumberofKids() == 0) {
					if (actual == root) {
						root = null;
						size--;
						return actual.getData();
					} else {
						if (isLeftKid) {
							parent.left = null;
							size--;
							return actual.getData();
						} else {
							parent.right = null;
							size--;
							return actual.getData();
						}
					}
				}
				if (actual.getNumberofKids() == 1) {
					if (actual == root) {
						if (actual.left == null) {
							root = actual.right;
							size--;
							return actual.getData();
						}
						if (actual.right == null) {
							root = actual.left;
							size--;
							return actual.getData();
						}
					}
					else if (isLeftKid) {
						if (actual.left == null) {
							parent.left = actual.right;
							size--;
							return actual.getData();
						}
						if (actual.right == null) {
							parent.left = actual.left;
							size--;
							return actual.getData();
						}
					} else {
						if (actual.left == null) {
							parent.right = actual.right;
							size--;
							return actual.getData();
						}
						if (actual.right == null) {
							parent.right = actual.left;
							size--;
							return actual.getData();
						}
					}
				}
				if (actual.getNumberofKids() == 2) {
					if(actual == root) {
						BstNode<K, T> successor = getSuccessorAndDeleteItFromParent(actual);
						successor.left = root.left;
						successor.right = root.right;
						root = successor;
						size--;
						return actual.getData();
					}
					// deleted node is left kid of parent
					else if (isLeftKid) {
						BstNode<K, T> successor = getSuccessorAndDeleteItFromParent(actual);
						successor.left = actual.left;
						successor.right = actual.right;
						parent.left = successor;
						size--;
						return actual.getData();
					} 
					// deleted node is right kid of parent
					else {
						BstNode<K, T> successor = getSuccessorAndDeleteItFromParent(actual);
						successor.left = actual.left;
						successor.right = actual.right;
						parent.right = successor;
						size--;
						return actual.getData();
					}
				}
			}
			else if (actual.compareKeys(key) < 0) {
				if (actual.left == null)
					return null;
				else {
					parent = actual;
					actual = actual.left;
					isLeftKid = true;
				}
			}
			else if (actual.compareKeys(key) > 0) {
				if (actual.right == null)
					return null;
				else {
					parent = actual;
					actual = actual.right;
					isLeftKid = false;
				}
			}
		}
	}
	private BstNode<K, T> getSuccessorAndDeleteItFromParent(BstNode<K, T> node) {
		BstNode<K, T> successor = node.right;
		BstNode<K, T> successorParent = node;
		
		while(successor.left != null){
			
			successorParent = successor;
			successor = successor.left;
		}
		// succesor ma na pravo deti
		if(successor.right != null) {
			//a je hned na pravo
			if(successorParent==node) {
				successorParent.right = successor.right;
				return successor;
			}
			//a nie je hned na pravo
			else {
				successorParent.left = successor.right;
				successor.right = null;
				return successor;
			}
		}
		// successor nema na pravo deti
		else {
			// a je hned na pravo
			if(successorParent==node) {
				successorParent.right = null;
				return successor;
			}
			//a nie je hned na pravo
			else {
				successorParent.left = null;
				return successor;
			}
		}
	}
	public int getSize() {
		return this.size;
	}

	
     T[] toArray(T[] pole){
		if(root==null) return null;
		int i = 0;
		Stack<BstNode<K,T>> zasobnik  = new Stack<>();
		BstNode<K, T> actual = root;
		while(actual!=null) {
			zasobnik.push(actual);
			actual = actual.left;
		}
		while(zasobnik.size()>0) {
			actual = zasobnik.pop();
			pole[i] = actual.getData();
			i++;
			if(actual.right!=null) {
				actual = actual.right;
				while(actual!=null) {
					zasobnik.push(actual);
					actual = actual.left;
				}
			}
			
		}
		return pole;
	}
}
