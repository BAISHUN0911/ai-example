package com.baishun.mcpservercsdn.service;

import com.baishun.mcpservercsdn.model.ArticleFunctionRequest;
import com.baishun.mcpservercsdn.model.CsdnPostRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @Author shengy
 * @Date 2025/7/25 10:09
 */
@Service
@Slf4j
public class CsdnService {
  @Value("${csdn.api.url}")
  private String apiUrl;

  @Value("${csdn.api.cookie}")
  private String cookie;

  @Value("${csdn.api.x-ca-key}")
  private String xCaKey;

  @Value("${csdn.api.user-agent}")
  private String userAgent;

  @Value("${csdn.api.x-ca-nonce}")
  private String xCaNonce;

  @Value("${csdn.api.x-ca-signature}")
  private String xCaSignature;

  private final RestTemplate restTemplate = new RestTemplate();

  /**
   * 将内容在csdn博客发布
   */
  @Tool(description = "在CSDN的博客网站发布一篇文章")
  public void postToCsdn(@RequestBody ArticleFunctionRequest request) {
    log.info("CSDN发帖\n标题:{}\n 内容:{}\n 标签:{}\n", request.getTitle(), request.getMarkdowncontent(), request.getTags());

    CsdnPostRequest postBody = new CsdnPostRequest();
    postBody.setTitle(request.getTitle());
    postBody.setMarkdowncontent(request.getMarkdowncontent());
    postBody.setTags(request.getTags());
    postBody.setContent(request.getContent());
    postBody.setDescription(request.getDescription());

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
    headers.set("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko)" +
            " Chrome/134.0.0.0 Safari/537.36");
    headers.set("x-ca-key", "203803574");
    headers.set("x-ca-nonce", "a70ca99e-8bfa-46d1-8d12-363c72707ebe");
    headers.set("x-ca-signature", "NGLzlIyvH7BuQgGJrgfGOzao0SVpzdTs4aTcw3hio6Y=");
    headers.set("x-ca-signature-headers", "x-ca-key,x-ca-nonce");

    headers.set("Cookie", "UN=weixin_45706752; p_uid=U010000; fid=20_13688691304-1723301334874-725241; c_dl_um=-; " +
            "c_dl_prid=1726238959428_528724; c_dl_rid=1732243562457_950669; c_dl_fref=https://blog.csdn" +
            ".net/gitblog_01123/article/details/141768484; c_dl_fpage=/download/weixin_42134554/15903208; " +
            "uuid_tt_dd=10_2420134770-1734667232542-752040; " +
            "ssxmod_itna=YqRxcD2QDQG=AExl4iqb+DyDIhj5" +
            "=oQKdbee7DlxoxA5D8D6DQeGTb2WDBWRQifdT3bk077TwWS8iOahIOAjYcnbHDCPGnDB95QqmDYA8Dt4DTD34DYDixibkxi5GRD0KDFWqvz19Dm4GWWqGfDDoDYb=RDitD4qDBzodDKqGgWLh=FA6PDDXPD1h=iwDDeb0qu82gIQU=texeWqDMfeGXQ09=sl9CHYXqs=FDThQDzu7DtLUgnkLox0p99utDp7GI0F+znr4qFOqenhDEi+4o/7heAGDruD1eS7Dmeyf627kDG+GU6bD===; ssxmod_itna2=YqRxcD2QDQG=AExl4iqb+DyDIhj5=oQKdbeeD6h9nQD05DtqkDLiciru/4n4Qx6PniinPNede3nGxw=D4wF5weuEh9GIlKGGIx8OQRa5=7KwIkw2Q7cr1GyRWH4/1Oy5L2Uh2XSHkT1kxebbPKNFgPG117+590i44EiNKosl0KwZc9hO3AwD4yhsrgOC8fAh37WE+EW4R9s5omjDZcp23yj21o844Wta3dmc7KNcAoNi98t41OHpzWiqr4arf9FXz7=64vjgejrCVOkHFT2k4yp0cp2gnfs4RUGPzLBX8lH64OtHUjGsoOSTicY8PeNiTznYkIrYbMOTqxW7xFLKlrY1hDaWr7WHoexfEw7dh=Ew=j=8oh53hKdhiQ8fn84ODW08odIbLdYrQr+dlQO0xwcQk0C8LQbhropxPD7Q34GcDG7diDD=; tfstk=gw7iBH0N5G-_az6-B98_qBr4Wbqp6fTX3t3vHEp4YpJBH599BsWci_-VWdefmMXhEqpaGFKqnTCPnnN6HKJcHsST9zUR11TXuE28yzCUMi3RIARw0SRegQFp_0QxIv8Xu8eKvq-_rE67psAVu6PHMI3q3tuZYWAk_Euw0VoELpO2uEJ2b28eZQM2gqu4t6JBgEJV3ElkplJ1uw7UdLx9lSs8rNRMjL5wQ1dO8A-of_vnkqQhfhvzop0quwAG94tT44qvUg992LWUJVYcTIXH2G2oStfVNi-hoAPOU9Sc3BsQnmvl01I5JHhqb6bDI37wxjNRa1OV3njQE4dMWMxlSGNjL1WJIg81Mb2Ot3jHVB-Eic8O2_QX4NziFpKWi9xRj80GUgloY0PWtqOUMwojchRBtLdfBQVnKMEUnWVn4StwOC98tWmjchRBtLF3t0zXbBO6e; csdn_newcert_weixin_45706752=1; _ga_7W1N0GEY1P=GS2.1.s1749714609$o7$g1$t1749714635$j34$l0$h0; _ga=GA1.2.1903478846.1748331084; c_ab_test=1; __gads=ID=cd12a7af789e804f:T=1720682071:RT=1753435659:S=ALNI_Mazm7x4EConth7ECB4kS7TkpVSFTQ; __gpi=UID=00000e8c46d6ddc3:T=1720682071:RT=1753435659:S=ALNI_MYsQo1ahtY4_EH4nofgg6lUMsU69A; __eoi=ID=ba3c9983e84246a6:T=1747907719:RT=1753435659:S=AA-AfjYvX0xnjpJXuU8XzkWXUtMI; FCNEC=%5B%5B%22AKsRol_EC8xUZr8VwqqkxwGeHy_5k-uPnIETxnmiXnabgCwmKZSw8xYGgq-4oNTrm8RphqvdMf56CfsnwjjZQXr9WVfheMulHi0gm9nfpAaQyensiDaCSDrHhge-w6St0aCgwRH8KExNeRztbp1Q8fQrah5HtTznRw%3D%3D%22%5D%5D; UserName=weixin_45706752; UserInfo=532f37dda8224f569e088e85b0471b06; UserToken=532f37dda8224f569e088e85b0471b06; UserNick=BAISHUN628; AU=CBD; BT=1753666389979; c_first_ref=default; c_first_page=https%3A//blog.csdn.net/weixin_45706752%3Ft%3D1; c_segment=8; dc_sid=807e3b72e39f8580b8e4bc923c9ff4f1; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1753320810,1753407799,1753665628,1753752345; HMACCOUNT=97681E246CCF9930; _clck=1mlc48m%7C2%7Cfy0%7C0%7C1653; _clsk=1t2goi%7C1753752635460%7C3%7C0%7Ch.clarity.ms%2Fcollect; dc_session_id=10_1753768374370.262735; c_dsid=11_1753768374060.603281; creativeSetApiNew=%7B%22toolbarImg%22%3A%22https%3A//i-operation.csdnimg.cn/images/5454b5087888478e88ab72a217fc8162.png%22%2C%22publishSuccessImg%22%3A%22https%3A//img-home.csdnimg.cn/images/20240229024608.png%22%2C%22articleNum%22%3A0%2C%22type%22%3A0%2C%22oldUser%22%3Afalse%2C%22useSeven%22%3Atrue%2C%22oldFullVersion%22%3Afalse%2C%22userName%22%3A%22weixin_45706752%22%7D; c_pref=https%3A//mp.csdn.net/; c_ref=https%3A//editor.csdn.net/; c_page_id=default; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1753768495; log_Id_pv=10; log_Id_view=195; dc_tos=t05cg6; log_Id_click=16");

    // 构建请求体和头
    HttpEntity<CsdnPostRequest> entity = new HttpEntity<>(postBody, headers);
    // 执行请求
    ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

    // 输出响应
    System.out.println("状态码: " + response.getStatusCode());
    System.out.println("响应体: " + response.getBody());

    log.info("成功发布文章: {}, 当前时间：{}", request.getTitle(), LocalDateTime.now());
  }

