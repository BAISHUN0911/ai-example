package com.baishun.mcpservercsdn;

import com.baishun.mcpservercsdn.model.ArticleFunctionRequest;
import com.baishun.mcpservercsdn.model.CsdnPostRequest;
import com.baishun.mcpservercsdn.model.CsdnPostResponse;
import com.baishun.mcpservercsdn.service.CsdnService;
import com.baishun.mcpservercsdn.utils.MarkdownConverter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class McpServerCsdnApplicationTests {

  @Autowired
  private CsdnService csdnService;

  @Test
  void testDel() {
    csdnService.del("123");
  }

  /**
   * 普通内容格式文章发布 会报401 未找到原因
   * */
  @Test
  void testV1() {
    RestTemplate restTemplate = new RestTemplate();

    String url = "https://bizapi.csdn.net/blog-console-api/v1/postedit/saveArticle";

    // 请求头
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("accept", "application/json, text/plain, */*");
    headers.set("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");
    headers.set("cache-control", "no-cache");
    headers.set("dnt", "1");
    headers.set("origin", "https://mp.csdn.net");
    headers.set("pragma", "no-cache");
    headers.set("priority", "u=1, i");
    headers.set("referer", "https://mp.csdn.net/");
    headers.set("sec-ch-ua", "\"Not)A;Brand\";v=\"8\", \"Chromium\";v=\"138\", \"Microsoft Edge\";v=\"138\"");
    headers.set("sec-ch-ua-mobile", "?0");
    headers.set("sec-ch-ua-platform", "\"Windows\"");
    headers.set("sec-fetch-dest", "empty");
    headers.set("sec-fetch-mode", "cors");
    headers.set("sec-fetch-site", "same-site");
    headers.set("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36 Edg/138.0.0.0");
    headers.set("x-ca-key", "203803574");
    headers.set("x-ca-nonce", "87f00723-b255-4a2e-aa36-a7fea60112f4");
    headers.set("x-ca-signature", "2t023HuJoQjPyvvbHjkCsloE5fQ/AqhV9fKtqaFTgNM=");
    headers.set("x-ca-signature-headers", "x-ca-key,x-ca-nonce");
    headers.set("cookie", "UN=weixin_45706752; p_uid=U010000; fid=20_13688691304-1723301334874-725241; c_dl_um=-; c_dl_prid=1726238959428_528724; c_dl_rid=1732243562457_950669; c_dl_fref=https://blog.csdn.net/gitblog_01123/article/details/141768484; c_dl_fpage=/download/weixin_42134554/15903208; uuid_tt_dd=10_2420134770-1734667232542-752040; ssxmod_itna=YqRxcD2QDQG=AExl4iqb+DyDIhj5=oQKdbee7DlxoxA5D8D6DQeGTb2WDBWRQifdT3bk077TwWS8iOahIOAjYcnbHDCPGnDB95QqmDYA8Dt4DTD34DYDixibkxi5GRD0KDFWqvz19Dm4GWWqGfDDoDYb=RDitD4qDBzodDKqGgWLh=FA6PDDXPD1h=iwDDeb0qu82gIQU=texeWqDMfeGXQ09=sl9CHYXqs=FDThQDzu7DtLUgnkLox0p99utDp7GI0F+znr4qFOqenhDEi+4o/7heAGDruD1eS7Dmeyf627kDG+GU6bD===; ssxmod_itna2=YqRxcD2QDQG=AExl4iqb+DyDIhj5=oQKdbeeD6h9nQD05DtqkDLiciru/4n4Qx6PniinPNede3nGxw=D4wF5weuEh9GIlKGGIx8OQRa5=7KwIkw2Q7cr1GyRWH4/1Oy5L2Uh2XSHkT1kxebbPKNFgPG117+590i44EiNKosl0KwZc9hO3AwD4yhsrgOC8fAh37WE+EW4R9s5omjDZcp23yj21o844Wta3dmc7KNcAoNi98t41OHpzWiqr4arf9FXz7=64vjgejrCVOkHFT2k4yp0cp2gnfs4RUGPzLBX8lH64OtHUjGsoOSTicY8PeNiTznYkIrYbMOTqxW7xFLKlrY1hDaWr7WHoexfEw7dh=Ew=j=8oh53hKdhiQ8fn84ODW08odIbLdYrQr+dlQO0xwcQk0C8LQbhropxPD7Q34GcDG7diDD=; tfstk=gw7iBH0N5G-_az6-B98_qBr4Wbqp6fTX3t3vHEp4YpJBH599BsWci_-VWdefmMXhEqpaGFKqnTCPnnN6HKJcHsST9zUR11TXuE28yzCUMi3RIARw0SRegQFp_0QxIv8Xu8eKvq-_rE67psAVu6PHMI3q3tuZYWAk_Euw0VoELpO2uEJ2b28eZQM2gqu4t6JBgEJV3ElkplJ1uw7UdLx9lSs8rNRMjL5wQ1dO8A-of_vnkqQhfhvzop0quwAG94tT44qvUg992LWUJVYcTIXH2G2oStfVNi-hoAPOU9Sc3BsQnmvl01I5JHhqb6bDI37wxjNRa1OV3njQE4dMWMxlSGNjL1WJIg81Mb2Ot3jHVB-Eic8O2_QX4NziFpKWi9xRj80GUgloY0PWtqOUMwojchRBtLdfBQVnKMEUnWVn4StwOC98tWmjchRBtLF3t0zXbBO6e; csdn_newcert_weixin_45706752=1; _ga_7W1N0GEY1P=GS2.1.s1749714609$o7$g1$t1749714635$j34$l0$h0; _ga=GA1.2.1903478846.1748331084; c_ab_test=1; _clck=1mlc48m%7C2%7Cfxw%7C0%7C1653; __gads=ID=cd12a7af789e804f:T=1720682071:RT=1753435659:S=ALNI_Mazm7x4EConth7ECB4kS7TkpVSFTQ; __gpi=UID=00000e8c46d6ddc3:T=1720682071:RT=1753435659:S=ALNI_MYsQo1ahtY4_EH4nofgg6lUMsU69A; __eoi=ID=ba3c9983e84246a6:T=1747907719:RT=1753435659:S=AA-AfjYvX0xnjpJXuU8XzkWXUtMI; FCNEC=%5B%5B%22AKsRol_EC8xUZr8VwqqkxwGeHy_5k-uPnIETxnmiXnabgCwmKZSw8xYGgq-4oNTrm8RphqvdMf56CfsnwjjZQXr9WVfheMulHi0gm9nfpAaQyensiDaCSDrHhge-w6St0aCgwRH8KExNeRztbp1Q8fQrah5HtTznRw%3D%3D%22%5D%5D; dc_session_id=11_1753665625373.985362; c_first_ref=default; c_first_page=https%3A//mp.csdn.net/mp_blog/manage/article%3Fspm%3D1011.2124.3001.10336; c_dsid=11_1753665625374.808306; c_segment=8; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1753234640,1753320810,1753407799,1753665628; HMACCOUNT=97681E246CCF9930; dc_sid=c1defec9878fe2837fbe2ed53c91b7c1; is_advert=1; SESSION=10dc07d5-4249-49d1-9695-94757177e16f; creative_btn_mp=2; hide_login=1; UserName=weixin_45706752; UserInfo=532f37dda8224f569e088e85b0471b06; UserToken=532f37dda8224f569e088e85b0471b06; UserNick=BAISHUN628; AU=CBD; BT=1753666389979; creativeSetApiNew=%7B%22toolbarImg%22%3A%22https%3A//img-home.csdnimg.cn/images/20231011044944.png%22%2C%22publishSuccessImg%22%3A%22https%3A//img-home.csdnimg.cn/images/20240229024608.png%22%2C%22articleNum%22%3A0%2C%22type%22%3A0%2C%22oldUser%22%3Afalse%2C%22useSeven%22%3Atrue%2C%22oldFullVersion%22%3Afalse%2C%22userName%22%3A%22weixin_45706752%22%7D; c_pref=https%3A//mp.csdn.net/mp_blog/manage/article%3Fspm%3D1011.2480.3001.8124; c_ref=https%3A//mp.csdn.net/; c_page_id=default; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1753667828; log_Id_pv=28; log_Id_view=356; dc_tos=t036s2; log_Id_click=40");

    // 请求体 JSON 字符串
    String jsonBody = """
            {
              "article_id": "",
              "title": "笔记7281012",
              "description": "",
              "content": "<p>笔记内容</p>\\n",
              "tags": "笔记",
              "categories": "",
              "type": "original",
              "status": 0,
              "read_type": "public",
              "reason": "",
              "original_link": "",
              "authorized_status": false,
              "check_original": false,
              "source": "pc_postedit",
              "not_auto_saved": 1,
              "creator_activity_id": "",
              "cover_images": [],
              "cover_type": 1,
              "vote_id": 0,
              "resource_id": "",
              "scheduled_time": 0,
              "is_new": 1,
              "sync_git_code": 0
            }
        """;
    // 构造请求实体
    HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

    // 发送 POST 请求
    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

    // 输出响应
    System.out.println("响应状态码: " + response.getStatusCode());
    System.out.println("响应内容: " + response.getBody());


  }

  @Test
  void testV3() {
    RestTemplate restTemplate = new RestTemplate();

    // 请求 URL
    String url = "https://bizapi.csdn.net/blog-console-api/v3/mdeditor/saveArticle";

    // 请求体 JSON 内容
    //                    "markdowncontent": "`笔记内容`\n",
    //                    "content": "<p><code>笔记内容</code></p>\n\n",
    ArticleFunctionRequest functionRequest = new ArticleFunctionRequest();
    functionRequest.setTitle("笔记731936");
    functionRequest.setMarkdowncontent("`笔记内容`\n");
    functionRequest.setTags("笔记");
    functionRequest.setDescription("这是一篇测试笔记");

    CsdnPostRequest postRequest = new CsdnPostRequest();
    postRequest.setTitle(functionRequest.getTitle());
    postRequest.setContent(functionRequest.getContent());
    postRequest.setTags(functionRequest.getTags());
    postRequest.setDescription(functionRequest.getDescription());
    postRequest.setMarkdowncontent(functionRequest.getMarkdowncontent());

    // 设置请求头
    HttpHeaders headers = new HttpHeaders();

    headers.set("accept", "*/*");
    headers.set("accept-language", "zh-CN,zh;q=0.9");
    headers.set("content-type", "application/json");
    headers.set("dnt", "1");
    headers.set("origin", "https://editor.csdn.net");
    headers.set("priority", "u=1, i");
    headers.set("referer", "https://editor.csdn.net/");
    headers.set("sec-ch-ua", "\"Chromium\";v=\"134\", \"Not:A-Brand\";v=\"24\", \"Google Chrome\";v=\"134\"");
    headers.set("sec-ch-ua-mobile", "?0");
    headers.set("sec-ch-ua-platform", "\"macOS\"");
    headers.set("sec-fetch-dest", "empty");
    headers.set("sec-fetch-mode", "cors");
    headers.set("sec-fetch-site", "same-site");
    headers.set("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36");
    headers.set("x-ca-key", "203803574");
    headers.set("x-ca-nonce", "a70ca99e-8bfa-46d1-8d12-363c72707ebe");
    headers.set("x-ca-signature", "NGLzlIyvH7BuQgGJrgfGOzao0SVpzdTs4aTcw3hio6Y=");
    headers.set("x-ca-signature-headers", "x-ca-key,x-ca-nonce");

    headers.set("Cookie", "UN=weixin_45706752; p_uid=U010000; fid=20_13688691304-1723301334874-725241; c_dl_um=-; c_dl_prid=1726238959428_528724; c_dl_rid=1732243562457_950669; c_dl_fref=https://blog.csdn.net/gitblog_01123/article/details/141768484; c_dl_fpage=/download/weixin_42134554/15903208; uuid_tt_dd=10_2420134770-1734667232542-752040; ssxmod_itna=YqRxcD2QDQG=AExl4iqb+DyDIhj5=oQKdbee7DlxoxA5D8D6DQeGTb2WDBWRQifdT3bk077TwWS8iOahIOAjYcnbHDCPGnDB95QqmDYA8Dt4DTD34DYDixibkxi5GRD0KDFWqvz19Dm4GWWqGfDDoDYb=RDitD4qDBzodDKqGgWLh=FA6PDDXPD1h=iwDDeb0qu82gIQU=texeWqDMfeGXQ09=sl9CHYXqs=FDThQDzu7DtLUgnkLox0p99utDp7GI0F+znr4qFOqenhDEi+4o/7heAGDruD1eS7Dmeyf627kDG+GU6bD===; ssxmod_itna2=YqRxcD2QDQG=AExl4iqb+DyDIhj5=oQKdbeeD6h9nQD05DtqkDLiciru/4n4Qx6PniinPNede3nGxw=D4wF5weuEh9GIlKGGIx8OQRa5=7KwIkw2Q7cr1GyRWH4/1Oy5L2Uh2XSHkT1kxebbPKNFgPG117+590i44EiNKosl0KwZc9hO3AwD4yhsrgOC8fAh37WE+EW4R9s5omjDZcp23yj21o844Wta3dmc7KNcAoNi98t41OHpzWiqr4arf9FXz7=64vjgejrCVOkHFT2k4yp0cp2gnfs4RUGPzLBX8lH64OtHUjGsoOSTicY8PeNiTznYkIrYbMOTqxW7xFLKlrY1hDaWr7WHoexfEw7dh=Ew=j=8oh53hKdhiQ8fn84ODW08odIbLdYrQr+dlQO0xwcQk0C8LQbhropxPD7Q34GcDG7diDD=; tfstk=gw7iBH0N5G-_az6-B98_qBr4Wbqp6fTX3t3vHEp4YpJBH599BsWci_-VWdefmMXhEqpaGFKqnTCPnnN6HKJcHsST9zUR11TXuE28yzCUMi3RIARw0SRegQFp_0QxIv8Xu8eKvq-_rE67psAVu6PHMI3q3tuZYWAk_Euw0VoELpO2uEJ2b28eZQM2gqu4t6JBgEJV3ElkplJ1uw7UdLx9lSs8rNRMjL5wQ1dO8A-of_vnkqQhfhvzop0quwAG94tT44qvUg992LWUJVYcTIXH2G2oStfVNi-hoAPOU9Sc3BsQnmvl01I5JHhqb6bDI37wxjNRa1OV3njQE4dMWMxlSGNjL1WJIg81Mb2Ot3jHVB-Eic8O2_QX4NziFpKWi9xRj80GUgloY0PWtqOUMwojchRBtLdfBQVnKMEUnWVn4StwOC98tWmjchRBtLF3t0zXbBO6e; csdn_newcert_weixin_45706752=1; _ga_7W1N0GEY1P=GS2.1.s1749714609$o7$g1$t1749714635$j34$l0$h0; _ga=GA1.2.1903478846.1748331084; c_ab_test=1; __gads=ID=cd12a7af789e804f:T=1720682071:RT=1753435659:S=ALNI_Mazm7x4EConth7ECB4kS7TkpVSFTQ; __gpi=UID=00000e8c46d6ddc3:T=1720682071:RT=1753435659:S=ALNI_MYsQo1ahtY4_EH4nofgg6lUMsU69A; __eoi=ID=ba3c9983e84246a6:T=1747907719:RT=1753435659:S=AA-AfjYvX0xnjpJXuU8XzkWXUtMI; FCNEC=%5B%5B%22AKsRol_EC8xUZr8VwqqkxwGeHy_5k-uPnIETxnmiXnabgCwmKZSw8xYGgq-4oNTrm8RphqvdMf56CfsnwjjZQXr9WVfheMulHi0gm9nfpAaQyensiDaCSDrHhge-w6St0aCgwRH8KExNeRztbp1Q8fQrah5HtTznRw%3D%3D%22%5D%5D; UserName=weixin_45706752; UserInfo=532f37dda8224f569e088e85b0471b06; UserToken=532f37dda8224f569e088e85b0471b06; UserNick=BAISHUN628; AU=CBD; BT=1753666389979; c_first_ref=default; c_first_page=https%3A//blog.csdn.net/weixin_45706752%3Ft%3D1; c_segment=8; dc_sid=807e3b72e39f8580b8e4bc923c9ff4f1; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1753320810,1753407799,1753665628,1753752345; HMACCOUNT=97681E246CCF9930; _clck=1mlc48m%7C2%7Cfy0%7C0%7C1653; _clsk=1t2goi%7C1753752635460%7C3%7C0%7Ch.clarity.ms%2Fcollect; dc_session_id=10_1753768374370.262735; c_dsid=11_1753768374060.603281; creativeSetApiNew=%7B%22toolbarImg%22%3A%22https%3A//i-operation.csdnimg.cn/images/5454b5087888478e88ab72a217fc8162.png%22%2C%22publishSuccessImg%22%3A%22https%3A//img-home.csdnimg.cn/images/20240229024608.png%22%2C%22articleNum%22%3A0%2C%22type%22%3A0%2C%22oldUser%22%3Afalse%2C%22useSeven%22%3Atrue%2C%22oldFullVersion%22%3Afalse%2C%22userName%22%3A%22weixin_45706752%22%7D; c_pref=https%3A//mp.csdn.net/; c_ref=https%3A//editor.csdn.net/; c_page_id=default; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1753768495; log_Id_pv=10; log_Id_view=195; dc_tos=t05cg6; log_Id_click=16");

    // 创建请求体
    HttpEntity<CsdnPostRequest> requestEntity = new HttpEntity<>(postRequest, headers);

    // 发送 POST 请求
    try {
      ResponseEntity<String> response = restTemplate.exchange(
              url,
              HttpMethod.POST,
              requestEntity,
              String.class
      );

      // 输出响应结果
      System.out.println("响应状态码: " + response.getStatusCode());
      System.out.println("响应内容: " + response.getBody());
    } catch (Exception e) {
      System.err.println("请求失败: " + e.getMessage());
    }
  }

  @Test
  void testV4() {
    String mdContext = """
    好的，作为Java开发岗位的技术面试官，以下是两个关于Redis技术的常见面试题及其对应的理想回答：

---

**面试题 1： 请谈谈你对Redis的理解，以及它在Java应用中常见的应用场景有哪些？**

**回答要点：**

1.  **核心理解与特点：**
    *   首先，我会强调Redis是一个**开源的、内存中的数据结构存储系统**。
    *   它可以用作**数据库、缓存和消息中间件**。
    *   关键点在于**内存存储**，这使得它的读写速度非常快（通常是微秒级），远超基于磁盘的传统数据库。
    *   它支持**多种数据结构**，如字符串（Strings）、哈希（Hashes）、列表（Lists）、集合（Sets）、有序集合（Sorted Sets）、位图（Bitmaps）、 HyperLogLogs 等，这为各种应用场景提供了灵活性。
    *   数据**持久化**机制（RDB快照和AOF日志）保证了即使服务器重启，数据也不会完全丢失（根据配置策略）。
    *   支持高可用性（如哨兵 Sentinel）和分布式扩展（如集群 Cluster）。

2.  **在Java应用中的常见应用场景：**
    *   **缓存层 (Caching Layer)：** 这是最常见的应用。Java应用可以将频繁访问但更新不频繁的数据（如数据库查询结果、配置信息、用户会话信息）缓存到Redis中。通过缓存，可以**减轻后端数据库的压力**，**显著提高应用的响应速度**。例如，使用Spring Cache结合Redis实现方法级别的缓存。
    *   **会话管理 (Session Management)：** 将用户的HTTP会话信息存储在Redis中，而不是默认的Tomcat内存或文件中。这使得**应用服务器可以分布式部署**，因为所有服务器都可以访问同一个Redis实例来获取用户会话数据，解决了会话粘滞问题。
    *   **分布式锁 (Distributed Locking)：** 在分布式环境中，多个Java进程可能需要安全地访问共享资源。可以使用Redis的 `SET resource_name my_random_value NX PX 30000` 命令（结合Lua脚本或Redlock算法）来实现**互斥锁**，确保同一时间只有一个进程能执行特定操作。
    *   **计数器与限流 (Counters & Rate Limiting)：** 利用Redis的原子操作（如INCR, INCRBY）可以实现精确的计数器，例如网站访问量统计、用户请求次数统计等。结合TTL（Time To Live）可以方便地实现**接口限流**，防止恶意请求或突发流量压垮服务。
    *   **消息队列/发布订阅 (Message Queue / Pub/Sub)：** Redis的发布订阅功能虽然不是专业的消息队列（没有持久化保证和ACK机制），但可以用于简单的**实时通知、事件驱动**等场景。例如，用户注册成功后发布一个消息，让其他服务（如发送欢迎邮件的服务）订阅并处理。
    *   **排行榜 (Leaderboards)：** 利用有序集合（Sorted Set）数据结构，可以非常高效地实现各种排行榜功能，如按积分、按时间等排序，并快速获取Top N。

**总结：** Redis凭借其高性能、丰富的数据结构和易用性，在Java应用中扮演着越来越重要的角色，尤其是在提升性能、实现分布式协作方面。

---

**面试题 2： 请解释一下Redis的持久化机制RDB和AOF，它们各自的优缺点是什么？在Java应用中，你会如何配置它们？**

**回答要点：**

1.  **RDB (Redis Database) 持久化：**
    *   **原理：** 在指定的时间间隔内，将Redis在内存中的**数据集快照**（Snapshot）保存到磁盘上的一个单独的文件中（通常是 `.rdb` 文件）。保存过程是由**子进程**（`bgsave` 命令触发）完成的，主进程**不阻塞**，对服务的影响较小。
    *   **优点：**
        *   **文件紧凑，适合备份：** RDB文件是一个紧凑的、全量的数据快照，非常适合进行**备份**和**灾难恢复**。
        *   **恢复速度快：** 从RDB文件恢复数据比AOF快得多，因为文件体积通常更小，且数据是全量的。
        *   **性能影响小：** `bgsave` 由子进程完成，主进程只fork一次，之后子进程完成IO操作，对性能影响相对较小。
    *   **缺点：**
        *   **可能丢失数据：** RDB是**定时**保存的快照，如果在两次保存之间Redis发生故障，那么这段时间内的数据**会丢失**。保存间隔越短，丢失的数据越少，但性能开销越大。
        *   **fork开销：** `bgsave` 需要fork子进程，如果Redis数据量非常大，fork操作本身可能会**阻塞**主线程几毫秒甚至更长时间，影响服务。

2.  **AOF (Append Only File) 持久化：**
    *   **原理：** 记录服务器**执行的所有写操作**（如 SET, LPUSH 等）命令。这些命令会被追加到一个日志文件（AOF文件）的末尾。当Redis重启时，它会**重新执行**AOF文件中的所有命令来恢复数据。
    *   **优点：**
        *   **数据更安全，丢失更少：** AOF提供了**多种同步策略**（`appendfsync`配置项：always, everysec, no），其中`everysec`（默认推荐）可以做到**每秒**将缓冲区中的命令同步到AOF文件，即使发生故障，最多也只会丢失**1秒**的数据。`always`模式则完全无数据丢失，但性能最低。
        *   **兼容性更好：** Redis 4.0 后支持**AOF重写 (rewrite)**，可以压缩AOF文件，移除冗余命令，保持文件体积不至于过大。
    *   **缺点：**
        *   **文件体积通常更大：** AOF文件记录的是命令本身，相比RDB的紧凑结构，文件体积通常更大。
        *   **恢复速度可能更慢：** 重启时需要重新执行所有命令，如果AOF文件很大，恢复时间会比RDB长。
        *   **性能开销可能更大：** 每次写命令都需要记录到AOF缓冲区并可能触发fsync，对性能有一定影响（`everysec`和`no`模式影响较小，`always`影响较大）。

3.  **Java应用中的配置建议：**
    *   **推荐组合：** 在生产环境中，**通常建议同时启用RDB和AOF**，以获得最佳的数据安全性和性能平衡。
        *   **RDB：** 配置一个相对较长但合理的保存间隔（例如 `save 900 1` 表示15分钟内至少1个key变化就触发 `bgsave`）。主要用于**灾难恢复和备份**。
        *   **AOF：** 启用AOF，并配置 `appendfsync everysec`。这是**数据安全**的主要保障，平衡了性能和数据丢失风险。
    *   **配置位置：** 这些配置通常在Redis服务器的配置文件 `redis.conf` 中进行设置。
    *   **Java客户端（如Jedis, Lettuce, Redisson）：** Java客户端本身**不直接控制**RDB/AOF的生成和配置。这些机制是Redis服务器层面的。Java应用启动时，只需要确保能够正确连接到配置好持久化策略的Redis服务器实例即可。客户端库可能会提供与持久化相关的健康检查或监控接口，但配置本身是在服务端完成的。

**总结：** 理解RDB和AOF的机制、优缺点以及如何根据业务需求（对数据丢失的容忍度、性能要求、备份策略）进行组合配置，是评估候选人是否具备Redis实战经验的重要方面。同时启用RDB和AOF是生产环境下的常见且推荐的做法。
    """;
    ArticleFunctionRequest functionRequest = new ArticleFunctionRequest();
    functionRequest.setTitle("java开发面试中关于redis常见的2个问题");
    functionRequest.setMarkdowncontent(mdContext);
    functionRequest.setDescription("redis常见面试题");
    functionRequest.setTags("redis,面试");
    csdnService.postToCsdn(functionRequest);
  }

}
