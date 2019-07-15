package image.action;


import image.service.ImageService;
import image.service.model.PictureModel;
import image.exception.CommonException;
import image.exception.CommonResponse;
import image.exception.EnumException;
import image.exception.FinallyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller("image")
@RequestMapping("/image")
@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
public class ImageAction extends BaseAction{

    @Value("${max_image_size}")
    private long max_image_size;
    @Value("${upload_image_dir}")
    private String image_dir;

    @Autowired
    private ImageService imageService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 图片保存在本地服务器
     * 使用一般MySQL存储数据。
     * @return 返回结果
     */
    @RequestMapping(value = "/upload",params = {"token"})
    @ResponseBody
    public CommonResponse uploadImage(MultipartFile file,String token, HttpServletRequest request) throws FinallyException {
        if (token == null || token.equals("")) {
            throw new FinallyException(EnumException.USER_NOT_LOGIN);
        }
        Map<String,Object> user = redisTemplate.opsForHash().entries(token);

        if (user == null) {
            throw new FinallyException(EnumException.USER_NOT_LOGIN.setErrMsg("请重新登录"));
        }
        if (user.containsKey("otherLogin")) {
            throw new FinallyException(EnumException.USER_NOT_LOGIN.setErrMsg("帐号在其他端口登录"));
        }
        // 文件检测
        if (file == null) {
            throw new FinallyException(EnumException.PARAMS_ERROR.setErrMsg("请选择图片"));
        }
        // 文件大小不大于5M，单位为字节单位：B
        if (file.getSize() > max_image_size) {
            throw new FinallyException(EnumException.PARAMS_ERROR.setErrMsg("图片大于5M"));
        }
        // 上传文件
        String originalFilename = file.getOriginalFilename();
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 文件上传后路径
        String fileName = UUID.randomUUID() + suffixName;
        File dest = new File(this.image_dir + fileName);
        try {
            file.transferTo(dest);
            InetAddress address = InetAddress.getLocalHost();
            String htp_url = "http://" + address.getHostAddress() + "/image/"+fileName;
            PictureModel pictureModel = new PictureModel();
            pictureModel.setCategory(1);
            pictureModel.setPath(this.image_dir+fileName);
            pictureModel.setUrl(htp_url);
            pictureModel.setKeywords("喵喵");
            PictureModel resModel = imageService.saveImageInfo(pictureModel);
            return CommonResponse.create(convertToVO(resModel));
        } catch (IOException e) {
            throw new FinallyException(EnumException.PARAMS_ERROR.setErrMsg("图片保存失败"));
        }
    }


    /**
     * 提取上传方法为公共方法
     * @param file 参数
     * @return 返回结果
     * @throws Exception 返回结果
     */
    private String executeUpload(MultipartFile file)throws Exception{
        //文件后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //上传文件名
        String fileName = UUID.randomUUID()+suffix;
        //服务端保存的文件对象
        File serverFile = new File(image_dir + fileName);
        // 检测是否存在目录
        if (!serverFile.getParentFile().exists()) {
            serverFile.getParentFile().mkdirs();
        }
        //将上传的文件写入到服务器端文件内
        file.transferTo(serverFile);
        return fileName;
    }












