package com.baishun.mcpservercsdnwebflux.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description:
 * @Author shengy
 * @Date 2025/8/5 15:25
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class ArticleFunctionResponse {
  @JsonPropertyDescription("返回信息")
  private String msg;
}
