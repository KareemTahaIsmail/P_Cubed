package p3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import p3.interpreter.Interpreter;
import p3.lexer.Lexer;
import p3.parser.Parser;
import p3.tokens.Token;

public class P3 {
    
    private static String program = "";
    
    public static void loadProgramFile(String file) {
        try {
            File programFile = new File(file);
            FileReader fileReader = new FileReader(programFile);
            BufferedReader reader = new BufferedReader(fileReader);
            String programLine;
            while ((programLine = reader.readLine()) != null) {
                program += programLine;
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void loadProgramString(String currentProgram) {
        program = currentProgram;
    }
    
    public static void execute() {
        Lexer l = new Lexer(program);
        ArrayList<Token> lst = l.lex();
        
        Parser parser = new Parser(lst);
        parser.parse();
        
        Interpreter interpreter = new Interpreter(parser.getStatements());
        interpreter.interpret();
    }

}