    /**
     * 图片保存在本地服务器
     * 使用一般MySQL存储数据。
     *
     * @return
     */
//    @RequestMapping(value = "/uploads",params = {"token"})
//    @ResponseBody
//    public CommonResponse uploadImages(List<MultipartFile> file, String token, HttpServletRequest request) throws FinallyException {
//        if (token == null || token.equals("")) {
//            throw new FinallyException(EnumException.USER_NOT_LOGIN);
//        }
//        Map<String,Object> user = redisTemplate.opsForHash().entries(token);
//
//    }




//    implements ApplicationListener<WebServerInitializedEvent>
//    private Map<String,String> callbackInfo = new HashMap<>();
//    private int serverPort;
//    private final static String ALLOW_SUFFIX = "jpg,png,gif,jpeg";//允许文件格式
//    private final static long ALLOW_SIZE = 2_000_000;//允许文件大小单位：byte
//    public int getServerPort() {
//        return serverPort;
//    }
//    public void setServerPort(int serverPort) {
//        this.serverPort = serverPort;
//    }
//
//    @RequestMapping("/upload")
//    @ResponseBody
//    public Map savePic(MultipartFile file, HttpServletRequest request) {
//        if(request.getParameter("token") == null){
//            callbackInfo.put("message","用户不存在");
//            callbackInfo.put("status","failure");
//            return callbackInfo;
//        }
//        // 文件检测
//        if(file.isEmpty()){
//            callbackInfo.put("message","文件不存在。");
//            callbackInfo.put("status","failure");
//            return callbackInfo;
//        }
//        // 文件大小不大于2M，单位为字节单位：B
//        if(file.getSize() > ALLOW_SIZE){
//            callbackInfo.put("message","文件大于2M。");
//            callbackInfo.put("status","failure");
//            return callbackInfo;
//        }
//        // 上传文件
//        String fileName = file.getOriginalFilename();
//        String suffixName = fileName.substring(fileName.lastIndexOf("."));
//        // 文件上传后路径
//        fileName = UUID.randomUUID() + suffixName;
//        String filePath = "D:\\work\\";
//        File dest = new File(filePath + fileName);
//        try {
//            file.transferTo(dest);
//            callbackInfo.put("status","success");
//            // 设置返回地址
//            InetAddress address = InetAddress.getLocalHost();
//            String htp_url =  "http://"+address.getHostAddress()+":"+serverPort+"/";
//            callbackInfo.put("path",htp_url + fileName);
//            // TODO HBase保存图片路径并返回编号。
//            callbackInfo.put("HBase_id","ecf553");
//        } catch (IOException e) {
////            e.printStackTrace();
//            callbackInfo.put("status","failure");
//            callbackInfo.put("message","没有上传成功。");
//        }
//        return callbackInfo;
//    }
//
//    //文件下载相关代码
//    @RequestMapping(value = "/downloadImage",method = RequestMethod.GET)
//    public String downloadImage(String imageName,HttpServletRequest request, HttpServletResponse response) {
//        //String fileName = "123.JPG";
//        logger.debug("the imageName is : "+imageName);
//        String fileUrl = uploadDir+imageName;
//        if (fileUrl != null) {
//            //当前是从该工程的WEB-INF//File//下获取文件(该目录可以在下面一行代码配置)然后下载到C:\\users\\downloads即本机的默认下载的目录
//           /* String realPath = request.getServletContext().getRealPath(
//                    "//WEB-INF//");*/
//            /*File file = new File(realPath, fileName);*/
//            File file = new File(fileUrl);
//            if (file.exists()) {
//                response.setContentType("application/force-download");// 设置强制下载不打开
//                response.addHeader("Content-Disposition",
//                        "attachment;fileName=" + imageName);// 设置文件名
//                byte[] buffer = new byte[1024];
//                FileInputStream fis = null;
//                BufferedInputStream bis = null;
//                try {
//                    fis = new FileInputStream(file);
//                    bis = new BufferedInputStream(fis);
//                    OutputStream os = response.getOutputStream();
//                    int i = bis.read(buffer);
//                    while (i != -1) {
//                        os.write(buffer, 0, i);
//                        i = bis.read(buffer);
//                    }
//                    System.out.println("success");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (bis != null) {
//                        try {
//                            bis.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (fis != null) {
//                        try {
//                            fis.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
//        return null;
//    }
//
//
//    /**
//     * 多文件上传
//     * @param files
//     * @return
//     * @throws RuntimeException
//     */
//    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
//    public JSONObject uploadFiles(@RequestParam(value = "file") MultipartFile[] files){
//        StringBuffer result = new StringBuffer();
//        try {
//            for (int i = 0; i < files.length; i++) {
//                if (files[i] != null) {
//                    //调用上传方法
//                    String fileName = executeUpload(files[i]);
//                    result.append(fileName+";");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            JsonUtil.getFailJsonObject("文件上传失败");
//        }
//        return JsonUtil.getSuccessJsonObject(result.toString());
//    }
//
//    /**
//     * 提取上传方法为公共方法
//     * @param file
//     * @return
//     * @throws Exception
//     */
//    private String executeUpload(MultipartFile file)throws Exception{
//        //文件后缀名
//        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//        //上传文件名
//        String fileName = UUID.randomUUID()+suffix;
//        //服务端保存的文件对象
//        File serverFile = new File(uploadDir + fileName);
//        // 检测是否存在目录
//        if (!serverFile.getParentFile().exists()) {
//            serverFile.getParentFile().mkdirs();
//        }
//        //将上传的文件写入到服务器端文件内
//        file.transferTo(serverFile);
//        return fileName;
//    }


//    @Override
//    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
//        this.serverPort = webServerInitializedEvent.getWebServer().getPort();
//    }


    public ImageVO convertToVO(PictureModel pictureModel){
        if(pictureModel == null){
            return null;
        }
        ImageVO imageVO = new ImageVO();
        BeanUtils.copyProperties(pictureModel,imageVO);
        return imageVO;
    }

}
