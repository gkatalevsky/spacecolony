package com.dataart.task;

import com.dataart.task.io.MapFileService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
  @Bean
  public MapFileService mapFileService() {
    return new MapFileService();
  }
}
