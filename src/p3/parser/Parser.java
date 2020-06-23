package p3.parser;

import java.util.ArrayList;
import java.util.Stack;

import p3.common.Operators;
import p3.common.Program;
import p3.parser.expressions.Expression;
import p3.parser.expressions.IExpression;
import p3.parser.expressions.symbols.BooleanValue;
import p3.parser.expressions.symbols.Evaluator;
import p3.parser.expressions.symbols.NumberValue;
import p3.parser.expressions.symbols.Operator;
import p3.parser.expressions.symbols.StringValue;
import p3.parser.expressions.symbols.Symbol;
import p3.parser.expressions.symbols.Variable;
import p3.parser.expressions.symbols.builtinfunctions.Abs;
import p3.parser.expressions.symbols.builtinfunctions.Pow;
import p3.parser.expressions.symbols.builtinfunctions.Print;
import p3.parser.expressions.symbols.builtinfunctions.Read;
import p3.parser.expressions.symbols.builtinoperators.And;
import p3.parser.expressions.symbols.builtinoperators.Colon;
import p3.parser.expressions.symbols.builtinoperators.Comma;
import p3.parser.expressions.symbols.builtinoperators.Division;
import p3.parser.expressions.symbols.builtinoperators.Equals;
import p3.parser.expressions.symbols.builtinoperators.GreaterThan;
import p3.parser.expressions.symbols.builtinoperators.GreaterThanOrEqual;
import p3.parser.expressions.symbols.builtinoperators.IsEqual;
import p3.parser.expressions.symbols.builtinoperators.LeftParenthesis;
import p3.parser.expressions.symbols.builtinoperators.LessThan;
import p3.parser.expressions.symbols.builtinoperators.LessThanOrEqual;
import p3.parser.expressions.symbols.builtinoperators.Minus;
import p3.parser.expressions.symbols.builtinoperators.Modulus;
import p3.parser.expressions.symbols.builtinoperators.Multiplication;
import p3.parser.expressions.symbols.builtinoperators.Not;
import p3.parser.expressions.symbols.builtinoperators.NotEquals;
import p3.parser.expressions.symbols.builtinoperators.Or;
import p3.parser.expressions.symbols.builtinoperators.Plus;
import p3.parser.expressions.symbols.builtinoperators.Quote;
import p3.parser.expressions.symbols.builtinoperators.RightParenthesis;
import p3.parser.expressions.symbols.builtinoperators.Semicolons;
import p3.parser.expressions.symbols.functions.CustomFunction;
import p3.parser.statements.*;
import p3.tokens.*;

public class Parser {
    
    private ArrayList<IStatement> statements;
    private ArrayList<Token> tokens;
    
    private int currentToken;
    
    public Parser(ArrayList<Token> tokens) {
        this.statements = new ArrayList<IStatement>();
        this.tokens = tokens;
        this.currentToken = 0;
    }
    
    public ArrayList<IStatement> getStatements() {
        return this.statements;
    }
    
    public void parse() {
        int tokensCount = this.tokens.size();
        while (currentToken < tokensCount) {
            this.parseBlockLine(this.statements);
            currentToken += 1;
        }
    }
    
    private void parseBlockLine(ArrayList<IStatement> block) {
        Token current;
        current = this.tokens.get(currentToken);
        
        if (this.isVar(current)) {
            this.parseVar(block);
        }
        if (this.isFunction(current)) {
            this.parseFunction(block);
        }
        if (this.isStatement(current)) {
            this.parseStatement(block);
        }
    }
    
    private void parseStatement(ArrayList<IStatement> block) {
        KeyWordToken statement = (KeyWordToken)this.tokens.get(currentToken);
        if (statement.value().equals("while")) {
            this.parseWhile(block);
        } else if (statement.value().equals("if")) {
            this.parseIf(block);
        } else if (statement.value().equals("return")) {
            this.parseReturn(block);
        } else if (statement.value().equals("def")) {
            this.parseFunctionStatement();
        }
    }
    
