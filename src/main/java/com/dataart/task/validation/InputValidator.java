package com.dataart.task.validation;

import com.dataart.task.domain.Constants;
import com.dataart.task.exception.ConsoleAppException;
import java.util.List;

public final class InputValidator {

  private static final String MAP_REGEX = "^[\\d\\s\\n]+#[\\d\\s\\n]+$";

  public static int validateFloodingValue(String flooding) {
    try {
      int floodHeight = Integer.parseInt(flooding);
      if (floodHeight < Constants.MIN_FLOODING || floodHeight > Constants.MAX_FLOODING) {
        throw new ConsoleAppException("Flooding value should be between %d and %d"
            .formatted(Constants.MIN_FLOODING, Constants.MAX_FLOODING));
      }

      return floodHeight;
    } catch (NumberFormatException e) {
      throw new ConsoleAppException("Provided flooding height value is not valid integer");
    }
  }

  public static void validateMapContents(List<String> mapByLines) {
    String wholeMap = String.join("\n", mapByLines);
    if (!wholeMap.matches(MAP_REGEX)) {
      throw new ConsoleAppException("Provided map is not valid");
    }
  }

  private InputValidator() {
  }

}
