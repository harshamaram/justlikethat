package com.sample.sudoku;


public class Cell {
	private int[] xy;
	private int[] elements;
	private int maxSize;
	private boolean isSolved;
	private int size;
	private boolean isChanged;
	
	Cell() {
		initialize();
	}
	
	Cell(int size) {
		this.maxSize = size;
		initialize();
	}

	public void initialize() {
		if(this.maxSize==0) {
			this.maxSize = 9;
		}
		
		elements = new int[maxSize];
		for(int i=0; i<maxSize; i++) {
			elements[i] = i+1;
		}
		setChanged(true);
		xy = new int[2];
	}
	
	public void setValue(int x, int y, int value) {
		elements[0] = value;
		for(int i=1; i<maxSize; i++) {
			elements[i] = 0;
		}
		setSolved(true);
		setChanged(true);
		setSize(1);
		xy[0] = x;
		xy[1] = y;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public int getFinalValue() {
		return elements[0];
	}
	
	private void refreshSize(){
		int size = 0;
		for(int i=0; i<maxSize; i++) {
			if(elements[i] != 0) {
				size++;
			}
		}
		setSize(size);
		if(size == 1) {
			setSolved(true);
		}
	}
	
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
	
	
	public void removeElement(int element) throws Exception{
		
		if(getSize()==1 && getFinalValue()==element) 
			throw new Exception("this element ["+element+"] shoould not be deleted at (" + xy[0] + "," + xy[1] + ")");
		
		for(int i=0; i<maxSize; i++) {
			if(elements[i] == element) {
				for(int j=i; j<maxSize-1; j++) {
					elements[j] = elements[j+1];
				}
				elements[maxSize-1] = 0;
				break;
			}
		}
		refreshSize();
		setChanged(true);
	}
	
	public int countElements() {
		int size = 0;
		for(int i=0; i<maxSize; i++) {
			if(elements[i] != 0) {
				size++;
			} else {
				break;
			}
		}
		return size;
	}
	
	public boolean isSolved() {
		return isSolved;
	}

	public void setSolved(boolean isSolved) {
		this.isSolved = isSolved;
	}

	public boolean isChanged() {
		return isChanged;
	}

	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<maxSize; i++) {
			sb.append(elements[i]);
		}
		return sb.toString();
	}

}
