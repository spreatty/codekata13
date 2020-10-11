package io.spreatty.codekata13.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileReader {
    public String read(File file) throws IOException {
        return String.join("\n", Files.readAllLines(file.toPath()));
    }
}
