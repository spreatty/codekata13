package io.spreatty.codekata13.view.node;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FileNode extends AbstractNode {
    private int lineCount;

    @Override
    public int getLineCount() {
        return lineCount;
    }

    @Override
    public List<Node> getChildren() {
        return Collections.emptyList();
    }

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
