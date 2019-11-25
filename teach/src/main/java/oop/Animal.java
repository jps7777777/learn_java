package oop;

import java.lang.reflect.Field;

public class Animal {

    private String name;
    private int age;
    private int feet;

    Animal(String name, int age, int feet) {
        this.name = name;
        this.age = age;
        this.feet = feet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getFeet() {
        return feet;
    }

    public void setFeet(int feet) {
        this.feet = feet;
    }


    @Override
    public String toString() {
        // 公有属性
        Field[] fields = this.getClass().getFields();
        for (Field field : fields) {
            System.out.println(field);
        }
        String returnString = "";
        // 获取所有属性
        Field[] allFields = this.getClass().getDeclaredFields();
        for (Field field : allFields) {
            System.out.println("打印属性名称：" + field.getName());
            try {
                // 设置对象的访问权限，保证对private的属性的访问,
                // 没使用上
                //  field.setAccessible(true);
                returnString += field.getName() + ":" + field.get(this) + ",";
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        System.out.println(returnString);
        return this.getClass().getName() + ":" + returnString.substring(0, returnString.length() - 1);
    }


    /**
     * 根据属性名获取属性值
     *
     * @param fieldName 字段名
     * @param object    对象
     * @return 返回结果
     */
    private String getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            return (String) field.get(object);
        } catch (Exception e) {
            return null;
        }
    }


}
