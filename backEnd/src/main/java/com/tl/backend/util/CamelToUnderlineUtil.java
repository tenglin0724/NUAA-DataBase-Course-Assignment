package com.tl.backend.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CamelToUnderlineUtil {

    /**
     * @author Howe
     * @Description 将驼峰转为下划线
     * @param str
     * @return java.lang.String
     * @Date   2022/4/22 13:11
     * @since  1.0.0
     */

    private static String under2camel(String s)
    {
        String separator = "_";
        String under="";
        s = s.toLowerCase().replace(separator, " ");
        String sarr[]=s.split(" ");
        for(int i=0;i<sarr.length;i++)
        {
            String w=sarr[i].substring(0,1).toUpperCase()+sarr[i].substring(1);
            under +=w;
        }
        return under;
    }

    /**
     * @author Howe
     * @Description 将下划线转为驼峰
     * @param str
     * @return java.lang.String
     * @Date   2022/4/22 13:12
     * @since  1.0.0
     */
    public static String camel2under(String c)
    {
        String separator = "_";
        c = c.replaceAll("([a-z])([A-Z])", "$1"+separator+"$2").toLowerCase();
        return c;
    }
}
