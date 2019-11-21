package image.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class CheckImage {

    /**
     * 加密文件 2015年7月1日 上午12:06:37
     */
    public static String getMd5ByFile(File file) {
        String value = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
//        听说 如果文件大于2G，那么这种方式会出现异常。所以不推荐。
        // TODO
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

//    public static String fileMD5(String inputFile) throws IOException {
//        // 缓冲区大小（这个可以抽出一个参数）
//        int bufferSize = 256 * 1024;
//        FileInputStream fileInputStream = null;
//        DigestInputStream digestInputStream = null;
//        try {
//            // 拿到一个MD5转换器（同样，这里可以换成SHA1）
//            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
//            // 使用DigestInputStream
//            fileInputStream = new FileInputStream(inputFile);
//            digestInputStream = new DigestInputStream(fileInputStream, messageDigest);
//            // read的过程中进行MD5处理，直到读完文件
//            byte[] buffer = new byte[bufferSize];
//            while (digestInputStream.read(buffer) > 0) ;
//            // 获取最终的MessageDigest
//            messageDigest = digestInputStream.getMessageDigest();
//            // 拿到结果，也是字节数组，包含16个元素
//            byte[] resultByteArray = messageDigest.digest();
//            // 同样，把字节数组转换成字符串
//            return byteArrayToHex(resultByteArray);
//        } catch (Exception e) {
//            return null;
//        } finally {
//            try {
//                digestInputStream.close();
//            } catch (Exception e) {
//            }
//            try {
//                fileInputStream.close();
//            } catch (Exception e) {
//
//            }
//
//        }
//
//    }
//    public static String byteArrayToHex(byte[] byteArray) {
//        // 首先初始化一个字符数组，用来存放每个16进制字符
//        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
//        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
//        char[] resultCharArray =new char[byteArray.length * 2];
//        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
//        int index = 0;
//        for (byte b : byteArray) {
//            resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
//            resultCharArray[index++] = hexDigits[b& 0xf];
//        }
//        // 字符数组组合成字符串返回
//        return new String(resultCharArray);
//    }
}
