
public class TestDT {
  public static void main(String[] args) {
    DecisionTree tree = new DecisionTree("Is it a Mammal?");
    tree.setLeft(new DecisionTree("Mouse"));
    tree.setRight(new DecisionTree("Crocodile"));
		tree.getLeft().setLeft(new DecisionTree("Mouse 1"));
		tree.getRight().setRight(new DecisionTree("Crocodile 1"));
		tree.getLeft().getLeft().setLeft(new DecisionTree("Mouse 2"));
		tree.getRight().getRight().setRight(new DecisionTree("Crocodile 2"));
    tree.getLeft().getLeft().getLeft().setLeft(new DecisionTree("Mouse 3"));
		tree.getRight().getRight().getRight().setRight(new DecisionTree("Crocodile 3"));
                    tree.getLeft().getLeft().getLeft().getLeft().setLeft(new DecisionTree("Mouse 4"));		  
tree.getRight().getRight().getRight().getRight().setRight(new DecisionTree("Crocodile 4"));
    System.out.println(tree);
  }
}