  /**
   * 根据文章id删除文章
   */
  @Tool(description = "根据文章id删除文章")
  public void del(String articleId) {
    String url = "https://bizapi.csdn.net/blog/phoenix/console/v1/article/del";
    // 请求体
    Map<String, Object> body = new HashMap<>();
    body.put("articleId", articleId);
    body.put("deep", false);

    // 设置请求头
    HttpHeaders headers = new HttpHeaders();
    headers.set("accept", "application/json, text/plain, */*");
    headers.set("content-type", "application/json;");
    headers.set("x-ca-key", "203803574");
    headers.set("x-ca-nonce", "3fe32afa-6aac-480c-849e-36a597dc02c3");
    headers.set("x-ca-signature", "GMHqdYwXWdtB3K2xvdPQv8D/sA7RSQZmMB3VeuogOfU=");
    headers.set("x-ca-signature-headers", "x-ca-key,x-ca-nonce");
    headers.set("Cookie", "UN=weixin_45706752; p_uid=U010000; fid=20_13688691304-1723301334874-725241; c_dl_um=-; " +
            "c_dl_prid=1726238959428_528724; c_dl_rid=1732243562457_950669; c_dl_fref=https://blog.csdn" +
            ".net/gitblog_01123/article/details/141768484; c_dl_fpage=/download/weixin_42134554/15903208; " +
            "uuid_tt_dd=10_2420134770-1734667232542-752040; " +
            "ssxmod_itna=YqRxcD2QDQG=AExl4iqb+DyDIhj5" +
            "=oQKdbee7DlxoxA5D8D6DQeGTb2WDBWRQifdT3bk077TwWS8iOahIOAjYcnbHDCPGnDB95QqmDYA8Dt4DTD34DYDixibkxi5GRD0KDFWqvz19Dm4GWWqGfDDoDYb=RDitD4qDBzodDKqGgWLh=FA6PDDXPD1h=iwDDeb0qu82gIQU=texeWqDMfeGXQ09=sl9CHYXqs=FDThQDzu7DtLUgnkLox0p99utDp7GI0F+znr4qFOqenhDEi+4o/7heAGDruD1eS7Dmeyf627kDG+GU6bD===; ssxmod_itna2=YqRxcD2QDQG=AExl4iqb+DyDIhj5=oQKdbeeD6h9nQD05DtqkDLiciru/4n4Qx6PniinPNede3nGxw=D4wF5weuEh9GIlKGGIx8OQRa5=7KwIkw2Q7cr1GyRWH4/1Oy5L2Uh2XSHkT1kxebbPKNFgPG117+590i44EiNKosl0KwZc9hO3AwD4yhsrgOC8fAh37WE+EW4R9s5omjDZcp23yj21o844Wta3dmc7KNcAoNi98t41OHpzWiqr4arf9FXz7=64vjgejrCVOkHFT2k4yp0cp2gnfs4RUGPzLBX8lH64OtHUjGsoOSTicY8PeNiTznYkIrYbMOTqxW7xFLKlrY1hDaWr7WHoexfEw7dh=Ew=j=8oh53hKdhiQ8fn84ODW08odIbLdYrQr+dlQO0xwcQk0C8LQbhropxPD7Q34GcDG7diDD=; tfstk=gw7iBH0N5G-_az6-B98_qBr4Wbqp6fTX3t3vHEp4YpJBH599BsWci_-VWdefmMXhEqpaGFKqnTCPnnN6HKJcHsST9zUR11TXuE28yzCUMi3RIARw0SRegQFp_0QxIv8Xu8eKvq-_rE67psAVu6PHMI3q3tuZYWAk_Euw0VoELpO2uEJ2b28eZQM2gqu4t6JBgEJV3ElkplJ1uw7UdLx9lSs8rNRMjL5wQ1dO8A-of_vnkqQhfhvzop0quwAG94tT44qvUg992LWUJVYcTIXH2G2oStfVNi-hoAPOU9Sc3BsQnmvl01I5JHhqb6bDI37wxjNRa1OV3njQE4dMWMxlSGNjL1WJIg81Mb2Ot3jHVB-Eic8O2_QX4NziFpKWi9xRj80GUgloY0PWtqOUMwojchRBtLdfBQVnKMEUnWVn4StwOC98tWmjchRBtLF3t0zXbBO6e; csdn_newcert_weixin_45706752=1; _ga_7W1N0GEY1P=GS2.1.s1749714609$o7$g1$t1749714635$j34$l0$h0; _ga=GA1.2.1903478846.1748331084; c_ab_test=1; __gads=ID=cd12a7af789e804f:T=1720682071:RT=1753435659:S=ALNI_Mazm7x4EConth7ECB4kS7TkpVSFTQ; __gpi=UID=00000e8c46d6ddc3:T=1720682071:RT=1753435659:S=ALNI_MYsQo1ahtY4_EH4nofgg6lUMsU69A; __eoi=ID=ba3c9983e84246a6:T=1747907719:RT=1753435659:S=AA-AfjYvX0xnjpJXuU8XzkWXUtMI; FCNEC=%5B%5B%22AKsRol_EC8xUZr8VwqqkxwGeHy_5k-uPnIETxnmiXnabgCwmKZSw8xYGgq-4oNTrm8RphqvdMf56CfsnwjjZQXr9WVfheMulHi0gm9nfpAaQyensiDaCSDrHhge-w6St0aCgwRH8KExNeRztbp1Q8fQrah5HtTznRw%3D%3D%22%5D%5D; UserName=weixin_45706752; UserInfo=532f37dda8224f569e088e85b0471b06; UserToken=532f37dda8224f569e088e85b0471b06; UserNick=BAISHUN628; AU=CBD; BT=1753666389979; creative_btn_mp=3; dc_session_id=11_1753752343219.625048; c_first_ref=default; c_first_page=https%3A//blog.csdn.net/weixin_45706752%3Ft%3D1; c_dsid=11_1753752343221.401783; c_segment=8; dc_sid=807e3b72e39f8580b8e4bc923c9ff4f1; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1753320810,1753407799,1753665628,1753752345; HMACCOUNT=97681E246CCF9930; _clck=1mlc48m%7C2%7Cfy0%7C0%7C1653; c_pref=https%3A//editor.csdn.net/; c_ref=https%3A//mp.csdn.net/; c_page_id=default; log_Id_pv=14; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1753752633; _clsk=1t2goi%7C1753752635460%7C3%7C0%7Ch.clarity.ms%2Fcollect; dc_tos=t050eg; log_Id_view=259; log_Id_click=37");

    // 构建请求体和头
    HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

    // 发送 POST 请求
    ResponseEntity<String> response = restTemplate.exchange(
            url,
            HttpMethod.POST,
            requestEntity,
            String.class
    );

    // 输出响应内容
    System.out.println("响应状态码：" + response.getStatusCode());
    System.out.println("响应内容：" + response.getBody());
  }
}
