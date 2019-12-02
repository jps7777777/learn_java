package cn.jp.action;

import cn.jp.exception.CommonException;
import cn.jp.exception.EnumException;
import cn.jp.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseAction {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex){
        Map<String,Object> responseData = new HashMap<>();
        // 如果是程序定义的错误，做吸收处理
        if(ex instanceof CommonException){
            CommonException commonException = (CommonException) ex;
            responseData.put("errMsg",commonException.getErrMsg());
            responseData.put("errCode",commonException.getErrCode());
        }else {
            if(ex instanceof org.springframework.web.bind.MissingServletRequestParameterException){
                responseData.put("errCode", EnumException.PARAMS_ERROR.getErrCode());
                responseData.put("errMsg",ex.getMessage());// 开发阶段使用
            }else{
//                responseData.put("errCode", EnumException.SERVER_ERROR.getErrCode());
//                responseData.put("errMsg",EnumException.SERVER_ERROR.getErrMsg());// 正式使用
                responseData.put("errCode", 19201);
                responseData.put("errLine",ex.getLocalizedMessage());
                responseData.put("errMsgClass",ex.getClass());// 开发阶段使用
                responseData.put("errMsg",ex.getMessage());// 开发阶段使用
            }
            // TODO 系统添加错误信息
        }
        return CommonResponse.create(responseData,"failure");
    }



}
