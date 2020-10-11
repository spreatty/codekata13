package io.spreatty.codekata13;

import io.spreatty.codekata13.util.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringsTest {
    @Test
    void translateUnicodes() {
        String backslashUnicode = "\\" + "u005c";
        String backslash = "\\";
        Assertions.assertEquals(backslash, Strings.translateUnicodes(backslashUnicode),
                "Lowercase-lettered unicode translation fails");

        String slashUnicode = "\\" + "u002F";
        String slash = "/";
        Assertions.assertEquals(slash, Strings.translateUnicodes(slashUnicode),
                "Uppercase-lettered unicode translation fails");
    }

    @Test
    void replaceAll() {
        String source = "a-49-50-b-51-52-c";
        Pattern digitCodePair = Pattern.compile("(\\d{2})-(\\d{2})");
        Function<MatchResult, String> replacer = match ->
                Stream.of(match.group(1), match.group(2))
                        .map(Byte::parseByte)
                        .map(Character::toString)
                        .collect(Collectors.joining("-"));
        String expected = "a-1-2-b-3-4-c";
        Assertions.assertEquals(expected, Strings.replaceAll(source, digitCodePair, replacer));
    }

    /**
     * This test clearly shows, why Pattern.compile().matcher().replaceAll() fails on strings
     * containing unicode escapes.
     */
    @Test
    void standardReplaceAllFails() {
        String backslashUnicode = "\\" + "u005c";
        String slash = "\\";
        try {
            String replaced = Pattern.compile("\\\\u([0-9A-Fa-f]{4})").matcher(backslashUnicode)
                    .replaceAll(match -> Character.toString(Integer.parseInt(match.group(1))));

            Assertions.assertEquals(slash, replaced,
                    "This version of Java correctly replaces unicode escapes and you" +
                    " may switch to standard util. Your version of Java is " + Runtime.version());
        } catch (RuntimeException expected) {

        }
    }
}
