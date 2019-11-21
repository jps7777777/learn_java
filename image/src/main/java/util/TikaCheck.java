package image.util;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class TikaCheck {


    private String checkFile(MultipartFile[] multipartFiles,int i) throws IOException {
        Tika tika = new Tika();
        // 标识损坏文件数
        int n = 0;
        // 标识损坏文件位置
        int[] faultFlag = new int[multipartFiles.length];
        // multipartFiles是上传的文件数组，根据自己的需求来获取真实名称，此处为我的业务场景。
        String[] temp = multipartFiles[i].getOriginalFilename().split("\\.");
        // 获取文件现用格式
        String fileType = temp[temp.length - 1];
        if ("TechDoc".equals(fileType)) {
            // 正常doc:application/x-tika-msoffice ，正常txt:text/plain
            if (!"application/x-tika-msoffice".equals(tika.detect(multipartFiles[i].getInputStream()))) {
                // 对损坏文件标识
                faultFlag[i] = 1;
                n++;
            }
        }
        return "";
    }

}
