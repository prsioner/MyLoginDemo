package userlogindemo.demo.util;

import java.util.HashMap;
import java.util.Map;

public class ResultUtils {

    public static String dealFileResult(String content,String msg){

        String result;

        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("code","0");
        returnMap.put("data",content);
        returnMap.put("msg",msg);

        result = FastJsonUtil.toJSONString(returnMap,true);
        return result;
    }
}
