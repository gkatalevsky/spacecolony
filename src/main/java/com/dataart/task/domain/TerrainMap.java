package com.dataart.task.domain;

import java.util.HashMap;
import java.util.Map;

public class TerrainMap {
  private final Map<MapSquare, Integer> heightMap;
  private MapSquare shipLanding;

  public TerrainMap() {
    heightMap = new HashMap<>();
  }

  public boolean containsSquare(MapSquare square) {
    return heightMap.containsKey(square);
  }

  public Integer getHeight(MapSquare square) {
    return heightMap.get(square);
  }

  public void putSquare(MapSquare square, int height) {
    heightMap.put(square, height);
  }

  public void setShipLanding(MapSquare shipLanding) {
    this.shipLanding = shipLanding;
    putSquare(shipLanding, Constants.SHIP_LANDING_HEIGHT);
  }

  public MapSquare getShipLanding() {
    return shipLanding;
  }
}
