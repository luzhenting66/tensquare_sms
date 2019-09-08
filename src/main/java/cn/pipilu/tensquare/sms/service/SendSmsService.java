package cn.pipilu.tensquare.sms.service;

import com.aliyuncs.exceptions.ClientException;

public interface SendSmsService {

    void sendCode(String mobile,String code) throws ClientException;
}
