package p3.parser.expressions.symbols.builtinoperators;

import p3.parser.expressions.IExpression;
import p3.parser.expressions.symbols.NumberValue;
import p3.parser.expressions.symbols.Operator;
import p3.parser.expressions.symbols.Value;

public class Multiplication extends Operator {
    
    public Multiplication() {
        super(2);
    }
    
    public Value<Double> evaluate() {
        Double arg1 = (Double)((IExpression) this.args.pop()).evaluate().getValue();
        Double arg2 = (Double)((IExpression) this.args.pop()).evaluate().getValue();
        return new NumberValue(arg1 * arg2);
    }
}
