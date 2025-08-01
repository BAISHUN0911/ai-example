package com.baishun.difydemo.client;

import com.baishun.difydemo.dto.request.DifyRequest;
import com.baishun.difydemo.dto.request.RenameRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

/**
 * @description:
 * @Author shengy
 * @Date 2025/7/9 15:22
 */
@Service
public class DifyClient {

  @Value("${dify.chatflow.chat_test.key}")
  private String CHAT_FLOW_CHAT_TEST_KEY;

  private static final String CHAT_URI = "/v1/chat-messages";

  private static final String CONVERSATIONS_URI = "/v1/conversations";

  private final WebClient webClient;

  public DifyClient(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("https://dify.irs01.cn").build();
  }

  public SseEmitter chat(DifyRequest req, SseEmitter emitter) {
    Map<String, Object> requestBody = Map.of(
            "inputs", "",
            "query", req.getPrompt(),
            "response_mode", "streaming",
            "conversation_id", "",
            "user", "3715"
    );
    String conversationId = req.getChatId();

    webClient.post()
            .uri(CHAT_URI)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + CHAT_FLOW_CHAT_TEST_KEY)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.TEXT_EVENT_STREAM)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToFlux(String.class)
            .doOnNext(chunk -> {
              try {
                emitter.send(SseEmitter.event().data(chunk));
              } catch (IOException e) {
                emitter.completeWithError(e);
              }
            })
            .doOnComplete(() -> {
              System.out.println("流式请求完成");
              emitter.complete();
            })
            .doOnError(emitter::completeWithError)
            .doOnTerminate(emitter::complete)
            .subscribe();

    return emitter;
  }

  public Map conversations() {
    return webClient.get().uri(b -> b.path(CONVERSATIONS_URI).queryParam("user", "3715").build())
            .header(HttpHeaders.AUTHORIZATION, "Bearer app-Frf5yIy3ZALGE95QtQCHpYUd").retrieve()
            .bodyToMono(Map.class).block();
  }

  public Map rename(RenameRequest req) {
    String conversationId = req.getConversationId();

    boolean autoGenerate = !StringUtils.hasText(req.getName());

    Map<String, Object> requestBody = Map.of("name", req.getName(), "auto_generate", autoGenerate, "user", req.getUser());

    Map response = webClient.post().uri("/v1/conversations/" + conversationId + "/name")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + CHAT_FLOW_CHAT_TEST_KEY)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(Map.class).block();
    return response;
  }
}
