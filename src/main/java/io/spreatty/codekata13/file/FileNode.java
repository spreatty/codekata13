package io.spreatty.codekata13.file;

import java.util.Collections;
import java.util.List;

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
}
