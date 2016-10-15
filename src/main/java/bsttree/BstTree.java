package bsttree;

public class BstTree<K extends Comparable<K>, T> {
	private BstNode<K, T> root;

	public BstTree() {
		root = null;
	}

	public boolean insert(K key, T newData) {
		if (root == null) {
			root = new BstNode<>(key, newData);
			;
			return true;
		}
		BstNode<K, T> actualNode = root;
		while (true) {
			if (actualNode.compareKeys(key) < 0) {
				if (actualNode.left == null) {
					actualNode.left = new BstNode<>(key, newData);
					return true;
				} else {
					actualNode = actualNode.left;
					continue;
				}
			}
			if (actualNode.compareKeys(key) > 0) {
				if (actualNode.right == null) {
					actualNode.right = new BstNode<>(key, newData);
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
						return actual.getData();
					} else {
						if (isLeftKid) {
							parent.left = null;
							return actual.getData();
						} else {
							parent.right = null;
							return actual.getData();
						}
					}
				}
				if (actual.getNumberofKids() == 1) {
					if (actual == root) {
						if (actual.left == null) {
							root = actual.right;
							return actual.getData();
						}
						if (actual.right == null) {
							root = actual.left;
							return actual.getData();
						}
					}
					if (isLeftKid) {
						if (actual.left == null) {
							parent.left = actual.right;
							return actual.getData();
						}
						if (actual.right == null) {
							parent.left = actual.left;
							return actual.getData();
						}
					} else {
						if (actual.left == null) {
							parent.right = actual.right;
							return actual.getData();
						}
						if (actual.right == null) {
							parent.right = actual.left;
							return actual.getData();
						}
					}
				}
				if (actual.getNumberofKids() == 2) {
					if (isLeftKid) {

					}
				}
			}
			if (actual.compareKeys(key) < 0) {
				if (actual.left == null)
					return null;
				else {
					parent = actual;
					actual = actual.left;
					isLeftKid = true;
				}
			}
			if (actual.compareKeys(key) > 0) {
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

	private void inOrder(BstNode<K, T> node) {
		if (node != null) {
			this.inOrder(node.left);
			System.out.println(node.toString() + System.lineSeparator());
			this.inOrder(node.right);
		}
	}

	public void inOrder() {
		if (root == null)
			return;
		this.inOrder(root);
	}
}
