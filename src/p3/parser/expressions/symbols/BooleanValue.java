package p3.parser.expressions.symbols;

import p3.parser.expressions.IExpression;
import p3.parser.expressions.symbols.Value;

public class BooleanValue extends Value<Boolean> implements IExpression {

    public BooleanValue(Boolean val) {
        super(val);
    }
    
    public Value evaluate() {
        return this;
    }
    
}
