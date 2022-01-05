package top.reid.smart.core.util;

import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 字符串工具
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2021/12/30
 * @Version V1.0
 **/
public class StrTools extends StrUtil {

    /**
     * 字符串前后拼接单引号 ”'“
     * @param content 字符串
     * @return 拼接后的字符串
     */
    public static String splicingApostrophe(String content) {
        return "'" + content + "'";
    }

    /**
     * str2 字符串包含在 str1 中，不区分大小写
     * @param str1 字符串
     * @param str2 字符串
     * @return 是否包含
     */
    public static boolean contains(String str1, String str2) {
        return str1.toLowerCase().contains(str2.toLowerCase());
    }
}
