package io.spreatty.codekata13.view.node;

import java.util.Objects;

/**
 * An abstraction that implements common methods for child classes in <i>node hierarchy</i>.
 */
public abstract class AbstractNode implements Node {
    private String name;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object){
            return true;
        }
        if (object == null || getClass() != object.getClass()){
            return false;
        }
        AbstractNode that = (AbstractNode) object;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
