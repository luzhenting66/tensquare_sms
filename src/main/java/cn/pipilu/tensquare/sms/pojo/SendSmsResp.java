package cn.pipilu.tensquare.sms.pojo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class SendSmsResp implements Serializable {

    private static final long serialVersionUID = -71287696995834865L;
    private String code;
    private String mobile;
}
