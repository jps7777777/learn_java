package cn.jp.action;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class Link implements Runnable{

    private Socket user;

    Link(Socket tmp){
        this.user = tmp;
    }

    @Override
    public void run() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(user.getInputStream()));
            String msg = "";
            while ((msg = br.readLine()) != null) {
                System.out.println("输入信息："+msg);
                if(msg.equals("game_over")){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


public class Mclient {

    private static int port = 6666;

    public static void main(String[] args) {
        Mclient mclient = new Mclient();
        try {
            Socket user = new Socket("127.0.0.1",port);
            Thread t1 = new Thread(new Link(user));
            t1.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
