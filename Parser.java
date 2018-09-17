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
import java.util.ArrayList;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;


public class Parser {
    private Scanner scanner;
    private Type token;
    private void match(Type t) throws SyntaxException{
        if(token!=t) throw new SyntaxException("matching wrong because of "+token);
        token=scanner.nextToken();
    }
    
    public Program parse(BufferedReader r) throws SyntaxException
    {
        scanner = new Scanner(r);
        token = scanner.nextToken();
        ArrayList<VariableValues> varList =parseVars(new ArrayList<>());
        ArrayList<Tuple> posRels = parsePosRel();
        ArrayList<Tuple> negRels = parseNegRel();
        match(Type.EOF);
        //return new Program(varList,posrel,negrel);
        return new Program(varList, posRels, negRels);
    }
    
    private ArrayList<VariableValues> parseVars(ArrayList<VariableValues> varList) throws SyntaxException{
        switch(token){
            case OPEN_CURLY_BRACKET: //Parse until the positive relations start
                return varList;
            default:
            	varList.add(parseVar());
                return parseVars(varList);
        }
    }
    
    
    private VariableValues parseVar() throws SyntaxException{
        VariableValues var = new VariableValues(scanner.getToken());
        match(Type.VALUE); // it is actually a variable
        match(Type.EQ);
        match(Type.OPEN_CURLY_BRACKET);
        var.setPossibleValues(parseValue(new ArrayList<>()));
        match(Type.CLOSE_CURLY_BRACKET);
        return var;
    }
    
    
    private ArrayList<Variable> parseValue(ArrayList<Variable> possibleValues) throws SyntaxException{
        switch(token){
            case CLOSE_CURLY_BRACKET:
                return possibleValues;
            default:
                Variable val = new Variable(scanner.getToken());
                match(Type.VALUE);
                
                if(token == Type.COMMA) match(Type.COMMA);
                
                possibleValues.add(val);
                return parseValue(possibleValues);  // it is recursive
        }
    }
    
    private ArrayList<Tuple> parsePosRel() throws SyntaxException{
        match(Type.OPEN_CURLY_BRACKET);
        ArrayList<Tuple> posRels = parseTuples(new ArrayList<Tuple>());
        match(Type.CLOSE_CURLY_BRACKET);
        return posRels;
        
    }
    
    
    private ArrayList<Tuple> parseNegRel() throws SyntaxException{
        match(Type.EXCLAMATION);
        match(Type.OPEN_CURLY_BRACKET);
        ArrayList<Tuple> negRels = parseTuples(new ArrayList<Tuple>());
        match(Type.CLOSE_CURLY_BRACKET);
        return negRels;
    }
            
    
    private ArrayList<Tuple> parseTuples(ArrayList<Tuple> tupleList) throws SyntaxException{
        switch(token){
            case CLOSE_CURLY_BRACKET: //Parse until the closing curly bracket
                return tupleList;
            default:
                tupleList.add(parseTuple());

                if(token == Type.COMMA)
                    match(Type.COMMA);

                return parseTuples(tupleList);
            }
    }
    
    private Tuple parseTuple() throws SyntaxException {
        match(Type.OPEN_BRACKET);
        String val1 = scanner.getToken();
        match(Type.VALUE);
        match(Type.COMMA);
        String val2 = scanner.getToken();
        match(Type.VALUE);
        match(Type.CLOSE_BRACKET);
        return new Tuple(val1,val2);
    }
}

