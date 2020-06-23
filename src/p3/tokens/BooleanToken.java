package p3.tokens;

import p3.tokens.Token;

public class BooleanToken extends Token<Boolean> {

    public BooleanToken(String symbol) {
        if (symbol.equals("true")) {
            this.value = true;
        } else {
            this.value = false;
        }
    }
    
}
