package com.baishun.mcpdemo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @description:
 * @Author shengy
 * @Date 2025/7/31 17:57
 */
@SpringBootTest
class ClientServiceTest {
  @Autowired
  private ClientService clientService;

  @Test
  void test() {
    clientService.postCsdnArticle();
  }
}