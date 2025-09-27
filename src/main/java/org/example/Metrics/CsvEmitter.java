package org.example.Metrics;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvEmitter {

    public static void append(Path file, Map<String, Object> row) throws IOException {
        boolean exists = file.toFile().exists();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file.toFile(), true))) {
            if (!exists) {
                String header = String.join(",", row.keySet());
                bw.write(header);
                bw.newLine();
            }
            String line = row.values().stream().map(Object::toString).collect(Collectors.joining(","));
            bw.write(line);
            bw.newLine();
        }
    }
}
