package com.dataart.task;

import com.dataart.task.domain.MapSquare;
import com.dataart.task.domain.TerrainMap;
import com.dataart.task.exception.ConsoleAppException;
import com.dataart.task.io.MapFileService;
import com.dataart.task.service.TerrainMapService;
import com.dataart.task.validation.InputValidator;
import java.util.List;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsoleApp implements CommandLineRunner {

  private final MapFileService fileService;
  private final TerrainMapService terrainMapService;

  public static void main(String[] args) {
    SpringApplication.run(ConsoleApp.class, args);
  }

  @Override
  public void run(String... args) {
    if (args.length < 2) {
      throw new ConsoleAppException(
          "Arguments missing. Exactly 2 arguments must be provided (filename and floodHeight).");
    }
    var filename = args[0];
    var flooding = args[1];

    int floodHeight = InputValidator.validateFloodingValue(flooding);
    List<String> mapLines = fileService.readFile(filename);
    InputValidator.validateMapContents(mapLines);

    TerrainMap map = terrainMapService.parseMap(mapLines);
    Set<MapSquare> suitableSquares = terrainMapService.valuateSuitableForBuilding(map, floodHeight);
    List<String> mapWithMarks = terrainMapService.createValuatedMap(mapLines, suitableSquares);

    fileService.writeMapToFile(mapWithMarks);
  }

  public ConsoleApp(MapFileService fileService, TerrainMapService terrainMapService) {
    this.fileService = fileService;
    this.terrainMapService = terrainMapService;
  }
}
