package com.dataart.task.io;

import com.dataart.task.domain.Constants;
import com.dataart.task.exception.ConsoleAppException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MapFileService.class)
public class MapFileServiceTest {

  private final MapFileService mapFileService;

  @Test
  public void testFileRead() throws IOException {
    String sampleMap = """
        000130210
        001240145
        00255#220
        001121110""";
    String filename = "testfile.txt";

    Path path = Paths.get(filename);
    Files.deleteIfExists(path);

    Files.createFile(path);
    Files.writeString(path, sampleMap);


    List<String> lines = mapFileService.readFile(filename);

    Assertions.assertEquals(sampleMap, String.join("\n", lines));

    Files.deleteIfExists(path);
  }

  @Test
  public void testNoFile() throws IOException {
    String filename = "testfile.txt";

    Path path = Paths.get(filename);
    Files.deleteIfExists(path);

    Assertions.assertThrows(ConsoleAppException.class, () -> mapFileService.readFile(filename));
  }

  @Test
  public void testWriteMapToFile() throws IOException {
    String sampleMap = """
        000130210
        001240145
        00255#220
        001121110""";

    List<String> lines = Arrays.stream(sampleMap.split("\n")).toList();
    mapFileService.writeMapToFile(lines);

    byte[] fileBytes = Files.readAllBytes(Paths.get(Constants.OUTPUT_FILENAME));
    Assertions.assertArrayEquals(sampleMap.getBytes(), fileBytes);
  }

  @Autowired
  public MapFileServiceTest(MapFileService mapFileService) {
    this.mapFileService = mapFileService;
  }
}
