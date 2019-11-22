package Test;

import Utils.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        String url="http://47.105.220.11:9999/web/api/XyApplication/getAllList";
        String service="http://172.16.100.2:8700/home/proxyLogin?" +
                "service=http://172.16.100.2:8700/card/findCardList.action?" +
                "time=1574304798919&pageNo=1&pageSize=15&sortName=&sortOrder=&query=&qtype=&cardType=1";

        Map<String,Object> map=new HashMap<>();
        map.put("service",service);
        String result=HttpClientUtil.get(url,map);
        System.out.println(result);
    }
}
