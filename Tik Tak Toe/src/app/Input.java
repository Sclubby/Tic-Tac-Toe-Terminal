package app;

public class Input {
	char col;
	int row;
	boolean isX;
Input(char col, int row, Boolean isX) { this.col = col;  this.row = row; this.isX = isX; }

public int getRow() { return this.row;}

public char getCol() { return this.col;}

public boolean isX() { return this.isX(); }

public String ToString() { return "Row: " + row + " Col: " + col + " isX: " + isX; }
}
