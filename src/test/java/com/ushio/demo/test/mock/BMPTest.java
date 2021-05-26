package com.ushio.demo.test.mock;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class BMPTest {

    @Test
    public void bmpTest() throws IOException {
        BrowserMobProxy proxy = new BrowserMobProxyServer();
        proxy.start(8085);
        int port = proxy.getPort();
        System.out.println(port);
        proxy.addRequestFilter((request, contents, messageInfo) -> {
            //过滤修改部分请求体或head的内容
            if (messageInfo.getOriginalUrl().endsWith("/some-endpoint-to-intercept")) {
                // retrieve the existing message contents as a String or, for binary contents, as a byte[]
                String messageContents = contents.getTextContents();

                // do some manipulation of the contents
                String newContents = messageContents.replaceAll("original-string", "my-modified-string");
                //[...]

                // replace the existing content by calling setTextContents() or setBinaryContents()
                contents.setTextContents(newContents);
            }

            // in the request filter, you can return an HttpResponse object to "short-circuit" the request
            //如果返回值不是null, 那么代理不再往外发送请求，而是直接将这个非空的元素返回去给浏览器
            return null;
        });

        // responses are equally as simple:
        proxy.addResponseFilter((response, contents, messageInfo) -> {
            /*...some filtering criteria...*/
            //过滤响应结果的内容
            if (messageInfo.getOriginalUrl().contains(".json")) {
                contents.setTextContents("This message body will appear in all responses!");
            }
        });


        System.in.read();
    }
}
