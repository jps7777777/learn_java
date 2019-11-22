package day03;

import base.User;

import javax.jws.soap.SOAPBinding;

/**
 * 传值验证
 */
public class TranferValue01 {


    /**
     *
     */
    public void methodStr(String str){
        str = "xxx";
    }

    /**
     *
     */
    public void methodString(String str){
        str = "新内容";
    }

    /**
     *
     */
    public void methodUser(User user){
        user.setUserName("ls");
    }

    /**
     *
     */
    public void methodAge(int age){
        age = 30;
    }

    public static void main(String[] args) {
        TranferValue01 tranferValue01 = new TranferValue01();

        // 基本类型的值传递
        int age = 20;
        tranferValue01.methodAge(age);
        System.out.println("age ==> "+ age);

        // 一般对象的引用传递
        User user = new User("zs");
        tranferValue01.methodUser(user);
        System.out.println("user name ==> " + user.getUserName());

        // String 的传值方式
        String str = "abed";
        tranferValue01.methodString(str);
        System.out.println("string value ==> "+str);

    }


}
