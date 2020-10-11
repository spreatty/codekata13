package io.spreatty.codekata13.view.node;

import java.util.List;

/**
 * The root interface in the <i>node hierarchy</i>. A {@code Node} represents a file in the file
 * system with its meta information.
 */
public interface Node {
    String getName();

    int getLineCount();

    List<Node> getChildren();

    boolean isDirectory();

    boolean hasErrors();
}
