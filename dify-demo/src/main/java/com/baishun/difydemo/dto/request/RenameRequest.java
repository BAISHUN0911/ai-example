package com.baishun.difydemo.dto.request;

import lombok.Data;

/**
 * @description:
 * @Author shengy
 * @Date 2025/7/15 14:22
 */
@Data
public class RenameRequest {
  private String conversationId;
  private String name;
  private String user;
}
