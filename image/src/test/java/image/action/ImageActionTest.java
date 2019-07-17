package image.action;

import image.exception.EnumException;
import image.exception.FinallyException;
import image.util.FileType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import static org.junit.Assert.*;

public class ImageActionTest {

    @Autowired
    private RedisTemplate redisTemplate;

    //设置token登录异常及销毁
//    public void destoryToken(){
//        stringRedisTemplate.opsForValue().set("test", "100",60*10,TimeUnit.SECONDS);//向redis里存入数据和设置缓存时间
//        redisTemplate.opsForValue().set("time_out","60秒销毁",60, TimeUnit.SECONDS);
//    }


    // 第一种方式，简单判断，通过图片后缀判断
    @Test
    public void checkImageMethodOne() {
        String suffix = "png";
        if (suffix.equals("") || suffix == null) {
//            throw new FinallyException(EnumException.PARAMS_ERROR.setErrMsg("图片内容错误"));
            System.out.println("没有内容1");
        }
        // 验证图片后缀
        if ("jpg".equals(suffix) || "png".equals(suffix) || "JPG".equals(suffix)) {
            System.out.println("没有内容2");
//            return true;
        }
        // 判断是否在匹配格式范围
        String image_regex = "png|jpg|jpeg|gif|bmp|git";

        System.out.println(image_regex.indexOf(suffix) + "---");
        if (image_regex.indexOf(suffix) > 0) {
            System.out.println("没有内容image_regex");
//            throw new FinallyException(EnumException.PARAMS_ERROR.setErrMsg("图片格式错误"));
        }
    }

    // 第二种判断方式，通过文件头，
//    @Test
//    public void checkImageMethodTwo() throws IOException {
//        String path = "D:\\images\\1114.jpg";
//        FileInputStream ios = new FileInputStream(new File(path));
//        byte[] bytes = new byte[32];
//        ios.read(bytes);
//        String sss = bytesToHexString(bytes);
//        ios.close();
//        boolean flag = false;
//        Map<String,String> map = FileType.IMAGE_TYPE_MAP;
//        Iterator<Entry<String, String>> entries = map.entrySet().iterator();
//        while (entries.hasNext()){
//            Entry<String, String> entry = entries.next();
//            String key = entry.getKey();
//            if(sss.indexOf(key) == 0){
//                flag = true;
//                break;
////                return;
//            }
//        }
//
//        if(flag){
//            System.out.println("图片正确可以返回");
//        }
//
////        return "";
//    }

    @Test
    public void checkImageMethodThree() {
        String in = "|png|jpg|jpeg|gif|bmp|mpg";
        System.out.println(in.indexOf("png"));

    }

    // 获得文件头部字符串
//    public String bytesToHexString(byte[] src) {
//        StringBuilder stringBuilder = new StringBuilder();
//        if (src == null || src.length <= 0) {
//            return null;
//        }
//        for (int i = 0; i < src.length; i++) {
//            int v = src[i] & 0xFF;
//            String hv = Integer.toHexString(v);
//            if (hv.length() < 2) {
//                stringBuilder.append(0);
//            }
//            stringBuilder.append(hv);
//        }
//        return stringBuilder.toString();
//    }


    @Test
    public void uploadImage() {

//        Map<String,Object> user = new HashMap<>();
//        user.put("userId","13");
//        user.put("phone","15285649686");
//        user.put("nickName","jing");
//        redisTemplate.opsForHash().put("new_user","user_id","12");
//        redisTemplate.opsForHash().put("new_user","phone","15285649685");
//        redisTemplate.opsForHash().put("new_user","nickName","jin");
//        redisTemplate.opsForValue().set(token,"60kill",60, TimeUnit.SECONDS);
//        redisTemplate.opsForHash().putAll("user_jing",user);
//        Map<String,Object> user_jin = redisTemplate.opsForHash().entries("user_jing");
//        System.out.println(user_jin.get("userId"));
//        System.out.println(user_jin.get("phone"));
//        System.out.println(user_jin.get("nickName"));
    }
}