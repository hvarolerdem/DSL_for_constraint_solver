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

public class ConstraintsSolver {
    private ArrayList<Tuple> posRel; //positive constraints
    private ArrayList<Tuple> negRel; //negative constraints
    private ArrayList<Tuple> solvedPosConst;
    private ArrayList<Tuple> solvedNegConst;


    public ConstraintsSolver(ArrayList<Tuple> pPosRel, ArrayList<Tuple> pNegRel){
        this.posRel = pPosRel;
        this.negRel = pNegRel;
        this.solvedPosConst = new ArrayList<>();
        this.solvedNegConst = new ArrayList<>();
    }

    /**
     * Function that checks if a solution solve all the applicable constraints
     * 
     * @param pSol The array of the solution
     * @return true if the solution solves the constraints, false otherwise
     */
    public boolean checkSolution(ArrayList<Variable> pSol){
        solvedPosConst.clear();
        solvedNegConst.clear();

        //We start by taking each value in the array solution
        for(Variable var: pSol){
            //for each value in the solution we compare it
            //with the first value of all the positive constraints
            for(Tuple pos: posRel){
                //If the value in the solution is equal to the first value of the positive constraint
                //it means there is a constraint on it so we need to check the second value to assure
                //it IS in the solution
                if(var.equals(pos.v1)){
                    //this function simply scrolls through the solution array
                    //to check if it contains the second value of the constraint tuple
                    if(checkConst(pSol, pos.v2)){
                        //if the value is present in the solution, the constraint holds
                        solvedPosConst.add(pos);
                    }else{
                        //otherwise the constrain does not hold and we have to stop since the solution is not acceptable
                        return false;
                    }
                }
            }
        }

        //If all the positive constraints hold, the function did not return
        //so we can now check the negative constraints
        //We start by taking each value in the array solution
        for(Variable var: pSol){
            //for each value in the solution we compare it
            //with the first value of all the negative constraints
            for(Tuple neg: negRel){
                //If the value in the solution is equal to the first value of the negative constraint
                //it means there is a constraint on it so we need to check the second value to assure
                //it is NOT in the solution 
                if(var.equals(neg.v1)){
                    //this function simply cycles through the solution array
                    //to check if it contains the second value of the constraint tuple
                    if(!checkConst(pSol, neg.v2)){
                        //if the value is NOT present in the solution, the constraint holds
                        solvedNegConst.add(neg);
                    }else{
                        //otherwise if the value is present in the solution, it is not acceptable
                        return false;
                    }					
                }
            }
        }		
            return true;
    }

    /**
     * Function to check if a value solve a constraint
     * It simply cycles through the array of the solution
     * and checks if the second value in the pair appears in it
     * @param pSol The solution array
     * @param v2 The second value of the constraint tuple to look for
     * @return
     */
    public boolean checkConst(ArrayList<Variable> pSol, Variable v2){
        for(Variable var: pSol){
            if(var.equals(v2))
                    return true;
        }		
        return false;
    }

    public ArrayList<Tuple> getSolvedPosConst(){
        return solvedPosConst;
    }

    public ArrayList<Tuple> getSolvedNegConst(){
        return solvedNegConst;
    }

}

