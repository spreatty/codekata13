package io.spreatty.codekata13.view.node;

import java.util.Collections;
import java.util.List;

public final class ErrorNode extends AbstractNode {
    @Override
    public int getLineCount() {
        return -1;
    }

    @Override
    public List<Node> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public boolean hasErrors() {
        return true;
    }
}
