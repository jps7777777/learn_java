package validate01;


import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {

    public static void main(String[] args) {
        // 与sync的区别，如果比较成功就修改，
        AtomicInteger atomicInteger = new AtomicInteger(5);
        atomicInteger.getAndIncrement();
        System.out.println(atomicInteger.compareAndSet(6, 52) + "\t result = " + atomicInteger.get());
        atomicInteger.getAndIncrement();
        System.out.println(atomicInteger.compareAndSet(53, 67) + "\t result = " + atomicInteger.get());
        atomicInteger.getAndIncrement();
        System.out.println( "\t result = " + atomicInteger.get());
    }





}
