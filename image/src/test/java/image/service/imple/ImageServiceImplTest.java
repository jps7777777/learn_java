package image.service.imple;

import image.bean.PictureDO;
import image.dao.PictureDOMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ImageServiceImplTest {

    @Autowired
    private PictureDOMapper pictureDOMapper;

    @Test
    public void checkImageSaved() {
        PictureDO pictureDO = pictureDOMapper.selectByMD5("3B4F3DF2CAB06A3CFF2475C4BB26C52A3");
        if(pictureDO == null){
            System.out.println("没哟内容。。。");
        }
        System.out.println(pictureDO);
//        System.out.println(pictureDO.getPath());
    }
}