package cn.jp.utils;

import cn.jp.exception.EnumException;
import cn.jp.exception.FinallyException;
import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {

    /**
     * model转DO，model转VO，等使用
     *
     * @param origin
     * @param target
     * @return
     */
    public static Object convertTo(Object origin, Object target) throws FinallyException {
        if (origin == null) {
            throw new FinallyException(EnumException.PARAMS_IS_EMPTY.setErrMsg("对象转换是传参不能为空：" +
                    "CommonUtils.convertTo(Object origin,Object target)"));
        }
        BeanUtils.copyProperties(origin, target);
        return target;
    }

    public static String getTimeStr(Date date) {
        if (date == null) {
            date = new Date();
        }
        return new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(date);
    }

    /**
     * 获取IP地址
     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，
     *  X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.length () > 15) {
            if (ip.indexOf (",") > 0) {
                ip = ip.substring (0, ip.indexOf (","));
            }
        }
        if("0:0:0:0:0:0:0:1".equals(ip)){
            ip = "127.0.0.1";
        }
        return ip;
    }


}
