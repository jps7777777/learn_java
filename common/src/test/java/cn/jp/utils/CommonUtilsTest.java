package cn.jp.utils;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;


public class CommonUtilsTest {


    @Test
    public void getTimeStr(){
        Date date = new Date();
        String str = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(date);
        System.out.println(str);
    }

}