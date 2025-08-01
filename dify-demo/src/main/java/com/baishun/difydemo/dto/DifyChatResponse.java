package com.baishun.difydemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @Author shengy
 * @Date 2025/7/7 14:50
 */
@Data
public class DifyChatResponse {

  private String event;

  @JsonProperty("conversation_id")
  private String conversationId;

  @JsonProperty("message_id")
  private String messageId;

  @JsonProperty("created_at")
  private Long createdAt;

  @JsonProperty("task_id")
  private String taskId;

  private String id;

  private String answer;

  @JsonProperty("from_variable_selector")
  private List<String> fromVariableSelector;
}
