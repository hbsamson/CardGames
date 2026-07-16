package io;

import java.nio.file.*;

public class DeckFileReader {
    public static String readFileAsString(String fileName) throws Exception {
        // https://www.geeksforgeeks.org/java/different-ways-reading-text-file-java/
        String data = "";
        data = new String(
                Files.readAllBytes(Paths.get(fileName)));
        return data;
    }
}
