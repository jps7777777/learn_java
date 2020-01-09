package annotation01;

import java.io.*;
import java.lang.reflect.Method;

//@SecondDefined()
public class ActionMain {



    public static void main(String[] args) {
        System.out.println("hello .");
        User user = new User();
        System.out.println("获取内容:"+user.getColumn()+",\t"+user.getTable());
    }

    /**
     *
     */
    public void method(){

    }

    public void seld(Method method){

        File file = new File("e:\\exe\\ext.txt");
        File file1 = new File("e:\\exe\\ext.txt");
        try {
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(file1);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(method.isAnnotationPresent(SecondDefined.class)){

        }

    }

    @Override
    public String toString(){
        return "12345";
    }

}
