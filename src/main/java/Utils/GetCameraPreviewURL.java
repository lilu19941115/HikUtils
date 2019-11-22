package Utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.entity.HiKCard;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetCameraPreviewURL {

    public static String GetCameraPreviewURL() {

        /**
         * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
         */
        ArtemisConfig.host = "172.16.100.2:443"; // artemis网关服务器ip端口
        ArtemisConfig.appKey = "29180881";  // 秘钥appkey
        ArtemisConfig.appSecret = "XO0wCAYGi4KV70ybjznx";// 秘钥appSecret

        /**
         * STEP2：设置OpenAPI接口的上下文
         */
        final String ARTEMIS_PATH = "/artemis";

        /**
         * STEP3：设置接口的URI地址
         */
        final String previewURLsApi = ARTEMIS_PATH + "/api/resource/v1/card/cardList";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", previewURLsApi);//根据现场环境部署确认是http还是https
            }
        };

        /**
         * STEP4：设置参数提交方式
         */
        String contentType = "application/json";

        /**
         * STEP5：组装请求参数
         */
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("pageNo", 1);
        jsonBody.put("pageSize", 10000);
        String body = jsonBody.toJSONString();
        /**
         * STEP6：调用接口
         */
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType , null);// post请求application/json类型参数
        return result;
    }

    public static List<HiKCard> getCardList(String s){
        //String result=GetCameraPreviewURL();
        JSONObject jsonObject=JSONObject.parseObject(s);
        JSONObject json= (JSONObject) jsonObject.get("data");
        JSONArray jsons= (JSONArray) json.get("list");
        System.out.println(jsons);
        List<HiKCard> list=JSONObject.parseArray(JSONArray.toJSONString(jsons),HiKCard.class);
        return  list;

    }

    public static List<HiKCard> removeNullNameCard(List<HiKCard> list){
        int index=0;
        int count=0;
       for(HiKCard hiKCard:list){

           if(StringUtils.isEmpty(hiKCard.getPersonName())){
               index=count;
           }
           count++;
       }
       list.remove(index);
        return list;
    }

    public static void main(String[] args) {
        String s="{\n" +
                "    \"code\": \"0\", \n" +
                "    \"msg\": \"ok\", \n" +
                "    \"data\": {\n" +
                "        \"total\": 4, \n" +
                "        \"pageNo\": 1, \n" +
                "        \"pageSize\": 4, \n" +
                "        \"list\": [\n" +
                "            {\n" +
                "                \"cardId\": \"c2754120-a9f0-11e8-bc12-af5377f6fe30\", \n" +
                "                \"cardNo\": \"11111111\", \n" +
                "                \"personId\": \"9ba12083-0560-43f8-b898-6801e04c57d8\", \n" +
                "                \"personName\": \"xufangjie\", \n" +
                "                \"useStatus\": 2, \n" +
                "                \"startDate\": \"2018-08-27T00:00:00:000+08:00\", \n" +
                "                \"endDate\": \"2037-12-31T23:59:59:000+08:00\", \n" +
                "                \"lossDate\": \"2018-08-27T20:37:39:067+08:00\", \n" +
                "                \"unlossDate\": null\n" +
                "            }, \n" +
                "            {\n" +
                "                \"cardId\": \"c0bf80fc-a9f5-11e8-bc13-674008df3d9a\", \n" +
                "                \"cardNo\": \"77777777\", \n" +
                "                \"personId\": \"8ffb3b91-f823-42b6-8fca-137bff553857\", \n" +
                "                \"personName\": \"xfj\", \n" +
                "                \"useStatus\": 1, \n" +
                "                \"startDate\": \"2018-08-27T00:00:00:000+08:00\", \n" +
                "                \"endDate\": \"2037-12-31T23:59:59:000+08:00\", \n" +
                "                \"lossDate\": null, \n" +
                "                \"unlossDate\": null\n" +
                "            }, \n" +
                "            {\n" +
                "                \"cardId\": \"f79b94f8-a9f5-11e8-bc14-d35eea50c216\", \n" +
                "                \"cardNo\": \"2222222222222222222\", \n" +
                "                \"personId\": \"9ba12083-0560-43f8-b898-6801e04c57d8\", \n" +
                "                \"personName\": \"\", \n" +
                "                \"useStatus\": 1, \n" +
                "                \"startDate\": \"2018-08-27T00:00:00:000+08:00\", \n" +
                "                \"endDate\": \"2037-12-31T23:59:59:000+08:00\", \n" +
                "                \"lossDate\": null, \n" +
                "                \"unlossDate\": null\n" +
                "            }, \n" +
                "            {\n" +
                "                \"cardId\": \"edc0fc9c-a9f6-11e8-bc15-934a1fd19c22\", \n" +
                "                \"cardNo\": \"555555555\", \n" +
                "                \"personId\": \"a0d985b0-7db7-452d-abda-d5a8f6b3fda2\", \n" +
                "                \"personName\": \"zhuchenxi\", \n" +
                "                \"useStatus\": 1, \n" +
                "                \"startDate\": \"2018-08-27T00:00:00:000+08:00\", \n" +
                "                \"endDate\": \"2037-12-31T23:59:59:000+08:00\", \n" +
                "                \"lossDate\": null, \n" +
                "                \"unlossDate\": null\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        List<HiKCard> list=getCardList(s);
        List<HiKCard> lists=removeNullNameCard(list);
        System.out.println(lists.toString());
    }
}