package io.spreatty.codekata13;

import io.spreatty.codekata13.io.FileCrawler;
import io.spreatty.codekata13.view.node.FileNode;
import io.spreatty.codekata13.io.FileReader;
import io.spreatty.codekata13.view.node.Node;
import io.spreatty.codekata13.source.SourceAnalyzer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileCrawlerTest {
    private static File rootDirectory;
    private static File childFile;
    private static File childDirectory;

    private static SourceAnalyzer sourceAnalyzer;

    private FileReader fileReader;
    private FileCrawler fileCrawler;

    @BeforeAll
    static void beforeAll() {
        sourceAnalyzer = Mockito.mock(SourceAnalyzer.class);
        Mockito.when(sourceAnalyzer.getLineCount(Mockito.anyString())).thenReturn(1);
    }

    @BeforeEach
    void beforeEach() {
        rootDirectory = Mockito.mock(File.class);
        childDirectory = Mockito.mock(File.class);
        childFile = Mockito.mock(File.class);
        Mockito.when(childFile.getName()).thenReturn(".java");

        fileReader = Mockito.mock(FileReader.class);
        fileCrawler = new FileCrawler(sourceAnalyzer, fileReader);
    }

    @Test
    void crawl() throws IOException {
        Mockito.when(rootDirectory.isDirectory()).thenReturn(true);
        Mockito.when(rootDirectory.listFiles())
                .thenReturn(new File[] {childDirectory, childFile});
        Mockito.when(childDirectory.isDirectory()).thenReturn(true);
        Mockito.when(childDirectory.listFiles()).thenReturn(new File[] {});
        Mockito.when(childFile.isDirectory()).thenReturn(false);
        Mockito.when(fileReader.read(Mockito.any())).thenReturn("");

        Node node = fileCrawler.crawl(rootDirectory);

        Assertions.assertFalse(node.hasErrors());
        Assertions.assertEquals(1, node.getLineCount());
        FileNode expected = new FileNode();
        expected.setName(childFile.getName());
        expected.setLineCount(1);
        Assertions.assertIterableEquals(List.of(expected), node.getChildren());
    }

    @Test
    void crawlIOException() throws IOException {
        Mockito.when(rootDirectory.isDirectory()).thenReturn(false);
        Mockito.when(fileReader.read(Mockito.any())).thenThrow(IOException.class);

        Node node = fileCrawler.crawl(rootDirectory);

        Assertions.assertTrue(node.hasErrors(), "Error was not handled properly");
    }
}
