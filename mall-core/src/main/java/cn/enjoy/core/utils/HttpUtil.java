package cn.enjoy.core.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company:享学信息科技有限公司 Co., Ltd.</p>
 * @author Ray
 *         Created on 2016/10/9.
 */
public class HttpUtil {

    private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static CloseableHttpClient httpClient;
    private static Charset UTF_8 = Charset.forName("UTF-8");
    private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();

    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        // Increase max total connection to 200
        cm.setMaxTotal(200);
        // Increase default max connection per route to 20
        cm.setDefaultMaxPerRoute(20);
        // Increase max connections for localhost:80 to 50
        HttpHost localhost = new HttpHost("localhost", 80);
        cm.setMaxPerRoute(new HttpRoute(localhost), 50);

        httpClient = HttpClients.custom().setConnectionManager(cm).build();
    }

    public static String post(String url, String content){
        logger.debug("{} - {}",url, content);
        HttpPost httppost = new HttpPost(url);
        httppost.setConfig(requestConfig);
        if(content != null) {
            HttpEntity httpEntity = new StringEntity(content, UTF_8);
            httppost.setEntity(httpEntity);
        }
        return execute(httppost);
    }

    public static String post4GzipResponse(String url, String content){
        logger.debug("{} - {}",url, content);
        HttpPost httppost = new HttpPost(url);
        httppost.setConfig(requestConfig);
        httppost.addHeader("Accept-Encoding", "gzip");
        if(content != null) {
            HttpEntity httpEntity = new StringEntity(content, UTF_8);
            httppost.setEntity(httpEntity);
        }
        return execute(httppost);
    }

    /**
     * 以JSON的格式发送请求
     * @param url
     * @param content
     * @return
     */
    public static String postAsJson(String url, String content) {
        logger.debug("{} - {}",url, content);
        HttpPost httppost = new HttpPost(url);
        httppost.setConfig(requestConfig);
        HttpEntity httpEntity = new StringEntity(content, ContentType.APPLICATION_JSON);
        httppost.setEntity(httpEntity);
        return execute(httppost);
    }

    public static String get(String url){
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        return execute(httpGet);
    }

    private static String execute(HttpRequestBase httpRequestBase){
        String result = null;
        try {
            HttpResponse response = httpClient.execute(httpRequestBase);
            HttpEntity resEntity = response.getEntity();
            if(resEntity != null){
                result = EntityUtils.toString(resEntity, UTF_8);
                EntityUtils.consume(resEntity);
                if(result.length() < 2000) {
                    logger.debug(result);
                }else {
                    if(logger.isTraceEnabled()){
                        logger.trace(result);
                    }
                }
            }
        } catch (IOException e) {
            logger.error("", e);
        }
        return result;
    }

    public static void main(String[] args) {
        String res = HttpUtil.get("http://www.baidu.com");
        System.out.println("res = " + res);
    }
}
