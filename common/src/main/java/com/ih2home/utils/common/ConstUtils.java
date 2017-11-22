package com.ih2home.utils.common;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/1
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public class ConstUtils {

    public static String md5(String text){
        return DigestUtils.md2Hex(text);
    }
}
