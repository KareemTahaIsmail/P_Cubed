package p3.parser.expressions;

import java.util.ArrayList;
import java.util.Stack;

import p3.parser.expressions.symbols.Evaluator;
import p3.parser.expressions.symbols.Function;
import p3.parser.expressions.symbols.Symbol;
import p3.parser.expressions.symbols.Value;

public class Expression implements IExpression {

    private ArrayList<Symbol> expression;
    
    public Expression(ArrayList<Symbol> result) {
        this.expression = result;
    }
    
    public Value evaluate() {
        Value result = null;
        int i = 0;
        int exprSize = this.expression.size();
        Stack<Symbol> stack = new Stack<Symbol>();
        Symbol current;
        IExpression temp;
        Value tempResult;
        while (i < exprSize) {
            current = this.expression.get(i);
            if (!isEvaluator(current)) {
                stack.push(current);
            } else {                
                Evaluator currentEvaluator = (Evaluator)current;
                if (currentEvaluator instanceof Function) {
                    Function func = ((Function) currentEvaluator);
                    int currentArg = 0;
                    while (currentArg < func.getArgumentsCount()) {
                        temp = (IExpression)stack.pop();
                        func.setOperand(temp);
                        currentArg += 1;
                    }
                } else {
                    temp = (IExpression)stack.pop();
                    currentEvaluator.setOperand(temp);
                    if (!stack.isEmpty()) {
                        temp = (IExpression)stack.pop();
                        currentEvaluator.setOperand(temp);
                    }
                }
                stack.push(currentEvaluator.evaluate());
            }
            i += 1;
        }
        result = ((IExpression)stack.pop()).evaluate();
        return result;
    }
    
    private boolean isEvaluator(Symbol sym) {
        if (!(sym instanceof Evaluator)) {
            return false;
        }
        return true;
    }
    
}
