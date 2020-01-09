package cn.jp.action;

import cn.jp.exception.EnumException;
import cn.jp.exception.FinallyException;
import cn.jp.response.CommonResponse;
import cn.jp.service.UserService;
import cn.jp.service.model.UserModel;
import cn.jp.service.model.UserPasswordModel;
import cn.jp.utils.CommonUtils;
import cn.jp.utils.EncryptUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("systemLogin")
@Api(tags = "登录注册接口")
@RequestMapping("/sys_lg")
public class SysLoginAction extends BaseAction{

    private UserService userService;


    @Autowired
    public SysLoginAction(UserService userService){
        this.userService = userService;
    }

    private RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 扫码登录返回结果
     * @return 返回信息
     */
    @PostMapping("/qr_login")
    @ApiOperation(value = "扫描二维码登录")
    @ResponseBody
    public CommonResponse qrcodeLogin(){
        return CommonResponse.create("url:"+"file_url->"+ CommonUtils.getTimeStr(null));
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

    @PostMapping("/reset")
    @ApiOperation(value = "修改密码接口")
    @ResponseBody
    public CommonResponse resetPwd(@RequestParam("mobile")String mobile,
                                   @RequestParam("verify_code")int code,
                                   @RequestParam("pwd")String password) throws FinallyException {
        if(!isVerifyCode(mobile,code)){
            throw new FinallyException(EnumException.PARAMS_ERROR.setErrMsg("验证码不正确"));
        }
        UserModel userModel = new UserModel();
        userModel.setMobile(mobile);
        UserPasswordModel userPasswordModel = new UserPasswordModel();
        userPasswordModel.setPassword(password);
        userModel.setPasswordModel(userPasswordModel);
        userService.updatePwd(userModel);
        return CommonResponse.create("修改成功");
    }

    /**
     * 修改密码
     * @param phone 手机号码
     * @param code 验证码
     * @return boolean
     */
    protected boolean isVerifyCode(String phone,int code){
        int oldCode = (int) redisTemplate.opsForHash().get(phone,"verify");
        if(oldCode == code){
            return true;
        }
        return false;
    }


}
