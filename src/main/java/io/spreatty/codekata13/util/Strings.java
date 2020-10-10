package io.spreatty.codekata13.util;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Strings {
    private static final Pattern UNICODE_ESCAPES = Pattern.compile("\\\\u([0-9A-Fa-f]{4})");

    private Strings() {
    }

    public static String translateUnicodes(String source) {
        return replaceAll(source, UNICODE_ESCAPES, match ->
                Character.toString(Integer.parseInt(match.group(1), 16)));
    }

    public static String replaceAll(String source, Pattern regex, Function<MatchResult, String> replacer) {
        List<MatchResult> matches = regex.matcher(source).results().collect(Collectors.toList());
        if (matches.isEmpty()) {
            return source;
        }

        Iterator<MatchResult> iterator = matches.iterator();
        MatchResult left = iterator.next();
        MatchResult right = left;

        StringBuilder result = new StringBuilder()
                .append(source, 0, left.start());
        while (iterator.hasNext()) {
            left = right;
            right = iterator.next();
            result.append(replacer.apply(left))
                    .append(source, left.end(), right.start());
        }
        result.append(replacer.apply(right))
                .append(source.substring(right.end()));

        return result.toString();
    }
}
