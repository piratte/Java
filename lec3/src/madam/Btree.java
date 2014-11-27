package src.madam;

public class Btree /* implements Iterable */ {
	private class Node {
		public Node l = null; 
		public Node r = null; 
		private int val;
		private final int defVal = 0;

		public Node() {
			val = defVal;
		}

		public Node(int x) {
			val = x;
		}

		public Node(int x, Node left, Node right) {
			val = x;
			l = left;
			r = right;
		}

		int getVal() { return val;}
		void setVal(int n) { val = n;}
		boolean hasLeft() { return l != null;}
		boolean hasRight() {return r != null;}
	}

	public Node root;

	public Btree(int x, Btree left, Btree right) {
		root = new Node(x, left.root, right.root);
	}

	public void add(int o) {
		if (root == null) {
			root = new Node(o);
		} else {
			Node n = find(root, o, true);
			int cur = n.getVal();
			if (cur != o) {
				if (cur > 0)
					n.left = new Node(o);
				else
					n.right = new Node(o);
			}
		}

	}

	public Node find(int i) {
		return find(root, i, false);
	}

	private Node find(Node n, int i, boolean toler) {
		int cur = n.getVal();
		if (cur == i)
			return n;
		else if (cur > i) {
			if (n.hasLeft)
				find(n.left, i);
			else if (toler)
				return n;
			else
				return null;
		}
		else /* cur < i */ {
			if (n.hasRight())
				find(n.right, i);
			else if (toler)
				return n;
			else
				return null;
		}
	}

	public int get(int i) {}
	public void remove(int i) {}
}
