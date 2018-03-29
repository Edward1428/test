package carSystem.com.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.http.protocol.HTTP.DEF_CONTENT_CHARSET;

/**
 * Created by Rico on 2017/6/14.
 */
public class HttpUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    private final static int TRY_TIME = 2;

    public static String httpGet(final String url, Charset charset) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        charset = charset != null ? charset : DEF_CONTENT_CHARSET;
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            try {
                StatusLine statusLine = response.getStatusLine();
                HttpEntity entity = response.getEntity();
                if (statusLine.getStatusCode() >= 300) {
                    throw new HttpResponseException( statusLine.getStatusCode(), statusLine.getReasonPhrase());
                } else if (entity == null) {
                    throw new ClientProtocolException("Response contains no content");
                } else {
                    long len = entity.getContentLength();
                    if (len != -1 && len < 2048) {
                        return EntityUtils.toString(entity, charset);
                    } else if (len > 2048) {
                        return getContent(entity, charset);
                    } else {
                        return getContent(entity, charset);
                    }
                }
            } finally {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String httpGetSetHeader(final String url, Charset charset, String headerKey, String headerValue) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        charset = charset != null ? charset : DEF_CONTENT_CHARSET;
        httpGet.setHeader(headerKey, headerValue);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            try {
                StatusLine statusLine = response.getStatusLine();
                HttpEntity entity = response.getEntity();
                if (statusLine.getStatusCode() >= 300 && statusLine.getStatusCode() != 555) {
                    throw new HttpResponseException( statusLine.getStatusCode(), statusLine.getReasonPhrase());
                } else if (entity == null) {
                    throw new ClientProtocolException("Response contains no content");
                } else {
                    long len = entity.getContentLength();
                    if (len != -1 && len < 2048) {
                        return EntityUtils.toString(entity, charset);
                    } else if (len > 2048) {
                        return getContent(entity, charset);
                    } else {
                        return getContent(entity, charset);
                    }
                }
            } finally {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HTTP POST( x-www-form-urlencoded )
     *
     * @param url       url
     * @param charset   Consts.UTF_8
     * @param formData  eg: List<NameValuePair> formparams = new ArrayList<NameValuePair>();
     *                      formparams.add(new BasicNameValuePair("param1", "value1"));
     *                      formparams.add(new BasicNameValuePair("param2", "value2"));
     *                  to: param1=value1&param2=value2
     * @return String
     */
    public static String httpPostForm(String url, Charset charset, List<NameValuePair> formData) {
        HttpPost httpPost = new HttpPost(url);
        charset = charset != null ? charset : DEF_CONTENT_CHARSET;
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formData, charset);
        httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
        httpPost.setEntity(entity);
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(3000).setConnectionRequestTimeout(3000)
                    .setSocketTimeout(3000).build();
            httpPost.setConfig(requestConfig);
            try ( CloseableHttpResponse response = httpClient.execute(httpPost) ){
                StatusLine statusLine = response.getStatusLine();
                HttpEntity responseEntity = response.getEntity();
                if (statusLine.getStatusCode() >= 300) {
                    throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
                } else if (responseEntity == null) {
                    throw new ClientProtocolException("Response contains no content");
                } else {
                    long len = responseEntity.getContentLength();
                    if (len != -1 && len < 2048) {
                        return EntityUtils.toString(responseEntity, charset);
                    } else if (len > 2048) {
                        logger.warn("HTTP POST url: " + url + ", the response contents is bigger, please confirm");
                        return getContent(responseEntity, charset);
                    } else {
                        return getContent(responseEntity, charset);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static String getContent(HttpEntity entity, Charset charset) {
        try {
            InputStream instream = entity.getContent();
            try {
                InputStreamReader inReader = new InputStreamReader(instream, charset);
                BufferedReader reader = new BufferedReader(inReader);
                String line = null;
                StringBuilder builder = new StringBuilder();
                String ls = System.getProperty("line.separator");
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                    builder.append(ls);
                }
                return builder.toString();
            } finally {
                instream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject httpGetJsonObj(final String url) {
        String content = httpGet(url, Consts.UTF_8);
        return check(content, url);
    }

    public static JSONObject httpGetHeaderJsonObj(final String url, String headerKey, String headerValue) {
        String content = httpGetSetHeader(url, Consts.UTF_8, headerKey, headerValue);
        return check(content, url);
    }

    private static JSONObject check(String content, String url) {
        if (content != null) {
            return JSON.parseObject(content);
        } else {
            int i = 1;
            while (i < TRY_TIME) {
                i++;
                if (content == null) {
                    content = httpGet(url, Consts.UTF_8);
                }
            }
            if (content == null) {
                logger.error("请求失败，URL:"+url+"；已失败"+TRY_TIME+"次");
            }
            return JSON.parseObject(content);
        }
    }
}
