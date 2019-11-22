package Utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */
    public static String post(String url, Map<String, Object> map) {
        StringBuffer res=new StringBuffer();
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for (String key : map.keySet()) {
            if(map.get(key) == null || "".equals(map.get(key))){
                continue;
            }
            formparams.add(new BasicNameValuePair(key, map.get(key).toString()));
        }
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    res.append(EntityUtils.toString(entity, "UTF-8"));
                    return res.toString();
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
    /**
     * 发送 get请求
     */
    public static String get(String url, Map<String, Object> map) {
        StringBuffer str=new StringBuffer();
        if (map!=null && map.size() != 0) {
            url += transferToParam(map);
        }
        // 创建httpget.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            URL urls = new URL(url);
            String host = urls.getHost();
            if(!StringUtils.isEmpty(urls.getPort())){
                host = host+":"+urls.getPort();
            }
            URI uri = new URI(urls.getProtocol(),host, urls.getPath(),urls.getQuery(),null);
            HttpGet httpget = new HttpGet(uri);
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    str.append(EntityUtils.toString(entity, "utf-8"));
                    return str.toString();
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String doPostJson(String url, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }

    private static String transferToParam(Map<String, Object> map) {
        StringBuffer param = new StringBuffer("");
        boolean isFirst=true;
        for (String key : map.keySet()) {
            if(isFirst){
                isFirst=false;
                param.append("?");
            }else {
                param.append("&");
            }
            param.append(key);
            param.append("=");
            param.append(map.get(key));
        }
        return param.toString();
    }
}
