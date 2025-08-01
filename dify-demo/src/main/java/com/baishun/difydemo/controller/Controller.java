package com.baishun.difydemo.controller;

import com.baishun.difydemo.client.DifyClient;
import com.baishun.difydemo.dto.request.DifyRequest;
import com.baishun.difydemo.dto.request.RenameRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @description:
 * @Author shengy
 * @Date 2025/7/7 14:38
 */
@RequestMapping("/dify")
@RestController
public class Controller {

  @Value("${dify.chatflow.chat_test.key}")
  private String CHAT_FLOW_CHAT_TEST_KEY;

  private final WebClient webClient;

  private final DifyClient difyClient;

  public Controller(WebClient.Builder webClientBuilder, DifyClient difyClient) {
    this.webClient = webClientBuilder.baseUrl("https://dify.irs01.cn").build();
    this.difyClient = difyClient;
  }

  @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public SseEmitter call(@ModelAttribute DifyRequest req) {
    SseEmitter sseEmitter = new SseEmitter(0L);

    difyClient.chat(req, sseEmitter);

    return sseEmitter;
  }

  // 会话列表
  @GetMapping("/conversations")
  public Map conversations(@RequestParam("user") String user) {
    return difyClient.conversations();
  }

  // 会话重命名
  @PostMapping("/conversations/name")
  public Map name(@RequestBody RenameRequest req) {
    return difyClient.rename(req);
  }

  // 删除会话
  @PostMapping("/conversations/delete")
  public void delete(@RequestParam String conversationId) {
    Map<String, Object> requestBody = Map.of("user", "3715");
    Map map = webClient.method(HttpMethod.DELETE).uri("/v1/conversations/{conversationId}",
                    conversationId).header(HttpHeaders.AUTHORIZATION,
                    "Bearer " + CHAT_FLOW_CHAT_TEST_KEY).contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody).retrieve().bodyToMono(Map.class).block();
    System.out.println(map);
  }
}