    private void parseReturn(ArrayList<IStatement> block) {
        currentToken += 1;
        block.add(new ReturnStatement(this.parseExpression(Operators.SCL)));
    }
    
    private ArrayList<Variable> getFunctionArguments() {
        ArrayList<Variable> funcArgs = new ArrayList<Variable>();
        
        Token current = this.tokens.get(currentToken);
        
        while (!current.value().equals(Operators.CB)) {
            currentToken += 1;
            current = this.tokens.get(currentToken);

            if (current instanceof NameToken) {
                funcArgs.add(new Variable(current.value().toString()));
            }
        }
        return funcArgs;
    }
    
    private ArrayList<IStatement> getFunctionStatements() {
        ArrayList<IStatement> statements = new ArrayList<IStatement>();
        int tokensCount = this.tokens.size();
        Token current = this.tokens.get(currentToken);
        while (currentToken < tokensCount && !current.value().equals("enddef")) {
            currentToken += 1;
            current = this.tokens.get(currentToken);
            
            this.parseBlockLine(statements);
        }
        return statements;
    }
    
    private void parseFunctionStatement() {
        currentToken += 1;
        String name = this.tokens.get(currentToken).value().toString();
        
        CustomFunction function = new CustomFunction();
        Program.Get().addFunction(name, function);
        currentToken += 1;
        Token current = this.tokens.get(currentToken);
        if (!current.value().equals(Operators.OB)) {
            throw new RuntimeException("The function is not defined currectly!");
        }
        
        ArrayList<Variable> funcArgs = this.getFunctionArguments();
        ArrayList<IStatement> statements = this.getFunctionStatements();

        function.setArguments(funcArgs);
        function.setStatements(statements);
     
        currentToken += 1;
        current = this.tokens.get(currentToken);
        if (!current.value().equals(Operators.SCL)) {
            throw new RuntimeException("After the end of the function definition you must set semicolons.");
        }
        
    }
    
    private void parseWhile(ArrayList<IStatement> block) {
        Token current = this.tokens.get(currentToken);
        IExpression condition = null;
        ArrayList<IStatement> statements = new ArrayList<IStatement>();
        boolean conditionParsed = false;
        while (!current.value().equals("endwhile")) {
            currentToken += 1;
            current = this.tokens.get(currentToken);
            
            if (!conditionParsed) {
                condition = parseExpression(Operators.CLN);
                currentToken += 1;
                current = this.tokens.get(currentToken);
                conditionParsed = true;
            }            
            this.parseBlockLine(statements);
        }
        block.add(new WhileStatement(condition, statements));
    }
    
    private void parseIf(ArrayList<IStatement> block) {
        Token current = this.tokens.get(currentToken);
        IExpression condition = null;
        ArrayList<IStatement> statements = new ArrayList<IStatement>();
        ArrayList<IStatement> elseStatements = new ArrayList<IStatement>();
        boolean conditionParsed = false;
        boolean parseElse = false;
        while (!current.value().equals("endif")) {
            currentToken += 1;
            current = this.tokens.get(currentToken);

            if (!conditionParsed) {
                condition = parseExpression(Operators.CLN);
                currentToken += 1;
                current = this.tokens.get(currentToken);
                conditionParsed = true;
            }
            
            if (current.value().equals("else")) {
                currentToken += 2;
                parseElse = true;
            }

            if (parseElse) {
                this.parseBlockLine(elseStatements);
            } else {
                this.parseBlockLine(statements);
            }
        }
        block.add(new IfStatement(condition, statements, elseStatements));
    }
    
    private void parseFunction(ArrayList<IStatement> block) {
        String funcName = ((FunctionToken)this.tokens.get(currentToken)).value();
        if (funcName.equals("print")) {
            currentToken += 1;
            block.add(new Statement(new Print(this.parseExpression(Operators.SCL))));
        }
    }
    
