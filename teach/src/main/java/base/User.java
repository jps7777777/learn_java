package base;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 一些常用到的对象
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String userName;
    private int age;

    public User(String name){
        this.userName = name;
    }

}
