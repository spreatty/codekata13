package io.spreatty.codekata13.view.node;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A class representing successfully processed file in <i>node hierarchy</i>. This node can not
 * have children and errors.
 */
public class FileNode extends AbstractNode {
    private int lineCount;

    @Override
    public int getLineCount() {
        return lineCount;
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

    @Override
    public boolean isDirectory() {
        return false;
    }

    /**
     * Always returns {@code false}.
     *
     * @return {@code false}
     */
    @Override
    public boolean hasErrors() {
        return false;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object){
            return true;
        }
        if (object == null || getClass() != object.getClass()){
            return false;
        }
        if (!super.equals(object)){
            return false;
        }
        FileNode fileNode = (FileNode) object;
        return lineCount == fileNode.lineCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), lineCount);
    }
}
