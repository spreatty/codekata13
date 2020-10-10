package io.spreatty.codekata13.file;

import io.spreatty.codekata13.source.SourceAnalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Collectors;

public class FileCrawler {
    private SourceAnalyzer sourceAnalyzer;

    public FileCrawler(SourceAnalyzer sourceAnalyzer) {
        this.sourceAnalyzer = sourceAnalyzer;
    }

    public Node crawl(File root) {
        if (!root.exists()) {
            throw new IllegalArgumentException();
        }

        return root.isDirectory() ? directory(root): file(root);
    }

    private Node directory(File root) {
        DirectoryNode directoryNode = new DirectoryNode();
        directoryNode.setName(root.getName());
        directoryNode.setChildren(
                Arrays.stream(root.listFiles())
                        .filter(file -> file.isDirectory() || file.getName().endsWith(".java"))
                        .map(this::crawl)
                        .collect(Collectors.toList()));
        return directoryNode;
    }

    private Node file(File root) {
        try {
            FileNode fileNode = new FileNode();
            fileNode.setName(root.getName());
            String source = String.join("\n", Files.readAllLines(root.toPath()));
            fileNode.setLineCount(sourceAnalyzer.getLineCount(source));
            return fileNode;
        } catch (IOException e) {
            return new ErrorNode();
        }
    }
}
