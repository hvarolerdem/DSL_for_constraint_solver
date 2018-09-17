/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apconstraintsolver;


/**
 * Class which finds the first possible solution of the Sudoku problem
 * @author HüseyinVarol
 *
 */

import java.util.ArrayList;

public class SudokuSingleSolver {
	
    private ArrayList<VariableValues> varList; //variables list
    private ConstraintsSolver solver; //solver
    private boolean stopRec; //flag to stop the recurstion
    private ArrayList<Variable> sol; //solution array
    private ArrayList<Variable> startingValues; //values to start with in the cells
    private ArrayList<Integer> fixedIndexes; //indexes of the fixed values to check at runtime if we should cycle them or not

    public SudokuSingleSolver(ArrayList<VariableValues> pVarList, ConstraintsSolver pSolver, ArrayList<Variable> pStartingValues){
        this.varList = pVarList;
        this.solver = pSolver;		
        this.startingValues = pStartingValues;
        this.fixedIndexes = new ArrayList<>();
        this.sol = new ArrayList<>();
    }

    public ArrayList<Variable> findSolution(){
        this.sol.clear();
        int startIndex = 0;
        fixedIndexes.clear();

        //This time initialization is a bit different
        //Since we need to fix the value of some variables
        for(int i = 0; i < varList.size(); i++){
            //if the name (e.g x) of the fixed value is equal to the name of the first possible value of the variable in the list
            //it means there is a value we need to fix for the variable
            if(startingValues.get(startIndex).getName().equals(varList.get(i).varName)){
                //so we add the fixed value of the variable to the solution
                sol.add(new Variable(startingValues.get(startIndex)));
                fixedIndexes.add(i); //we add the index to know that this value is fixed
                //and we proceed to check the next fixed value
                if(startIndex < startingValues.size()-1)
                    startIndex++;
            }
            else{
                //if instead the value is not fixed we give it the first possible value (e.g x1)
                sol.add(new Variable(varList.get(i).getInitialValue()));				
            }
        }

        //Just a print to show the starting solution array with the fixed values
        System.out.println("\nStarting Values: " +sol +"\n");

        //start the recursion
        stopRec = false;
        findSolution2(0);	

        //if stopRec never became true it means a solution has not been found
        if(!stopRec)
            System.out.println("This problem has no solution!");

        return sol;
    }

    /**
     * This is the core of the solution
     * it is a recursive algorithm where the input (varIndex) indicates the variable
     * of the solution we are currently changing
     * 
     * In this case it is a bit different since the values of some variables are fixed
     * @param varIndex
     */
    public void findSolution2(int varIndex){
        //if the varIndex is in the array of fixed indexes
        //we do nothing and instead proceed to the next variable to cycle
        if(fixedIndexes.contains(varIndex)){
                //we check if this is a solution
                if(solver.checkSolution(sol)){
                    stopRec = true;
                System.out.println("Solution: "+ sol
                +"\nPositive Const Solved: " +solver.getSolvedPosConst()
                +"\nNegative Const Solved: " +solver.getSolvedNegConst());
                        return;
                }

                //if it is not a solution we go on with the next variable
                if(varIndex < sol.size()-1)
                    findSolution2(varIndex+1);	

                //if while checking the next variable we found a solution we need to stop
                if(stopRec)
                    return;
        }else{
            //if instead the value is not fixed, we need to scroll through all the possible values
            for(Variable value : varList.get(varIndex).getPossibleValues()){			
                sol.get(varIndex).setValue(value); //so we set the new value of the variable				

                //Use the solver to check the array of the solution
                if(solver.checkSolution(sol)){
                    stopRec = true; //Notice that we found a solution and we need to stop the recurstion
                System.out.println("Solution: "+ sol
                +"\nPositive Const Solved: " +solver.getSolvedPosConst()
                +"\nNegative Const Solved: " +solver.getSolvedNegConst());
                        return;
                }

                //if we are not at the last variable (e.g we have x,y,z,t and we are at y
                //we call again the function incrementing the index so varList.get(varIndex) will return
                //the next variable in the array, e.g in [x1, y1, z1, t1] we are at varIndex == 2 so at z1 and
                //we call the function with varIndex == 4  so at the next step we are cycling through the values of t
                //and when it comes here it will be 4 < 4 and it will not call anymore findSolution2
                if(varIndex < sol.size()-1)
                    findSolution2(varIndex+1);

                //if the previous function returned and found a solution
                //we don't need to check no more so we stop immediately
                if(stopRec)
                    return;

                }
                //everytime the loop ends we reset the value to 1
                //e.g we checked x1 y1 z4, it was not a solution
                //so before going back to y, we set z to z1
                //so when it returns it will be x1 y2 z1
                sol.get(varIndex).setValueIndex(1);
            }
    }
}

