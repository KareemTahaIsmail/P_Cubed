package p3.parser.statements;

import p3.parser.expressions.IExpression;
import p3.parser.expressions.symbols.Variable;
import p3.parser.statements.IStatement;

public class AssignmentStatement implements IStatement {

    private Variable var;
    private IExpression expression;
    
    public AssignmentStatement(Variable var, IExpression expr) {
        this.var = var;
        this.expression = expr;
    }
    
    public void execute() {
        this.var.setValue(this.expression.evaluate());
    }
    
    public void setExpression(IExpression expr) {
        this.expression = expr;
    }

}
