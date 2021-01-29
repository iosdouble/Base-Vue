package com.ruoyi.common.utils.qrcode;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ruoyi.common.config.RuoYiConfig;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * 二维码生成
 */
@Component
public class QrGenerate {

    public  String getRqCode(String activityId,String activityType){
        // 上传文件路径
        String filePath = RuoYiConfig.getUploadPath();
        // 文件名
        String fileName = "";
        String url ="";
        fileName = "/"+activityId+".jpg";
        url = filePath + fileName;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activityId",activityId);
        jsonObject.put("type",activityType);
        try {
            rqCodeGenerate(900,900,jsonObject.toJSONString(),url);
            return fileName;
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 生成二维码
     * @param width
     * @param height
     * @param content
     * @param url
     * @throws WriterException
     * @throws IOException
     * @throws NotFoundException
     */
    public void rqCodeGenerate(int width,int height,String content,String url) throws WriterException, IOException, NotFoundException {
        final String format = "jpg";
        //定义二维码的参数
        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //L级：约可纠错7%的数据码字,M级：约可纠错15%的数据码字,Q级：约可纠错25%的数据码字,H级：约可纠错30%的数据码字
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
        hints.put(EncodeHintType.MARGIN, 2);
        //生成二维码
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        Path file = new File(url).toPath();
        MatrixToImageWriter.writeToPath(bitMatrix, format, file);
        System.out.println("生成成功,路径：" + file.toString());
        System.out.println("------------------------------");
        //解析二维码
        MultiFormatReader formatReader = new MultiFormatReader();
        BufferedImage image = ImageIO.read(file.toFile());
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
        Result result = formatReader.decode(binaryBitmap, hints);
        System.out.println("二维码解析结果：" + result.toString());
        System.out.println("二维码的格式：" + result.getBarcodeFormat());
        System.out.println("二维码的文本内容：" + result.getText());
    }
 
 
    /**
     * 解析二维码
     * @param url
     * @return
     * @throws IOException
     * @throws NotFoundException
     */
    public String rqCodeAnalysis(String url) throws IOException, NotFoundException {
        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //L级：约可纠错7%的数据码字,M级：约可纠错15%的数据码字,Q级：约可纠错25%的数据码字,H级：约可纠错30%的数据码字
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
        hints.put(EncodeHintType.MARGIN, 2);
        Path file = new File(url).toPath();
        MultiFormatReader formatReader = new MultiFormatReader();
        BufferedImage image = ImageIO.read(file.toFile());
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
        Result result = formatReader.decode(binaryBitmap, hints);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",result.toString());
        jsonObject.put("format",result.getBarcodeFormat());
        jsonObject.put("content",result.getText());
        return jsonObject.toJSONString();
    }
}