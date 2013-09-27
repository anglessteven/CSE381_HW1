/**
 * anglessw_hw1.java
 * Author: Steven Angles
 * Class: CSE381
 * Professor: Dr. Rao
 * 
 * Description:
 * This class contains the main method which sets everything in motion,
 * beginning with interaction with the user via the console.
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class anglessw_hw1 {
	private Scanner keyboard = null;
	private anglessw_hw1_reader reader = null;
	private PrintStream ps = System.out;
	
	private static final String ENTER_SCHEMA = "Enter name of schema file: ";
	private static final String ENTER_BINARY = "Enter name of binary file: ";
	private static final String SUPPORTED_OPS = "Supported operations:\n"
			+ "\t1. Print schema\n"
			+ "\t2. Print binary data on screen\n"
			+ "\t3. Print binary data to text file\n"
			+ "\t4. Search and print data on screen\n"
			+ "\t5. Search and save data to binary file\n"
			+ "\t6. Quit\n"
			+ "Enter your choice: ";
	private static final String ENTER_TEXT_NAME = "Enter text file name: ";
	private static final String ENTER_SEARCH = "Enter search value of type ";
	private static final String ENTER_BINARY_SAVE = "Enter name of binary file to save data in: ";
	private static final String CURRENT_SCHEMA = "Current schema being used:\n";
	
	/**
	 * Main method
	 */
	public static void main(String[] args) {
		anglessw_hw1 main = new anglessw_hw1();
		main.run();
	}
	
	/**
	 * Initializes necessary streams and prints
	 * a request to the user via the console.
	 */
	private void run() {
		initializeScannerAndReader();
		printMenu();
	}
	
	/**
	 * Manages program-user interaction via a menu.
	 */
	private void printMenu() {
		int choice = -1;
		getSchemaAndBinaryName();
		do {
			choice = getChoice();
			switch (choice) {
			case 1:
				unifiedPrint(ps, CURRENT_SCHEMA+reader.getSchemaContents());
				break;
			case 2:
				unifiedPrint(ps, reader.getBinaryContents());
				break;
			case 3:
				binaryToText();
				break;
			case 4:
				searchToConsole();
				break;
			case 5:
				searchToFile();
				break;
			}
		} while (choice != 6);
		cleanup();
	}
	
	/**
	 * Closes program streams.
	 */
	private void cleanup() {
		ps.close();
		keyboard.close();
	}
	
	/**
	 * Method for saving binary file data to a
	 * text file.
	 */
	private void binaryToText() {
		int errorCode = -1;
		do {
			try {
				unifiedPrint(ps, ENTER_TEXT_NAME);
				PrintStream temp = createFileStream(keyboard.nextLine());
				errorCode = 0;
				unifiedPrint(temp, reader.getBinaryContents());
				temp.close();
			} catch (FileNotFoundException e) {
				System.err.println(e.getMessage());
			}
		} while (errorCode == -1);
	}
	
	/**
	 * Method for getting a search query from the user
	 * and displaying search results via the console.
	 */
	private void searchToConsole() {
		unifiedPrint(ps, ENTER_SEARCH+"\'"+reader.getSearchableType()+"\': ");
		String query = keyboard.nextLine();
		unifiedPrint(ps, reader.getSearchResults(query));
	}
	
	/**
	 * Method for getting a search query from the user
	 * and saving those search results in a user-specified
	 * file.
	 */
	private void searchToFile() {
		unifiedPrint(ps, ENTER_SEARCH+"\'"+reader.getSearchableType()+"\': ");
		String query = keyboard.nextLine();

		int errorCode = -1;
		do {
			try {
				unifiedPrint(ps, ENTER_BINARY_SAVE);
				String fileName = keyboard.nextLine();
				PrintStream temp = createFileStream(fileName);
				errorCode = 0;
				binaryPrint(temp, reader.getBinarySearch(query));
				temp.close();
			} catch (FileNotFoundException e) {
				System.err.println(e.getMessage());
			}
		} while (errorCode == -1);
	}
	
	/**
	 * Method for displaying the numeric menu and getting
	 * a choice from the user.
	 * @return int
	 * 				The menu number picked by the user.
	 */
	private int getChoice() {
		unifiedPrint(ps, SUPPORTED_OPS);
		return Integer.parseInt(keyboard.nextLine());
	}
	
	/**
	 * Method for getting schema and binary file names from the user.
	 */
	private void getSchemaAndBinaryName() {
		int errorCode = -1;
		do {
			unifiedPrint(ps, ENTER_SCHEMA);
			try {
				reader.openAndReadSchema(keyboard.nextLine());
				errorCode = 0;
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		} while (errorCode == -1);
		
		errorCode = -1;
		do {
			unifiedPrint(ps, ENTER_BINARY);
			try {
				reader.openAndReadBinary(keyboard.nextLine());
				errorCode = 0;
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		} while (errorCode == -1);
	}
	
	/**
	 * Method for initializing streams for user input and the
	 * reader that is used to extract file information.
	 */
	private void initializeScannerAndReader() {
		keyboard = new Scanner(System.in);
		reader = new anglessw_hw1_reader();
	}
	
	/**
	 * Method for printing a string message via a PrintStream.
	 * @param ps
	 * 			The PrintStream to use.
	 * @param message
	 * 			The message to print to the PrintStream.
	 */
	private void unifiedPrint(final PrintStream ps, final String message) {
		ps.print(message);
		ps.flush();
	}
	
	/**
	 * A method for printing binary data to a PrintStream.
	 * @param ps
	 * 			The PrintStream to use.
	 * @param data
	 * 			The binary data to print.
	 */
	private void binaryPrint(final PrintStream ps, final byte[] data) {
		try {
			ps.write(data);
		} catch (IOException e) {
			System.err.println("Error writing binary data to file.");
			System.err.println(e.getMessage());
		}
		ps.flush();
	}
	
	/**
	 * A method for creating a PrintStream to a specified file.
	 * @param fileName
	 * 			The name of the file for which to construct a PrintStream.
	 * @return
	 * 			The initialized PrintStream.	
	 */
	private PrintStream createFileStream(final String fileName) 
			throws FileNotFoundException {
		return new PrintStream(fileName);
	}
}
