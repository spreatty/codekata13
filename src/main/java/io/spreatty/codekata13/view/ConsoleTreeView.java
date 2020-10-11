package io.spreatty.codekata13.view;

import io.spreatty.codekata13.view.node.Node;

import java.io.PrintStream;
import java.util.Comparator;

/**
 * A class that prints tree structure using indentation of 4 spaces.
 */
public class ConsoleTreeView {
    private static final String TAB = "    ";

    private final PrintStream out;

    private String indentation = "";

    /**
     * Creates new instance of {@code ConsoleTreeView} for provided {@linkplain PrintStream}
     *
     * @param out
     *        A {@link PrintStream} instance
     *
     * @throws NullPointerException
     *         If print stream is {@code null}
     */
    public ConsoleTreeView(PrintStream out) {
        if (out == null) {
            throw new NullPointerException();
        }
        this.out = out;
    }

    /**
     * Prints tree structure starting from provided node. When printing child nodes, file nodes
     * come first.
     *
     * @param node
     *        A starting point for this viewer
     *
     * @throws NullPointerException
     *         if node is {@code null}
     */
    public void print(Node node) {
        if (node == null) {
            throw new NullPointerException();
        }
        print(getNodeHeader(node));
        indent();
        node.getChildren().stream()
                .sorted(Comparator.comparing(Node::isDirectory))
                .forEachOrdered(this::print);
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
