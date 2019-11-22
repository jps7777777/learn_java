package validate01;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyData {
    volatile int number = 0;

    public void addTo60() {
        this.number = 60;
    }

    public void addPlusPlus(){
        number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    public void addMyAtomic(){
        atomicInteger.getAndIncrement();
    }

}

/**
 * 1 验证volatile的可见性
 * 1.1 加入number=0；没有添加volatile关键字进行修饰
 * 1.2 添加volatile，可以解决可见性
 *
 * 2 验证volatile不保证原子性
 *  2.1 原子性是什么？
 *  2.2 demo
 *  2.3 why
 *  2.4 如何解决原子性
 *      * 使用 synchronized
 *      * 使用juc下的atomicInteger
 */
public class VolatileDemo {

    public static void main(String[] args) {
        useAtomic();
    }

    private static void useAtomic() {
        MyData myData = new MyData();
        /**
         * 当i=10时，运行结果为10000.
         * 当i=20时，运行结果不为20000.结果始终小于20000。且机器线程越多，所得结果越小。
         * volatile不保证原子性
         */
        for(int i =1;i<=20;i++){
            new Thread(()->{
                for (int j = 1; j <= 1000; j++) {
                    myData.addPlusPlus();
                    myData.addMyAtomic();
                }
            },String.valueOf(i)).start();
        }

        // Thread.activeCount() 统计线程数量
        while (Thread.activeCount() > 2){
            // Thread.yield() 线程不执行，交出执行权限
            Thread.yield();
        }

        // main 结束时验证结果
        System.out.println(Thread.currentThread().getName()+"线程的 int 运行结果="+ myData.number);
        System.out.println(Thread.currentThread().getName()+"线程的 atomic 运行结果="+ myData.atomicInteger);
    }

    private static void setVolatileOfSee() {
        MyData myData = new MyData();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t coming in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName() + "\t update number value = ");
        }, "AAA").start();

        while (myData.number == 0) {
//            System.out.println("主线程没有操作...");
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        System.out.println(Thread.currentThread().getName() + "\t mission is over. number = " + myData.number);
    }

}
