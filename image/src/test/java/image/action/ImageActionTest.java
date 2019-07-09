package image.action;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.Assert.*;

public class ImageActionTest {

    @Autowired
    private RedisTemplate redisTemplate;

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