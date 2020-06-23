package p3.parser.expressions.symbols.builtinoperators;

import p3.parser.expressions.IExpression;
import p3.parser.expressions.symbols.BooleanValue;
import p3.parser.expressions.symbols.Operator;
import p3.parser.expressions.symbols.Value;

public class Not extends Operator {
    
    public Not() {
        super(1);
    }
    
    public Value<Boolean> evaluate() {
        Boolean arg1 = (Boolean)((IExpression) this.args.pop()).evaluate().getValue();
        return new BooleanValue(!arg1);
    }
}
