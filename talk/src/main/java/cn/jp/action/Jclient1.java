package cn.jp.action;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Jclient1 extends JFrame implements ActionListener {

    //**************
    JLabel jLabel = new JLabel("客户端1");
    JTextField jTextField = new JTextField(15);
    JTextArea jTextArea = new JTextArea();
    JPanel jPanel = new JPanel();

    // **
    private Socket client = null;

    // ...
    private InputStream ins = null;
    private OutputStream ous = null;

    public Jclient1() {
        this.setSize(280,300);
        jTextArea.setBackground(Color.gray);

        jPanel.add(jLabel);
        jPanel.add(jTextField);
        this.add("North",jPanel);
        this.add("Center",jTextArea);
        this.setTitle("client1");
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        jTextField.addActionListener(this);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        try {
            client = new Socket("127.0.0.1",4000);
            jTextArea.append("服务器已连接...\n");
            ins = client.getInputStream();// 读取信息流
            ous = client.getOutputStream();// 输出信息流
        } catch (IOException e) {
            System.out.println("关闭连接");
        }

        // ------------------ 保持连接 接收信息
        while (true){
            try {
                byte[] b = new byte[256];
                ins.read(b);
                String str = new String(b);
                jTextArea.append(str.trim());
                jTextArea.append("\n");
            } catch (IOException e) {
                System.out.println("服务器已关闭");
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        new Jclient1();
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        // 监听事件，发送信息
        try {
            String str = jTextField.getText().trim();
            byte[] b = str.getBytes();
            ous.write(b);
            jTextArea.append("我说："+str);
            jTextArea.append("\n");
        } catch (IOException e2) {
            System.out.println("客户端已关闭");
        }
    }
}
