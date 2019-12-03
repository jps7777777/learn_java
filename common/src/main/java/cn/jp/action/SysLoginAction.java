package cn.jp.action;

import cn.jp.action.view.UserVO;
import cn.jp.exception.EnumException;
import cn.jp.exception.FinallyException;
import cn.jp.response.CommonResponse;
import cn.jp.service.UserService;
import cn.jp.service.model.UserModel;
import cn.jp.utils.CommonUtils;
import cn.jp.utils.EncryptUtil;
import com.alibaba.druid.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller("systemLogin")
@Api(tags = "登录注册接口")
@RequestMapping("/sys_lg")
public class SysLoginAction extends BaseAction{

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/qr_login")
    @ApiOperation(value = "扫描二维码登录")
    @ResponseBody
    public CommonResponse qrcodeLogin(){
        return CommonResponse.create("url:"+"file_url->"+ CommonUtils.getTimeStr(null));
    }


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

    @PostMapping("/reg")
    @ApiOperation(value = "注册接口")
    @ResponseBody
    public CommonResponse insert(@RequestParam("mobile")String mobile,
                                 @RequestParam("pwd")String password) throws FinallyException {
//        String mobile = reqMap.get("mobile").toString();
//        String password = reqMap.get("password").toString();
        UserModel userModel = new UserModel(mobile, EncryptUtil.entryptByMd5(password));
        userModel.setAccount(mobile);
        userService.saveUser(userModel);
        return CommonResponse.create("注册成功");
    }

    @PostMapping("/log")
    @ApiOperation(value = "注册接口")
    @ResponseBody
    public CommonResponse login(@RequestParam("mobile")String mobile,
                                @RequestParam("pwd")String password) throws FinallyException {
        UserModel userModel = new UserModel(mobile, EncryptUtil.entryptByMd5(password));
        userService.login(userModel);
        return CommonResponse.create("登录成功");
    }


}
