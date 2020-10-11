package io.spreatty.codekata13.view.node;

import java.util.List;
import java.util.Objects;

/**
 * A class representing directory in <i>node hierarchy</i>. This node may have any number of child
 * nodes of any type. The methods have no own logic, but fully rely on children's values.
 */
public class DirectoryNode extends AbstractNode {
    private List<Node> children;

    /**
     * Returns total number of lines in all child nodes. Does not count nodes that have errors.
     *
     * @return Total number of lines
     */
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
