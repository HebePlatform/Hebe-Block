/*
 * Copyright © 2017-2019 HebeBlock.
 */
package hebe.util;


import hebe.HEBE;
import hebe.http.ParameterException;
import hebe.http.ParameterParser;
import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

/**
 * Created on 2016/11/18.
 * 获取客户端输入密码后加密后的密文 >20字符
 * 如果密文长度>20 进行解密（如果是自己调试且大于20字符的，解密失败会抛异常，自动回复原值）
 * 不够20长度 是自己调试官方提供的API接口时候输入的，不进行解密
 */

public class DecodeUtil {

    //读取配置文件私钥
    static Properties priKeyProperties = new Properties();

    static{

        HEBE.loadProperties(priKeyProperties, "globalPrivkeystr.properties", true);
    }

   //开始解密
    public static String ifdecode(String secretPhrase){

        String globalPrikeyStr = priKeyProperties.getProperty("prikeystr");

        //判断获取的密码长度是否>20
        if(secretPhrase!=null && secretPhrase.length() >20){
            try{
                //开始解密 出现异常恢复到原来输入的密码
                secretPhrase = RSAUtil.decode(globalPrikeyStr, secretPhrase);
            }catch (Exception e){
                //如果不是密文且长度>20 抛异常 并且恢复原来输入的值
                Logger.logInfoMessage("{解密失败，返回正常码}");
            }
        }
        return secretPhrase;

    }



}
