package image.action;

import image.exception.CommonException;
import image.exception.CommonResponse;
import image.exception.EnumException;
import image.exception.FinallyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseAction {

    @Autowired
    private RedisTemplate redisTemplate;

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


    protected String checkLogin(String token) throws FinallyException {
        if (token == null || token.equals("")) {
            throw new FinallyException(EnumException.USER_NOT_LOGIN);
        }
        Map<String,Object> user = redisTemplate.opsForHash().entries(token);
        if (user == null) {
            throw new FinallyException(EnumException.USER_NOT_LOGIN.setErrMsg("请重新登录"));
        }
        if (user.containsKey("otherLogin")) {
            throw new FinallyException(EnumException.USER_NOT_LOGIN.setErrMsg("帐号在其他端口登录"));
        }


    }




}
