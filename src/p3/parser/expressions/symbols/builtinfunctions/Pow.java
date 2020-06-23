package p3.parser.expressions.symbols.builtinfunctions;

import p3.parser.expressions.IExpression;
import p3.parser.expressions.symbols.Function;
import p3.parser.expressions.symbols.NumberValue;
import p3.parser.expressions.symbols.Value;

public class Pow extends Function {
    
    public Pow() {
        super(2);
    }
    
    public Value<Double> evaluate() {
        Double arg1 = (Double)((IExpression) this.args.pop()).evaluate().getValue();
        Double arg2 = (Double)((IExpression) this.args.pop()).evaluate().getValue();
        return new NumberValue(Math.pow(arg1, arg2));
    }
}
