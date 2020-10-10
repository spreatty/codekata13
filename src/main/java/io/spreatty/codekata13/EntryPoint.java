package io.spreatty.codekata13;

import java.io.FileNotFoundException;

public class EntryPoint {
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length == 0) {
            throw new IllegalArgumentException("No input provided");
        }
        new Application().run(args[0]);
    }
}
