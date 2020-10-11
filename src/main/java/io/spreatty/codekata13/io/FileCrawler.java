package io.spreatty.codekata13.io;

import io.spreatty.codekata13.source.SourceAnalyzer;
import io.spreatty.codekata13.view.node.DirectoryNode;
import io.spreatty.codekata13.view.node.ErrorNode;
import io.spreatty.codekata13.view.node.FileNode;
import io.spreatty.codekata13.view.node.Node;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class FileCrawler {
    private SourceAnalyzer sourceAnalyzer;
    private FileReader fileReader;

    public FileCrawler(SourceAnalyzer sourceAnalyzer, FileReader fileReader) {
        this.sourceAnalyzer = sourceAnalyzer;
        this.fileReader = fileReader;
    }

    public Node crawl(File file) {
        return file.isDirectory() ? directory(file): file(file);
    }

    private Node directory(File directory) {
        DirectoryNode directoryNode = new DirectoryNode();
        directoryNode.setName(directory.getName());
        directoryNode.setChildren(
                Arrays.stream(directory.listFiles())
                        .filter(file -> file.isDirectory() || file.getName().endsWith(".java"))
                        .map(this::crawl)
                        .filter(node -> node.getLineCount() > 0 || node.hasErrors())
                        .collect(Collectors.toList()));
        return directoryNode;
    }

    private Node file(File file) {
        try {
            FileNode fileNode = new FileNode();
            fileNode.setName(file.getName());
            fileNode.setLineCount(sourceAnalyzer.getLineCount(fileReader.read(file)));
            return fileNode;
        } catch (IOException e) {
            ErrorNode errorNode = new ErrorNode();
            errorNode.setName(file.getName());
            return errorNode;
        }
    }
}