    private int getOperatorPriority(Symbol operator) {
        if (this.symbolIsOperator(operator)) {
            if (operator instanceof Plus || operator instanceof Minus || operator instanceof Or || operator instanceof And) {
                return 2;
            } else {
                return 3;
            }   
        }
        return 4;
    }
       
    private IExpression parseExpression(Operators statementEnd) {
        Token current = this.tokens.get(currentToken);
        
        Stack<Symbol> stack = new Stack<Symbol>();
        ArrayList<Symbol> result = new ArrayList<Symbol>();
        Symbol currentSym;
        if (isString(current)) {
            return parseString(statementEnd);
        }
        return parseCalculationExpression(statementEnd);
    }
    
    private IExpression parseString(Operators statementEnd) {
        Token current = this.tokens.get(currentToken);
        currentToken += 1;
        Token temp = this.tokens.get(currentToken);
        
        if (!temp.value().equals(statementEnd)) {
            throw new RuntimeException("You cannot perform operations over strings!");
        }
        return new StringValue((String)current.value());
    }
    
    private IExpression parseCalculationExpression(Operators statementEnd) {
        Token current = this.tokens.get(currentToken);
        
        Stack<Symbol> stack = new Stack<Symbol>();
        ArrayList<Symbol> result = new ArrayList<Symbol>();
        Symbol currentSym = null;
        
        while (!current.value().equals(statementEnd)) {
            currentSym = this.convertToken(current, currentSym);
            if (this.isVar(current) || this.isNumber(current) || this.isBoolean(current)) {
                result.add(currentSym);
            } else if (this.isFunction(current)) {
                stack.push(currentSym);
            } else if (this.isComma(current)) {
                while (!(stack.peek() instanceof LeftParenthesis)) {
                    if (stack.isEmpty()) {
                        throw new RuntimeException("Parenthesis error!");
                    }
                    result.add(stack.pop());
                }
            } else if (this.isOperator(current) && !current.value().equals(Operators.OB) && !current.value().equals(Operators.CB)) {
                while (!stack.isEmpty() && this.symbolIsOperator(stack.peek()) && !(stack.peek() instanceof LeftParenthesis) && this.getOperatorPriority(currentSym) <= this.getOperatorPriority(stack.peek())) {
                    if (stack.peek() instanceof LeftParenthesis) {
                        stack.pop();
                    } else {
                        result.add(stack.pop());
                    }
                }
                stack.push(currentSym);
            } else if (currentSym instanceof LeftParenthesis) {
                stack.push(currentSym);
            } else if (currentSym instanceof RightParenthesis) {
                while (!stack.isEmpty() && this.symbolIsOperator(stack.peek()) && !(stack.peek() instanceof LeftParenthesis)) {
                    result.add(stack.pop());
                }
                if (!stack.isEmpty() && stack.peek() instanceof LeftParenthesis) {
                    stack.pop();
                }
                if (!stack.isEmpty() && stack.peek() instanceof Evaluator && 
                        !(stack.peek() instanceof LeftParenthesis || stack.peek() instanceof RightParenthesis)) {
                    result.add(stack.pop());
                }
            }
            currentToken += 1;
            current = this.tokens.get(currentToken);
        }
        

        while (!stack.empty()) { 
                result.add(stack.pop());
        }

        return new Expression(result);
    }
       
    private void parseVar(ArrayList<IStatement> block) {
        Token current = this.tokens.get(currentToken);
        String name = ((NameToken)current).value();
        currentToken += 1;
        current = this.tokens.get(currentToken);
        if (isOperator(current)) {
            Operators type = ((OperatorToken)current).value();
            if (type != Operators.EQ) {
                throw new RuntimeException();
            }
            currentToken++;
            current = this.tokens.get(currentToken);
            if (isFunction(current) && current.value().equals("read")) {
                block.add(new AssignmentStatement(new Variable(name, null), new Read()));
            } else {
                IExpression expr = this.parseExpression(Operators.SCL);
                block.add(new AssignmentStatement(new Variable(name, null), expr));
            }
        } else {
            throw new RuntimeException("Invalid syntax.");
        }
    }
    
