package cn.jp.action;

import cn.jp.action.view.UserVO;
import cn.jp.exception.EnumException;
import cn.jp.exception.FinallyException;
import cn.jp.response.CommonResponse;
import cn.jp.service.UserService;
import cn.jp.service.model.UserModel;
import cn.jp.utils.CommonUtils;
import com.alibaba.druid.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

public class UserAction extends BaseAction {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "浏览器获取用户信息")
    @PostMapping("/user")
    public CommonResponse getUserInfoByBrowser(HttpServletRequest request){
        String session_token = request.getRequestedSessionId().toString();
//        UserModel userModel = userService.getUserInfo();
        return CommonResponse.create(session_token);
    }

    @ApiOperation(value = "浏览器获取用户信息")
    @PostMapping("/userInfo")
    public CommonResponse getUserInfoByApp(@RequestParam("token")String token) throws FinallyException {
        if(StringUtils.isEmpty(token)){
            throw new FinallyException(EnumException.PARAMS_IS_EMPTY);
        }
        UserModel userModel = userService.getUserInfoByToken(token);
        UserVO userVO = (UserVO) CommonUtils.convertTo(userModel,new UserVO());
        return CommonResponse.create(userVO);
    }


}
