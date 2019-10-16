package com.justlikethat.sudoku;

public class Main {

	int GRID[][] = { 
			{ 0, 1, 6, 7, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 7, 6, 4, 9, 0, 0, 0 }, 
			{ 4, 2, 0, 0, 0, 0, 6, 9, 0 },
			{ 0, 6, 0, 0, 0, 7, 4, 1, 9 }, 
			{ 1, 3, 2, 8, 0, 4, 7, 6, 5 }, 
			{ 7, 4, 9, 1, 0, 0, 0, 8, 0 },
			{ 0, 8, 3, 0, 0, 0, 0, 7, 4 }, 
			{ 0, 0, 0, 4, 7, 3, 8, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 6, 2, 3, 0 } 
		};

	int GRID_3D[][][] = new int[9][9][10];

	boolean restartFlag = false;

	void initialize() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				GRID_3D[i][j][0] = GRID[i][j];
				if (GRID_3D[i][j][0] == 0) {
					for (int k = 1; k < 10; k++) {
						GRID_3D[i][j][k] = k;
					}
				}
			}
		}
	}

	void printGrid() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				sb.append(GRID[i][j]).append(" ");
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}

	void printGrid3D() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				for (int k = 0; k < 10; k++) {
					sb.append(GRID_3D[i][j][k]);
				}
				sb.append(" ");
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}

	public static void main(String s[]) {
		Main main = new Main();
		main.initialize();

		main.printGrid3D();
	}

	void process() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (GRID[i][j] != 0) {
					processRow(i);
					processColumn(j);
					processBox(i, j);
				}
			}
		}
	}

	void processRow(int row) {
		for (int j = 0; j < 9; j++) {
			if (GRID_3D[row][j][0] != 0) {
				for (int x = 0; x < 9; x++) {
					if (x == j)
						continue;
					remove(GRID_3D[row][x], GRID_3D[row][j][0]);
				}
			}
		}
	}

	void processColumn(int column) {
		for (int i = 0; i < 9; i++) {
			if (GRID_3D[i][column][0] != 0) {
				for (int x = 0; x < 9; x++) {
					if (x == i)
						continue;
					remove(GRID_3D[x][column], GRID_3D[x][column][0]);
				}
			}
		}
	}

	void processBox(int row, int column) {
		int x = row - row % 3;
		int y = column - column % 3;
		for (int i = x; i < x + 3; i++) {
			for (int j = y; j < y + 3; j++) {
				if (GRID_3D[i][j][0] != 0) {
					remove(GRID_3D[i][j], GRID_3D[i][j][0]);
				}
			}
		}
	}

	int remove(int[] arr, int number) {
		boolean pass = false;
		for (int i = 0; i < 9; i++) {
			if (arr[i] == number || pass) {
				arr[i] = (i != 8) ? arr[i + 1] : 0;
				pass = true;
			}
		}
		restartFlag = pass;
		if (size(arr) == 1) {
			return arr[0];
		} else {
			return 0;
		}
	}

	int size(int[] arr) {
		int size = 0;
		for (int i = 0; i < 9; i++) {
			if (arr[i] != 0) {
				size++;
			}
		}
		return size;
	}

}
