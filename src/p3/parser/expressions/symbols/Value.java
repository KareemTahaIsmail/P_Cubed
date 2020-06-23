package p3.parser.expressions.symbols;

import p3.parser.expressions.IExpression;

public class Value<T> extends Symbol<T> implements IExpression {
    
    public Value(T val) {
        super(val);
    }
    
    public Value evaluate() {
        return this;
    }
    
}
