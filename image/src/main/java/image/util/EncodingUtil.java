package image.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static sun.security.krb5.internal.crypto.dk.DkCrypto.bytesToString;

public class EncodingUtil {

    /**
     * 计算文件的MD5码
     *
     * @param file
     * @return
     */
    public static String getMD5(File file) throws NoSuchAlgorithmException {
        FileInputStream fis = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length = -1;
            System.out.println("开始算");
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            System.out.println("算完了");
            return md.digest().toString();
        } catch (IOException ex) {
            Logger.getLogger(EncodingUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EncodingUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(EncodingUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    /**
     * 得到文件的SHA码,用于校验
     *
     * @param file
     * @return
     */
    public static String getSHA(File file) {
        FileInputStream fis = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            fis = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length = -1;
            System.out.println("开始算");
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            System.out.println("算完了");
            return md.digest().toString();
        } catch (IOException ex) {
            Logger.getLogger(EncodingUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EncodingUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(EncodingUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


}
