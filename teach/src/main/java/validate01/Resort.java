package validate01;

public class Resort {

    int a = 0;
    boolean flag = false;

    public void method1(){
        a = 1;          //  1
        flag = true;    //  2
    }
    // 当单线程执行时，指令是顺序的
    // 但是多线程下（4万个线程时）
    // 多线程环境中线程交替执行，由于编译器优化重排的存在
    // 两个线程中的使用变量能否保证一致性无法保证，最终的执行结果无法预测
    public void method2(){
        if(flag){
            a = a + 5;
            System.out.println(" ***** can not forecast result ,a ="+a);
        }
    }

    public static void main(String[] args) {

    }

}
