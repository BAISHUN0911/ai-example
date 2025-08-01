package com.baishun.tooldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ToolDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(ToolDemoApplication.class, args);
  }

}
