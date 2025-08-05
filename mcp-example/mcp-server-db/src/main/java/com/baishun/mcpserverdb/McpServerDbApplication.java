package com.baishun.mcpserverdb;

import com.baishun.mcpserverdb.service.DbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class McpServerDbApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(McpServerDbApplication.class, args);
  }

  @Bean
  public ToolCallbackProvider computerTools(DbService dbService) {
    return MethodToolCallbackProvider.builder().toolObjects(dbService).build();
  }

  @Override
  public void run(String... args) {
    log.info("mcp server db success!");
  }
}
