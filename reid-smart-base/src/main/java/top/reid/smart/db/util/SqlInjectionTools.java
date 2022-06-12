package top.reid.smart.db.util;

import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import top.reid.smart.exception.ReidException;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * sql 注入处理工具类
 * </p>
 *
 * @Author REID
 * @Blog <a href="https://blog.csdn.net/qq_39035773">Blog</a>
 * @GitHub <a href="https://github.com/BeginnerA">GitHub</a>
 * @Data 2022/2/7
 * @Version V1.0
 **/
@Slf4j
public class SqlInjectionTools {
    /**
     * sign 用于表字典加签的盐值【SQL漏洞】
     */
    private final static String TABLE_DICT_SIGN_SALT = "20200501";
    private final static String XSS_STR = "'|and |exec |insert |select |delete |update |drop |count |chr |mid |master |truncate |char |declare |;|or |+";

    /**
     * 针对表字典进行额外的 sign 签名校验（增加安全机制）
     * @param dictCode 字典代码
     * @param sign 符号
     * @param request 请求
     */
    public static void checkDictTableSign(String dictCode, String sign, HttpServletRequest request) {
        // 表字典 SQL 注入漏洞,签名校验
        String accessToken = request.getHeader("X-Access-Token");
        String signStr = dictCode + SqlInjectionTools.TABLE_DICT_SIGN_SALT + accessToken;
        String javaSign = SecureUtil.md5(signStr);
        if (!javaSign.equals(sign)) {
            log.error("表字典，SQL注入漏洞签名校验失败 ：" + sign + "!=" + javaSign+ ",dictCode=" + dictCode);
            throw new ReidException("无权限访问！");
        }
        log.info(" 表字典，SQL注入漏洞签名校验成功！sign=" + sign + ",dictCode=" + dictCode);
    }


    /**
     * sql 注入过滤处理，遇到注入关键字抛异常
     * @param value 值
     */
    public static void filterContent(String value) {
        if (value == null || "".equals(value)) {
            return;
        }
        // 统一转为小写
        value = value.toLowerCase();
        String[] xssArr = XSS_STR.split("\\|");
        for (String s : xssArr) {
            if (value.contains(s)) {
                log.error("请注意，存在 SQL 注入关键词---> {}", s);
                log.error("请注意，值可能存在 SQL 注入风险!---> {}", value);
                throw new RuntimeException("请注意，值可能存在 SQL 注入风险!--->" + value);
            }
        }
    }

    /**
     * sql注入过滤处理，遇到注入关键字抛异常
     * @param values 值集合
     */
    public static void filterContent(String[] values) {
        String[] xssArr = XSS_STR.split("\\|");
        for (String value : values) {
            if (value == null || "".equals(value)) {
                return;
            }
            // 统一转为小写
            value = value.toLowerCase();
            for (String s : xssArr) {
                if (value.contains(s)) {
                    log.error("请注意，存在SQL注入关键词---> {}", s);
                    log.error("请注意，值可能存在SQL注入风险!---> {}", value);
                    throw new RuntimeException("请注意，值可能存在SQL注入风险!--->" + value);
                }
            }
        }
    }
}
