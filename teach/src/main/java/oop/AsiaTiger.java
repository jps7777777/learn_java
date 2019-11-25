package oop;

import java.lang.reflect.Field;

public class AsiaTiger extends Tiger {

    private String country;

    AsiaTiger(String name, int age, int feet, boolean fur) {
        super(name, age, feet, fur);
    }

    AsiaTiger(String name, int age, int feet, boolean fur,String country){
        super(name, age, feet, fur);
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        String returnString = "";
        // 所有属性
        Field[] allFields = this.getClass().getDeclaredFields();
        for (Field field:allFields){
            try {
                field.setAccessible(true);
                returnString += field.getName()+":" + field.get(this)+",";
            }  catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 超类返回仅仅返回上面一层超类（可以返回父类，不能返回爷爷类）
        Field[] superFields = this.getClass().getSuperclass().getDeclaredFields();
        for (Field field:superFields){
            try {
                field.setAccessible(true);
                returnString += field.getName()+":" + field.get(this)+",";
            }  catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return this.getClass().getName()+":"+returnString.substring(0,returnString.length()-1);
    }

}
