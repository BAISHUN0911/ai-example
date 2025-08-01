package com.baishun.mcpservercsdn;

import com.baishun.mcpservercsdn.service.CsdnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class McpServerCsdnApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(McpServerCsdnApplication.class, args);
  }

  @Bean
  public ToolCallbackProvider computerTools(CsdnService csdnService) {
    return MethodToolCallbackProvider.builder().toolObjects(csdnService).build();
  }

  @Override
  public void run(String... args) {
    log.info("mcp server computer success!");
  }
}
