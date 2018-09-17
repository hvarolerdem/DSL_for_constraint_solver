/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apconstraintsolver;

/**
 * 
 * This class represents the whole problem
 * 
 * varList contains the list of variables, each with a list of their possible values
 * posRels is the list of positive constraints stored as tuples(pairs)
 * negRels is the list of negative constraints stored as tuples(pairs)
 * 
 * @author HüseyinVarol
 *
 */

import java.util.ArrayList;

public class Program {
    private ArrayList<VariableValues> varList;
    private ArrayList<Tuple> posRels;
    private ArrayList<Tuple> negRels;

    public Program(ArrayList<VariableValues> pVarList, ArrayList<Tuple> pPosRels, ArrayList<Tuple> pNegRels){
            varList = pVarList;
            posRels = pPosRels;
            negRels = pNegRels;
    }
    //This function can be used for getting variables and its values e.g x = {x1,x2,x3} 
    public ArrayList<VariableValues> getVarList(){
            return this.varList;
    }
    //This function can be used for getting positive constraints
    public ArrayList<Tuple> getPosRels(){
            return this.posRels;
    }
    //This function can be used for getting negative constraints
    public ArrayList<Tuple> getNegRels(){
            return this.negRels;
    }

    //It is used for writing text input	
    @Override
    public String toString() {	
        String ret ="";

        //Print the list of variables, each with its own list of possible values
        for(VariableValues valuesList: varList){
            ret += valuesList;
        }

        //print the positive constraints
        ret += "{";
        for(Tuple rel: posRels){
            ret += rel;
            ret += (rel.equals(posRels.get(posRels.size()-1))) ?  "" :  ", "; //avoid the comma after the last constraint
        }
        ret += "}\n";


        //print the negative constraints
        ret += "! {";
        for(Tuple rel: negRels){
            ret += rel;
            ret += (rel.equals(negRels.get(negRels.size()-1))) ?  "" :  ", "; //avoid the comma after the last constraint
        }
        ret += "}\n";
        return ret;
    }
}

