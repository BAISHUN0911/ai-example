package com.baishun.mcpserverdemo.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @description:
 * @Author shengy
 * @Date 2025/7/22 13:56
 */
@Configuration
public class JdbcConfig {
  @Bean(name = "atDataSource")
  public DataSource dataSource() {
    return DataSourceBuilder.create()
            .url("jdbc:mysql://localhost:3307/iADCapture")
            .username("root")
            .password("123456")
            .driverClassName("com.mysql.cj.jdbc.Driver")
            .build();
  }

  @Bean(name = "atJdbcTemplate")
  public JdbcTemplate jdbcTemplate(DataSource atDataSource) {
    return new JdbcTemplate(atDataSource);
  }
}
