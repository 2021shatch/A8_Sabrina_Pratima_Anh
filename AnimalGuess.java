import java.util.Scanner;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Implements a "20 Questions" style guessing game.
 * 
 * The programs ask Yes or No style questions
 * about what animal the user is thinking of
 * and guess the animal using a decision tree.
 * 
 * If the program guesses wrong
 * the tree will extend by one node.
 * 
 * @author Anh Nguyen
 * @author Sabrina Hatch
 * @author Pratima Naroula
 * @version September 2021
 */
public class AnimalGuess {

	private static Scanner input = new Scanner(System.in);
	private static boolean change = false;

	/** Read in decision tree from a text file */
	public static DecisionTree readTree(String filename) {
		DecisionTree tree = new DecisionTree("");
		try {
			Scanner sc = new Scanner(new File(filename));
			while (sc.hasNextLine()) {
				String[] split = sc.nextLine().split("\\s", 2);
				String path = split[0];
				DecisionTree newNode = new DecisionTree(split[1]);
				if (path.length() == 0) {
					tree.setData(split[1]);
				} else if (path.length() == 1) {
					if (path.equals("Y")) {
						tree.setLeft(newNode);
					} else if (path.equals("N")) {
						tree.setRight(newNode);
					}
				} else if (path.length() > 1) {
					DecisionTree rootNode = tree.followPath(path.substring(0, path.length() - 1), tree);
					if (path.charAt(path.length() - 1) == 'Y') {
						rootNode.setLeft(newNode);
					} else if (path.charAt(path.length() - 1) == 'N') {
						rootNode.setRight(newNode);
					}
				} 
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return tree;
	}

	/**
	 * Write decision tree to a text file
	 * 
	 * @param filename output file name
	 * @param tree     decision tree to write
	 */
	public static void writeTree(String filename, DecisionTree tree) {
		try {
			PrintWriter output = new PrintWriter(new FileWriter(filename));
			ArrayList<String> nodePath = tree.breadthFirstPath();
			for (String line : nodePath) {
				output.println(line);
			}
			output.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

  //public static voic

	/**
	 * take a prompt
	 * elicit a yes or no answer from the user
	 * 
	 * @param question yes or no question to ask
	 * @return boolean
	 * @throws RuntimeException when the user doesn't input an acceptable form of
	 *                          yes or no
	 */
	public static boolean prompt(String question) throws RuntimeException {
		boolean error = true;
		boolean ans = true;

		do {
			try {
				System.out.println(question);
				String userAns = input.nextLine();
				if (userAns.matches("y|Y|yes|Yes")) {
					ans = true;
					error = false;
				} else if (userAns.matches("n|N|no|No")) {
					ans = false;
					error = false;
				} else {
					throw new RuntimeException();
				}
			} catch (RuntimeException e) {
				System.err.println("Invalid input!\nType 'y/Y/yes/Yes' or 'n/N/no/No' only.");
				input.reset();
			}
		} while (error);
		return ans;
	}

	/**
	 * add a question distinguishing
	 * the wrong guess from the correct answer
	 * then you store it in the tree as the new node
	 * 
	 * @param T decision tree to extend new node to
	 */
	public static void addQuestion(DecisionTree T) {
		String oldAnimal = T.getData();

		// Scanner input = new Scanner(System.in);
		System.out.println("Please help me learn.\nWhat was your animal?");
		String newAnimal = input.nextLine();

		// Get new y/n question
		System.out
				.println("Type a yes or no question that would distinguish between a " + newAnimal + " and a " + oldAnimal);
		String question = input.nextLine();

		// Extend node to tree
		T.setData(question);
		if (prompt("Would you answer yes or no to this question for the " + newAnimal + "?")) {
			T.setLeft(new DecisionTree(newAnimal));
			T.setRight(new DecisionTree(oldAnimal));
		} else {
			T.setRight(new DecisionTree(newAnimal));
			T.setLeft(new DecisionTree(oldAnimal));
		}
	}

	/**
	 * Guessing game recursion
	 * 
	 * @param decision tree that contains info for game
	 */
	public static void guessing(DecisionTree T) {
		// Base case: when we get to an animal (leaf)
		if (T.isLeaf()) {

			// Respond
			if (prompt("Is your animal a " + T.getData() + "?")) {
				System.out.println("I guessed it!");
			} else {
				System.out.println("I got it wrong.");
				addQuestion(T);
				change = true;
			}

			// End recursion
			return;
		} else {
			if (prompt(T.getData())) {
				guessing(T.getLeft());
			} else {
				guessing(T.getRight());
			}
		}
	}

	/** Start guessing game */
	public static void animalGuessGame(String filename) {
		DecisionTree tree = readTree(filename);
		
		// Start game
		System.out.println("Think of an animal.\nI'll try to guess it.");

		// Switch if user want to play again
		boolean playAgain = true;

		while (playAgain == true) {
			guessing(tree);

			// Write new tree here
			if (change) {
				writeTree(filename, tree);
			}

			// Ask user to play again
			if (!prompt("Play again?")) {
				playAgain = false;
			}
		}
		input.close();
	}

	/** Run program from console */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Usage:  java AnimalGuess <expr>");
		} else {
			animalGuessGame(args[0]);
		}
	}
}