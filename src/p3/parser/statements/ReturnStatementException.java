package p3.parser.statements;

import p3.parser.expressions.symbols.Value;

public class ReturnStatementException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private Value returnValue;
    
    public ReturnStatementException(Value returnValue) {
        this.returnValue = returnValue;
    }
    
    public Value getResult() {
        return this.returnValue;
    }
    
}
