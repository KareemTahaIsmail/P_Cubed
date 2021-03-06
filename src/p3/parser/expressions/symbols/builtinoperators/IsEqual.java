package p3.parser.expressions.symbols.builtinoperators;

import p3.parser.expressions.IExpression;
import p3.parser.expressions.symbols.BooleanValue;
import p3.parser.expressions.symbols.Operator;
import p3.parser.expressions.symbols.Value;

public class IsEqual extends Operator {
    
    public IsEqual() {
        super(2);
    }
    
    public Value<Boolean> evaluate() {
        Object arg1 = (Object)((IExpression) this.args.pop()).evaluate().getValue();
        Object arg2 = (Object)((IExpression) this.args.pop()).evaluate().getValue();
        return new BooleanValue(arg1.equals(arg2));
    }
}
