package com.baishun.mcpservercsdnwebflux.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @Author shengy
 * @Date 2025/8/5 11:34
 */
@Service
public class TimeService {

  @Tool(description = "获取当前时间")
  public String getTime() {
    return "当前时间：" + System.currentTimeMillis();
  }
}
