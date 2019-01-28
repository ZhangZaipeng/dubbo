package com.example.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符操作常用方法集 Created by zhouhao on 16-6-6.
 */
public class StringUtils {

  static final char CN_CHAR_START = '\u4e00';
  static final char CN_CHAR_END = '\u9fa5';
  /**
   * 编译后的正则表达式缓存
   */
  private static final Map<String, Pattern> PATTERN_CACHE = new ConcurrentHashMap<>();

  /**
   * 编译一个正则表达式，并且进行缓存,如果换成已存在则使用缓存
   *
   * @param regex 表达式
   * @return 编译后的Pattern
   */
  public static final Pattern compileRegex(String regex) {
    Pattern pattern = PATTERN_CACHE.get(regex);
    if (pattern == null) {
      pattern = Pattern.compile(regex);
      PATTERN_CACHE.put(regex, pattern);
    }
    return pattern;
  }

  /**
   * 将字符串的第一位转为小写
   *
   * @param str 需要转换的字符串
   * @return 转换后的字符串
   */
  public static String toLowerCaseFirstOne(String str) {
    if (Character.isLowerCase(str.charAt(0))) {
      return str;
    } else {
      char[] chars = str.toCharArray();
      chars[0] = Character.toLowerCase(chars[0]);
      return new String(chars);
    }
  }

  /**
   * 将字符串的第一位转为大写
   *
   * @param str 需要转换的字符串
   * @return 转换后的字符串
   */
  public static String toUpperCaseFirstOne(String str) {
    if (Character.isUpperCase(str.charAt(0))) {
      return str;
    } else {
      char[] chars = str.toCharArray();
      chars[0] = Character.toUpperCase(chars[0]);
      return new String(chars);
    }
  }

  /**
   * 下划线命名转为驼峰命名
   *
   * @param str 下划线命名格式
   * @return 驼峰命名格式
   */
  public static final String underScoreCase2CamelCase(String str) {
    if (!str.contains("_")) {
      return str;
    }
    StringBuilder sb = new StringBuilder();
    char[] chars = str.toCharArray();
    boolean hitUnderScore = false;
    sb.append(chars[0]);
    for (int i = 1; i < chars.length; i++) {
      char c = chars[i];
      if (c == '_') {
        hitUnderScore = true;
      } else {
        if (hitUnderScore) {
          sb.append(Character.toUpperCase(c));
          hitUnderScore = false;
        } else {
          sb.append(c);
        }
      }
    }
    return sb.toString();
  }

