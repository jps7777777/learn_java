package day03;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexDemo01 {

    String parent = "109.94.112.162 - - [17/Aug/2018:17:31:15 +0800] \"GET / HTTP/1.1\" 200 3160 \"-\"" +
            " \"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36\"";
    String child = "Mozilla/5.0";

    public String method(){
        Pattern p = Pattern.compile(child);
        Matcher m = p.matcher(parent);
        System.out.println(m.toString());
        boolean rs = m.find();
        boolean rs1 = m.matches();
        boolean rs2 = m.find();

        if(rs){
            System.out.println("找到了内容");
        }else {
            System.out.println("没有找到内容");
        }
        if(rs1){
            System.out.println("找到了内容");
        }else {
            System.out.println("没有找到内容");
        }
        if(rs2){
            System.out.println("找到了内容");
        }else {
            System.out.println("没有找到内容");
        }
        return "ok";
    }

    public static void main(String[] args) {
        RegexDemo01 regexDemo01 = new RegexDemo01();
        System.out.println(regexDemo01.method());
    }

}
