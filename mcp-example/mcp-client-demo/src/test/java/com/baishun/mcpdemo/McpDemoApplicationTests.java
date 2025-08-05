package com.baishun.mcpdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class McpDemoApplicationTests {

  private ChatClient zhipuAiChatClient;

  @Autowired
  private ZhiPuAiChatModel zhiPuAiChatModel;

  @Autowired
  private ToolCallbackProvider tools;

  @BeforeEach
  void setUp() {
    this.zhipuAiChatClient =
            ChatClient.builder(zhiPuAiChatModel)
                    .defaultAdvisors(
                            MessageChatMemoryAdvisor.builder(MessageWindowChatMemory.builder().build()).build()
                    )
                    .defaultToolCallbacks(tools)
                    .build();
  }

  @Test
  void testChatClient() {
    ChatClient.ChatClientRequestSpec requestSpec = zhipuAiChatClient.prompt("请介绍你自己");
    ChatClient.CallResponseSpec responseSpec = requestSpec.call();
    System.out.println(responseSpec.content());
  }

  @Test
  void testChatModel() {
    System.out.println(zhiPuAiChatModel.call("请介绍你自己"));
  }

  @Test
  void test_tool() {
    String userInput = "你当前有哪些工具可以使用";

    System.out.println("\n>>> QUESTION: " + userInput);
    System.out.println("\n>>> ASSISTANT: " + zhipuAiChatClient.prompt(userInput).call().content());
  }

  @Test
  void test() {
    String userInput = "获取电脑配置 在 E:\\tmp 文件夹下，创建 电脑.txt 把电脑配置写入 电脑.txt";

    System.out.println("\n>>> QUESTION: " + userInput);
    System.out.println("\n>>> ASSISTANT: " + zhipuAiChatClient.prompt(userInput).call().chatResponse());
  }

  @Test
  void testQueryDataFromTable() {
    String userInput = "主题名（themeName）：产品信息，查询对应的数据信息";

    System.out.println("\n>>> QUESTION: " + userInput);
    ChatClient.CallResponseSpec responseSpec = zhipuAiChatClient.prompt(userInput).call();
    System.out.println("\n>>> ASSISTANT: " + responseSpec.chatResponse());
  }

  @Test
  void testPostCsdnArticle() {
    String systemPrompt = "你是一个资深的Java开发工程师，请按照以下要求，生成一个 CSDN 博客文章并发布。";
    String userPrompt = """
    随机选取Java开发常用技术栈中任意一个技术编写3个常见的该技术面试问题并附上回答，问题应该结合当下企业实际开发场景。
    你需要注意以下几点
    1.在给文章取标题时，应该与你写的技术内容有关
    2.尽可能让标题更吸引读者，标题可以适当的夸张、夸大
    3.涉及到的技术可以是：Java SE、JVM、SpringBoot、SpringCloud、MySQL、PostgreSQL、Redis、Elasticsearch、
    Kafka、RocketMQ、Docker、Kubernetes
    4.文章内容不要有非用户需要的无关信息，不要有你的语气词。
    5.不要返回给我内容，你必须调用我给你提供的工具去CSDN发布文章
    """;
    Prompt prompt = new Prompt(new UserMessage(userPrompt), new SystemMessage(systemPrompt));
    System.out.println(zhipuAiChatClient.prompt(prompt).call().chatResponse());
  }
}
