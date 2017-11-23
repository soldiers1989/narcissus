package com.ih2home.utils.common;

import com.ih2home.utils.common.Secret.DesUtils;
import com.ih2home.utils.common.utils.RandomUtil;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/1
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public class ConstUtils {

    /**
     * MD5加密
     * @param text
     * @return
     */
    public static String md5(String text){
        return DigestUtils.md2Hex(text);
    }

    /**
     * des加密
     * @param text
     * @return
     */
    public static String desEncode(String text){
        byte[] secretArr = DesUtils.encryptMode(text.getBytes());
        return new String(secretArr);
    }

    /**
     * des解密
     * @param text
     * @return
     */
    public static String desDecode(String text){
        byte[] myMsgArr = DesUtils.decryptMode(text.getBytes());
        return new String(myMsgArr);
    }

    /**
     * 响应数据随机数
     * @return
     */
    public static String getResponseRandomStr(){
        return RandomUtil.generateString(16);
    }

}
