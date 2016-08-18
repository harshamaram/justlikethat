package com.sample.sudoku;

public class Main {
	
	public static Grid grid;
	
	public static void main(String s[]) {
		grid = new Grid(9,9);
		setUpProblem();
		System.out.println(grid);
		System.out.println(grid.getSolvedGrid());
		solve();
		System.out.println(grid);
	}
	
	static void solve() {
		boolean isChanged = false;
		
		try {
		
		while(!isChanged) {
			
			for(int i=0; i<9; i++) {
				for(int j=0; j<9; j++) {
					if(!grid.getCell(i, j).isSolved()) {
						isChanged = filter(i,j);
					}
				}
			}
		}
			
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
		
		for(int x=0; x<9; x++) {
			if(grid.getCell(i, x).isSolved()) {
				temp = grid.getCell(i, x).getFinalValue();
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
		int[][] problemGrid = {
			{8,3,0,0,1,0,5,0,0},
			{0,0,7,4,5,0,1,0,0},
			{1,0,0,0,0,7,0,0,0},
			{6,0,0,8,0,0,0,0,7},
			{5,0,0,0,9,0,0,2,0},
			{0,9,0,7,4,2,0,0,0},
			{2,0,3,0,0,0,6,5,0},
			{0,0,0,0,0,9,0,8,0},
			{0,6,0,0,0,0,0,0,0}
		};
		
		
		
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(problemGrid[i][j]!=0) 
					setup(i, j, problemGrid[i][j]);
			}
		}
		
	}
	
	static void setup(int x, int y, int value) {
		grid.getCell(x, y).setValue(value);
	}

}
