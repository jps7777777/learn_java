package cn.jp.action;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 同一个聊天室的聊天信息记录
 */
public class Jserver extends JFrame {

    private static List<Socket> list = null;// 客户信息列表
    private static List<PrintWriter> printList = null;// 聊天记录

    //**************
    JLabel jLabel = new JLabel("服务器显示信息：");
    JTextArea jTextArea = new JTextArea();
    JPanel jPanel = new JPanel();

    // **
    private ServerSocket serverSocket = null;


    // ...
    private InputStream ins = null;
    private OutputStream ous = null;

    public Jserver() {

        this.setSize(280, 300);
        jTextArea.setBackground(Color.gray);

        jPanel.add(jLabel);
        this.add("North", jPanel);
        this.add("Center", jTextArea);
        this.setTitle("server");
        this.setLocationRelativeTo(null);
        this.setVisible(true);


        try {
            list = new ArrayList<>();
            printList = new ArrayList<>();
            serverSocket = new ServerSocket(4000);
            while (true) {
                Socket client = serverSocket.accept();
                list.add(client);// 保存用户信息
                PrintWriter pw = new PrintWriter(client.getOutputStream());// 添加新的用户输出流信息到列表
                printList.add(pw);// 保存聊天记录

                // 为客户端开启新线程
                Thread thread = new Thread(new Chat(client));
                thread.start();// 添加用户线程，服务器获取用户的聊天信息
            }
        } catch (IOException e) {
            System.out.println("关闭连接");
        }

        // ------------------ 保持连接 显示客户端信息


    }

    public static void main(String[] args) {
        new Jserver();
    }

    class Chat implements Runnable {

        private Socket socket;
        private int flag;
        private byte[] bys = new byte[256];
        private String msg = "";
        private InputStream ins;
        private BufferedReader br;

        public Chat(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                System.out.println("\n");
                jTextArea.append(socket.getPort() + "：进入聊天室。");
                jTextArea.append("\n");
                sendMessage("welcome :" + socket.getPort());
//                br = socket.getInputStream();
//                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ins = socket.getInputStream();
                while ((flag = ins.read(bys)) != -1) {
                    String ong = new String(bys);
                    if(!ong.equals("")  || ong != null){
                        sendMessage(socket.getPort() + " :" + ong);
                    }
                }
//                while (true) {
////                    while((msg = br.readLine()) != null){
////                        sendMessage(socket.getPort()+" :"+ msg );
////                    }
////                    msg = br.readLine();
////                    if(msg.equals("break")){
////                        break;
////                    }
////                while ((flag = br.read(msg)) != -1) {
////                    System.out.println(msg); // 客户端读取信息流
//                    if(!msg .equals("") || msg != null){
//                        sendMessage(socket.getPort()+" :"+ msg + "\t没有内容的输出。");// 输出信息到用户端
//                    }
//                    sendMessage(socket.getPort()+" : where are you ." );
//                    TimeUnit.SECONDS.sleep(3);
//                }
            } catch (IOException e) {
                //|InterruptedException
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message)//群发消息方法
    {
        try {
            //输出流集合
            for (PrintWriter pw : printList) {
                pw.println(message);
                pw.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
