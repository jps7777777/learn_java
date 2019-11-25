package thread;

import java.util.concurrent.locks.ReentrantLock;

public class LockUseDemo01 {


    /**
     *
     */
    public void method(){
        ReentrantLock lock = new ReentrantLock();

        lock.lock();

    }

    public static void main(String[] args) {
        LockUseDemo01 lockUseDemo01 = new LockUseDemo01();
        lockUseDemo01.method();
    }

}
