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
import java.net.ServerSocket;
import java.net.Socket;

public class Tserver extends JFrame implements ActionListener {

    //**************
    JLabel jLabel = new JLabel("服务器");
    JTextField jTextField = new JTextField(15);
    JTextArea jTextArea = new JTextArea();
    JPanel jPanel = new JPanel();

    // **
    private ServerSocket serverSocket = null;
    private Socket client = null;

    // ...
    private InputStream ins = null;
    private OutputStream ous = null;

    public Tserver() {
        this.setSize(280,300);
        jTextArea.setBackground(Color.gray);

        jPanel.add(jLabel);
        jPanel.add(jTextField);
        this.add("North",jPanel);
        this.add("Center",jTextArea);
        this.setTitle("server");
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
            serverSocket = new ServerSocket(4000);
            client = serverSocket.accept();
            jTextArea.append("服务器连接\n");
            ins = client.getInputStream();
            ous = client.getOutputStream();
        } catch (IOException e) {
            System.out.println("关闭连接");
        }

        // ------------------ 保持连接 显示客户端信息
        while (true){
            try {
                byte[] b = new byte[256];
                ins.read(b);
                String str = new String(b);
                jTextArea.append("客户端:"+str.trim());
                jTextArea.append("\n");
            } catch (IOException e) {
                System.out.println("服务器已关闭");
            }
        }
    }

    public static void main(String[] args) {
        new Tserver();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 监听输入事件
        try{
            String str = jTextField.getText().trim();
            byte[] bs = str.getBytes();
            ous.write(bs);
            jTextArea.append("我说："+str);
            jTextArea.append("\n");
        }catch (Exception e3){
            System.out.println("监听事件");
        }
    }
}
