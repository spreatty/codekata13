package io.spreatty.codekata13.util.clause;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

public class ClauseParser<T> implements Iterator<Parenthesis<T>> {
    private final String source;
    private final Collection<Clause<T>> clauses;

    private Opening opening = null;
    private int position = 0;

    public ClauseParser(String source, Collection<Clause<T>> clauses) {
        if (source == null || clauses == null) {
            throw new NullPointerException();
        }
        this.source = source;
        this.clauses = clauses;
    }

    public Stream<Parenthesis<T>> toStream() {
        Stream.Builder<Parenthesis<T>> streamBuilder = Stream.builder();
        forEachRemaining(streamBuilder);
        return streamBuilder.build();
    }

    @Override
    public boolean hasNext() {
        return position < source.length();
    }

    @Override
    public Parenthesis<T> next() {
        if (opening == null) {
            Optional<Opening> optionalOpening = findClosestOpening();

            if (optionalOpening.isEmpty()) {
                return defaultParenthesisTo(source.length());
            }

            opening = optionalOpening.get();
            if (opening.position > position) {
                return defaultParenthesisTo(opening.position);
            }
        }

        position += opening.clause.getOpenString().length();

        String token = buildToken();

        T type = opening.clause.getType();
        opening = null;
        return new Parenthesis<>(type, token);
    }

    private Parenthesis<T> defaultParenthesisTo(int to) {
        String token = source.substring(position, to);
        position = to;
        return new Parenthesis<>(null, token);
    }

    private Optional<Opening> findClosestOpening() {
        return clauses.stream()
                .map(this::findOpening)
                .filter(Opening::exists)
                .sorted()
                .findFirst();
    }

    private Opening findOpening(Clause<T> clause) {
        return new Opening(source.indexOf(clause.getOpenString(), position), clause);
    }

    private String buildToken() {
        StringBuilder token = new StringBuilder();

        boolean closingFound;
        do {
            int closingPosition = source.indexOf(opening.clause.getCloseString(), position);
            if (closingPosition == -1) {
                token.append(source.substring(position));
                position = source.length();
                break;
            }

            token.append(source, position, closingPosition);
            position = closingPosition + opening.clause.getCloseString().length();
            closingFound = opening.clause.getTokenTester() == null
                    || opening.clause.getTokenTester().test(token.toString());
            if (!closingFound) {
                token.append(opening.clause.getCloseString());
            }
        } while (!closingFound);

        return token.toString();
    }

    private class Opening implements Comparable<Opening> {
        private final int position;
        private final Clause<T> clause;

        private Opening(int position, Clause<T> clause) {
            this.position = position;
            this.clause = clause;
        }

        public boolean exists() {
            return position > -1;
        }

        @Override
        public int compareTo(Opening another) {
            return Integer.compare(position, another.position);
        }
    }
}
