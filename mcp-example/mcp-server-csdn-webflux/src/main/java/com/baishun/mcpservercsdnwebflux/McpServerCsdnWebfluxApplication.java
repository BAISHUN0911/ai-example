package com.baishun.mcpservercsdnwebflux;

import com.baishun.mcpservercsdnwebflux.service.CsdnService;
import com.baishun.mcpservercsdnwebflux.service.TimeService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class McpServerCsdnWebfluxApplication {

  public static void main(String[] args) {
    SpringApplication.run(McpServerCsdnWebfluxApplication.class, args);
  }

  @Bean
  public ToolCallbackProvider timeTools(TimeService timeService, CsdnService csdnService) {
    return MethodToolCallbackProvider.builder().toolObjects(timeService, csdnService).build();
  }

}
