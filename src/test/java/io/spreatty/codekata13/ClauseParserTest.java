package io.spreatty.codekata13;

import io.spreatty.codekata13.util.clause.Clause;
import io.spreatty.codekata13.util.clause.ClauseParser;
import io.spreatty.codekata13.util.clause.Parenthesis;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClauseParserTest {
    private final int[] callCounter = new int[1];
    private final int expectedCallCount = 2;

    private final Map<String, Clause<String>> clauses = Map.of(
            "hyphen", new Clause<>("hyphen", "-", "-"),
            "asymmetric", new Clause<>("asymmetric", "_", "="),
            "special", new Clause<>("special", "=>", "!", token -> {
                ++callCounter[0];
                return token.endsWith("&");
            }));

    private final List<Parenthesis<String>> expectedParentheses = List.of(
            new Parenthesis<>(null, "default parenthesis"),
            new Parenthesis<>("hyphen", "hyphen_parenthesis"),
            new Parenthesis<>("asymmetric", "asymmetric-parenthesis"),
            new Parenthesis<>(null,
                    "default parenthesis between provided parentheses"),
            new Parenthesis<>("special",
                    "parenthesis with external closing validator! Requires & before closing &"),
            new Parenthesis<>(null, "final default parenthesis"));

    private final String source = expectedParentheses.stream()
            .map(parenthesis -> Optional.ofNullable(parenthesis.getType())
                    .map(clauses::get)
                    .map(clause -> clause.getOpenString() + parenthesis.getToken()
                            + clause.getCloseString())
                    .orElse(parenthesis.getToken()))
            .collect(Collectors.joining());

    @Test
    void clauseParserStream() {
        List<Parenthesis<String>> parentheses = new ClauseParser<>(source, clauses.values())
                .toStream()
                .collect(Collectors.toList());

        Assertions.assertIterableEquals(expectedParentheses, parentheses);

        Assertions.assertEquals(expectedCallCount, callCounter[0], "Asked token tester either" +
                " more or less than expected");
    }

    @Test
    void clauseParserIterator() {
        Iterator<Parenthesis<String>> expectedIterator = expectedParentheses.iterator();
        Iterator<Parenthesis<String>> iterator = new ClauseParser<>(source, clauses.values());

        while (iterator.hasNext() && expectedIterator.hasNext()) {
            Assertions.assertEquals(expectedIterator.next(), iterator.next(),
                    "Parentheses do not match");
        }

        Assertions.assertEquals(expectedIterator.hasNext(), iterator.hasNext(),
                "Parentheses' count is not as expected");

        Assertions.assertEquals(expectedCallCount, callCounter[0], "Asked token tester either" +
                " more or less than expected");
    }
}
