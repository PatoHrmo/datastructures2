package structures.splaytree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class SplayTree<K extends Comparable<K>, T> {
	private SplayNode<K, T> root;
	private int size;

	public SplayTree() {
		this.root = null;
		this.size = 0;
	}

	public boolean insert(K key, T newData) {
		if (root == null) {
			root = new SplayNode<>(key, newData);
			size++;
			return true;
		}
		SplayNode<K, T> actualNode = root;
		while (true) {

			if (actualNode.compareKeys(key) < 0) {

				if (actualNode.left == null) {
					actualNode.left = new SplayNode<>(key, newData, actualNode);
					splay(actualNode.left);
					size++;
					return true;
				} else {
					actualNode = actualNode.left;
					continue;
				}
			}
			if (actualNode.compareKeys(key) > 0) {

				if (actualNode.right == null) {
					actualNode.right = new SplayNode<>(key, newData, actualNode);
					splay(actualNode.right);
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
		SplayNode<K, T> actual = root;
		while (true) {
			if (actual.compareKeys(key) == 0) {
				splay(actual);
				return actual.getData();
			}

			if (actual.compareKeys(key) < 0) {
				if (actual.left == null) {
					splay(actual);
					return null;
				} else
					actual = actual.left;
			}
			if (actual.compareKeys(key) > 0) {
				if (actual.right == null) {
					splay(actual);
					return null;
				} else
					actual = actual.right;
			}
		}
	}

	public T delete(K key) {
		if (root == null)
			return null;

		SplayNode<K, T> actual = root;

		while (true) {
			if (actual.compareKeys(key) == 0) {
				if (actual.getNumberofKids() == 0) {
					if (actual == root) {
						root = null;
						size--;
						return actual.getData();
					} else {
						if (actual == actual.parent.left) {
							actual.parent.left = null;
							splay(actual.parent);
							actual.parent = null;
							size--;
							return actual.getData();
						} else {
							actual.parent.right = null;
							splay(actual.parent);
							actual.parent = null;
							size--;
							return actual.getData();
						}
					}
				} else if (actual.getNumberofKids() == 1) {
					if (actual == root) {
						if (root.left == null) {
							root = root.right;
							root.parent = null;
							size--;
							return actual.getData();
						}
						if (root.right == null) {
							root = root.left;
							root.parent = null;
							size--;
							return actual.getData();
						}
					}
					// je lavy syn
					else if (actual == actual.parent.left) {
						if (actual.left == null) {
							actual.parent.left = actual.right;
							actual.right.parent = actual.parent;
							splay(actual.parent);
							size--;
							return actual.getData();
						}
						if (actual.right == null) {
							actual.parent.left = actual.left;
							actual.left.parent = actual.parent;
							splay(actual.parent);
							size--;
							return actual.getData();
						}
						// je pravy syn
					} else {
						if (actual.left == null) {
							actual.parent.right = actual.right;
							actual.right.parent = actual.parent;
							splay(actual.parent);
							size--;
							return actual.getData();
						}
						if (actual.right == null) {
							actual.parent.right = actual.left;
							actual.left.parent = actual.parent;
							splay(actual.parent);
							size--;
							return actual.getData();
						}
					}
				} else if (actual.getNumberofKids() == 2) {
					if (actual == root) {
						SplayNode<K, T> successor = getSuccessorAndDeleteItFromParent(actual);
						successor.left = root.left;
						successor.right = root.right;
						if (root.left != null)
							root.left.parent = successor;
						if (root.right != null)
							root.right.parent = successor;
						root = successor;
						root.parent = null;
						size--;
						return actual.getData();
					}
					// deleted node is left kid of parent
					else if (actual == actual.parent.left) {
						SplayNode<K, T> successor = getSuccessorAndDeleteItFromParent(actual);
						successor.left = actual.left;
						successor.right = actual.right;
						successor.parent = actual.parent;
						actual.parent.left = successor;
						actual.left.parent = successor;
						if (actual.right != null)
							actual.right.parent = successor;
						splay(actual.parent);
						size--;
						return actual.getData();
					}
					// deleted node is right kid of parent
					else {
						SplayNode<K, T> successor = getSuccessorAndDeleteItFromParent(actual);
						successor.left = actual.left;
						successor.right = actual.right;
						successor.parent = actual.parent;
						actual.parent.right = successor;
						actual.left.parent = successor;
						if (actual.right != null)
							actual.right.parent = successor;
						splay(actual.parent);
						size--;
						return actual.getData();
					}
				}
			} else if (actual.compareKeys(key) < 0) {
				if (actual.left == null)
					return null;
				else {
					actual = actual.left;
				}
			} else if (actual.compareKeys(key) > 0) {
				if (actual.right == null)
					return null;
				else {
					actual = actual.right;
				}
			}
		}
	}

	private SplayNode<K, T> getSuccessorAndDeleteItFromParent(SplayNode<K, T> node) {
		SplayNode<K, T> successor = node.right;

		while (successor.left != null) {
			successor = successor.left;
		}
		// succesor ma na pravo deti
		if (successor.right != null) {
			// a je hned na pravo
			if (successor.parent == node) {
				successor.parent.right = successor.right;
				successor.right.parent = successor.parent;
				successor.parent = null;
				successor.right = null;
				return successor;
			}
			// a nie je hned na pravo
			else {
				successor.parent.left = successor.right;
				successor.right.parent = successor.parent;
				successor.right = null;
				successor.parent = null;
				return successor;
			}
		}
		// successor nema na pravo deti
		else {
			// a je hned na pravo
			if (successor.parent == node) {
				successor.parent.right = null;
				successor.parent = null;
				return successor;
			}
			// a nie je hned na pravo
			else {
				successor.parent.left = null;
				successor.parent = null;
				return successor;
			}
		}
	}

	private void rightRotation(SplayNode<K, T> node) {
		SplayNode<K, T> leftSon = node.left;
		if (leftSon != null) {
			node.left = leftSon.right;
			if (leftSon.right != null)
				leftSon.right.parent = node;
			leftSon.parent = node.parent;
		}
		if (node.parent == null)
			root = leftSon;
		else if (node == node.parent.left)
			node.parent.left = leftSon;
		else
			node.parent.right = leftSon;
		if (leftSon != null)
			leftSon.right = node;
		node.parent = leftSon;

	}

	private void leftRotation(SplayNode<K, T> node) {
		SplayNode<K, T> rightSon = node.right;
		if (rightSon != null) {
			node.right = rightSon.left;
			if (rightSon.left != null)
				rightSon.left.parent = node;
			rightSon.parent = node.parent;
		}

		if (node.parent == null)
			root = rightSon;
		else if (node == node.parent.left)
			node.parent.left = rightSon;
		else
			node.parent.right = rightSon;
		if (rightSon != null)
			rightSon.left = node;
		node.parent = rightSon;
	}

	private void splay(SplayNode<K, T> node) {
		while (node.parent != null) {
			if (node.parent.parent == null) {
				if (node.parent.left == node) {
					rightRotation(node.parent);
				} else {
					leftRotation(node.parent);
				}
			} else if (node.parent.left == node && node.parent.parent.left == node.parent) {
				rightRotation(node.parent.parent);
				rightRotation(node.parent);
			} else if (node.parent.right == node && node.parent.parent.right == node.parent) {
				leftRotation(node.parent.parent);
				leftRotation(node.parent);
			} else if (node.parent.left == node && node.parent.parent.right == node.parent) {
				rightRotation(node.parent);
				leftRotation(node.parent);
			} else if (node.parent.right == node && node.parent.parent.left == node.parent) {
				leftRotation(node.parent);
				rightRotation(node.parent);
			}
		}
	}

	T[] toArray(T[] pole) {
		if (root == null)
			return null;
		int i = 0;
		Stack<SplayNode<K, T>> zasobnik = new Stack<>();
		SplayNode<K, T> actual = root;
		while (actual != null) {
			zasobnik.push(actual);
			actual = actual.left;
		}
		while (zasobnik.size() > 0) {
			actual = zasobnik.pop();
			pole[i] = actual.getData();
			i++;
			if (actual.right != null) {
				actual = actual.right;
				while (actual != null) {
					zasobnik.push(actual);
					actual = actual.left;
				}
			}

		}
		return pole;
	}

	public int getSize() {
		return this.size;
	}

	private void isOk(SplayNode<K, T> noda) {
		if (noda == null)
			return;
		System.out.println(noda.getKey());

		if (noda.parent != null) {
			if (noda.parent.left == noda) {
				System.out.println("noda " + noda.getKey() + " je lavy potomok nody " + noda.parent.getKey());
			}
			if (noda.parent.right == noda) {
				System.out.println("noda " + noda.getKey() + " je pravy potomok nody " + noda.parent.getKey());
			}
			if (noda == noda.parent) {
				System.out.println("AHA noda " + noda.getKey() + " je potomkom nody " + noda.parent.getKey());
				System.exit(0);
			}
		}
		if (noda.left != null) {
			System.out.println("lavy syn nody " + noda.getKey() + " je " + noda.left.getKey());
			if (noda == noda.left) {
				System.out.println("AHA noda " + noda.getKey() + " ma laveho syna " + noda.left.getKey());
				System.exit(0);
			}
		}
		if (noda.right != null) {
			System.out.println("pravy syn nody " + noda.getKey() + " je " + noda.right.getKey());
			if (noda == noda.right) {
				System.out.println("AHA noda " + noda.getKey() + " ma praveho syna " + noda.right.getKey());
				System.exit(0);
			}
		}
	}

	public void SkontrolujStromCezLevelOrder() {
		if (root == null)
			return;
		Queue<SplayNode<K, T>> queue = new LinkedList<SplayNode<K, T>>();
		queue.add(root);
		while (!queue.isEmpty()) {
			SplayNode<K, T> tempNode = queue.poll();
			isOk(tempNode);
			if (tempNode.left != null) {
				queue.add(tempNode.left);
			}
			if (tempNode.right != null) {
				queue.add(tempNode.right);
			}
		}
	}

	protected T getRoot() {
		return root.getData();
	}

	/**
	 * Ulozi do pola hodnoty dannej nody v inOrder poradi.
	 * 
	 * Pocet vratenych prvkov je zavisli od velkosti vstupneho pola: Ak je
	 * velkost pola menej nez prvkov v tomto strome, naplni cele pole. Ak je
	 * velkost pola viac nez pocet prvkov v tomto strome naplni pole a do
	 * zvysnych indexov pola da hodnotu null
	 * 
	 * @param inArray
	 *            pole do ktoreho budu vlozene hodnoty tohto stromu zotriedene
	 * @param node
	 *            Noda od ktorej bude zacinat InOrder
	 */
	public void inOrder(T[] inArray, SplayNode<K, T> node) {
		Stack<SplayNode<K, T>> s = new Stack<>();
		int sizeOfArray = inArray.length;
		int actualIndex = 0;
		while (!s.isEmpty() || node != null) {
			if (node != null) {
				s.push(node);
				node = node.left;
			} else {
				node = s.pop();
				inArray[actualIndex] = node.getData();
				actualIndex++;
				if (actualIndex == sizeOfArray)
					return;
				node = node.right;
			}
		}
		while (actualIndex < sizeOfArray) {
			inArray[actualIndex] = null;
			actualIndex++;
		}

	}

	/**
	 * Ulozi do pola hodnoty stromu v inOrder poradi.
	 * 
	 * @param inArray
	 *            pole do ktoreho sa ulozia hodnoty tohto stromu
	 * @see SplayTree#inOrder(T[], SplayNode)
	 */
	public void inOrder(T[] inArray) {
		inOrder(inArray, root);
	}

	public void getSuccessorsOfRoot(T[] inArray) {
		if (root.right != null) {
			inOrder(inArray, root.right);
		} else {
			for (T prvok : inArray) {
				prvok = null;
			}
		}
	}
	public List<T> getSuccessorsOfRootinList(int pocetSuccesorov) {
		if (root.right != null) {
			return toList(root.right).subList(0, pocetSuccesorov);
		} else {
			return new ArrayList<>();
		}
	}
	
	public T getDataZRootu() {
		return root.getData();
	}
	
	public List<T> toList(SplayNode<K, T> node) {
		Stack<SplayNode<K, T>> s = new Stack<>();
		List<T> list = new ArrayList<>();
		while (!s.isEmpty() || node != null) {
			if (node != null) {
				s.push(node);
				node = node.left;
			} else {
				node = s.pop();
				list.add(node.getData());
				node = node.right;
			}
		}
		return list;

	}

	/**
	 * Ulozi do pola hodnoty stromu v inOrder poradi.
	 * 
	 * @param inArray
	 *            pole do ktoreho sa ulozia hodnoty tohto stromu
	 * @see SplayTree#inOrder(T[], SplayNode)
	 */
	public List<T> toList() {
		return toList(root);
	}

}
