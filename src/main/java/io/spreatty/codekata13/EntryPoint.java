package io.spreatty.codekata13;

public class EntryPoint {
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No input provided");
        }
        new Application().run(args[0]);
    }
}
