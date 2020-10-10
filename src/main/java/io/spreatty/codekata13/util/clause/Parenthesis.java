package io.spreatty.codekata13.util.clause;

public final class Parenthesis<T> {
    private final T type;
    private final String token;

    Parenthesis(T type, String token) {
        this.type = type;
        this.token = token;
    }

    public T getType() {
        return type;
    }

    public String getToken() {
        return token;
    }
}
