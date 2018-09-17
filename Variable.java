/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apconstraintsolver;

/**
 * This class represents a single variable
 * for instance "x4"
 * where name is x and value is 4
 * @author HüseyinVarol
 *
 */
//This class represents a single variable
public class Variable {
	private String name;//name of the value e.g x
	private int value;//its number so name and value shows e.g x1 or z4 
	
	public Variable(Variable v){
		this.name = v.name;
		this.value = v.value;			
	}

	public Variable(String s){
		this.name = s.substring(0, 1);
		this.value = Integer.parseInt(s.substring(1)); //it can be x11..
	}
	
	//It is used for printing a value
        @Override
	public String toString() {
		return name + value;
	}
	
	public String getName(){
		return name;
	}
        
	public void setValue(Variable v){
		this.name = v.name;
		this.value = v.value;	
	}
	
	public int getValueIndex(){
		return this.value;
	}
	
	public void setValueIndex(int val){
		this.value = val;
	}
	
	@Override
	public boolean equals(Object obj) {
		Variable var = (Variable) obj;
		return (this.name.equals(var.name)) && this.value == var.value;
	}
}

