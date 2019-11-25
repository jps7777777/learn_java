package oop;

public class User {

    public static void main(String[] args) {
        test();
    }

    public static void test(){
        Animal people = new People("jin",2,2);
        Animal tiger = new Tiger("mao",2,4,true);
        Animal yu = tiger;
        System.out.println(tiger.toString());
        System.out.println(yu.toString());
        System.out.println(tiger.hashCode());
        System.out.println(yu.hashCode());
        tiger.setAge(5);
        System.out.println(yu.getAge());

    }

    public static void test3(){
        Animal obj = new Tiger("bai",3,4,true);
//        Animal obj = new AsiaTiger("bai",3,4,true,"中国");
        System.out.println(obj.toString());
    }

    public static void test2(){
        Tiger tiger = new Tiger("bai",3,4,true);
        People jin = new People("jin",29,2);
        if(jin instanceof Animal){
            System.out.println("==");
            System.out.println(jin.getClass().getName());
        }else{
            System.out.println("!=");
        }
    }



}
