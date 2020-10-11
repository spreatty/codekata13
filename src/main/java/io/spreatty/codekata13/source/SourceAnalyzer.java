package io.spreatty.codekata13.source;

import io.spreatty.codekata13.util.clause.Clause;
import io.spreatty.codekata13.util.clause.ClauseParser;
import io.spreatty.codekata13.util.clause.Parenthesis;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The {@code SourceAnalyzer} class provides an ability to count the number of lines of actual code
 * in java source. The source must use UNIX style line break {@code \n} and be valid java source
 * meaning every single quote ' outside of comment and string literal must have closing single
 * quote ' outside of comment and string literal on the same line, every double quote " outside of
 * comment and character literal must have closing double quote " outside of comment and character
 * literal on the same line, a closing for multi-line comment *\/ outside of comment and string and
 * character literals must not appear without opening /*.
 *
 * <p> A line is counted if it contains something other than whitespace or text in a comment.
 * This analyzer covers following cases:
 * <ul>
 *     <li>
 *         Simple source - no overlaps of comments and literals.
 *         Example: {@code '' "" // \n /*}
 *     </li>
 *     <li>
 *         Case #1 - literals and comments overlap other literals and comments.
 *         Example: {@code '"' /* " // }
 *     </li>
 *     <li>
 *         Case #2 - literals contain escaped characters of their bounds.
 *         Example: {@code " \" " '\''}
 *     </li>
 *     <li>
 *         Case #3 - string literals contain backslash escapes.
 *         Example: {@code "string ends \\\\\" here ->"}
 *     </li>
 *     <li>
 *         Case #4 - unicode escapes appear in source.
 *         Example: {@code String s = \u005cu0022 This is a valid compilable source.
 *         Here \u005cu0022 is \" character. Unicode escape is replaced with corresponding
 *         character by java compiler at compile time."}
 *     </li>
 * </ul>
 */
public class SourceAnalyzer {
    private static final Pattern UNICODE_ESCAPES = Pattern.compile("\\\\u([0-9A-Fa-f]{4})");

    private static final Function<MatchResult, String> UNICODE_MATCH_REPLACER = match -> {
        String replacement = Character.toString(Integer.parseInt(match.group(1), 16));
        return replacement.equals("\\") ? "\\\\" : replacement;
    };

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

    /**
     * Counts number of lines of code in java source.
     *
     * @param source
     *        Valid java source with UNIX style line breaks {@code \n}
     *
     * @return Number of lines of actual code
     *
     * @throws NullPointerException
     *         If source is {@code null}
     */
    public int getLineCount(String source) {
        if (source == null) {
            throw new NullPointerException();
        }

        String unicodelessSource = UNICODE_ESCAPES.matcher(source)
                .replaceAll(UNICODE_MATCH_REPLACER);

        /*
         * The following invocation of ClauseParser has the same result as following code using
         * standard StreamTokenizer. However custom solution (i.e. ClauseParser) seems easier to
         * understand.
         *
         * StreamTokenizer tokenizer = new StreamTokenizer(fileReader);
         * tokenizer.resetSyntax();
         * tokenizer.whitespaceChars(0, 0x20);
         * tokenizer.wordChars(0x21, 0xff);
         * tokenizer.ordinaryChar('/');
         * tokenizer.slashSlashComments(true);
         * tokenizer.slashStarComments(true);
         * tokenizer.quoteChar('\'');
         * tokenizer.quoteChar('"');
         * tokenizer.eolIsSignificant(true);
         * StringBuilder normalizedSourceBuilder = new StringBuilder();
         * while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
         *     normalizedSourceBuilder.append(
         *             tokenizer.ttype == StreamTokenizer.TT_EOL? "\n" : tokenizer.sval);
         * }
         * String normalizedSource = normalizedSourceBuilder.toString();
         */
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
