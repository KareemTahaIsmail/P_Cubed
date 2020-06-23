package p3.parser.expressions.symbols.builtinoperators;

import p3.parser.expressions.IExpression;
import p3.parser.expressions.symbols.BooleanValue;
import p3.parser.expressions.symbols.Operator;
import p3.parser.expressions.symbols.Value;

public class LessThanOrEqual extends Operator {
    
    public LessThanOrEqual() {
        super(2);
    }
    
    public Value<Boolean> evaluate() {
        Double arg1 = (Double)((IExpression) this.args.pop()).evaluate().getValue();
        Double arg2 = (Double)((IExpression) this.args.pop()).evaluate().getValue();
        return new BooleanValue(arg1 <= arg2);
    }
}
