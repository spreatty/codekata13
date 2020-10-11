package io.spreatty.codekata13.view.node;

import java.util.List;
import java.util.Objects;

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
        DirectoryNode that = (DirectoryNode) object;
        return Objects.equals(children, that.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), children);
    }
}
