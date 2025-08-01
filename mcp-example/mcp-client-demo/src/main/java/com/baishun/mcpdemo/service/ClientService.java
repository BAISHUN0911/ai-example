package com.baishun.mcpdemo.service;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * @description:
 * @Author shengy
 * @Date 2025/7/31 17:43
 */
@Service
public class ClientService {
  private static final List<String> points;

  private ChatClient zhipuAiChatClient;

  @Autowired
  private ZhiPuAiChatModel zhiPuAiChatModel;

  @Autowired
  private ToolCallbackProvider tools;

  static {
    points = List.of("Java SE", "JVM", "Java多线程", "SpringBoot", "SpringCloud", "MySQL",
            "PostgreSQL", "Redis", "Doris", "Elasticsearch", "Kafka", "RocketMQ", "Docker", "Kubernetes",
            "Linux", "设计模式", "架构设计", "常见算法", "数据结构", "计算机网络");

  }

  @PostConstruct
  public void setUp() {
    this.zhipuAiChatClient =
            ChatClient.builder(zhiPuAiChatModel)
                    .defaultAdvisors(
                            MessageChatMemoryAdvisor.builder(MessageWindowChatMemory.builder().build()).build()
                    )
                    .defaultToolCallbacks(tools)
                    .build();
  }

  private String getPoint() {
    return points.get(new Random().nextInt(points.size()));
  }

  public String postCsdnArticle() {
    String systemPrompt = "你是一个资深的Java开发工程师，请按照以下要求，生成一个 CSDN 博客文章并发布。";
    String userPrompt = """
            请编写关于 %s 技术的3个常见面试问题并附上回答，问题应该结合当下企业实际开发场景。
            你需要注意以下几点
            1.在给文章取标题时，应该与你写的技术内容有关
            2.尽可能让标题更吸引读者，标题可以适当的夸张、夸大
            3.文章内容不要有无关信息，不要有你的语气词。
            4.不要返回给我内容，你必须调用我给你提供的工具去CSDN发布文章
            """;
    Prompt prompt = new Prompt(new UserMessage(String.format(userPrompt, getPoint())), new SystemMessage(systemPrompt));

    zhipuAiChatClient.prompt(prompt).call().chatResponse();
    return "success";
  }
}
