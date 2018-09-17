/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apconstraintsolver;

/**
* This class represents the list of possible values of a Variables
* For instance it can represent
*  x = {x1, x2, x3, x4}
* Where varName is x
* possibleValues is a list of Variable (see the Variable class)
* and maxValue is simply the size of the array (in this case is 4, from x1 to x4)
* @author HüseyinVarol
*
*/

import java.util.ArrayList;

public class VariableValues {
	public String varName; //the variable name e.g x
	private ArrayList<Variable> possibleValues; //the list of possible values for the variable
	public int maxValue; //the maximum value of the variable
	
	public VariableValues(String pName){ 
		this.varName = pName;
	}
	
	public VariableValues(ArrayList<Variable> possibleVals){
		this.varName = possibleVals.get(0).getName().substring(0,1);
		this.possibleValues = possibleVals;
		this.maxValue = possibleVals.size();
	}	
	
	public void setPossibleValues(ArrayList<Variable> pV){		
		this.possibleValues = pV;
		this.maxValue = this.possibleValues.size();
	}
	
	public ArrayList<Variable> getPossibleValues(){
		return this.possibleValues;
	}
	
	public String getMaximum(){
		return varName+maxValue;
	}
	
	public String getInitialValue(){
		return varName+1;
	}
	
	@Override
	public String toString(){
		String ret = varName +" = {";
		for(Variable val : possibleValues){
			ret += val;
			ret += (val.equals(possibleValues.get(possibleValues.size()-1))) ? "" : ", "; 
		}
		return ret +"}\n";
	}

}

