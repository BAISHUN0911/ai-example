package com.baishun.mcpserverdb.service;

import com.alibaba.fastjson2.JSON;
import com.baishun.mcpserverdb.model.TableRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description:
 * @Author shengy
 * @Date 2025/7/22 11:31
 */
@Slf4j
@Service
public class DbService {

  private final JdbcTemplate atJdbcTemplate;

  public DbService(@Qualifier("atJdbcTemplate") JdbcTemplate atJdbcTemplate) {
    this.atJdbcTemplate = atJdbcTemplate;
  }

  @Tool(description = "根据主题名查询指定数据库表数据")
  public String queryDataFromTable(TableRequest request) {
    if (request == null) {
      TableRequest tableRequest = new TableRequest();
      tableRequest.setThemeName("行业信息");
      request = tableRequest;
    }
    String tableName = mapThemeToTable(request.getThemeName());
    String themeName = request.getThemeName();

    String sql = "SELECT * FROM " + tableName + " LIMIT ?";
    List<Map<String, Object>> data = atJdbcTemplate.queryForList(sql, 100);

    String fileFormat = "csv";
    String filePath = writeToFile(tableName, data, fileFormat != null ? fileFormat : "csv");

    log.info("主题:{}, 目标文件:{}", themeName, filePath);
    return "已将主题 [" + themeName + "] 的数据导出到文件：" + filePath;
  }

  private String writeToFile(String tableName, List<Map<String, Object>> data, String format) {
    String filePath = "export/" + tableName + "_" + System.currentTimeMillis() + "." + format;

    try {
      File dir = new File("export");
      if (!dir.exists()) dir.mkdirs();

      if ("csv".equalsIgnoreCase(format)) {
        writeCsv(filePath, data);
      } else if ("json".equalsIgnoreCase(format)) {
        Files.writeString(Paths.get(filePath), JSON.toJSONString(data));
      }
    } catch (Exception e) {
      throw new RuntimeException("导出失败: " + e.getMessage());
    }
    return filePath;
  }

  private void writeCsv(String filePath, List<Map<String, Object>> data) throws IOException {
    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
      if (data.isEmpty()) return;
      // 写表头
      Set<String> headers = data.get(0).keySet();
      writer.write(String.join(",", headers));
      writer.newLine();
      // 写数据
      for (Map<String, Object> row : data) {
        String line = headers.stream()
                .map(h -> String.valueOf(row.getOrDefault(h, "")))
                .collect(Collectors.joining(","));
        writer.write(line);
        writer.newLine();
      }
    }
  }

  private String mapThemeToTable(String themeName) {
    return switch (themeName) {
      case "行业信息" -> "IAC_Vocation";
      case "产品信息" -> "iac_product";
      default -> throw new IllegalArgumentException("Invalid theme name: " + themeName);
    };
  }


}
