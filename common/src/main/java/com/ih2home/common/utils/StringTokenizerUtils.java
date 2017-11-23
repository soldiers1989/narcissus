package com.ih2home.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StringTokenizerUtils {

    /**
     * 将str按多个分隔符进行切分，
     * <p>
     * 示例：StringTokenizerUtils.split("1,2;3 4"," ,;");
     * 返回: ["1","2","3","4"]
     *
     * @param str
     * @param seperators
     * @return
     */
    public static String[] split(String str, String seperators) {
        StringTokenizer tokenlizer = new StringTokenizer(str, seperators);
        List result = new ArrayList();

        while (tokenlizer.hasMoreElements()) {
            Object s = tokenlizer.nextElement();
            result.add(s);
        }
        return (String[]) result.toArray(new String[result.size()]);
    }

    public static void main(String[] args) {
        String strArray[] = StringTokenizerUtils.split("1,2;3 4,wwwww", " ,;");
        for (String str : strArray) {
            System.out.println(str);
        }
    }

}
