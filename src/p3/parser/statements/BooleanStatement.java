package p3.parser.statements;

import p3.parser.expressions.IExpression;
import p3.parser.statements.IStatement;

public class BooleanStatement implements IStatement {

    private IExpression expression;
    
    public BooleanStatement(IExpression expression) {
        this.expression = expression;
    }
    
    public void execute() {
        this.expression.evaluate();
    }
    
}
