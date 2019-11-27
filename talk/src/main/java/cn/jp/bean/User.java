package cn.jp.bean;

import lombok.Getter;
import lombok.Setter;

import java.net.Socket;

@Setter
@Getter
public class User {
    private String name;//用户姓名
    private String sex;//用户性别
    private Socket sock;//用户自己的socket
    public User(String name,String sex,Socket sock)
    {
        setName(name);
        setSex(sex);
        setSock(sock);
    }
}
