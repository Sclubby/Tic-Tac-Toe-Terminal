package app;

import java.util.*;
import java.util.Scanner;

public class game {

	public static void main(String[] args) { // This method starts the game and runs other methods
		Boolean gameEnd = false, playerTurn = true;
		int result = 0, turns = 0;
		ArrayList<Input> inputs = new ArrayList<Input>();

		playerTurn = CoinToss();

		while (gameEnd == false) {

			if (turns > 0 || playerTurn == true) {
				SetUp(inputs);
			} // Sets up new board

			result = CheckForWin(inputs); // checks for win or draw
			if (result != 0) {
				gameEnd = true;
				break;
			}
			if (playerTurn == true) { // runs current turna1
				PlayerTurn(inputs);
			} else
				BotTurn(inputs);

			if (playerTurn == false) { // switches each turn
				playerTurn = true;
			} else {
				playerTurn = false;
			}
			// for (Input x : inputs) { System.out.println(x.ToString()); } // Test
			turns++;
		}

		System.out.println("Game End");
		if (result == 2) {
			System.out.println("DRAW");
		}
		else if (result == 1) {
			System.out.println("Player Wins");
		} else
			System.out.println("Bot Wins");
	}

	public static void SetUp(List<Input> inputs) { // This creates a board with all data from inputs
		int sideNum = 1, Aspace = 0, currentRow = 1;
		String normalLine = ("\t\t|\t\t|"); // Stock line if nothing changes
		String line = normalLine; // placeholder
		System.out.println("\tA\t\tB\t\tC"); // Prints header

		for (int i = 0; i < 12; i++) { // adds x/o on each line
			if (i == 2 || i == 6 || i == 10) {
				for (Input x : inputs) {
					if (x.getRow() == currentRow) {
						if (x.getCol() == 'a') {
							Aspace = 1;
						}
						line = lineCreator(line, x.getCol(), Aspace, x.isX);
					}
				}
				currentRow++;
				Aspace = 0;
			}

			System.out.println(line);

			if (i == 1 || i == 5 || i == 9) { // Prints side #
				System.out.print(sideNum);
				sideNum++;
			}

			if (i == 3 || i == 7) { // Prints horizontal lines
				System.out.println("--------------------------------------------------");
			}
			line = normalLine; // resets line to default
		}
	}

	public static String lineCreator(String line, char inputCol, int Aspace, boolean isX) { // Creates new line from //
																							// // normalLine with
		// input data
		if (inputCol == 'a') {
			if (isX == true) {
				return line = line.substring(0, 1) + "X" + line.substring(1);
			} else {
				return line = line.substring(0, 1) + "O" + line.substring(1);
			}
		}
		if (inputCol == 'b') {
			if (isX == true) {
				return line = line.substring(0, 4 + Aspace) + "X" + line.substring(4 + Aspace);
			} else {
				return line = line.substring(0, 4 + Aspace) + "O" + line.substring(4 + Aspace);
			}
		}
		if (inputCol == 'c') {
			if (isX == true) {
				return line = line + "\tX";
			} else {
				return line = line + "\tO";
			}
		} else
			return line;
	}

	public static void BotTurn(List<Input> inputs) {
		int row = 0, currentLetter = 0, inLine = 0, totalInLine = 0, currentRow = 0;
		char col = 'n';
		boolean loop = true;

		while (currentLetter < 3) {// Checks each column if player might win
			for (Input x : inputs) {
				if (x.isX == true && x.getCol() == ('a' + currentLetter)) {
					inLine++;
				}
			}
			if (inLine == 2) {
				col = (char) ('a' + currentLetter);
				loop = false;
				for (Input y : inputs) {
					if (y.getCol() == ('a' + currentLetter)) {
						totalInLine = +y.getRow();
					}
				}
				if (totalInLine == 3) { // finds empty space using totals in line
					row = 1;
				} else if (totalInLine == 4) {
					row = 2;
				} else {
					row = 3;
				}
				for (Input x : inputs) { // If Row & Column are in list repeat
					if (x.getRow() == row && x.getCol() == col) {
						loop = true;
						break;
					}
				}
			}
			currentLetter++;
			inLine = 0;
		}
		totalInLine = 0;
		inLine = 0;
		while (currentRow < 4) {
			for (Input x : inputs) {
				if (x.isX == true && x.getRow() == currentRow) {
					inLine++;
				}
			}
			if (inLine == 2) {
				row = currentRow;
				loop = false;
				for (Input y : inputs) {
					if (y.getRow() == currentRow) {
						totalInLine = +y.getCol();
					}
				}
				if (totalInLine == 197) { // finds empty space using totals in line
					col = 'a';
				} else if (totalInLine == 196) {
					col = 'b';
				} else {
					col = 'c';
				}
				for (Input x : inputs) { // If Row & Column are in list repeat
					if (x.getRow() == row && x.getCol() == col) {
						loop = true;
						break;
					}
				}
			}
			currentRow++;
			inLine = 0;
		}

		while (loop == true) {
			String alph = "abc";
			row = (int) ((Math.random() * 3) + 1);
			col = alph.charAt((int) (Math.random() * 3));
			loop = false;
			for (Input x : inputs) { // If Row & Column are in list repeat
				if (x.getRow() == row && x.getCol() == col) {
					loop = true;
				}
			}
		}
		inputs.add(new Input(col, row, false));
		System.out.println("Bot puts \"O\" on " + col + row);
	}

