package com.baishun.difydemo.dto.request;

import lombok.Data;

/**
 * @description: @Author shengy @Date 2025/7/8 15:31
 */
@Data
public class DifyRequest {
  // 会话id 首次对话时无需设置
  private String chatId;

  // 用户输入的消息
  private String prompt;

  // dify app名称
  private String appName;
}
