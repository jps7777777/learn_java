package cn.jp.action;

import cn.jp.bean.User;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class UserInfo implements Runnable {

    private Socket socket;
    private User user;
    private PrintWriter pw;
    private InputStream br;
    private int flag = 0;
    private byte[] ch = new byte[256];
    private String msg = "";

    public UserInfo(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            br = socket.getInputStream();
            pw = new PrintWriter(socket.getOutputStream());
            Mserver.listPw.add(pw);// 添加到输出流集合中
            pw.write("input you info:name-age-room");
            pw.flush();
            br.read(ch);
            String info = new String(ch);
            String[] ttt = info.trim().split("-");
            // 金-24-3 中-25-3 云-26-3
            Mserver.log.info(ttt[2]);
            int roomId = Integer.valueOf(ttt[2]);
            if (Mserver.listMap.containsKey(roomId)) {
                Mserver.listMap.get(roomId).add(pw);
            } else {
                List<PrintWriter> pws = new ArrayList<>();
                pws.add(pw);
                Mserver.listMap.put(roomId, pws);
            }
            while ((flag = br.read(ch)) != -1) {
                String message = new String(ch);
                String[] sss = message.trim().split("_");
                if(sss.length == 4){
                    System.out.println(message);
                    System.out.println(sss[2]);
                    sendMsg(sss[0], Integer.valueOf(sss[1]), Integer.valueOf(sss[2]));
                }else{
                    pw.write("input content is error;");
                    pw.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg, int port, int roomId) {
        if (port > 0) {
            int getUser = 0;
            for (User user : Mserver.listUser) {
                if (port == user.getId()) {
                    try {
                        for (PrintWriter opw : Mserver.listPw) {
                            opw.println(msg);
                            opw.flush();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getUser = 1;
                }
            }
            if (getUser == 0) {
                pw.print(port + "的用户不存在。");
                pw.flush();
            }
        }
        if (roomId > 0) {
            int getRooms = 0;
            if (Mserver.listMap.containsKey(roomId)) {
                for (PrintWriter pwe : Mserver.listMap.get(roomId)) {
                    pwe.println(msg);
                    pwe.flush();
                }
            } else {
                pw.print(roomId + "聊天室不存在。");
                pw.flush();
            }
        }
    }

}


/**
 * 开启服务端监听
 * 1、循环监听
 * 2、添加收听内容到list
 * 3、
 */
public class Mserver {

    private static final int PORT = 4000;// 服务器端口
    private ServerSocket server; // 服务器
    public static ArrayList<PrintWriter> listPw; // 聊天记录集合
    public static Map<Integer, List<PrintWriter>> listMap;
    public static ArrayList<User> listUser = new ArrayList<User>();//定义用户集合

    public static Logger log = Logger.getLogger(Mserver.class);

    public Mserver() {
        try {
            listMap = new HashMap<>();
            listPw = new ArrayList<>();
            server = new ServerSocket(PORT);
            System.out.println("服务器开启...");
            while (true) {
                Socket client = server.accept();//接收客户端线程
                System.out.println("welcome to use this chat:" + client.toString());
                new Thread(new UserInfo(client)).start();// 开启新用户的线程
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new Mserver();
    }

}
