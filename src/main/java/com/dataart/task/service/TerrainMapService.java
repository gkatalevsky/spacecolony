package com.dataart.task.service;

import com.dataart.task.domain.Constants;
import com.dataart.task.domain.MapSquare;
import com.dataart.task.domain.TerrainMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;

@Service
public class TerrainMapService {

  public TerrainMap parseMap(List<String> mapLines) {
    TerrainMap tMap = new TerrainMap();

    IntStream.range(0, mapLines.size()).forEach(i -> {
      String line = mapLines.get(i);
      IntStream.range(0, line.length()).forEach(j -> {
        MapSquare square = new MapSquare(j, i);
        var character = line.substring(j, j + 1);

        if (character.equals(Constants.SHIP_LANDING_CHAR)) {
          tMap.setShipLanding(square);
        } else {
          tMap.putSquare(square, Integer.parseInt(character) * Constants.SQUARE_HEIGHT_FACTOR);
        }
      });
    });

    return tMap;
  }

  public Set<MapSquare> valuateSuitableForBuilding(TerrainMap map, int floodHeight) {
    Set<MapSquare> suitableSquares = new HashSet<>();

    MapSquare landing = map.getShipLanding();
    landing.getClosest().stream()
        .filter(map::containsSquare)
        .filter(sq -> map.getHeight(sq) > floodHeight)
        .peek(suitableSquares::add)
        .forEach(sq -> checkNeighbours(sq, floodHeight, map, suitableSquares));

    return suitableSquares;
  }

  public List<String> createValuatedMap(List<String> mapLines, Set<MapSquare> buildingMask) {
    List<StringBuilder> strBuildersList = mapLines.stream().map(StringBuilder::new).toList();
    buildingMask.forEach(square -> strBuildersList.get(square.y())
        .setCharAt(square.x(), Constants.SUITABLE_FOR_BUILDING_MARK));

    return strBuildersList.stream().map(StringBuilder::toString).toList();
  }

  private void checkNeighbours(MapSquare square, int floodHeight,
      TerrainMap map, Set<MapSquare> suitableSquares) {
    Set<MapSquare> neighbours = square.getClosest();
    neighbours.stream()
        .filter(sq -> !suitableSquares.contains(sq) && map.containsSquare(sq))
        .filter(sq -> {
          int heightToCheck = map.getHeight(sq);

          return heightToCheck > floodHeight
              && Math.abs(heightToCheck - map.getHeight(square))
              <= Constants.HEIGHT_DIFF_SUITABLE_FOR_ROAD;
        })
        .forEach(sq -> {
          suitableSquares.add(sq);
          checkNeighbours(sq, floodHeight, map, suitableSquares);
        });
  }
}
