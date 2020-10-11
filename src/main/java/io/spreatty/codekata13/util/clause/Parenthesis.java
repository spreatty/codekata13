package io.spreatty.codekata13.util.clause;

import java.util.Objects;

/**
 * POJO representing parenthesis extracted from string by {@link ClauseParser}.
 *
 * @param <T>
 *        A class type for {@code type} parameter
 */
public class Parenthesis<T> {
    private final T type;
    private final String token;

    public Parenthesis(T type, String token) {
        this.type = type;
        this.token = token;
    }

    public T getType() {
        return type;
    }

    public String getToken() {
        return token;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Parenthesis<?> that = (Parenthesis<?>) object;
        return Objects.equals(type, that.type) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, token);
    }

    @Override
    public String toString() {
        return "Parenthesis{type=" + type + ", token='" + token + "'}";
    }
}
