package cn.jp.action;

import cn.jp.App;
import cn.jp.response.CommonResponse;
import cn.jp.utils.CommonUtils;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/pv")
@Api(tags = "点击记录日志")
public class PvlogAction extends BaseAction{

    /**
     * 使用日志记录框架记录传参信息
     */
    @PostMapping("/log")
    @ResponseBody
    public CommonResponse rewriteLog(@RequestParam("mobile")String res, HttpServletRequest request){
        String ip = CommonUtils.getIpAddr(request);
        App.log.info("记录test日志参数："+res+",\t地址："+ip);
        return CommonResponse.create("ok");
    }

    /**
     * 使用日志记录框架记录传参信息
     */
    @PostMapping("/r")
    @ResponseBody
    public CommonResponse recordLog(@RequestBody Map<String,Object> map){
        String res = (String) map.get("mobile");
        String uid = UUID.randomUUID().toString();
        App.log.info("记录日志:"+uid+",\t参数："+res);
        return CommonResponse.create("ok");
    }



}
