package io.spreatty.codekata13.file;

import java.util.List;

public class DirectoryNode extends AbstractNode {
    private List<Node> children;

    @Override
    public int getLineCount() {
        return children.stream()
                .filter(node -> node.getLineCount() != -1)
                .mapToInt(Node::getLineCount).sum();
    }

    @Override
    public List<Node> getChildren() {
        return children;
    }

    @Override
    public boolean hasErrors() {
        return children.stream().anyMatch(Node::hasErrors);
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }
}
