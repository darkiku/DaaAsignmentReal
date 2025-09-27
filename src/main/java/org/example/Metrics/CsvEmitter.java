package org.example.Metrics;

import java.io.*;
import java.nio.file.Path;
import java.util.Map;

public class CsvEmitter {
    public static void append(Path file, Map<String, Object> row) throws IOException {
        boolean exists = file.toFile().exists();
        try (FileWriter fw = new FileWriter(file.toFile(), true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            if (!exists) {
                bw.write(String.join(",", row.keySet()));
                bw.newLine();
            }
            bw.write(String.join(",", row.values().stream().map(Object::toString).toArray(String[]::new)));
            bw.newLine();
        }
    }
}
