package core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTML操作包装类
 *
 * @author NilsZhang
 */
@SuppressWarnings("all")
public class HTMLUtil {

    /**
     * 返回符合该正则表达式的 字符串 列表
     * pString：源字符串
     * pExpress：用于匹配的正则表达式
     * 最好举个例子
     */
    public static List<String> getPatternString(String pString, String pExpress) {
        Pattern pattern = Pattern.compile(pExpress);
        String s = pString;

        List<String> sList = new ArrayList<String>();
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            sList.add(matcher.group());
        }

        return sList;
    }




    /**
     * 返回被夹在 pri 和 end 之间的String
     * @param 源字符串
     * @param 起始值
     * @param 结束值
     * @return
     */
    public static String getSelectedString(String temp,String pri,String end) {
        int indexOf = temp.indexOf(pri);

        int indexOf2 = temp.indexOf(end);

        return temp.substring(indexOf+pri.length(),indexOf2);
    }


    /**
     * 返回符合该正则表达式的 第一个 字符集
     * pString：源字符串
     * pExpress：用于匹配的正则表达式
     * 最好举个例子
     */
    public static String getFirstPatternString(String pString, String pExpress) {
        List<String> patternString = HTMLUtil.getPatternString(pString, pExpress);
        if(patternString != null&&patternString.size()>0)
        {
            return patternString.get(0);
        }

        return "";
    }

    /**
     * 返回夹在两个字符串间的 第一个 字符集
     * pString：源字符串
     * pFirst：首字符串
     * pLast: 尾字符串
     * 最好举个例子
     */
    public static String getBetweenString(String pString, String pFirst,String pLast) {
        String pExpress = pFirst+".*?"+pLast;
        String firstPatternString = getFirstPatternString(pString, pExpress);
        if(firstPatternString.length()>0)
            return firstPatternString.substring(pFirst.length(),firstPatternString.length()-pLast.length());
        else
            return "";
    }




}
