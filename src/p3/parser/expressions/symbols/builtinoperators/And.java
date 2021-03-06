package p3.parser.expressions.symbols.builtinoperators;

import p3.parser.expressions.IExpression;
import p3.parser.expressions.symbols.BooleanValue;
import p3.parser.expressions.symbols.Operator;
import p3.parser.expressions.symbols.Value;

public class And extends Operator {
    
    public And() {
        super(2);
    }
    
    public Value<Boolean> evaluate() {
        Boolean arg1 = (Boolean)((IExpression) this.args.pop()).evaluate().getValue();
        Boolean arg2 = (Boolean)((IExpression) this.args.pop()).evaluate().getValue();
        return new BooleanValue(arg1 && arg2);
    }
}
