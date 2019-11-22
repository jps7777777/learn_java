package validate01;

public class InstanceDemo {

    private static InstanceDemo instance = null;

    private InstanceDemo(){
        System.out.println("print once instance demo");
    }

    // DCL (double check lock 双端检锁机制)
    private static InstanceDemo getInstance(){
        if(instance == null){
            synchronized(InstanceDemo.class){
                if(instance == null){
                    instance = new InstanceDemo();
                }
            }
        }
        return instance;
    }

//    // synchronized 加锁
//    private static synchronized InstanceDemo getInstance(){
//        if(instance == null){
//            instance = new InstanceDemo();
//        }
//        return instance;
//    }

//    // DCL (double check lock 双端检锁机制)
//    private static InstanceDemo getInstance(){
//        if(instance == null){
//            instance = new InstanceDemo();
//        }
//        return instance;
//    }

    public static void main(String[] args) {
        // 单线程内容
//        System.out.println(InstanceDemo.getInstance() == InstanceDemo.getInstance());
//        System.out.println(InstanceDemo.getInstance() == InstanceDemo.getInstance());
//        System.out.println(InstanceDemo.getInstance() == InstanceDemo.getInstance());

        // 多线程下的情况
        for(int i =0;i< 10;i++){
            new Thread(()->{
                InstanceDemo.getInstance();
            },String.valueOf(i)).start();
        }
        // 多线程下被打印了多次。
        //print once instance demo
        //print once instance demo
        //print once instance demo
        /**
         * 跟机器线程数量有关。
         * 当机器有4个总线程，打印次数不超过4
         * 当机器有8个总线程，打印次数不超过8
         * 程序分成多个进程执行，但每个进程执行前都会去查询内存数据，
         * InstanceDemo已经定义过了，程序分配的10个进程在4个机器线程中排序执行
         * 当后面的进程执行的时候，前面的进程已经创建对象，所以没有在被定义。
         * 但开始的时候程序的多个进程被分配到四个线程中，每个线程并没有即时将结果写入内存并反馈给其他线程，
         * 所以多个线程就执行了多次InstanceDemo的实例化。
         */

        /**
         * 使用synchronized能保证程序的单例执行，但是会阻塞程序的后续代码执行，
         * synchronized的使用，会增加程序的开销。并发性性能下降
         *
         */
    }




}
