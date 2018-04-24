package com.sample.sudoku;

public class Main {
	
	public static Grid grid;
	
	public static void main(String s[]) {
		grid = new Grid(9,9);
		setUpProblem();
		System.out.println("INITIALIZING");
		System.out.println(grid);
		System.out.println(grid.getSolvedGrid());
		solve();
		System.out.println("FINAL");
		System.out.println(grid);
		System.out.println(grid.getSolvedGrid());
	}
	
	static void solve() {
		boolean isChanged = false;
		
		try {
		
			int count = 0;
			do {
				
				isChanged = false;
				for(int i=0; i<9; i++) {
					for(int j=0; j<9; j++) {
						if(!grid.getCell(i, j).isSolved()) {
							isChanged |= filter(i,j);
						}
					}
				}
				
				System.out.println("ITERATIONS count: " + (++count));
				System.out.println("isChanged: " + isChanged);
				System.out.println(grid);
				
			} while(isChanged);
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 *  process each cell and filter values in itself based on the
	 *  neighboring cells in rows, columns and box
	 */
	static boolean filter(int i, int j) throws Exception{
		
		boolean isChanged = false;
		
		isChanged = fiterWithRows(i, j);
		isChanged |= fiterWithColumns(i, j);
		isChanged |= fiterWithBox(i, j);
		
		return isChanged;
//		return grid.getCell(i, j).isSolved();
	}
	
	static boolean fiterWithRows(int i, int j) throws Exception{
		boolean isChanged = false;
		int temp;
		
		for(int y=0; y<9; y++) {
			if(j==y) continue;
			if(grid.getCell(i, y).isSolved()) {
				temp = grid.getCell(i, y).getFinalValue();
				grid.getCell(i, j).removeElement(temp);
				isChanged = true;
			}
		}
		return isChanged;
	}
	
	static boolean fiterWithColumns(int i, int j) throws Exception{
		boolean isChanged = false;
		int temp;
		
		for(int x=0; x<9; x++) {
			if(i==x) continue;
			if(grid.getCell(x, j).isSolved()) {
				temp = grid.getCell(x, j).getFinalValue();
				grid.getCell(i, j).removeElement(temp);
				isChanged = true;
			}
		}
		return isChanged;
	}
	
	static boolean fiterWithBox(int i, int j) throws Exception{
		boolean isChanged = false;
		int[] bounderies = getBounderies(i, j);
		int temp;
		
		for(int x=bounderies[0]; x<bounderies[0]+3; x++) {
			for(int y=bounderies[1]; y<bounderies[1]+3; y++) {
				if(i==x && j==y) continue;
				if(grid.getCell(x, y).isSolved()) {
					temp = grid.getCell(x, y).getFinalValue();
					grid.getCell(i, j).removeElement(temp);
					isChanged = true;
				}
			}
		}
		
		return isChanged;
	}
	
	static int[] getBounderies(int i, int j) {
		
		int[] val = new int[2];
		val[0] = i/3*3;
		val[1] = j/3*3;
//		System.out.println("For ("+i+", "+j+"), bounderies are: ("+val[0]+", "+val[1]+")");
		return val;
	}
	
	static void setUpProblem() {
		
		// easy level
		/*
		int[][] problemGrid = {
				{0,2,0,3,0,0,9,4,0},
				{0,9,4,0,0,0,0,0,3},
				{0,0,8,0,5,0,2,7,0},
				{0,8,2,0,0,4,3,0,0},
				{0,4,7,5,0,2,8,9,0},
				{0,0,6,9,0,0,4,2,0},
				{0,7,9,0,3,0,1,0,0},
				{4,0,0,0,0,0,5,3,0},
				{0,5,3,0,0,1,0,8,0}
		}; //*/
		
		//hard level
		/*int[][] problemGrid = {
			{8,3,0,0,1,0,5,0,0},
			{0,0,7,4,5,0,1,0,0},
			{1,0,0,0,0,7,0,0,0},
			{6,0,0,8,0,0,0,0,7},
			{5,0,0,0,9,0,0,2,0},
			{0,9,0,7,4,2,0,0,0},
			{2,0,3,0,0,0,6,5,0},
			{0,0,0,0,0,9,0,8,0},
			{0,6,0,0,0,0,0,0,0}
		};*/
		
		//hard level
		int[][] problemGrid = {
				{0,6,4,1,0,0,0,2,7},
				{1,0,0,2,3,0,0,0,6},
				{9,2,3,0,0,6,0,0,0},
				{0,8,6,0,5,0,0,0,0},
				{7,1,0,0,0,0,0,3,2},
				{0,0,0,0,1,0,6,8,0},
				{0,0,0,5,0,0,1,7,9},
				{5,0,0,0,8,4,0,0,3},
				{3,9,0,0,0,1,8,5,0}
		};
		
		
		
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(problemGrid[i][j]!=0) 
					setup(i, j, problemGrid[i][j]);
			}
		}
		
	}
	
	static void setup(int x, int y, int value) {
		grid.getCell(x, y).setValue(x, y, value);
	}

}
