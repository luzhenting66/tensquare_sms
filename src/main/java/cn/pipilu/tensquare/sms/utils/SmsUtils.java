package cn.pipilu.tensquare.sms.utils;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.HashMap;
import java.util.Map;

public class SmsUtils {
    private static final String accessKeyId = "aaaaaa";
    private static final String accessKeySecret = "bbbbb";
    public static void sendMsg(String templateCode,String signName,String mobile,String code) throws ClientException {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("PhoneNumbers", mobile);
        Map<String,String> codeMap = new HashMap<>();
        codeMap.put("code",code);
        request.putQueryParameter("TemplateParam", JSON.toJSONString(codeMap));

        CommonResponse response = client.getCommonResponse(request);
        System.out.println(response.getData());

    }
}
