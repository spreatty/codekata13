package io.spreatty.codekata13.util.clause;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * POJO representing clause thing like "quoted parenthesis". POJO consists of type of desired
 * class, opening sequence, closing sequence, and optional tester for special rules.
 *
 * @param <T>
 *        A class type for {@code type} parameter
 */
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

    @Override
    public boolean equals(Object object) {
        if (this == object){
            return true;
        }
        if (object == null || getClass() != object.getClass()){
            return false;
        }
        Clause<?> clause = (Clause<?>) object;
        return Objects.equals(type, clause.type) &&
                Objects.equals(openString, clause.openString) &&
                Objects.equals(closeString, clause.closeString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, openString, closeString);
    }
}
