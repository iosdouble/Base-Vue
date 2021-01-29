package com.ruoyi.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * com.ruoyi.common.config
 * create by admin nihui
 * create time 2021/1/29
 * version 1.0
 **/
@Component
@ConfigurationProperties(prefix = "sendmessage")
public class SendMessageConfig {

    private String sendTag;

    public SendMessageConfig() {
    }

    public SendMessageConfig(String sendTag) {
        this.sendTag = sendTag;
    }

    public String getSendTag() {
        return sendTag;
    }

    public void setSendTag(String sendTag) {
        this.sendTag = sendTag;
    }
}
