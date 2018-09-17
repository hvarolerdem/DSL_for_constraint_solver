/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apconstraintsolver;

/**
 *
 * @author HüseyinVarol
 */

import java.io.*;
import java.util.*;
import java.util.Scanner;


public class APConstraintSolver {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws FileNotFoundException {
   
      BufferedReader inputfile = new BufferedReader (new FileReader("input3.txt")); 
        Parser parser = new Parser(); 
        Program rules = parser.parse(inputfile);
        System.out.println(rules);
        
        ConstraintsSolver solver = new ConstraintsSolver(rules.getPosRels(), rules.getNegRels());

        System.out.println("--- Start First Solution --- \n");
        SingleSolver ss = new SingleSolver(rules.getVarList(), solver);
        ss.findSolution();
        System.out.println("\n--- End First Solution --- \n\n");
        
        //It is for optional checking to compare with generator
        /*
        System.out.println("--- (Optional for checking) Start ALL Solutions --- \n\n");
        MultiSolver ms = new MultiSolver(rules.getVarList(), solver);
        ms.findSolution();
        System.out.println("--- (Optional for checking) End ALL Solution --- \n\n");
        */
        
        System.out.println("--- Start Generator of Solutions --- \n\n");
        IteratorSolver is = new IteratorSolver(rules.getVarList(), solver);
        Iterator<ArrayList<Variable>> it = is.iterator();
        Scanner scanner = new Scanner(System.in);
        while(it.hasNext()){
        	it.next();
            scanner.nextLine();
            System.out.println("Press for next solution...");
        }
        System.out.println("--- End Generator of Solutions --- \n\n");
 
        System.out.println("----- SUDOKU ------");
        SudokuTable table = new SudokuTable(2,2);      
        BufferedReader inputfile2 = new BufferedReader (new FileReader("Sudoku.txt")); 
        Parser parser2 = new Parser(); 
        Program sudokuRules = parser2.parse(inputfile2);
        ConstraintsSolver sudSolver = new ConstraintsSolver(sudokuRules.getPosRels(), sudokuRules.getNegRels());
        System.out.println(sudokuRules);
        
        
        ArrayList<Variable> starting = new ArrayList<>();
        //Fixing a number in each cell
        starting.add(new Variable("b3"));
        starting.add(new Variable("g4"));
        starting.add(new Variable("n2"));
        starting.add(new Variable("o2"));
        SudokuSingleSolver sss = new SudokuSingleSolver(sudokuRules.getVarList(), sudSolver, starting);
        ArrayList<Variable> sudokuSolution = sss.findSolution();
        table.setValues(sudokuSolution);
        table.printTable(true);
        System.out.println();
        table.printTable(false);
      
       
    }

}
    
