package image.action;

import image.exception.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("file")
@RequestMapping("/file")
public class FileAction extends BaseAction {


    @Autowired
    private RedisTemplate redisTemplate;


    public CommonResponse upload(){


        return CommonResponse.create("");
    }









}
