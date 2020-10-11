package io.spreatty.codekata13.view;

import io.spreatty.codekata13.view.node.Node;

import java.io.PrintStream;

public class ConsoleTreeView {
    private static final String TAB = "    ";

    private final PrintStream out;

    private String indentation = "";

    public ConsoleTreeView(PrintStream out) {
        this.out = out;
    }

    public void render(Node node) {
        print(getNodeHeader(node));
        indent();
        node.getChildren().forEach(this::render);
        unindent();
    }

    private String getNodeHeader(Node node) {
        return node.getName() + " : "
                + (node.getLineCount() == -1 ? "IO error" : node.getLineCount());
    }

    private void print(String line) {
        out.print(indentation);
        out.println(line);
    }

    private void indent() {
        indentation += TAB;
    }

    private void unindent() {
        if (indentation.length() == 0) {
            throw new IllegalStateException("Can not unindent more");
        }
        indentation = indentation.substring(0, indentation.length() - TAB.length());
    }
}
