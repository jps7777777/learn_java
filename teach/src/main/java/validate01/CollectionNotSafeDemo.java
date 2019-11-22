package validate01;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 集合类不安全问题
 */
public class CollectionNotSafeDemo {

    public static void main(String[] args) {
        // Constructs an empty list with an initial capacity of ten.
//        new ArrayList<Integer>().add(1);// 底层定义一个数组
        CollectionNotSafeDemo co = new CollectionNotSafeDemo();
        co.method();

    }

    /**
     * CopyOnWriteArrayList验证方法
     */
    public void method(){
        List<String> list = new CopyOnWriteArrayList<>();
        for(int i =0;i< 9;i++){
            new Thread(()->{
                String str = UUID.randomUUID().toString().substring(0,8);
                System.out.println(str);
                list.add(str);
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }

    /**
     * 集合并发异常示例
     */
    public void methodTwo(){
        List<String> list = new ArrayList<>();
        List<String> ls = Collections.synchronizedList(new ArrayList<>());

        for(int i =0;i< 3;i++){
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }

    /**
     *
     */
    public void methodOne(){
        List<String> list = Arrays.asList("a","b");
        list.forEach(System.out::println);
    }


}
