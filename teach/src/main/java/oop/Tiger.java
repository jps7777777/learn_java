package oop;

import java.lang.reflect.Field;

public class Tiger extends Animal {

    private boolean hasFur = false;

    Tiger(String name, int age, int feet,boolean fur) {
        super(name, age, feet);
        this.hasFur = fur;
    }


//    @Override
//    public String toString() {
////        System.out.println(this.getClass().getDeclaredFields().length);
////        System.out.println(this.getClass().getFields());
////        System.out.println(super.getClass().getFields().length);
//        Field[]  fields = super.getClass().getFields();
//        for (Field field:fields){
//            System.out.println(field);
//        }
//
//        return this.getClass().getName()+":"+this.getName()+","+this.getAge()+",";
//    }


    public boolean isHasFur() {
        return hasFur;
    }

    public void setHasFur(boolean hasFur) {
        this.hasFur = hasFur;
    }

    @Override
    public String toString() {
        // 公有属性
        Field[]  fields = this.getClass().getFields();
        for (Field field:fields){
            System.out.println(field);
        }
        String returnString = "";
        // 所有属性
        Field[] allFields = this.getClass().getDeclaredFields();
        for (Field field:allFields){
            System.out.println(field.getName());
            try {
//                field.setAccessible(true);
                returnString += field.getName()+":" + field.get(this)+",";
            }  catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        Field[] superFields = this.getClass().getSuperclass().getDeclaredFields();
        for (Field field:superFields){
            System.out.println(field.getName());
            try {
                field.setAccessible(true);
                returnString += field.getName()+":" + field.get(this)+",";
            }  catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        System.out.println(returnString);
        return this.getClass().getName()+":"+returnString.substring(0,returnString.length()-1);
    }

}
