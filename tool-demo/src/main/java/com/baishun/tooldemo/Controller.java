package com.baishun.tooldemo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @Author shengy
 * @Date 2025/7/4 15:47
 */
@RestController
@RequestMapping("/time")
public class Controller {
  private final ChatClient zhiPuApiChatClient;

  public Controller(ZhiPuAiChatModel zhiPuAiChatModel) {
    this.zhiPuApiChatClient = ChatClient.builder(zhiPuAiChatModel).build();
  }

  /**
   * No Tool
   */
  @GetMapping("/chat")
  public String simpleChat(@RequestParam(value = "query", defaultValue = "请告诉我现在北京时间几点了") String query) {
    return zhiPuApiChatClient.prompt(query).call().content();
  }

  /**
   * Methods as Tools
   */
  @GetMapping("/chat-tool-method")
  public String chatWithTimeFunction(@RequestParam(value = "query", defaultValue = "请告诉我现在北京时间几点了") String query) {
    return zhiPuApiChatClient.prompt(query).tools(new DateTimeTools()).call().content();
  }
}