    private Symbol convertToken(Token token, Symbol lastSymbol) {
        if (this.isOperator(token)) {
            if (lastSymbol == null) {
                return this.getOperator((Operators)token.value(), true);
            } else {
                if (this.symbolIsOperator(lastSymbol) && !(lastSymbol instanceof RightParenthesis)) {
                    return this.getOperator((Operators)token.value(), true);
                }
            }
        }
        return this.convertToken(token);
    }
    
    private Symbol convertToken(Token token) {
        Symbol result = null;
        String name = token.value().toString();
        if (isVar(token) && !Program.Get().functionExists(name)) {
                return new Variable(name);
        } else if (isNumber(token)) {
            return new NumberValue(((NumberToken)token).value());
        } else if (isBoolean(token)) {
            return new BooleanValue(((BooleanToken)token).value());
        } else if (isOperator(token)) {
            return this.getOperator(((OperatorToken)token).value());
        } else if (isFunction(token)) {
            return this.getFunction(name);
        } else {
            throw new RuntimeException("Unknown token.");
        }
    }
    
    private Symbol getOperator(Operators operator) {
        return this.getOperator(operator, false);
    }
    
    private Symbol getOperator(Operators operator, boolean unary) {
        switch (operator) {
        case OB:
            return new LeftParenthesis();
        case AND:
            return new And();
        case CB:
            return new RightParenthesis();
        case CLN:
            return new Colon();
        case CM:
            return new Comma();
        case DIV:
            return new Division();
        case EQ:
            return new Equals();
        case EQL:
            return new IsEqual();
        case GT:
            return new GreaterThan();
        case GTE:
            return new GreaterThanOrEqual();
        case LT:
            return new LessThan();
        case LTE:
            return new LessThanOrEqual();
        case MOD:
            return new Modulus();
        case MUL:
            return new Multiplication();
        case NEQ:
            return new NotEquals();
        case NOT:
            return new Not();
        case OR:
            return new Or();
        case QT:
            return new Quote();
        case SCL:
            return new Semicolons();
            default:
                throw new RuntimeException("Unknown operator.");
        }
    }
    
    private boolean symbolIsOperator(Symbol symbol) {
        if (symbol instanceof Operator) {
            return true;
        }
        return false;
    }
    
    private Symbol getFunction(String func) {
  
        if (func.equals("pow")) {
            return new Pow();
       
        } else if (func.equals("abs")) {
            return new Abs();
        } else if (func.equals("read")) {
            return new Read();
        } else {
            return Program.Get().getFunction(func);
        }
    }
    
    private boolean isComma(Token token) {
        if (this.isOperator(token)) {
            if (token.value().equals(Operators.CM)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isOperator(Token token) {
        if (token instanceof OperatorToken) {
            return true;
        }
        return false;
    }
    
    private boolean isVar(Token token) {
        if (token instanceof NameToken && !Program.Get().functionExists(token.value().toString())) {
            return true;
        }
        return false;
    }
    
    private boolean isNumber(Token token) {
        if (token instanceof NumberToken) {
            return true;
        }
        return false;
    }
    
    private boolean isBoolean(Token token) {
        if (token instanceof BooleanToken) {
            return true;
        }
        return false;
    }
    
    private boolean isString(Token token) {
        if (token instanceof StringToken) {
            return true;
        }
        return false;
    }
    
    private boolean isFunction(Token token) {
        if ((token instanceof FunctionToken) || Program.Get().functionExists(token.value().toString())) {
            return true;
        }
        return false;
    }
    
    private boolean isStatement(Token token) {
        if (token instanceof KeyWordToken) {
            return true;
        }
        return false;
    }
}
