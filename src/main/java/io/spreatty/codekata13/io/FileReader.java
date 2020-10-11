package io.spreatty.codekata13.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Reads text from character files. Converts non-UNIX style line breaks to UNIX style {@code \n}.
 */
public class FileReader {
    /**
     * Reads file converts non-UNIX style line breaks to UNIX style {@code \n}.
     *
     * @param file
     *        A {@link File} instance
     *
     * @return The text from the file
     *
     * @throws IOException
     *         If an I/O error occurs reading from the file or a malformed or unmappable byte
     *         sequence is read.
     * @throws SecurityException
     *         In the case of the default provider, and a security manager is installed, the
     *         {@link SecurityManager#checkRead(String) checkRead} method is invoked to check read
     *         access to the file
     */
    public String read(File file) throws IOException {
        return String.join("\n", Files.readAllLines(file.toPath()));
    }
}
