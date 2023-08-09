package com.dataart.task.domain;

import java.util.Set;

public record MapSquare (int x, int y) {
  public Set<MapSquare> getClosest() {
    return Set.of(
        new MapSquare(x, y + 1),
        new MapSquare(x, y - 1),
        new MapSquare(x + 1, y),
        new MapSquare(x - 1, y)
    );
}
}
