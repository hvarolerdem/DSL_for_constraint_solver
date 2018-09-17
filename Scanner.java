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
public class Scanner {
    private StreamTokenizer input;
    private Hashtable<String,Type> symbol_t;
    
	public Scanner(BufferedReader r)
	{
	    input = new StreamTokenizer(r);
	    input.wordChars('a','z');
            input.wordChars(1,9);
	    input.eolIsSignificant(false); 
            input.ordinaryChar('{');
	    input.ordinaryChar('}');
            input.ordinaryChar('(');
	    input.ordinaryChar(')');
	    input.ordinaryChar(',');
	    input.ordinaryChar('=');
	    input.ordinaryChar('!');
            symbol_t = new Hashtable<String,Type>();
                
	}
        
       
        public Type nextToken() {
          try {
             switch (input.nextToken()) {
              case StreamTokenizer.TT_EOF:
                  return Type.EOF;
              case StreamTokenizer.TT_WORD:
                  if(symbol_t.get(input.sval)==null)
                      symbol_t.put(input.sval, Type.VALUE);
                  return (symbol_t.get(input.sval));
              case '(':
                  return Type.OPEN_BRACKET;
              case ')':
                  return Type.CLOSE_BRACKET;
              case ',':
                  return Type.COMMA; 
              case '!':
                  return Type.EXCLAMATION;
              case '=':
                  return Type.EQ;
              case '{':
                  return Type.OPEN_CURLY_BRACKET;
              case '}':
                  return Type.CLOSE_CURLY_BRACKET;
              case ' ':
              case '\n':
              case '\t':
              case '\r':    
                  return nextToken();
              default : return Type.NO_TOKEN;
              }
             } catch (IOException e) {
			e.printStackTrace();
			return Type.EOF;
		}
	}
        
        public String getToken()  
        {
            return this.input.sval;
        }
    
}
