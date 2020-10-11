package io.spreatty.codekata13.view.node;

import java.util.List;

public interface Node {
    String getName();

    int getLineCount();

    List<Node> getChildren();

    boolean hasErrors();
}
