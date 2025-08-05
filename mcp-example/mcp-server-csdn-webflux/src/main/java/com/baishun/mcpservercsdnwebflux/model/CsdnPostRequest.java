package com.baishun.mcpservercsdnwebflux.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * CSDN发帖请求参数模型
 */
@Data
public class CsdnPostRequest {
    private String article_id = "";
    // 标题
    private String title;
    // 描述
    private String description = "";
    // md格式的内容
    private String markdowncontent = "";
    // html格式的内容
    private String content;
    // 标签
    private String tags;
    private String categories = "";
    private String type = "original";
    private int status = 0;
    private String read_type = "public";
    private String reason = "";
    private String original_link = "";
    private boolean authorized_status = false;
    private boolean check_original = false;
    private String source = "pc_postedit";
    private int not_auto_saved = 1;
    private String creator_activity_id = "";
    private List<String> cover_images = new ArrayList<>();
    private int cover_type = 1;
    private int vote_id = 0;
    private String resource_id = "";
    private long scheduled_time = 0;
    private int is_new = 1;
    private int sync_git_code = 0;
} 