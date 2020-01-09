package cn.jp.action;

import cn.jp.exception.EnumException;
import cn.jp.exception.FinallyException;
import cn.jp.response.CommonResponse;
import cn.jp.utils.CommonUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RequestMapping("/security")
@RestController
public class VerifyAction extends BaseAction {

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/send")
    @ApiOperation(value = "发送验证码")
    public CommonResponse getPhoneVerify(@RequestParam("phone")String phone) throws FinallyException {
        if(!CommonUtils.isPhoneNum(phone)){
            throw new FinallyException(EnumException.PARAMS_ERROR.setErrMsg("电话号码输入错误"));
        }
//        int code = new Random().nextInt(999999);
//        if(code < 100000){
//            code += 100000;
//        }
        int code = 666666;
        redisTemplate.opsForHash().put(phone,"verify",code);
        return CommonResponse.create("验证码已发送");
    }


}
