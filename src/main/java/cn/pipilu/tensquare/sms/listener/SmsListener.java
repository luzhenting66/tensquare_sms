package cn.pipilu.tensquare.sms.listener;

import cn.pipilu.tensquare.sms.pojo.SendSmsResp;
import cn.pipilu.tensquare.sms.service.SendSmsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "smsListener")
public class SmsListener  implements ChannelAwareMessageListener {
    private final Logger logger = LoggerFactory.getLogger(SmsListener.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SendSmsService sendSmsService;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long tag = message.getMessageProperties().getDeliveryTag();
        byte[] messageBody = message.getBody();
        SendSmsResp sendSmsResp = objectMapper.readValue(messageBody, SendSmsResp.class);
        String mobile = sendSmsResp.getMobile();
        String code = sendSmsResp.getCode();
        logger.info("监听到注册手机号：{},验证码：{}", mobile,code);
        try {
            sendSmsService.sendCode(mobile, code);
            channel.basicAck(tag,true);
        }catch (Exception e){
            logger.error("发送验证码失败，{},{}", mobile,code);
            channel.basicAck(tag,false);
        }
    }
}
