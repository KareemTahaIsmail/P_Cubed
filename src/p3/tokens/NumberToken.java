package p3.tokens;

public class NumberToken extends Token<Double> {
    
    public NumberToken(String symbol) {
        this.value = Double.parseDouble(symbol);
    }

}
