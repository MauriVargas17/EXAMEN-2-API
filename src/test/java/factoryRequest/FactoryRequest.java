package factoryRequest;

import java.util.HashMap;
import java.util.Map;

public class FactoryRequest {

    public static IRequest make(String type){
        Map<String,IRequest> requestMap = new HashMap<>();
        requestMap.put("put",new RequestPUT());
        requestMap.put("post",new RequestPOST());
        requestMap.put("get",new RequestGET());
        requestMap.put("delete",new RequestDELETE());
        requestMap.put("create",new RequestCREATE());
        requestMap.put("aput",new ARequestPUT());
        requestMap.put("apost",new ARequestPOST());
        requestMap.put("aget",new ARequestGET());
        requestMap.put("adelete",new ARequestDELETE());
        requestMap.put("acreate",new ARequestCREATE());

        return requestMap.containsKey(type.toLowerCase())?
                   requestMap.get(type.toLowerCase()):
                   requestMap.get("get");
    }

}
