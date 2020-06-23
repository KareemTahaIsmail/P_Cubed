package p3.parser.statements;

import p3.parser.expressions.IExpression;
import p3.parser.expressions.symbols.Value;
import p3.parser.expressions.symbols.functions.CustomFunction;

public class ReturnStatement implements IStatement {

    private IExpression expr;
    private Value result;
    
    public ReturnStatement(IExpression expr) {
        this.expr = expr;
    }
    
    public void execute() {
        this.result = this.expr.evaluate();
        throw new ReturnStatementException(result);
    }
    
}
