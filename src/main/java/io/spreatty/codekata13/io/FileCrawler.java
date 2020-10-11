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

/**
 * Crawls the file system and analyzes found source files.
 */
public class FileCrawler {
    private final SourceAnalyzer sourceAnalyzer;
    private final FileReader fileReader;

    /**
     * Creates a new {@code FileCrawler} instance with provided dependencies.
     *
     * @param sourceAnalyzer
     *        A {@link SourceAnalyzer} instance
     * @param fileReader
     *        A {@link FileReader} instance
     *
     * @throws NullPointerException
     *         If any argument is {@code null}
     */
    public FileCrawler(SourceAnalyzer sourceAnalyzer, FileReader fileReader) {
        if (sourceAnalyzer == null || fileReader == null) {
            throw new NullPointerException();
        }
        this.sourceAnalyzer = sourceAnalyzer;
        this.fileReader = fileReader;
    }

    /**
     * Crawls the file system starting from provided file. Directories that do not have at least
     * one file with at least one line or an error are discarded.
     *
     * @param file
     *        Starting point for crawl
     *
     * @return Tree structure containing results of checked files
     *
     * @throws NullPointerException
     *         If file is {@code null}
     */
    public Node crawl(File file) {
        if (file == null) {
            throw new NullPointerException();
        }
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
