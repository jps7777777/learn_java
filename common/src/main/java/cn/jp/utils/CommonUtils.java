package cn.jp.utils;

import cn.jp.exception.EnumException;
import cn.jp.exception.FinallyException;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {

    /**
     * model转DO，model转VO，等使用
     * @param origin
     * @param target
     * @return
     */
    public static Object convertTo(Object origin,Object target) throws FinallyException {
        if(origin == null){
            throw new FinallyException(EnumException.PARAMS_IS_EMPTY.setErrMsg("对象转换是传参不能为空：" +
                    "CommonUtils.convertTo(Object origin,Object target)"));
        }
        BeanUtils.copyProperties(origin,target);
        return target;
    }

    public static String getTimeStr(Date date){
        if(date == null){
            date = new Date();
        }
        return new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(date);
    }







}
