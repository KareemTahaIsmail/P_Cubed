package p3.parser.expressions.symbols.builtinoperators;

import p3.parser.expressions.symbols.Operator;
import p3.parser.expressions.symbols.Value;

public class Quote extends Operator {
    
    public Quote() {
        super(0);
    }
    
    public Value<Boolean> evaluate() {
        return null;
    }
}
