/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apconstraintsolver;

/**
 * This class represents a single pair of a relation (both positive and negative)
 * (var1, var2)
 * @author HüseyinVarol
 */

public class Tuple {
    public Variable v1;
    public Variable v2;

    public Tuple(String var1, String var2){
            this.v1 = new Variable(var1);
            this.v2 = new Variable(var2);
    }

    @Override
    public String toString() {
            return "(" +v1 +"," +v2 +")";
    }
}

