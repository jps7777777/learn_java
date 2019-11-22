package day03;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TryValidate01 {

    String path = "D:\\demo_use\\files\\";

    /**
     *
     * 使用m.find()方法时，第一次使用有效，第二次已经过时。需要重新对比。
     *  查看源码可以看出，比较对象已经修改。
     *
     */
    public String method(){
        BufferedReader bf = null;
        BufferedWriter bw = null;
        try{
            bf = new BufferedReader(new FileReader(new File(path+"access_log.txt")));
            File out = new File(path + "\\log");
            if(!out.exists()){
                out.mkdir();
            }
            bw = new BufferedWriter(new FileWriter(out + "\\203.txt"));
            int flag = 0;
            String str = "";
            Pattern p = Pattern.compile("Chrome");
            while ((str = bf.readLine()) != null){
                Matcher m = p.matcher(str);
                System.out.println("匹配结果matches="+m.matches());
                boolean rs = m.find();
                System.out.println("匹配结果find="+m.find());
                System.out.println("匹配结果find2="+m.find());
                if(rs){
                    System.out.println("找到内容");
                    bw.write(str+"\n");
                }
            }
            bw.close();
            bf.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return "ok";
    }


    /**
     * FileReader用例
     */
    public String methodThree(){
        try(FileReader fio = new FileReader(new File(path+"test.txt"))) {
            File out = new File(path+"log\\");
//            if(!out.exists()){
//                out.mkdir();
//            }
//            FileWriter fw = new FileWriter(out+"\\201.txt",true);
            char[] buf = new char[128];
            int str = 0;
            // 直接返回单个字符
            while ((str = fio.read())!= -1){
                System.out.println((char)str);
            }
            // 返回结果存储在char[]中
//            while ((str = fio.read(buf)) != -1){
//                System.out.println(buf);
//            }
        }  catch (IOException e) {
            e.printStackTrace();
        }

        return "OK";
    }


    /**
     * 简单的文件输入输出
     */
    public String methodTwo(){
        String path = "D:\\demo_use\\files\\";
        try(FileInputStream fio = new FileInputStream(new File(path+"test.txt"))) {
            File out = new File(path+"mk\\");
            if(!out.exists()){
                out.mkdir();
            }
            FileOutputStream fos = new FileOutputStream(out+"\\test.txt");
            byte[] bts = new byte[1024];
            int flag = 0;
            while ((flag = fio.read(bts)) != -1){
                fos.write(bts);
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("没有出现问题");
        }
        return "搞定";
    }

    /**
     * finally 执行在try的return之前。
     */
    public String methodOne(){
        int i = 4;
        try{
            System.out.println("output = "+i);
            return "over";
        }finally {
            System.out.println("finally result is here.");
        }
    }


    public static void main(String[] args) {
        TryValidate01 tryValidate01 = new TryValidate01();
        System.out.println("method's return result = "+tryValidate01.method());
    }

}
