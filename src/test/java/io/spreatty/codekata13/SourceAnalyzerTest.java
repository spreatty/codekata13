package io.spreatty.codekata13;

import io.spreatty.codekata13.source.SourceAnalyzer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SourceAnalyzerTest {
    private static SourceAnalyzer sourceAnalyzer = new SourceAnalyzer();

    @Test
    void empty() {
        Assertions.assertEquals(0, sourceAnalyzer.getLineCount(""));
    }

    @Test
    void commentedLineBreak() {
        Assertions.assertEquals(0, sourceAnalyzer.getLineCount("/*\n*/"));
    }

    @Test
    void singleLineCommentPreventsMultiLineComment() {
        Assertions.assertEquals(1, sourceAnalyzer.getLineCount("///*\nX\n//*/"));
    }

    @Test
    void characterPreventsString() {
        Assertions.assertEquals(1, sourceAnalyzer.getLineCount("'\"'/*\"\nX//*/"));
    }

    @Test
    void escapedSingleQuoteDetected() {
        Assertions.assertEquals(1, sourceAnalyzer.getLineCount("'\\''/*'\nX//*/"));
    }

    @Test
    void escapedDoubleQuoteDetected() {
        Assertions.assertEquals(1, sourceAnalyzer.getLineCount("\"\\\"\"/*\"\nX//*/"));
        Assertions.assertEquals(2, sourceAnalyzer.getLineCount("\"\\\\\"\"/*\"\nX//*/"));
        Assertions.assertEquals(1, sourceAnalyzer.getLineCount("\"\\\\\\\"\"/*\"\nX//*/"));
    }
}
