package io.spreatty.codekata13.view.node;

import java.util.Collections;
import java.util.List;

/**
 * A class representing error in <i>node hierarchy</i>.
 */
public final class ErrorNode extends AbstractNode {
    /**
     * Returns -1 telling that it was unable to count number of lines due to error.
     *
     * @return -1
     */
    @Override
    public int getLineCount() {
        return -1;
    }

    /**
     * Always returns empty list.
     *
     * @return Empty list
     */
    @Override
    public List<Node> getChildren() {
        return Collections.emptyList();
    }

    /**
     * Always returns {@code true}.
     *
     * @return {@code true}
     */
    @Override
    public boolean hasErrors() {
        return true;
    }
}
