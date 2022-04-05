import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Implements a binary decision tree
 *
 * @author Sabrina Hatch, Anh Ngyuen, Pratima Niroula
 * @version Spring 2022
 */
public class DecisionTree extends BinaryTree<String> {

	/** leaf constructor */
	public DecisionTree(String s) {
		super(s);
	}

	/** This constructor creates a branch node */
	public DecisionTree(String s, BinaryTree<String> left, BinaryTree<String> right) {
		super(s);
		this.setLeft(left);
		this.setRight(right);
	}

	/** This constructor creates a deep copy of the entire tree structure */
	public DecisionTree(BinaryTree<String> tree) {
		super(tree.getData());
		this.setLeft(tree.getLeft());
		this.setRight(tree.getRight());
	}

	/** @override set left */
	public void setLeft(BinaryTree<String> left) {
		if ((left == null) || (left instanceof DecisionTree)) {
			super.setLeft(left);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	/** @override get left */
	public DecisionTree getLeft() {
		return (DecisionTree) super.getLeft();
	}

	/** @override set right */
	public void setRight(BinaryTree<String> right) {
		if ((right == null) || (right instanceof DecisionTree)) {
			super.setRight(right);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	/** @override get right */
	public DecisionTree getRight() {
		return (DecisionTree) super.getRight();
	}

	/**
	 * program accepts an ordered list of Y/N values
	 * and then traverses a specific
	 * path of the BST
	 * 
	 * @param a   string tracePath of an ordered list of Y/N values
	 * @param the BST to be traversed
	 * @return void
	 */
	public DecisionTree followPath(String path, DecisionTree tree) {
		if (path.isEmpty()) {
			return tree;
		}

		if (path.charAt(0) == 'Y' && tree.getLeft() != null) {
			return followPath(path.substring(1), tree.getLeft());
		} else if (path.charAt(0) == 'N' && tree.getRight() != null) {
			return followPath(path.substring(1), tree.getRight());
		} else {
			throw new RuntimeException("Invalid input string!");
		}
	}

	/**
	 * Creates string representation
	 * in breadth-first order and
	 * corresponding path for each node
	 * 
	 * @return ArrayList<String> of path as Yes/No + node data for each item in the
	 *         array
	 */
	public ArrayList<String> breadthFirstPath() {
		ArrayDeque<DecisionTree> nodeList = new ArrayDeque<DecisionTree>();
		ArrayDeque<String> pathList = new ArrayDeque<String>();
		ArrayList<String> nodePathString = new ArrayList<String>();

		nodeList.addLast(this);
		pathList.add("");
		nodePathString.add(" " + this.getData());

		while (!nodeList.isEmpty()) {
			DecisionTree currentNode = nodeList.removeFirst();
			String currentPath = pathList.removeFirst();
			if (currentNode.getLeft() != null) {
				nodeList.addLast(currentNode.getLeft());
				pathList.addLast(currentPath + "Y");
				nodePathString.add(currentPath + "Y" + " " + currentNode.getLeft().getData());
			}
			if (currentNode.getRight() != null) {
				nodeList.addLast(currentNode.getRight());
				pathList.addLast(currentPath + "N");
				nodePathString.add(currentPath + "N" + " " + currentNode.getRight().getData());
			}
		}
		return nodePathString;
	}

}