	public static void PlayerTurn(List<Input> inputs) {
		char inputCol = 'n';
		int inputRow = 0;
		boolean chooseInput = false, validInput = false;
		Scanner in = new Scanner(System.in);

		System.out.println("Make Your Move");
		while (chooseInput == false) { // makes sure the space is'nt taken and gives the user another chance
			while (validInput == false) { // This loop makes sure the column input is valid
				System.out.println("What Space?");
				String userInput = in.next();
				if (userInput.length() == 2) {
					int colBeforeSet = userInput.codePointAt(0);
					int rowBeforeSet = userInput.codePointAt(1);
					if (colBeforeSet >= 97 && colBeforeSet <= 99 && rowBeforeSet >= 49 && rowBeforeSet <= 51) {
						validInput = true;
						inputCol = Character.toLowerCase(userInput.charAt(0)); // get column and turn it into a
						inputRow = Integer.parseInt(userInput.substring(1, 2));
					}
				}
				if (validInput == false) {
					System.out.println("Not a valid Input");
				}
			}
			chooseInput = true;
			for (Input x : inputs) { // If Row & Column are in list repeat
				if (x.getRow() == inputRow && x.getCol() == inputCol) {
					chooseInput = false;
					validInput = false;
					System.out.println("Space Already Taken");
					break;
				}
			}
		}
		inputs.add(new Input(inputCol, inputRow, true)); // Adds User input to list
	}

	public static boolean CoinToss() {
		int coinToss = ((int) (Math.random() * 101));
		if (coinToss > 50) {
			System.out.println("The Bot Goes First");
			return false;
		}
		return true;
	}

	public static int CheckForWin(List<Input> inputs) { // checks to see if anyone won the game
		int Xcounter = 0, Ocounter = 0, currentRow = 1, currentLetter = 0, XdownDiag = 0, OdownDiag = 0, XupDiag = 0,
				OupDiag = 0;
		while (currentLetter < 3) {
			for (Input x : inputs) { // Checks each column if a player has 3 in a row

				if (x.getCol() == ('a' + currentLetter)) {
					if (x.isX == true) {
						Xcounter++;
					} else
						Ocounter++;
				}
				if (Xcounter == 3) {
					return 1;
				}
				if (Ocounter == 3) {
					return -1;
				}
			}
			currentLetter++;
			Xcounter = 0;
			Ocounter = 0;
		}
		while (currentRow < 4) {
			for (Input x : inputs) { // Checks each row if a player has 3 in a row

				if (x.getRow() == currentRow) {
					if (x.isX == true) {
						Xcounter++;
					} else
						Ocounter++;
				}
				if (Xcounter == 3) {
					return 1;
				}
				if (Ocounter == 3) {
					return -1;
				}
			}
			currentRow++;
			Xcounter = 0;
			Ocounter = 0;
		}
		for (Input x : inputs) { // Checks diagonals
			if (x.getRow() == 1 && x.getCol() == 'a' || x.getRow() == 3 && x.getCol() == 'c') {
				if (x.isX == true) {
					XdownDiag++;
				} else
					OdownDiag++;
			}
			if (x.getRow() == 2 && x.getCol() == 'b') {
				if (x.isX == true) {
					XdownDiag++;
					XupDiag++;
				} else
					OupDiag++;
				OdownDiag++;
			}
			if (x.getRow() == 1 && x.getCol() == 'c' || x.getRow() == 3 && x.getCol() == 'a') {
				if (x.isX == true) {
					XupDiag++;
				} else
					OupDiag++;
			}
			if (XupDiag == 3 || XdownDiag == 3) {
				return 1;
			}
			if (OupDiag == 3 || OdownDiag == 3) {
				return -1;
			}
		}
		if (inputs.size() == 9) {
			return 2;
		}
		return 0;
	}
}