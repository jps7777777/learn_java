package validate01;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SpinLockDemo {

    // 原子引用线程
    AtomicReference<Thread> reference = new AtomicReference<>();

    /**
     *
     */
    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "\t come in .");
        while (!reference.compareAndSet(null,thread)){

        }
    }

    public void unMyLock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "\t invoke unMyLock() .");
        reference.compareAndSet(thread,null);
    }


    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(()->{
            spinLockDemo.myLock();
            try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
            spinLockDemo.unMyLock();
        },"one").start();

        new Thread(()->{
            spinLockDemo.myLock();
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            spinLockDemo.unMyLock();
        },"two").start();


    }

}
