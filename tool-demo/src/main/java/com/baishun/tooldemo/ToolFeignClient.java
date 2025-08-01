package com.baishun.tooldemo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description:
 * @Author shengy
 * @Date 2025/7/4 16:54
 */
@FeignClient(name = "toolClient", url = "https://qa.irs01.cn")
public interface ToolFeignClient {
  @GetMapping("/idaas-api-v2/admin/theme/{themeName}/_structure")
  String getThemeInfo(@PathVariable("themeName") String themeName);
}
