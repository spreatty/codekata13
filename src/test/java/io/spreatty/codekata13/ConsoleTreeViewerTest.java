package io.spreatty.codekata13;

import io.spreatty.codekata13.view.ConsoleTreeView;
import io.spreatty.codekata13.view.node.DirectoryNode;
import io.spreatty.codekata13.view.node.ErrorNode;
import io.spreatty.codekata13.view.node.FileNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class ConsoleTreeViewerTest {
    @Test
    void render() {
        FileNode childFile = new FileNode();
        childFile.setName("App.java");
        childFile.setLineCount(5);

        ErrorNode grandChildErrorFile = new ErrorNode();
        grandChildErrorFile.setName("$%@2*.java");

        DirectoryNode childDirectory = new DirectoryNode();
        childDirectory.setName("backup");
        childDirectory.setChildren(List.of(grandChildErrorFile));

        DirectoryNode root = new DirectoryNode();
        root.setName("root");
        root.setChildren(List.of(childDirectory, childFile));

        StringBuilder output = new StringBuilder();
        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int codePoint) throws IOException {
                output.appendCodePoint(codePoint);
            }
        });
        ConsoleTreeView consoleTreeView = new ConsoleTreeView(printStream);

        String ln = System.lineSeparator();
        String expected = root.getName() + " : " + root.getLineCount()
                + ln + "    " + childDirectory.getName() + " : " + childDirectory.getLineCount()
                + ln + "        " + grandChildErrorFile.getName() + " : IO error"
                + ln + "    " + childFile.getName() + " : " + childFile.getLineCount()
                + ln;

        consoleTreeView.print(root);
        Assertions.assertEquals(expected, output.toString());
    }
}
