package p3.parser.expressions.symbols;

import p3.parser.expressions.IExpression;

public class NumberValue extends Value<Double> implements IExpression {

    
    public NumberValue(Double val) {
        super(val);
    }

    public Value evaluate() {
        return this;
    }
    
}
