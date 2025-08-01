package com.baishun.tooldemo;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.LocalDateTime;

/**
 * @description:
 * @Author shengy
 * @Date 2025/7/4 15:50
 */
public class DateTimeTools {
  @Tool(description = "Get the current date and time in the user's timezone")
  String getCurrentDateTime() {
    return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
  }

  public static void main(String[] args) {
    System.out.println(new DateTimeTools().getCurrentDateTime());
  }
}
