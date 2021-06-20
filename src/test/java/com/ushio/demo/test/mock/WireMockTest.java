package com.ushio.demo.test.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.extension.requestfilter.FieldTransformer;
import com.github.tomakehurst.wiremock.extension.requestfilter.RequestFilterAction;
import com.github.tomakehurst.wiremock.extension.requestfilter.RequestWrapper;
import com.github.tomakehurst.wiremock.extension.requestfilter.StubRequestFilter;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;
import com.github.tomakehurst.wiremock.matching.ContainsPattern;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class WireMockTest {

    @Test
    void testWireMock() {
        //启动wiremockserver
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(8089)
                .extensions(new ResponseTemplateTransformer(true)));
        wireMockServer.start();
        WireMock.configureFor(8089);

        //stub设置

        //url包含/some/thing时，返回结果body为Hello world!
        //example：http://127.0.0.1:8089/some/thing
        stubFor(get(urlEqualTo("/some/thing")).willReturn(aResponse()
                .withHeader("Content-Type", "text/plain").withBody("Hello world!")));

        //url的query参数site包含ceshiren.com 时返回site=具体的参数结果
        //example：http://127.0.0.1:8089/?site=222ceshiren.com111
        stubFor(get(anyUrl()).withQueryParam("site", new ContainsPattern("ceshiren.com")).willReturn(
                        aResponse().withBody("site={{request.query.site}}")));

        //url里包含/a/b/c并且query参数id为1时，输出结果为files/1.png
        //example：http://127.0.0.1:8089/a/b/c?id=1
        stubFor(get(urlPathEqualTo("/a/b/c")).withQueryParam("id", equalTo("1")).willReturn(aResponse()
                .withBody("files/{{request.query.id}}.png")
        ));

        //url里包含/ceshiren/xxx 时输出ceshiren.com的内容信息（部分前端样式无法加载）
        //example：http://127.0.0.1:8089/ceshiren/2
        stubFor(get(urlMatching("/ceshiren/.*")).willReturn(aResponse()
                .proxiedFrom("https://ceshiren.com/")));

        //模拟百度搜索请求
        //example：http://127.0.0.1:8089/s?wd=ushio
        stubFor(get(urlMatching("/s\\?.*")).willReturn(aResponse()
                .proxiedFrom("https://www.baidu.com/")));

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //释放相关资源
        WireMock.reset();
        wireMockServer.stop();
    }

    /**
     * 请求定向到ceshiren.com，修改body体的部分内容
     */
    @Test
    void testMockProxy(){
        //WireMock server启动
        WireMockServer wireMockServer = new WireMockServer(
                wireMockConfig()
                        .port(8089)
                        .extensions(
                                new ResponseTemplateTransformer(true),
/*                                new ResponseDefinitionTransformer() {
                                    @Override
                                    public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters) {
                                        System.out.println(request);
                                        System.out.println(responseDefinition);
                                        System.out.println(files);
                                        System.out.println(parameters);
                                        return new ResponseDefinitionBuilder()
                                                .withHeader("MyHeader", "Transformed")
                                                .withStatus(200)
                                                .withBody("Transformed body")
                                                .build();
                                    }

                                    @Override
                                    public String getName() {
                                        return "ResponseDefinitionExample";
                                    }
                                },*/
                                new StubRequestFilter() {
                                    @Override
                                    public RequestFilterAction filter(Request request) {
                                        //修改header，避免服务器返回压缩格式的文本导致无法进行字符串替换
                                        Request wrappedRequest = RequestWrapper.create()
                                                .transformHeader("Accept-Encoding", new FieldTransformer<List<String>>() {
                                                    @Override
                                                    public List<String> transform(List<String> source) {
                                                        return Arrays.asList("identity");
                                                    }
                                                })
                                                .wrap(request);
                                        return RequestFilterAction.continueWith(wrappedRequest);

                                    }

                                    @Override
                                    public String getName() {
                                        return "StubRequestFilterDemo";
                                    }
                                },
                                new ResponseTransformer() {
                                    @Override
                                    public Response transform(Request request, Response response, FileSource files, Parameters parameters) {
                                        //把测试人改名为测式人
                                        return Response.Builder.like(response)
                                                .body(response.getBodyAsString().replace("测试人", "slamdunk"))
                                                .build();
                                    }

                                    @Override
                                    public String getName() {
                                        return "ResponseTransformerDemo";
                                    }
                                })
        ); //No-args constructor will start on port 8080, no HTTPS
        wireMockServer.start();

        //client配置
        configureFor(8089);

        //stub设置
        stubFor(get(urlMatching(".*")).willReturn(aResponse().proxiedFrom("https://ceshiren.com")));

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //释放相关资源
        WireMock.reset();
        wireMockServer.stop();
    }

    @Test
    void testRestAssuredMock() {
        //启动wiremockserver
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(8089)
                .extensions(new ResponseTemplateTransformer(true)));
        wireMockServer.start();
        WireMock.configureFor(8089);

        //stub设置

        String jsonStr = "{\n" +
                "\"lotto\":{\n" +
                " \"lottoId\":5,\n" +
                " \"winning-numbers\":[2,45,34,23,7,5,3],\n" +
                " \"winners\":[{\n" +
                "   \"winnerId\":23,\n" +
                "   \"numbers\":[2,45,34,23,3,5]\n" +
                " },{\n" +
                "   \"winnerId\":54,\n" +
                "   \"numbers\":[52,3,12,11,18,22]\n" +
                " }]\n" +
                "}\n" +
                "}";
        stubFor(get(urlEqualTo("/lotto")).willReturn(aResponse()
                .withHeader("Content-Type", "application/json").withBody(jsonStr)));

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //释放相关资源
        WireMock.reset();
        wireMockServer.stop();
    }

    @Test
    void testXmlAssuredMock() {
        //启动wiremockserver
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(8089)
                .extensions(new ResponseTemplateTransformer(true)));
        wireMockServer.start();
        WireMock.configureFor(8089);

        //stub设置

        String xmlStr = "<html>\n" +
                "  <body>{\"success\":false,\"code\":0,\"desc\":\"发生未知错误，请稍后重试\"}</body>\n" +
                "</html>";
        stubFor(get(urlEqualTo("/lotto")).willReturn(aResponse()
                .withHeader("Content-Type", "text/xml").withBody(xmlStr)));

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //释放相关资源
        WireMock.reset();
        wireMockServer.stop();
    }

}
