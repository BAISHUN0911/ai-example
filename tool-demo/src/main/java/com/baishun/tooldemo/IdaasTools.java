package com.baishun.tooldemo;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @Author shengy
 * @Date 2025/7/4 17:34
 */
@Component
public class IdaasTools {
  private final ToolFeignClient toolFeignClient;

  public IdaasTools(ToolFeignClient toolFeignClient) {
    this.toolFeignClient = toolFeignClient;
  }

  @Tool(description = "根据中台主题名称查询对应表结构")
  public String getSchemaByThemeName(String themeName) {
    String themeInfo = toolFeignClient.getThemeInfo(themeName);
    return themeInfo;
  }

}
