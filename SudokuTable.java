/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apconstraintsolver;

/**
 * Class to represent the Sudoku matrix
 * @author HüseyinVarol
 *
 */

import java.util.ArrayList;

public class SudokuTable {
	private int rows;
	private int cols;
	private String[][] table; //each cell may contain a string
	
	public SudokuTable(int pRows, int pCols){
		//we have to multiply by 2 since each cell has another 2x2 matrix
		this.rows = pRows*2;
		this.cols = pCols*2;
		table = new String[this.rows][this.cols];
		clearTable();
	}
	
	public void setValue(int row, int col, String value){
		table[row-1][col-1] = value;
	}
	
	public void clearTable(){
		for(int i = 0; i < this.rows; i++){
			for(int j = 0; j < this.cols; j++){
				table[i][j] = "0";
			}
		}
	}
	
	public void printTable(boolean printStrings){
		int x = 0;
		int y = 0;
		for(int i = -1; i <= this.rows+1; i++){	
			System.out.print("\t| ");
			if(i==-1 || i == this.rows+1){
				for(int k = 0; k <= this.cols; k++){
					if(printStrings)
						System.out.print("----");
					else
						System.out.print("---");
						
				}
			}else if(i == this.rows/2){
				for(int k = 0; k <= this.cols; k++){
					if(printStrings)
						System.out.print("----");
					else
						System.out.print("---");
				}			
			}
			else{
				for(int j = 0; j <= this.cols; j++){
					if(j == this.cols/2){
						System.out.print(" | ");
					}else{
						if(printStrings)
							System.out.print(" " +table[x][y] +" ");
						else 
							System.out.print(" " +table[x][y].substring(1, 2) +" ");
						y++;
						if(y == this.cols){
							y = 0;
							x++;						
						}
					}
				}
			}
			if(printStrings)
				System.out.println("\t| ");
			else 
				System.out.println(" | ");
		}
	}
	
	public void setValues(ArrayList<Variable> solution){
		for(int i = 0; i < this.rows; i++){
			for(int j = 0; j < this.cols; j++){
				table[i][j] = solution.get((i*this.rows)+j).toString();
			}
		}		
	}
}

