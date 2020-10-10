package io.spreatty.codekata13.file;

public abstract class AbstractNode implements Node {
    private String name;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
