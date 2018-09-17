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

import java.util.ArrayList;
import java.util.Iterator;

public class IteratorSolver implements Iterable<ArrayList<Variable>> {
    private ArrayList<VariableValues> varList; //list of variables each with their list of possible values
    private ConstraintsSolver solver; //solver

    //Constructor
    public IteratorSolver(ArrayList<VariableValues> pVarList, ConstraintsSolver pSolver){
        this.varList = pVarList;
        this.solver = pSolver;		
    }
	
    //Function for the Iterable interface
    public Iterator<ArrayList<Variable>> iterator() {
    	//Creating the new iterator
        return new SolutionIterator(varList, solver);
    }
    
    private class SolutionIterator implements  Iterator<ArrayList<Variable>>{ 
    	private ArrayList<VariableValues> varList;
    	private ConstraintsSolver solver;

    	private ArrayList<Variable> sol; //array to store the solution
    	private int recLevel = 0; //level of recursion to come back to after stopping
    	private boolean stopRec; //flag to stop the recursion
    	
    	//Constructor
    	public SolutionIterator(ArrayList<VariableValues> pVarList, ConstraintsSolver pSolver){
            this.sol = new ArrayList<>();
            this.varList = pVarList;
            this.solver = pSolver;

            //Initialize solution to the first possible value for each variable
            for(VariableValues values: varList){
                sol.add(new Variable(values.getInitialValue()));
            }    		
    	}
    	
    	/**
    	 * This function checks if all the variables has reached the maximum possible value
    	 * if even just one has not reached it, we don't need to check the others
    	 * because it means there are still other possibilities and we need to keep scrolling
    	 */
        public boolean hasNext() {
            for(Variable var: sol){
            	if(var.getValueIndex() < varList.get(sol.indexOf(var)).maxValue)
                    return true;
            }
            return false;
        }

        /**
         * Next function which returns the next possible solution to the problem
         */
        @Override
        public ArrayList<Variable> next() {			
                stopRec = false; //flag always set to false before starting the recursion
                findSolution2(0); //Recursion start
                return sol;
        }

        /**
         * This is the core of the solution
         * it is a recursive algorithm where the input (varIndex) indicates the variable
         * of the solution we are currently changing.
         * 
         * As soon as a solution is found, the level of the recursion is saved
         * and the stopRec flag is set to true to notice all the active functions
         * that they must stop instead of keep scrolling, in order to return
         * the solution just found
         * 
         * @param varIndex
         */
        public void findSolution2(int varIndex){
            //from varList we take the list of all possible values of the variable we are currently checking
            //e.g we are checking x, and we scroll through [x1,x2,x3,x4,x5], at each loop the for has a value for x
            for(Variable value : varList.get(varIndex).getPossibleValues()){
                //Firstly, we need to get back to the value of the variable we were checking in the solution
                //E.g if after stopping we now have [x1, y2, z3, t4] and we were checking t
                //it means we have to come back to t, but the for loops of the variables
                //have to go back to the value of the solution
                //so the loop of z must go up to z3 and then call the function for t
                //which will have to scroll up to t4 before start checking the solution
                if(sol.get(varIndex).getValueIndex() <= value.getValueIndex()) {
                    //now that we reach the right value of the solution
                    //E.g in  [x3, y1, z2, t3] we reached x3
                    //we have to go back to the recursion level we were, if we were checking t
                    //we need to go through 3 levels of recursion
                    if(recLevel <= 0){		
                            //now that we reached the recursion level we can proceed with cycling
                            sol.get(varIndex).setValue(value);
                            //we check the solution
                            if(solver.checkSolution(sol)){
                                    //if it is acceptable we set the flag to stop the recursion
                                    //and we save the recursion level we were
                                    //given by the index of the variable we are checking in the solution array
                                    stopRec = true;
                                    recLevel = varIndex;
                                    //System.out.println(sol);
                                    //return the solution with the list of solved constraints
                            System.out.println(sol +" : "
                            +"\nPositive Const Solved: " +solver.getSolvedPosConst()
                            +"\nNegative Const Solved: " +solver.getSolvedNegConst());
                            //In order to avoid starting again the recursion at next()
                            //we did one last increment so that when the recursion starts again for the next solution
                            //it will look for new values instead of starting from the old ones
                                    shiftValues();
                                    return; // firstly returns to x1 again
                            }                       
                    }else{
                            //If the recursion level is still > 0 it means we did not reach yet the level we were before
                            --recLevel;
                    }

                    //of course if there are no more possiblities we should stop
                    if(!hasNext()){
                        stopRec = true;
                        return;						
                    }

                    //now we go into a new level of the recursion
                    //if we were restoring a previously stopped recursion we did nothing before
                    //and we are going to the next level
                    if(varIndex < sol.size()-1){

                        findSolution2(varIndex+1);
                    }	

                    //if the previous function returned because of a solution
                    //we should stop this function as well to notice that we have to return
                    //the solutio and we should not proceed with the cycling
                    if(stopRec)
                    {
                       return;
                    }
                }
            }
             //everytime the loop ends we reset the value to 1
             //e.g we checked x1 y1 z4, it was not a solution
            //so before going back to y, we set z to z1
            //so when it returns it will be x1 y2 z1
            sol.get(varIndex).setValueIndex(1);
        }
		
		
        public void shiftValues(){
            int minimumCounter = 0;
            //We scroll through the solution starting from the end
            for(int i = sol.size()-1; i >= 0; i--){
                //if the value is the last possible for the variable
                if(sol.get(i).getValueIndex() >= varList.get(i).maxValue){
                    //we set the value back to 1
                    //at the next loop it will eventually increase the value if it is not at the maximum value
                    //or will set it to one if it is at the end
                    sol.get(i).setValueIndex(1);
                }
                else{
                    //if the variable is not at its maximum we increment its value
                    //and stop the loop since no other variable need to change
                    sol.get(i).setValueIndex(sol.get(i).getValueIndex()+1);	
                    return;
                }
                if(sol.get(i).getValueIndex() == 1)
                    minimumCounter++;
            }	

            //If they are all at the minimum it means we reached the endm
            //so we put them back to the maximum to stop the cycle
            if(minimumCounter == sol.size()){
                for(int i = sol.size()-1; i >= 0; i--){
                    sol.get(i).setValueIndex(varList.get(i).maxValue);					
                }
            }
        }
    }
}

