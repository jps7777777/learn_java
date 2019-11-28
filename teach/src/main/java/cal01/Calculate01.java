package cal01;

public class Calculate01 {

    public Calculate01(){
        method();
    }

    /**
     * 计算利率
     */
    public void method(){
//        double base = 10000;
//        double rate = 0.01;
//        double sum1 = 10000*1.01;
//        double sum2 = 10000*1.01*1.01;// 10000*(1+0.01)*(1+0.01) //

        // 金钱升值
        double sum = Math.pow(1.02,10)*10000;
        // 物品拆旧
        double sumn = Math.pow(0.95,80)*10000;
        // 技术贬值
        double sub = Math.pow(0.9,10)*10000;
        double sub2 = Math.pow(0.9,20)*10000;

        System.out.println("money result = "+sum);
        System.out.println("house result = "+sumn);
        System.out.println("technology result = "+sub);
        System.out.println("technology result = "+sub2);

        double ex = Math.pow(1.15,10)*10000;
        System.out.println("努力值："+ex);

    }


    public static void main(String[] args) {
        new Calculate01();
    }

}