  /**
   * 驼峰命名法转为下划线命名
   *
   * @param str 驼峰命名格式
   * @return 下划线命名格式
   */
  public static final String camelCase2UnderScoreCase(String str) {
    StringBuilder sb = new StringBuilder();
    char[] chars = str.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      char c = chars[i];
      if (Character.isUpperCase(c)) {
        sb.append("_").append(Character.toLowerCase(c));
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  /**
   * 将异常栈信息转为字符串
   *
   * @param e 字符串
   * @return 异常栈
   */
  public static String throwable2String(Throwable e) {
    StringWriter writer = new StringWriter();
    e.printStackTrace(new PrintWriter(writer));
    return writer.toString();
  }

  /**
   * 字符串连接，将参数列表拼接为一个字符串
   *
   * @param more 追加
   * @return 返回拼接后的字符串
   */
  public static String concat(Object... more) {
    return concatSpiltWith("", more);
  }

  public static String concatSpiltWith(String split, Object... more) {
    StringBuilder buf = new StringBuilder();
    for (int i = 0; i < more.length; i++) {
      if (i != 0) {
        buf.append(split);
      }
      buf.append(more[i]);
    }
    return buf.toString();
  }

  /**
   * 将字符串转移为ASCII码
   *
   * @param str 字符串
   * @return 字符串ASCII码
   */
  public static String toASCII(String str) {
    StringBuffer strBuf = new StringBuffer();
    byte[] bGBK = str.getBytes();
    for (int i = 0; i < bGBK.length; i++) {
      strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
    }
    return strBuf.toString();
  }

  public static String toUnicode(String str) {
    StringBuffer strBuf = new StringBuffer();
    char[] chars = str.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      strBuf.append("\\u").append(Integer.toHexString(chars[i]));
    }
    return strBuf.toString();
  }

  public static String toUnicodeString(char[] chars) {
    StringBuffer strBuf = new StringBuffer();
    for (int i = 0; i < chars.length; i++) {
      strBuf.append("\\u").append(Integer.toHexString(chars[i]));
    }
    return strBuf.toString();
  }

  /**
   * 是否包含中文字符
   *
   * @param str 要判断的字符串
   * @return 是否包含中文字符
   */
  public static boolean containsChineseChar(String str) {
    char[] chars = str.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] >= CN_CHAR_START && chars[i] <= CN_CHAR_END) {
        return true;
      }
    }
    return false;
  }

  /**
   * 对象是否为无效值
   *
   * @param obj 要判断的对象
   * @return 是否为有效值（不为null 和 "" 字符串）
   */
  public static boolean isNullOrEmpty(Object obj) {
    return obj == null || "".equals(obj.toString());
  }

  /**
   * 参数是否是有效数字 （整数或者小数）
   *
   * @param obj 参数（对象将被调用string()转为字符串类型）
   * @return 是否是数字
   */
  public static boolean isNumber(Object obj) {
    if (obj instanceof Number) {
      return true;
    }
    return isInt(obj) || isDouble(obj);
  }

  public static String matcherFirst(String patternStr, String text) {
    Pattern pattern = compileRegex(patternStr);
    Matcher matcher = pattern.matcher(text);
    String group = null;
    if (matcher.find()) {
      group = matcher.group();
    }
    return group;
  }

  /**
   * 参数是否是有效整数
   *
   * @param obj 参数（对象将被调用string()转为字符串类型）
   * @return 是否是整数
   */
  public static boolean isInt(Object obj) {
    if (isNullOrEmpty(obj)) {
      return false;
    }
    if (obj instanceof Integer) {
      return true;
    }
    return obj.toString().matches("[-+]?\\d+");
  }

  /**
   * 字符串参数是否是double
   *
   * @param obj 参数（对象将被调用string()转为字符串类型）
   * @return 是否是double
   */
  public static boolean isDouble(Object obj) {
    if (isNullOrEmpty(obj)) {
      return false;
    }
    if (obj instanceof Double || obj instanceof Float) {
      return true;
    }
    return compileRegex("[-+]?\\d+\\.\\d+").matcher(obj.toString()).matches();
  }

  /**
   * 判断一个对象是否为boolean类型,包括字符串中的true和false
   *
   * @param obj 要判断的对象
   * @return 是否是一个boolean类型
   */
  public static boolean isBoolean(Object obj) {
    if (obj instanceof Boolean) {
      return true;
    }
    String strVal = String.valueOf(obj);
    return "true".equalsIgnoreCase(strVal) || "false".equalsIgnoreCase(strVal);
  }

  /**
   * 对象是否为true
   */
  public static boolean isTrue(Object obj) {
    return "true".equals(String.valueOf(obj));
  }

  /**
   * 判断一个数组里是否包含指定对象
   *
   * @param arr 对象数组
   * @param obj 要判断的对象
   * @return 是否包含
   */
  public static boolean contains(Object arr[], Object... obj) {
    if (arr == null || obj == null || arr.length == 0) {
      return false;
    }
    return Arrays.asList(arr).containsAll(Arrays.asList(obj));
  }

  /**
   * 将对象转为int值,如果对象无法进行转换,则使用默认值
   *
   * @param object 要转换的对象
   * @param defaultValue 默认值
   * @return 转换后的值
   */
  public static int toInt(Object object, int defaultValue) {
    if (object instanceof Number) {
      return ((Number) object).intValue();
    }
    if (isInt(object)) {
      return Integer.parseInt(object.toString());
    }
    if (isDouble(object)) {
      return (int) Double.parseDouble(object.toString());
    }
    return defaultValue;
  }

  /**
   * 将对象转为int值,如果对象不能转为,将返回0
   *
   * @param object 要转换的对象
   * @return 转换后的值
   */
  public static int toInt(Object object) {
    return toInt(object, 0);
  }

  /**
   * 将对象转为long类型,如果对象无法转换,将返回默认值
   *
   * @param object 要转换的对象
   * @param defaultValue 默认值
   * @return 转换后的值
   */
  public static long toLong(Object object, long defaultValue) {
    if (object instanceof Number) {
      return ((Number) object).longValue();
    }
    if (isInt(object)) {
      return Long.parseLong(object.toString());
    }
    if (isDouble(object)) {
      return (long) Double.parseDouble(object.toString());
    }
    return defaultValue;
  }

  /**
   * 将对象转为 long值,如果无法转换,则转为0
   *
   * @param object 要转换的对象
   * @return 转换后的值
   */
  public static long toLong(Object object) {
    return toLong(object, 0);
  }

  /**
   * 将对象转为Double,如果对象无法转换,将使用默认值
   *
   * @param object 要转换的对象
   * @param defaultValue 默认值
   * @return 转换后的值
   */
  public static double toDouble(Object object, double defaultValue) {
    if (object instanceof Number) {
      return ((Number) object).doubleValue();
    }
    if (isNumber(object)) {
      return Double.parseDouble(object.toString());
    }
    if (null == object) {
      return defaultValue;
    }
    return 0;
  }

  /**
   * 将对象转为Double,如果对象无法转换,将使用默认值0
   *
   * @param object 要转换的对象
   * @return 转换后的值
   */
  public static double toDouble(Object object) {
    return toDouble(object, 0);
  }

  /**
   * 分隔字符串,根据正则表达式分隔字符串,只分隔首个,剩下的的不进行分隔,如: 1,2,3,4 将分隔为 ['1','2,3,4']
   *
   * @param str 要分隔的字符串
   * @param regex 分隔表达式
   * @return 分隔后的数组
   */
  public static String[] splitFirst(String str, String regex) {
    return str.split(regex, 2);
  }

  /**
   * 将对象转为字符串,如果对象为null,则返回null,而不是"null"
   *
   * @param object 要转换的对象
   * @return 转换后的对象
   */
  public static String toString(Object object) {
    return toString(object, null);
  }

  /**
   * 将对象转为字符串,如果对象为null,则使用默认值
   *
   * @param object 要转换的对象
   * @param defaultValue 默认值
   * @return 转换后的字符串
   */
  public static String toString(Object object, String defaultValue) {
    if (object == null) {
      return defaultValue;
    }
    return String.valueOf(object);
  }

  /**
   * 将对象转为String后进行分割，如果为对象为空或者空字符,则返回null
   *
   * @param object 要分隔的对象
   * @param regex 分隔规则
   * @return 分隔后的对象
   */
  public static final String[] toStringAndSplit(Object object, String regex) {
    if (isNullOrEmpty(object)) {
      return null;
    }
    return String.valueOf(object).split(regex);
  }

  private static boolean isChinese(char c) {
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

  public static boolean isMessyCode(String strName) {
    Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
    Matcher m = p.matcher(strName);
    String after = m.replaceAll("");
    String temp = after.replaceAll("\\p{P}", "");
    char[] ch = temp.trim().toCharArray();
    float chLength = 0;
    float count = 0;
    for (int i = 0; i < ch.length; i++) {
      char c = ch[i];
      if (!Character.isLetterOrDigit(c)) {
        if (!isChinese(c)) {
          count = count + 1;
        }
        chLength++;
      }
    }
    float result = count / chLength;
    if (result > 0.4) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 消除html标签。
   */
  public static String escapeHtml(String src) {
    if (src == null) {
      return null;
    }
    return src.replaceAll("<[a-zA-Z/][.[^<]]*>", "");
  }

  /**
   * 消除html特殊标记。如&nbsp;
   */
  public static String escapeBlank(String src) {
    if (src == null) {
      return null;
    }
    return src.replaceAll("&[a-zA-Z]+;", "");
  }

  /**
   * 消除html标签和特殊标记
   */
  public static String escapeHtmlAndBlank(String src) {
    return escapeBlank(escapeHtml(src));
  }

  /**
   * 消除html标签和特殊标记，并把空字符串替换成 &NBSP;
   */
  public static String escapeHtmlAndRelaceNullToNBSP(Object value) {
    if (value == null) {
      return "&nbsp;";
    } else {
      return escapeHtmlAndBlank(value.toString());
    }
  }

  /**
   * 消除html标签和特殊标记，并把空字符串替换成 &NBSP;
   */
  public static String escapeHtmlAndRelaceNullToNBSP(String value) {
    if (isNullOrEmpty(value)) {
      return "&nbsp;";
    } else {
      return escapeHtmlAndBlank(value);
    }
  }

  public static String escapeHtmlAndBlank(Object value) {
    if (value == null) {
      return "";
    } else {
      return escapeHtmlAndBlank(value.toString());
    }
  }

  /**
   * 去掉脚本信息
   */
  public static String removeScript(String str) {
    if (!StringUtils.isNullOrEmpty(str)) {
      Pattern p = Pattern
          .compile("<script[^>]*>.*?</script>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
      Matcher matcher = p.matcher(str);
      return matcher.replaceAll("");
    }
    return null;
  }

  /**
   * 转义html特殊标签
   */
  public static String escapeSpecialLabel(String src) {
    if (isNullOrEmpty(src)) {
      return null;
    }
    return src.replaceAll("&", "&amp;").replaceAll("\\<", "&lt;").replaceAll("\\>", "&gt;")
        .replaceAll("\r\n",
            "<br/>").replaceAll("\n",
            "<br/>").replaceAll(" ",
            "&nbsp;").replaceAll("\"",
            "&quot;");
  }

  /**
   * 反转义html特殊标签
   *
   * @param processBracket 是否处理尖括号'<' 和'>'
   * @author xujianguo
   */
  public static String unEscapeSpecialLabel(String src, boolean processBracket) {
    if (isNullOrEmpty(src)) {
      return null;
    }
    src = src.replaceAll("(?i)&amp;", "&").replaceAll("(?i)<br\\s*/?\\s*>", "\r\n")
        .replaceAll("(?i)&nbsp;", " ").replaceAll("(?i)&quot;",
            "\"");
    if (processBracket) {
      src = src.replaceAll("(?i)&lt;", "\\<").replaceAll("(?i)&gt;", "\\>");
    }
    return src;
  }

  public static String fixStringLen(String str, char fillChar, int length) {
    int strLen = str.length();
    if (strLen < length) {
      char[] chars = new char[length];

      Arrays.fill(chars, 0, length - strLen, fillChar);
      System.arraycopy(str.toCharArray(), 0, chars, length - strLen, strLen);

      return new String(chars);
    }
    if (strLen > length) {
      str = str.substring(strLen - length);
    }

    return str;
  }

  public static String trim(String str) {
    if ((str == null) || (str.length() == 0)) {
      return null;
    }
    str = str.trim();
    if (str.length() == 0) {
      return null;
    }
    return str;
  }

  public static boolean isEmpty(String str) {
    if ((str == null) || (str.trim().equals(""))) {
      return true;
    }

    return false;
  }


  /**
   * 适应CJK（中日韩）字符集，部分中日韩的字是一样的
   */
  public static boolean isChinese2(String strName) {
    char[] ch = strName.toCharArray();
    for (int i = 0; i < ch.length; i++) {
      char c = ch[i];
      if (isChinese(c)) {
        return true;
      }
    }
    return false;
  }


  /**
   * 判断是否含有特殊字符
   *
   * @param text
   * @return boolean true,通过，false，没通过
   */
  public static boolean hasSpecialChar(String text) {
    if (null == text || "".equals(text))
      return false;
    if (text.replaceAll("[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() == 0) {
      // 如果不包含特殊字符
      return true;
    }
    return false;
  }

  public static BigDecimal safeBigdecimal(Object str){
    if(str==null||str.equals("")){
      return new BigDecimal(0);
    }else{
      return new BigDecimal(str.toString()).compareTo(new BigDecimal(0))==0?new BigDecimal(0):new BigDecimal(str.toString()).setScale
              (8,
              BigDecimal
              .ROUND_HALF_UP);
    }

  }
  public static boolean isNumeric(String str){
    Pattern pattern = Pattern.compile("[0-9]*");
    Matcher isNum = pattern.matcher(str);
    if( !isNum.matches() ){
      return false;
    }
    return true;
  }

  public static String reverse(String str){
    return new StringBuilder(str).reverse().toString();
  }
}
