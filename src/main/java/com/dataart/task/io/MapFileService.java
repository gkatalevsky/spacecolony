package com.dataart.task.io;

import com.dataart.task.domain.Constants;
import com.dataart.task.exception.ConsoleAppException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MapFileService {

  public List<String> readFile(String filename) {
    try {
      Path path = Path.of(filename);

      if (Files.notExists(path)) {
        throw new ConsoleAppException("Error reading file: File does not exist.");
      }
      return Files.readAllLines(path);
    } catch (IOException e) {
      throw new ConsoleAppException("Error reading file");
    }
  }

  public void writeMapToFile(List<String> outputMapLines) {
    try {
      Path outputFilePath = Path.of(Constants.OUTPUT_FILENAME);
      Files.deleteIfExists(outputFilePath);
      Path outputPath = Files.createFile(outputFilePath);

      Files.writeString(outputPath, String.join("\n", outputMapLines));
    } catch (IOException e) {
      throw new ConsoleAppException("Error creating output file.");
    }
  }
}
