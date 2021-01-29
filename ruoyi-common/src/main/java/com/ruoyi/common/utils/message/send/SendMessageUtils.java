package com.ruoyi.common.utils.message.send;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.config.SendMessageConfig;
import com.ruoyi.common.utils.message.client.Request;
import com.ruoyi.common.utils.message.client.Response;
import com.ruoyi.common.utils.message.client.SendSmsEntity;
import com.ruoyi.common.utils.message.constant.SendConstant;
import com.ruoyi.common.utils.secret.MD5;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * com.ruoyi.common.utils.message.send
 * create by admin nihui
 * create time 2021/1/29
 * version 1.0
 **/
@Component
public class SendMessageUtils {

    Request request = null;



    public String sendMessage(String phone, String content){
        return run(phone,content);
    }


    private String run(String phone, String content) {
        String url = SendConstant.SMS_SEND_URL;
        try {
            request = Request.newHttpRequestBuilder().uri(url).timeout(5000).charset("utf-8").method(Request.POST).contentType(Request.ContentType.JSON).build();
            send(phone, content);
            request.release();
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

    /**
     * 短信发送
     * @throws UnsupportedEncodingException
     */
    private void send(String phone, String code) throws UnsupportedEncodingException {
        //1. 构建数据包对象
        long t1 = System.currentTimeMillis();
        SendSmsEntity sendEntity = buildSendEntity();
        sendEntity.setAppKey(SendConstant.APP_KEY);
        sendEntity.setTimestamp(String.valueOf(t1));
        sendEntity.setMobile(phone);
        String tag = "【"+new SendMessageConfig().getSendTag()+"】";
        sendEntity.setContent(tag + code);
        sendEntity.setSign(MD5.getMD5(SendConstant.APP_KEY + sendEntity.getTimestamp() + sendEntity.getMobile() + sendEntity.getContent() + SendConstant.APP_SECRET));

        //sendEntity.setReportUrl("http://127.0.0.1:80/test/callback");
        sendEntity.setSpNumber("");
        //2. 转化对象为json字符串数据
        String data = JSONObject.toJSONString(sendEntity);

        //3. 字符串url编码，防止特殊字符被客户端强行改变，譬如+会被浏览器或者其他客户端变更为空格
        String encodeData = URLEncoder.encode(data, "UTF-8");
        //4. 发送
        Response resp = request.send(SendConstant.SMS_SEND_URL, encodeData);
        //5. 处理发送响应结果 resp.status==1时，
        System.out.println("\n");
        System.out.println(">>> Request.url:             " + SendConstant.SMS_SEND_URL);
        System.out.println(">>> Request.data:            " + data);
        System.out.println(">>> ");
        System.out.println(">>> Response.contentLength:  " + resp.contentLength());
        System.out.println(">>> Response.contentType:    " + resp.contentType());
        System.out.println(">>> Response.status:         " + resp.status());
        System.out.println(">>> Response.charset:        " + resp.charset());
        System.out.println(">>> Response.content:        " + resp.content());
        System.out.println(">>> Response.error:          " + resp.error());
        System.out.println(">>> cost.time.ms:            " + (System.currentTimeMillis() - t1));

        //解析发送结果
        if (resp.status() == 200 && resp.content() != null) {
            JSONObject respJson = JSONObject.parseObject(resp.content());
            System.out.println(
                    ">>> Send.result:             " + (respJson.getInteger("status") == 1 ? "OK" : "FAIL"));
            if (respJson.getInteger("status") == 1) {
                System.out.println(">>> Send.result.taskId:      " + respJson.getLong("taskId"));
            }
        }

    }

    /**
     * {"appKey":"100819","timestamp":"1546589422955","mobile":"18695993557",
     * "content":"汉中市政府","spNumber":"","sendTime":"20190809090057","reportUrl":"",
     * "moUrl":"","attach":"","sign":"B65977B7BC6FF701A9B4395550CB3577"}
     *
     * @return
     */
    private static SendSmsEntity buildSendEntity() {
        // TODO Auto-generated method stub
        SendSmsEntity entity = new SendSmsEntity();
        entity.setAppKey(SendConstant.APP_KEY);
        entity.setTimestamp(System.currentTimeMillis() + "");
        entity.setMobile("13800138001");
        entity.setContent("【申报】测试消息,MT-00001");
        entity.setReportUrl("http://127.0.0.1:7801/test/callback");
        entity.setSpNumber("");
        String source = entity.getAppKey() + entity.getTimestamp() + entity.getMobile() + entity.getContent()
                + entity.getSpNumber() + entity.getSendTime() + SendConstant.APP_SECRET;
        String sign = MD5.getMD5(source);
        entity.setSign(sign);
        return entity;
    }

}
