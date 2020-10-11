package io.spreatty.codekata13.source;

import io.spreatty.codekata13.util.Strings;
import io.spreatty.codekata13.util.clause.Clause;
import io.spreatty.codekata13.util.clause.ClauseParser;
import io.spreatty.codekata13.util.clause.Parenthesis;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SourceAnalyzer {
    private static final Pattern BACKSLASH_TRAIL = Pattern.compile("\\\\*$");

    private static final List<Clause<Literal>> CLAUSES = List.of(
            new Clause<>(Literal.SINGLE_LINE_COMMENT, "//", "\n"),
            new Clause<>(Literal.MULTI_LINE_COMMENT, "/*", "*/"),
            new Clause<>(Literal.STRING_LITERAL, "\"", "\"",
                    Predicate.not(SourceAnalyzer::isTrailingBackslashCorrupted)),
            new Clause<>(Literal.CHAR_LITERAL, "'", "'",
                    Predicate.not(SourceAnalyzer::isTrailingBackslashCorrupted)));

    private static final Map<Literal, Function<Parenthesis<Literal>, String>> PARENTHESIS_MAPPERS =
            Map.of(Literal.SINGLE_LINE_COMMENT, any -> "\n",
                    Literal.MULTI_LINE_COMMENT, parenthesis ->
                            parenthesis.getToken().contains("\n") ? "\n" : "",
                    Literal.STRING_LITERAL, any -> "\"\"",
                    Literal.CHAR_LITERAL, any -> "''");

    public int getLineCount(String source) {
        String unicodelessSource = Strings.translateUnicodes(source);

        String normalizedSource = new ClauseParser<>(unicodelessSource, CLAUSES).toStream()
                .map(SourceAnalyzer::substituteParenthesis)
                .collect(Collectors.joining());

        return (int) Arrays.stream(normalizedSource.split("\n"))
                .map(String::trim)
                .filter(Predicate.not(String::isEmpty))
                .count();
    }

    private static String substituteParenthesis(Parenthesis<Literal> parenthesis) {
        return parenthesis.getType() == null ? parenthesis.getToken()
                : PARENTHESIS_MAPPERS.get(parenthesis.getType()).apply(parenthesis);
    }

    private static boolean isTrailingBackslashCorrupted(String source) {
        Matcher matcher = BACKSLASH_TRAIL.matcher(source);
        matcher.find();
        return matcher.group().length() % 2 != 0;
    }

    private enum Literal {
        SINGLE_LINE_COMMENT,
        MULTI_LINE_COMMENT,
        STRING_LITERAL,
        CHAR_LITERAL
    }
}
