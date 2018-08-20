package com.example.huxianpei.richeditor;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hxp on 2016/12/27.
 * 字符串操作工具包
 */
public class YHStringUtils {
    private final static Pattern emailer = Pattern.compile("^[\\.a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+[\\.][a-zA-Z0-9_-]+$");

    /**
     * 判断是否有中文
     *
     * @param sequence 字符
     * @return true or false
     */
    public static boolean hasChinese(String sequence) {
        final String format = "[\\u4E00-\\u9FA5\\uF900-\\uFA2D]";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(sequence);
        return matcher.find();
    }

    /**
     * 去掉所有空格
     */
    public static String removeBlank(String result) {
        if (isEmpty(result)) {
            return "";
        }
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(result);
        return m.replaceAll("");
    }

    public static String removeReturn(String result) {
        if (isEmpty(result)) {
            return "";
        }
        Pattern p = Pattern.compile("\r|\n");
        Matcher m = p.matcher(result);
        return m.replaceAll("");
    }

    /**
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public static boolean isShow(String obj, String owner) {
        return "0".equals(obj);
    }

    /**
     * 判断给定字符串是否空白串。
     */
    public static boolean isEmpty(Object obj) {
        return null == obj || obj.toString().trim().length() == 0 || "null".equals(obj.toString());
    }

    public static boolean isEmptyYH(Object obj) {
        return isEmpty(obj) || "0".equals(obj);
    }

    public static boolean isEquel(String str1, String str2) {
        return str1 == str2 || (str1 != null && str1.equals(str2));
    }

    public static boolean isEquel(int str1, String str2) {
        return (str1 + "").equals(str2) || (str1 + "" != null && (str1 + "").equals(str2));
    }

    public static boolean isEquel(int str1, int str2) {
        return str1 == str2;
    }

    public static String url2path(String url) {
        return url.substring(7, url.lastIndexOf("")).replaceAll("/", "_").replace("", "_").concat(url.substring(url.lastIndexOf(""), url.length()));
    }

    public static boolean isUrl(String url) {
        if (isEmpty(url)) {
            return false;
        }
        Pattern p = Pattern.compile("(http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&*=]*))");
        Matcher m = p.matcher(url);
        return m.find();
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     */
    public static boolean isEmail(String email) {
        return !(isEmpty(email)) && emailer.matcher(email).matches();
    }

//	/**
//	 * 判断是不是一个合法的url
//	 */
//	public static boolean isUrl(String url) {
//		return !(isEmpty(url)) && urler.matcher(url).matches();
//	}

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null) return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * Converts a Last.fm boolean result string to a boolean.
     *
     * @param resultString A Last.fm boolean result string.
     * @return <code>true</code> if the given String represents a true, <code>false</code> otherwise.
     */
    public static boolean convertToBoolean(String resultString) {
        return "1".equals(resultString);
    }

    /**
     * Converts from a boolean to a Last.fm boolean result string.
     *
     * @param value A boolean value.
     * @return A string representing a Last.fm boolean.
     */
    public static String convertFromBoolean(boolean value) {
        if (value) {
            return "1";
        } else {
            return "0";
        }
    }

    /**
     * Creates a Map out of an array with Strings.
     *
     * @param strings input strings, key-value alternating
     * @return a parameter map
     */
    public static Map<String, String> map(String... strings) {
        if (strings.length % 2 != 0)
            throw new IllegalArgumentException("strings.length % 2 != 0");
        Map<String, String> mp = new HashMap<String, String>();
        for (int i = 0; i < strings.length; i += 2) {
            mp.put(strings[i], strings[i + 1]);
        }
        return mp;
    }

