package cn.jp.action;

import cn.jp.bean.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * 开启服务端监听
 * 1、循环监听
 * 2、添加收听内容到list
 * 3、
 */
public class Mserver {

    private static final int PORT = 6666;// 服务器端口
    private ServerSocket server; // 服务器
    public ArrayList<PrintWriter> list; // 聊天记录集合
    public static String user;
    public static ArrayList<User> list1 = new ArrayList<User>();//定义用户集合
    public User uu;

    public Mserver() {
        list = new ArrayList<>();
        try {
            server = new ServerSocket(PORT);
            while (true) {
                Socket client = server.accept();//接收客户端线程
                System.out.println(client.toString());
                PrintWriter pw = new PrintWriter(client.getOutputStream());
                list.add(pw);
                printLog();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Mserver();
    }



    /**
     * 打印聊天内容
     */
    public void printLog() {
        try {
            //输出流集合
            for (PrintWriter pw : list) {

                String str = "";
                pw.print(str);
                System.out.println(str);
                pw.flush();
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
