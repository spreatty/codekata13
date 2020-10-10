package io.spreatty.codekata13;

import io.spreatty.codekata13.file.FileCrawler;
import io.spreatty.codekata13.file.Node;
import io.spreatty.codekata13.source.SourceAnalyzer;
import io.spreatty.codekata13.view.ConsoleTreeView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

public class Application {
    private SourceAnalyzer sourceAnalyzer;
    private FileCrawler fileCrawler;
    private ConsoleTreeView consoleTreeView;

    public void run(String input) throws FileNotFoundException {
        File root = new File(input);
        if (!root.exists()) {
            throw new FileNotFoundException(input + " does not exist");
        }

        initializeDependencies();

        Node rootNode = fileCrawler.crawl(root);
        consoleTreeView.render(rootNode);
    }

    private void initializeDependencies() {
        sourceAnalyzer = new SourceAnalyzer();
        fileCrawler = new FileCrawler(sourceAnalyzer);
        consoleTreeView = new ConsoleTreeView(System.out);
    }
}
