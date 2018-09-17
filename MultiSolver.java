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

/*
 *  This is a test class which computes all the possible solutions to the problem
 */

public class MultiSolver {	
	private ArrayList<VariableValues> varList; //list of variables, each with their list of possible values
	private ConstraintsSolver solver; //Constraints solver
	private ArrayList<Variable> sol; //Array to store the solution
	private boolean stopRec;
	
	public MultiSolver(ArrayList<VariableValues> pVarList, ConstraintsSolver pSolver){
		this.varList = pVarList;
		this.solver = pSolver;	
		this.sol = new ArrayList<>();	
	}
	
	public ArrayList<Variable> findSolution(){
		this.sol.clear();
		
		//Initialize solution array to the first possible value for each variable
		//I scroll all the variables and for each of them I get the first element of the list of possible values
		for(VariableValues values: varList){
			sol.add(new Variable(values.getInitialValue()));  // it assign first x1,y1,.....
		}
		
		//Beginning of the recursive algorithm
		stopRec = false; //we need stopRec here as well to be sure we don't get stuck in an infinite loop after shifting
		findSolution2(0);		
		
		return sol;
	}
	
	/**
	 * This is the core of the solution
	 * it is a recursive algorithm where the input (varIndex) indicates the variable
	 * of the solution we are currently changing
	 * @param varIndex
	 */
	public void findSolution2(int varIndex){
		//from varList we take the list of all possible values of the variable we are currently checking
		//e.g we are checking x, and we scroll through [x1,x2,x3,x4,x5], at each loop the for has a value for x
		for(Variable value : varList.get(varIndex).getPossibleValues()){
			sol.get(varIndex).setValue(value); //we set the new value to the variable, e.g x1
			
			//if we are not at the last variable (e.g we have x,y,z,t and we are at y
			//we call again the function incrementing the index so varList.get(varIndex) will return
			//the next variable in the array, e.g in [x1, y1, z1, t1] we are at varIndex == 2 so at z1 and
			//we call the function with varIndex == 4  so at the next step we are cycling through the values of t
			//and when it comes here it will be 4 < 4 and it will not call anymore findSolution2
			if(varIndex < sol.size()-1){
				findSolution2(varIndex+1);
			}		
			//It use the solver to check the array of the solution
			if(!stopRec && solver.checkSolution(sol)){
				System.out.println(sol);	//if it is a solution, print the output
				//We found a solution and it may happen that it is the last value of the variable
				//so the function returns and fo back to the previous variable
				//which will check again the solution, printin again the solution
				//to avoid it, before finishing we shift the values of one
				//System.out.println("Vefore: " +sol);
				shiftValues();
				//System.out.println("After  :" + sol);
			}	
			
			if(stopRec)
				return;
		}
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

		if(minimumCounter == sol.size())
			stopRec = true;
	}
}
