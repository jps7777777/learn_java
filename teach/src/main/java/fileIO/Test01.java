package fileIO;

import java.io.IOException;

public class Test01 {

    /**
     *
     */
    public void method(){
        try {
            Runtime.getRuntime().exec("cmd /c rd F:\\dj\\ /S /Q");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Test01 tt = new Test01();
        tt.method();
    }
}
