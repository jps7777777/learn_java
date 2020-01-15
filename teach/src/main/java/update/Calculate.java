package update;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Calculate extends JFrame {

    Calculate(){
        JPanel jp = new JPanel();
        JButton sub = new JButton("提交");
        JTextField input = new JTextField();
        JTextField output = new JTextField();
        output.setSize(250,60);
        input.setSize(250,140);

//        String str = jTextField.getText().trim();

        this.setSize(280,300);
        this.add("North",jp);
        this.add("Center",output);
        this.add("Center",input);
        this.add("South",sub);
        this.setTitle("服务器");
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });

    }



    /**
     *
     */
    public void method(){


    }

    public static void main(String[] args) {
        new Calculate();
//        calculate.method();
    }


}
