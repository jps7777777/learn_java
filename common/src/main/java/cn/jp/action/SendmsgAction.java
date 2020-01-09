package cn.jp.action;

import cn.jp.config.SendmsgConfig;
import cn.jp.exception.EnumException;
import cn.jp.exception.FinallyException;
import cn.jp.response.CommonResponse;
import com.alibaba.druid.util.StringUtils;
import com.aliyuncs.exceptions.ClientException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

@Controller
@Api(value = "发送短信验证码")
@RequestMapping("/send")
public class SendmsgAction extends BaseAction {

    private RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/code")
    @ApiOperation(value = "发送验证码")
    @ResponseBody
    public CommonResponse sendMsg(@RequestParam(name = "mobile")String phone,
                                  @RequestParam(name = "type")int status) throws FinallyException {
        if(StringUtils.isEmpty(phone)){
            throw new FinallyException(EnumException.PARAMS_ERROR.setErrMsg("电话号码不能为空"));
        }
        if(status == 0){
            throw new FinallyException(EnumException.PARAMS_ERROR.setErrMsg("请选择验证类型"));
        }
        // 用户注册使用验证码
        if(status == 1010){

        }
        // 用户已注册，使用验证码，查询电话号码是否注册
        if(status == 1020){

        }
        int code = new Random().nextInt(899999);
        redisTemplate.opsForValue().set("id_"+phone,code+100000+"0");


        // 使用发送短信
//        try {
//            SendmsgConfig.sendSms(phone,code+100000);
//        } catch (ClientException e) {
//            throw new FinallyException(EnumException.DATA_ERROR.setErrMsg(e.getErrMsg()));
//        }
        return CommonResponse.create("发送成功");
    }


}
