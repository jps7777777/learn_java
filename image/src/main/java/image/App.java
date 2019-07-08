package image;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Hello world!
 *  使用一般数据库，单机服务器保存文件。
 *  MySQL
 *  47.105.151.214服务器
 */
@SpringBootApplication(scanBasePackages = "image")
@RestController
@MapperScan("image.dao")
public class App
{

    @RequestMapping("/")
    @ResponseBody
    public String hello(){
        return "这里是图片上传地址...";
    }

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class,args);
    }

//    @Override
//    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
//        this.serverPort = webServerInitializedEvent.getWebServer().getPort();
//    }
}
