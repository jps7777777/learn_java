package annotation01;


import java.lang.annotation.*;

/*
 * @Target 表示这个注解能放在什么位置上。
 *      只能放在类上，或者可以放在方法上，还可以放在属性上
 *  ElementType.TYPE,// 能修饰类、接口或枚举类型
 *  ElementType.FIELD, // 能修饰成员变量
 *  ElementType.METHOD,// 能修饰方法
 *  ElementType.PARAMETER,// 能修饰参数
 *  ElementType.CONSTRUCTOR,// 能修饰构造器
 *  ElementType.LOCAL_VARIABLE,// 能修饰局部变量
 *  ElementType.ANNOTATION_TYPE,// 能修饰注解
 *  ElementType.PACKAGE,// 能修饰包
 *  ElementType.TYPE_PARAMETER,// 类的参数
 *  ElementType.TYPE_USE
 */
@Target({
        ElementType.TYPE,// 能修饰类、接口或枚举类型
        ElementType.FIELD, // 能修饰成员变量
        ElementType.METHOD,// 能修饰方法
        ElementType.PARAMETER,// 能修饰参数
        ElementType.CONSTRUCTOR,// 能修饰构造器
        ElementType.LOCAL_VARIABLE,// 能修饰局部变量
        ElementType.ANNOTATION_TYPE,// 能修饰注解
        ElementType.PACKAGE,// 能修饰包
        ElementType.TYPE_PARAMETER,// 类的参数
        ElementType.TYPE_USE
})
/*
    设置注释生命周期
    RetentionPolicy.CLASS // 注解在java文件编程成.class文件后，依然存在，但是运行起来后就没了
    RetentionPolicy.RUNTIME // 注解在运行起来之后依然存在，程序可以通过反射获取这些信息
    RetentionPolicy.SOURCE // 注解只在源代码中存在，编译成class之后，就没了。 @Override 就是这种注解
    @Retention默认使用CLASS设置，即当没有显式指定@Retention的时候，就会是这种类型。
 */
@Retention(RetentionPolicy.RUNTIME)
/*
    @Inherited 表示该注解具有继承性
    如果一个超类被 @Inherited 注解过的注解进行注解的话，
    那么如果它的子类没有被任何注解应用的话，那么这个子类就继承了超类的注解。
*/
@Inherited
// 元注解肯定是和文档有关。它的作用是能够将注解中的元素包含到 Javadoc 中去
@Documented
// 使用 @interface 关键字定义注解
public @interface FirstDefined {

    /*
        成员变量
     */

    String desc();
    String author();
    int age() default 18;
}
