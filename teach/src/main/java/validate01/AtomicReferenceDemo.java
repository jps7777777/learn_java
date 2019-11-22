package validate01;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
@ToString
@AllArgsConstructor
class User{
    String userName;
    int age;
}

public class AtomicReferenceDemo {


    /**
     * 游戏中，使用CAS
     * 直播群聊，使用CAS
     *
     *
     */





    public static void main(String[] args) {

        User zs = new User("zs",22);
        User ls = new User("ls",25);

        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(zs);
        ls.setAge(33);
        atomicReference.compareAndSet(zs,ls);

//        System.out.println(atomicReference.compareAndSet(zs,ls)+"\t"+atomicReference.get().toString());
//        System.out.println(atomicReference.compareAndSet(zs,ls)+"\t"+atomicReference.get().toString());

//        AtomicInteger atomicInteger =new AtomicInteger(4);
//        atomicInteger.set(5);
//        System.out.println(atomicInteger.get());




    }

}
