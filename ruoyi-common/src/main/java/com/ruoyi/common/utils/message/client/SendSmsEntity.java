package com.ruoyi.common.utils.message.client;

/**
 * com.ruoyi.common.utils.message.client
 * create by admin nihui
 * create time 2021/1/29
 * version 1.0
 **/
public class SendSmsEntity {
    String appKey = "";
    String timestamp = "";
    String mobile = "";
    String content = "";
    String sendTime = "";
    String spNumber = "";
    String sign = "";
    String reportUrl = "";
    String moUrl = "";
    String attach = "";

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSpNumber() {
        return spNumber;
    }

    public void setSpNumber(String spNumber) {
        this.spNumber = spNumber;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public String getMoUrl() {
        return moUrl;
    }

    public void setMoUrl(String moUrl) {
        this.moUrl = moUrl;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    @Override
    public String toString() {
        return String.format(
                "SendSmsEntity [appKey=%s, timestamp=%s, mobile=%s, content=%s, sendTime=%s, spNumber=%s, sign=%s, reportUrl=%s, moUrl=%s, attach=%s]",
                appKey, timestamp, mobile, content, sendTime, spNumber, sign, reportUrl, moUrl, attach);
    }

}
