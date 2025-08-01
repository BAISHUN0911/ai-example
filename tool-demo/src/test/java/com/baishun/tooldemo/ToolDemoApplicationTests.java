package com.baishun.tooldemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ToolDemoApplicationTests {

  @Autowired
  private ToolFeignClient toolFeignClient;

  @Test
  void contextLoads() {
    String themeInfo = toolFeignClient.getThemeInfo("vt_sport_event_insight");
    System.out.println(themeInfo);
  }

}
