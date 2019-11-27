package thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class TempThread implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"--->正在执行");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 线程池的学习测试
 */
public class ThreadPoolsDemo01 {

    private volatile int num = 0;

    /**
     * 缓存队列及线程池的使用
     */
    public void method(){
        // 创建数组型的缓冲队列
        ArrayBlockingQueue<Runnable> bq = new ArrayBlockingQueue<Runnable>(10);
        // ThreadPoolExecutor:创建自定义线程池，池中保存的线程数为3，允许最大的线程数为6
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(3,6,50,TimeUnit.MILLISECONDS,bq);

        // 创建3个任务
        Runnable t1 = new TempThread();
        Runnable t2 = new TempThread();
        Runnable t3 = new TempThread();

        // 3个任务在分别在3个线程上执行
        tpe.execute(t1);
        tpe.execute(t2);
        tpe.execute(t3);

        // 关闭自定义线程池
        tpe.shutdown();
    }

    /**
     *
     */
    public void methodTest(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    num++;
                    System.out.println(Thread.currentThread().getName()+"-->正在执行，num结果="+num);
                }
            });
        }
    }

    /**
     * Executors.newSingleThreadExecutor();
     * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，
     * 保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
     */
    public void methodFour(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // 错误示例，得到的num全部为10
        // 主线程先执行完毕后，才执行了executorService
        // 新线程池中的运行结果没有改变
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+"正在执行。");
                    System.out.println("num 的值是："+num);
                }
            });
            num++;
            System.out.println("输出内容："+num);
        }
    }

    /**
     * Executors.newScheduledThreadPool(3);
     * 创建一个定长线程池，支持定时及周期性任务执行
     */
    public void methodThree(){
        ScheduledExecutorService  executorService = Executors.newScheduledThreadPool(3);
        // 延迟3秒执行
//        executorService.schedule(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("延迟3秒执行。");
//            }
//        },3,TimeUnit.SECONDS);

        // 延迟1秒后没3秒执行一次
//        executorService.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                // 线程池中的线程随机选择使用，那个线程空闲就用那个。
//                System.out.println(Thread.currentThread().getName()+"延迟3秒后每2秒执行一次");
//            }
//        },3,2,TimeUnit.SECONDS);

        // 错误示例，没有用到时间暂停功能。
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+"--->正在执行。");
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    /**
     * Executors.newFixedThreadPool
     * 正确执行方式
     */
    public void methodTwo(){
        ExecutorService executorService = Executors.newFixedThreadPool(7);
        final AtomicInteger atomic = new AtomicInteger(0);
        for (int i = 0; i < 20; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    atomic.compareAndSet(atomic.get(),atomic.get()+1);
                    System.out.println(Thread.currentThread().getName()+"-->正在执行。");
                    System.out.println("执行结果:"+atomic.get());
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 使用Executors.newFixedThreadPool(9);方式建立线程池。
     * 创建一个可重用固定个数的线程池，
     * 以共享的无界队列方式来运行这些线程
     */
    public void methodTwoErr(){
        ExecutorService executorService = Executors.newFixedThreadPool(9);
        for (int i = 0; i < 30; i++) {
            try {
                // 错误的编程方式。线程执行了之后才让线程休眠。
                // 灭有起到让线程等待的要求。
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(Thread.currentThread().getName()+"-->正在执行。");
                    }
                });
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 线程池理论可以无限大，
     * 当执行当前任务时上一个任务已经完成，
     * 会复用执行上一个任务的线程
     * 而不用每次新建线程
     */
    public void methodOne(){
        //创建一个可缓存线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            try{
                //sleep 1秒
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 可明显看到使用的是线程池里面以前的线程，没有创建新的线程
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    //打印正在执行的缓存线程信息
                    System.out.println(Thread.currentThread().getName()+"--->正在被执行。");
                }
            });
        }
    }


    public static void main(String[] args) {
        ThreadPoolsDemo01 threadPoolsDemo01 = new ThreadPoolsDemo01();
        threadPoolsDemo01.method();
    }

}
