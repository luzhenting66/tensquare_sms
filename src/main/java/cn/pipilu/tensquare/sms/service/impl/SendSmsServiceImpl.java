package cn.pipilu.tensquare.sms.service.impl;

import cn.pipilu.tensquare.sms.service.SendSmsService;
import cn.pipilu.tensquare.sms.utils.SmsUtils;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.stereotype.Service;


@Service
public class SendSmsServiceImpl implements SendSmsService {
    public static final String TEMPLATE_CODE = "SMS_173760186";
    public static final String SIGN_NAME = "好茶道";
    @Override
    public void sendCode(String mobile,String code) throws ClientException {
        SmsUtils.sendMsg(TEMPLATE_CODE,SIGN_NAME,mobile, code);
    }
}
