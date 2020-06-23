package p3.parser.expressions.symbols;

import p3.parser.expressions.IExpression;

public class StringValue extends Value<String> implements IExpression {

    public StringValue(String val) {
        super(val);
    }
    
    public Value evaluate() {
        return this;
    }
    
}
