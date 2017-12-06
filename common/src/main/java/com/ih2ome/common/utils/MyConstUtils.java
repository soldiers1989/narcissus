package com.ih2ome.common.utils;

import com.ih2ome.common.secret.DesUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/1
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public class MyConstUtils {

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

    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

    public static Object mergeObject(Object master, Object follow){
        if(master ==null && follow ==null)
            return null;
        else if(master == null && null != follow)
            return follow;
        else if(null != master && null == follow)
            return master;
        else{
            Object[] objectNone =new Object[]{};
            Class<?> c1 =master.getClass();
            Field[] fields =c1.getDeclaredFields();
            Method[] methods =c1.getDeclaredMethods();
            for(Field f : fields){
                String fName =f.getName();
                Object o1 =null;
                Object o2 =null;
                for(Method m: methods){
                    String mName =m.getName();
                    if(mName.contains("get") &&mName.equalsIgnoreCase("get"+fName)){
                        try {
                            o1 = m.invoke(master, objectNone);
                            o2 = m.invoke(follow, objectNone);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                o1 = getFitObject(o1,o2);
                for (Method m : methods) {
                    String mName =m.getName();
                    if (mName.contains("set") &&mName.equalsIgnoreCase("set" +fName)) {
                        try {
                            m.invoke(master,new Object[] { o1 });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }
        return master;
    }


    private static Object getFitObject(Object o1, Object o2){
        if(null == o1 && null == o2){
            return o1;
        }else if(null != o1 && null == o2){
            return o1;
        }else if(null == o1 && null != o2){
            return o2;
        }else{
            String s1 = o1.toString();
            String s2 = o2.toString();
            if(s1.equals("0"))//过滤那些int long
                s1 = "";
            if(StringUtils.isBlank(s1) && StringUtils.isBlank(s2)){
                return o1;
            }else if(StringUtils.isNotBlank(s1) && StringUtils.isBlank(s2)){
                return o1;
            }else if(StringUtils.isBlank(s1) && StringUtils.isNotBlank(s2)){
                return o2;
            }else{
                return o1;
            }
        }
    }

}
