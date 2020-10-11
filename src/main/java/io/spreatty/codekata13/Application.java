package io.spreatty.codekata13;

import io.spreatty.codekata13.io.FileCrawler;
import io.spreatty.codekata13.io.FileReader;
import io.spreatty.codekata13.view.node.Node;
import io.spreatty.codekata13.source.SourceAnalyzer;
import io.spreatty.codekata13.view.ConsoleTreeView;

import java.io.File;

public class Application {
    private SourceAnalyzer sourceAnalyzer;
    private FileReader fileReader;
    private FileCrawler fileCrawler;
    private ConsoleTreeView consoleTreeView;

    public void run(String input) {
        initializeDependencies();

        Node rootNode = fileCrawler.crawl(new File(input));
        consoleTreeView.render(rootNode);
    }

    private void initializeDependencies() {
        sourceAnalyzer = new SourceAnalyzer();
        fileReader = new FileReader();
        fileCrawler = new FileCrawler(sourceAnalyzer, fileReader);
        consoleTreeView = new ConsoleTreeView(System.out);
    }
}
