package util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import java.io.InputStream;
import java.security.Principal;
import org.apache.http.protocol.BasicHttpContext;
import java.io.InputStreamReader;
import org.apache.http.protocol.HttpContext;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import org.apache.http.client.methods.HttpGet;
import java.util.HashMap;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import java.util.ArrayList;

import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import java.util.List;
import org.apache.http.protocol.HTTP;
import org.apache.http.NameValuePair;
import java.util.Iterator;
import org.apache.http.entity.StringEntity;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpException;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpRequest;
import org.apache.http.entity.HttpEntityWrapper;
import java.util.zip.GZIPInputStream;

public class HttpUtil {
    private static HashMap<String,
                           HttpClient> sessionMap = new HashMap<String,
            HttpClient>();

    public HttpUtil() {
    }

    /**
     * get  httpGet
     *
     * @param url String
     * @param name String     l����ƣ�����ά��session,����Ҫά��l��ʱ����null���������������destroy����
     * @param charset String
     * @return String
     */
    public static String get(String url, String name, String charset) {
        if (charset == null)
            charset = HTTP.UTF_8;

        try {
            HttpClient httpclient;

            if (name == null) { //����Ҫά��l��
                httpclient = createHttpClient();
            } else if (sessionMap.containsKey(name)) {
                httpclient = sessionMap.get(name);
            } else {
                httpclient = createHttpClient();
                sessionMap.put(name, httpclient);
            }

            HttpGet httpget1 = new HttpGet(url);
			httpget1.setHeader(new BasicHeader("Accept-Language", "zh-cn"));
			HttpResponse response1 = httpclient.execute(httpget1);
			HttpEntity entity1 = response1.getEntity();

            return EntityUtils.toString(entity1, charset);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * post
     *
     * @param url String
     * @param map HashMap     �ύ�?�ļ�ֵ��
     * @param name String     l����ƣ�����ά��session
     * @param charset String
     * @return String
     */
    public static String post(String url, HashMap<String, String> map,
            String name, String charset) {
        if (charset == null)
            charset = HTTP.UTF_8;

        try {
            HttpClient httpclient;

            if (name == null) { //����Ҫά��l��
                httpclient = createHttpClient();
            } else if (sessionMap.containsKey(name)) {
                httpclient = sessionMap.get(name);
            } else {
                httpclient = createHttpClient();
                sessionMap.put(name, httpclient);
            }

            HttpPost httpost = new HttpPost(url);

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();

            if (map != null) {
                Iterator it = map.keySet().iterator();

                while (it.hasNext()) {
                    String key = (String) it.next();
                    nvps.add(new BasicNameValuePair(key, map.get(key)));
                }
            }

            httpost.setEntity(new UrlEncodedFormEntity(nvps, charset));

            HttpResponse response = httpclient.execute(httpost);
            HttpEntity entity1 = response.getEntity();

            return EntityUtils.toString(entity1, charset);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static String post(String url, String content,
                              String name, String charset) {
        if (charset == null)
            charset = HTTP.UTF_8;

        try {
            HttpClient httpclient;

            if (name == null) { //����Ҫά��l��
                httpclient = createHttpClient();
            } else if (sessionMap.containsKey(name)) {
                httpclient = sessionMap.get(name);
            } else {
                httpclient = createHttpClient();
                sessionMap.put(name, httpclient);
            }

            HttpPost httpost = new HttpPost(url);

            if (content != null) {
                httpost.setEntity(new StringEntity(content,charset));
            }

            HttpResponse response = httpclient.execute(httpost);

            HttpEntity entity1 = response.getEntity();

            return EntityUtils.toString(entity1, charset);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void destroy(String name) {
        HttpClient httpclient = sessionMap.get(name);
        httpclient.getConnectionManager().shutdown();
        sessionMap.remove(name);
    }

    private static HttpClient createHttpClient() {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        System.getProperties().setProperty("httpclient.useragent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; CIBA; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");

        try {

            httpclient.addRequestInterceptor(new HttpRequestInterceptor() {

                public void process(
                        final HttpRequest request,
                        final HttpContext context) throws HttpException,
                        IOException {
                    if (!request.containsHeader("Accept-Encoding")) {
                        request.addHeader("Accept-Encoding", "gzip");
                    }
                }

            });

            httpclient.addResponseInterceptor(new HttpResponseInterceptor() {

                public void process(
                        final HttpResponse response,
                        final HttpContext context) throws HttpException,
                        IOException {
                    HttpEntity entity = response.getEntity();
                    Header ceheader = entity.getContentEncoding();
                    if (ceheader != null) {
                        HeaderElement[] codecs = ceheader.getElements();
                        for (int i = 0; i < codecs.length; i++) {
                            if (codecs[i].getName().equalsIgnoreCase("gzip")) {
                                response.setEntity(
                                        new GzipDecompressingEntity(response.
                                        getEntity()));
                                return;
                            }
                        }
                    }
                }

            });


        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpclient;

    }

    static class GzipDecompressingEntity extends HttpEntityWrapper {

        public GzipDecompressingEntity(final HttpEntity entity) {
            super(entity);
        }

        @Override
        public InputStream getContent() throws IOException,
                IllegalStateException {

            // the wrapped entity's getContent() decides about repeatability
            InputStream wrappedin = wrappedEntity.getContent();

            return new GZIPInputStream(wrappedin);
        }

        @Override
        public long getContentLength() {
            // length of ungzipped content is not known
            return -1;
        }

    }


}