    /**
     * URL Encodes the given String <code>s</code> using the UTF-8 character encoding.
     *
     * @param s a String
     * @return url encoded string
     */
    public static String encode(String s) {
        if (s == null)
            return null;
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // utf-8 always available
        }
        return null;
    }

    /**
     * Decodes an URL encoded String <code>s</code> using the UTF-8 character encoding.
     *
     * @param s an encoded String
     * @return the decoded String
     */
    public static String decode(String s) {
        if (s == null)
            return null;
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // utf-8 always available
        }
        return null;
    }

    /**
     * Strips all characters from a String, that might be invalid to be used in file names.
     * By default <tt>: / \ < > | ? " *</tt> are all replaced by <tt>-</tt>.
     * Note that this is no guarantee that the returned name will be definately valid.
     *
     * @param s the String to clean up
     * @return the cleaned up String
     */
    public static String cleanUp(String s) {
        return s.replaceAll("[*:/\\\\?|<>\"]", "-");
    }

    /**
     * @param checkCode
     * @return
     * @author hxp
     * @createdate 2012-9-21 下午8:53:12
     * @Description: 校验验证码
     */
    public static String validCheckCode(String checkCode) {
        String message = "";
        if (YHStringUtils.isEmpty(checkCode)) {
            message = "请输入验证码！";
        } else if (checkCode.length() != 4) {
            message = "验证码请输入4位有效数字！";
        }
        return message;
    }

    /**
     * 判断是否为整型
     */
    public static boolean isNumeric(String str) {
        str = str.replace(" ", "");
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String convertToSex(String sex) {
        String result = "男";
        if ("2".equals(sex)) {
            result = "女";
        }
        return result;
    }

    public static int toIntegration(String moneyString) {
        try {
            double money = Double.parseDouble(moneyString);
            return (int) (money * 200);
        } catch (Exception e) {
        }
        return 0;
    }

    public static double toMoney(String moneyString) {
        try {
            double money = Double.parseDouble(moneyString);
            return money / 200;
        } catch (Exception e) {
        }
        return 0;
    }

    public static int toInt(String moneyString) {
        try {
            int money = Integer.parseInt(moneyString);
            return money / 200;
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 检测String是否全是中文
     *
     * @param name
     * @return
     */
    public static boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }

//    public static boolean isMobile1(String str) {
//        if (str.startsWith("1") && str.length() == 11 && !str.contains(" ")) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    /**
     * 将输入流解析为String
     *
     * @param is
     * @return
     * @throws Exception
     */
    public static String InputStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,
                "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                line = null;
            }
        } finally {
            reader.close();
        }
        line = sb.toString();
        sb = null;
        return line;
    }

    public static String getLastPathSegment(String content) {
        if (content == null || content.length() == 0) {
            return "";
        }
        String[] segments = content.split("/");
        if (segments.length > 0) {
            return segments[segments.length - 1];
        }
        return "";
    }

    /**
     * 获取链接的后缀名
     *
     * @return
     */
    public static String parseSuffix(String url) {
        Pattern pattern = Pattern.compile("\\S*[?]\\S*");
        Matcher matcher = pattern.matcher(url);
        String[] spUrl = url.toString().split("/");
        int len = spUrl.length;
        String endUrl = spUrl[len - 1];

        if (matcher.find()) {
            String[] spEndUrl = endUrl.split("\\?");
            return spEndUrl[0].split("\\.")[1];
        }
        return endUrl.split("\\.")[1];
    }

    public static boolean isEmoji(char codePoint) {
        return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    public static String[] getArrayByString(String string) {
        if (YHStringUtils.isEmpty(string)) return null;
        if (!string.contains(",")) return new String[]{string};
        return string.split(",");
    }

    public static String trim(String str) {
        if (!isEmpty(str)) {
            return str.replaceAll("\\s*", "");
        }
        return null;
    }

    public static String surportNum(int num) {
        if (num < 10000) {
            return num + "";
        }
        return num / 10000 + "." + num % 10000 / 1000 + "万";
    }


    public static String formatNum(String num) {
        try {
            int number = Integer.valueOf(num);
            return surportNum(number);
        } catch (Exception e) {
            return "0";
        }
    }

    public static boolean isOkNum(String strng) {
        if (isEmpty(strng)) return false;
        if ("0".equals(strng)) return false;
        if ("false".equals(strng)) return false;
        return true;
    }

    public static boolean isTrue(String string) {
        return !isEmpty(string) && "1".equals(string);
    }

    public static boolean isFalse(String string) {
        return !isEmpty(string) && "0".equals(string);
    }

    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * 身份证
     *
     * @param num
     * @return
     */
    public static boolean verForm(String num) {
        String reg = "^\\d{15}$|^\\d{17}[0-9Xx]$";
        if (!num.matches(reg)) {
            return false;
        }
        return true;
    }

    public static String getAddressFromProCitArea(String province, String city, String area, String address) {
        StringBuilder sb = new StringBuilder();
        if (!isEmpty(province))
            sb.append(province);
        if (!isEmpty(city))
            sb.append(city);
        if (!isEmpty(area))
            sb.append(area);
        if (!isEmpty(address))
            sb.append(address);
        return sb.toString();
    }

    public static int getIndexBySelect(String s, String[] array) {
        if (YHStringUtils.isEmpty(s)) return 0;
        if (array == null || array.length == 0) return 0;
        for (int i = 0; i < array.length; i++) {
            if (s.equals(array.length)) return i;
        }
        return 0;
    }
}
