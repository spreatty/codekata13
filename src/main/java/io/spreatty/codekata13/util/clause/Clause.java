package io.spreatty.codekata13.util.clause;

import java.util.function.Predicate;

public class Clause<T> {
    private final T type;
    private final String openString;
    private final String closeString;
    private final Predicate<String> tokenTester;

    public Clause(T type, String openString, String closeString) {
        this(type, openString, closeString, null);
    }

    public Clause(T type, String openString, String closeString, Predicate<String> tokenTester) {
        this.type = type;
        this.openString = openString;
        this.closeString = closeString;
        this.tokenTester = tokenTester;
    }

    public T getType() {
        return type;
    }

    public String getOpenString() {
        return openString;
    }

    public String getCloseString() {
        return closeString;
    }

    public Predicate<String> getTokenTester() {
        return tokenTester;
    }
}
