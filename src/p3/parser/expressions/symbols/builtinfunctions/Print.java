
package p3.parser.expressions.symbols.builtinfunctions;

import mpi.*;
import p3.parser.expressions.IExpression;
import p3.parser.expressions.symbols.Value;

public class Print implements IExpression {

    private IExpression toPrint;
    
    public Print(IExpression toPrint) {
        this.toPrint = toPrint;
    }
    
    public Value evaluate() {
        Value result = this.toPrint.evaluate();
        System.out.print(result.getValue()+"\n");
        return result;
    }
    
}
