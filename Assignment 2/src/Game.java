import java.util.*;
import java.lang.*;
import java.io.*;
public class Game {

	public Board sudoku;

	public class Cell{
		private int row = 0;
		private int column = 0;

		public Cell(int row, int column) {
			this.row = row;
			this.column = column;
		}
		public int getRow() {
			return row;
		}
		public int getColumn() {
			return column;
		}
	}

	public class Region{
		private Cell[] matrix;
		private int num_cells;
		public Region(int num_cells) {
			this.matrix = new Cell[num_cells];
			this.num_cells = num_cells;
		}
		public Cell[] getCells() {
			return matrix;
		}
		public void setCell(int pos, Cell element){
			matrix[pos] = element;
		}

	}

	public class Board{
		private int[][] board_values;
		private Region[] board_regions;
		private int num_rows;
		private int num_columns;
		private int num_regions;

		public Board(int num_rows,int num_columns, int num_regions){
			this.board_values = new int[num_rows][num_columns];
			this.board_regions = new Region[num_regions];
			this.num_rows = num_rows;
			this.num_columns = num_columns;
			this.num_regions = num_regions;
		}

		public int[][] getValues(){
			return board_values;
		}
		public int getValue(int row, int column) {
			return board_values[row][column];
		}
		public Region getRegion(int index) {
			return board_regions[index];
		}
		public Region[] getRegions(){
			return board_regions;
		}
		public void setValue(int row, int column, int value){
			board_values[row][column] = value;
		}
		public void setRegion(int index, Region initial_region) {
			board_regions[index] = initial_region;
		}
		public void setValues(int[][] values) {
			board_values = values;
		}

	}

	public int[][] solver() {
		int[][] board = sudoku.getValues();

		if (solveSudoku(board)) {
			board = sudoku.getValues();

			return board;
		} else {
			return null; // No solution found
		}
	}

	private boolean solveSudoku(int[][] board) {
		Cell emptyCell = findEmptyCell(board);
		if (emptyCell == null) {
			return true; // Sudoku solved
		}

		int row = emptyCell.getRow();
		int col = emptyCell.getColumn();

		Region currentRegion = findRegionForCell(emptyCell);

		for (int num = 1; num <= currentRegion.num_cells; num++) {
			if (isSafeToPlace(board, emptyCell, num)) {
				board[row][col] = num;

				if (solveSudoku(board)) {
					return true;
				}

				board[row][col] = -1; // Backtrack
			}
		}

		return false; // No solution found
	}

	private Cell findEmptyCell(int[][] board) {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				if (board[row][col] == -1) {
					return new Cell(row, col);
				}
			}
		}
		return null; // No empty cell found
	}

	public boolean isSafeToPlace(int[][] board, Cell cell, int num) {
		// Check for conflicts within the same region
		Region region = findRegionForCell(cell);
		if (!isNumberInRegion(region, num)) {
			// Check for conflicts with adjacent cells (including diagonally)
			return !isNumberAdjacent(board, cell.getRow(), cell.getColumn(), num);
		}
		return false;
	}

	private boolean isNumberAdjacent(int[][] grid, int row, int col, int target) {
		int numRows = grid.length;
		int numCols = grid[0].length;

		// Check upwards
		if (row - 1 >= 0 && grid[row - 1][col] == target) {
			return true;
		}

		// Check downwards
		if (row + 1 < numRows && grid[row + 1][col] == target) {
			return true;
		}

		// Check left
		if (col - 1 >= 0 && grid[row][col - 1] == target) {
			return true;
		}

		// Check right
		if (col + 1 < numCols && grid[row][col + 1] == target) {
			return true;
		}

		// Check top-left diagonal
		if (row - 1 >= 0 && col - 1 >= 0 && grid[row - 1][col - 1] == target) {
			return true;
		}

		// Check top-right diagonal
		if (row - 1 >= 0 && col + 1 < numCols && grid[row - 1][col + 1] == target) {
			return true;
		}

		// Check bottom-left diagonal
		if (row + 1 < numRows && col - 1 >= 0 && grid[row + 1][col - 1] == target) {
			return true;
		}

		// Check bottom-right diagonal
		if (row + 1 < numRows && col + 1 < numCols && grid[row + 1][col + 1] == target) {
			return true;
		}

		return false;
	}

	public Region findRegionForCell(Cell cell) {
		for (Region region : sudoku.getRegions()) {
			for (Cell regionCell : region.getCells()) {
				if (regionCell.getRow() == cell.getRow() && regionCell.getColumn() == cell.getColumn()) {
					return region;
				}
			}
		}
		return null; // Cell does not belong to any region
	}

	private boolean isNumberInRegion(Region region, int num) {
		for (Cell cell : region.getCells()) {
			int value = sudoku.getValues()[cell.getRow()][cell.getColumn()];
			if (value == num) {
				return true;
			}
		}
		return false;
	}



	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int rows = sc.nextInt();
		int columns = sc.nextInt();
		int[][] board = new int[rows][columns];
		//Reading the board
		for (int i=0; i<rows; i++){
			for (int j=0; j<columns; j++){
				String value = sc.next();
				if (value.equals("-")) {
					board[i][j] = -1;
				}else {
					try {
						board[i][j] = Integer.valueOf(value);
					}catch(Exception e) {
						System.out.println("Ups, something went wrong");
					}
				}
			}
		}
		int regions = sc.nextInt();
		Game game = new Game();
		game.sudoku = game.new Board(rows, columns, regions);
		game.sudoku.setValues(board);
		for (int i=0; i< regions;i++) {
			int num_cells = sc.nextInt();
			Game.Region new_region = game.new Region(num_cells);
			for (int j=0; j< num_cells; j++) {
				String cell = sc.next();
				String value1 = cell.substring(cell.indexOf("(") + 1, cell.indexOf(","));
				String value2 = cell.substring(cell.indexOf(",") + 1, cell.indexOf(")"));
				Game.Cell new_cell = game.new Cell(Integer.valueOf(value1)-1,Integer.valueOf(value2)-1);
				new_region.setCell(j, new_cell);
			}
			game.sudoku.setRegion(i, new_region);
		}
		int[][] answer = game.solver();
		for (int i=0; i<answer.length;i++) {
			for (int j=0; j<answer[0].length; j++) {
				System.out.print(answer[i][j]);
				if (j<answer[0].length -1) {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}



}


