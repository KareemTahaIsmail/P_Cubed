package p3.parser.expressions.symbols;

import p3.common.Program;
import p3.parser.expressions.IExpression;

public class Variable extends Symbol<String> implements IExpression {
    
    public Variable(String name, Value value) {
        this(name);
        Program.Get().setVal(this.value, value);
    }
    
    public Variable(String name) {
        super(name);
    }
    
    public Value evaluate() {
        return Program.Get().getVar(this.value);
    }
    
    public void setValue(Value val) {
        Program.Get().setVal(this.value, val);
    }
    
}
