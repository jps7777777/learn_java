package cn.jp;

import cn.jp.response.CommonResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Hello world!
 *
 */
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@Controller
@ResponseBody
public class App 
{

    @RequestMapping("/")
    public CommonResponse index(){
        return CommonResponse.create("i am here.");
    }

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class,args);
    }
}
