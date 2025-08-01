package com.baishun.mcpserverdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

/**
 * @description:
 * @Author shengy
 * @Date 2025/7/22 11:34
 */
@Data
public class TableRequest {
  @JsonProperty(required = true, value = "themeName")
  @JsonPropertyDescription("主题名用于查询对应数据库表名")
  private String themeName;

//  @JsonProperty(required = false, value = "limit")
//  @JsonPropertyDescription("数据条数")
//  private Integer limit = 100;
//
//  @JsonProperty(required = false, value = "fileFormat")
//  @JsonPropertyDescription("文件格式默认是csv")
//  private String fileFormat = "csv";
}
