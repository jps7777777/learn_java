package cn.jp;

import cn.jp.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Hello world!
 */
@SpringBootApplication(scanBasePackages = "cn.jp")
@Controller
@MapperScan("cn.jp.dao")
@Api(value = "通用接口",tags = "系统通用接口")
public class App {

    public static Logger log = Logger.getLogger(App.class);

    /**
     * @return
     */
    @RequestMapping("/")
    @ApiOperation(value = "通用地址接口", notes = "通用接口地址，设置为微程序架构模式使用。")
    @ResponseBody
    public CommonResponse index() {

        return CommonResponse.create("hello common.");
    }

    public static void main(String[] args) {
        log.info("项目启动...");
        SpringApplication.run(App.class,args);
    }
}
