package com.baishun.chatdemo;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatDemoApplicationTests {

  @Autowired
  private ZhiPuAiChatModel zhiPuAiChatModel;

  @Test
  void testChatByModel() {
//    System.out.println(zhiPuAiChatModel.call("假如你是java开发岗位的技术面试官，请列举redis技术常见的10个面试题以及对应的回答，注意：面试题可以适当结合企业应用实际开发场景"));
    String userInput = "假如你是java开发岗位的技术面试官，请列举redis技术常见的2个面试题以及对应的回答";
    ChatResponse chatResponse = zhiPuAiChatModel.call(new Prompt(userInput));
    System.out.println(chatResponse.getResult());
  }

  @Test
  void testChatByClient() {
    ChatClient chatClient = ChatClient.builder(zhiPuAiChatModel)
            .defaultAdvisors(MessageChatMemoryAdvisor.builder(MessageWindowChatMemory.builder().build()).build())
            .defaultAdvisors(new SimpleLoggerAdvisor())
            .defaultOptions(ZhiPuAiChatOptions.builder().topP(0.7).build())
            .build();

    System.out.println(chatClient.prompt("请介绍你自己").call().content());
    System.out.println(chatClient.prompt("我刚刚问的什么问题").call().content());
  }

}
