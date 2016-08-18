package com.sample.sudoku;

public class Grid {
	
	Cell[][] grid;
	int noOfRows;
	int noOfColumns;
	
	Grid(int rows, int columns) {
		noOfRows = rows;
		noOfColumns = columns;
		grid = new Cell[rows][columns];
		
		for(int i=0; i<noOfRows; i++) {
			for(int j=0; j<noOfColumns; j++) {
				grid[i][j] = new Cell();
			}
		}
	}
	
	public Cell getCell(int x, int y) {
		return grid[x][y];
	}
	
	public String getSolvedGrid() {
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<noOfRows; i++) {
			for(int j=0; j<noOfColumns; j++) {
				if(grid[i][j].isSolved()) {
					sb.append(grid[i][j].getFinalValue());
				} else {
					sb.append("-");
				}
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<noOfRows; i++) {
			for(int j=0; j<noOfColumns; j++) {
				sb.append("("+i+","+j+")")
					.append(grid[i][j]);
				sb.append("\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
