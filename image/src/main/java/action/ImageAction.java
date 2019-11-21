package image.action;


import com.alibaba.druid.util.StringUtils;
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
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Controller("image")
@RequestMapping("/image")
@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
public class ImageAction extends BaseAction {

    @Value("${max_image_size}")
    private long max_image_size;
    @Value("${upload_image_dir}")
    private String image_dir;
    @Value("${image_regex}")
    private String image_regex;

    @Autowired
    private ImageService imageService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 图片保存在本地服务器
     * 使用一般MySQL存储数据。
     *
     * @return 返回结果
     */
    @RequestMapping(value = "/upload", params = {"token"})
    @ResponseBody
    public CommonResponse uploadImage(MultipartFile file, String token, HttpServletRequest request) throws FinallyException {
        if (token == null || token.equals("")) {
            throw new FinallyException(EnumException.USER_NOT_LOGIN);
        }
        Map<String, Object> user = checkLogin(token);
        // 验证图片是否存储
        String md5Value = checkUpload(file);
        PictureModel pictureModel = imageService.checkImageSaved(md5Value);
        if (pictureModel != null) {
            return CommonResponse.create(convertToVO(pictureModel));
        }
        // 存储图片
        String fileName = executeUpload(file);
        pictureModel = setPictureModel(fileName, md5Value, "", "");
        return CommonResponse.create(convertToVO(pictureModel));
    }

    @RequestMapping(value = "/uploads")
    @ResponseBody()
    public CommonResponse uploadImages(MultipartFile[] files,String token, HttpServletRequest request) throws Exception {
        if (token == null || token.equals("")) {
            throw new FinallyException(EnumException.USER_NOT_LOGIN);
        }
        Map<String, Object> user = checkLogin(token);
        List images = new ArrayList();
        for (MultipartFile fl:files) {
            // 验证图片是否存储
            String md5Value = checkUpload(fl);
            PictureModel pictureModel = imageService.checkImageSaved(md5Value);
            if(pictureModel == null){
                String fileName = executeUpload(fl);
                pictureModel = setPictureModel(fileName, md5Value, "", "");
            }
            images.add(convertToVO(pictureModel));
        }
        return CommonResponse.create(images);
    }

    // 保存图片信息到数据库
    private PictureModel setPictureModel(String fileName, String md5Val, String path, String keywords) throws FinallyException {
        PictureModel model = new PictureModel();
        // 线上使用时，配置网站域名。
//        InetAddress address = InetAddress.getLocalHost();
//        String htp_url = "http://" + address.getHostAddress() + "/image/" + fileName;
        String htp_url = "http://www.image.com/";
        // 保存图片本地保存使用
        if (path != null) {
            htp_url += path;
            model.setPath(this.image_dir + path + fileName);
        } else {
            model.setPath(this.image_dir + fileName);
        }
        // 保存外网访问连接
        model.setUrl(htp_url + fileName);
        // 保存md5值
        model.setMd5(md5Val);
        // 保存图片分类
        model.setCategory(1);
        // 保存图片关键字
        model.setKeywords(keywords);
        return imageService.saveImageInfo(model);
    }


    /**
     * 提取上传方法为公共方法
     *
     * @param file 参数
     * @return 返回结果
     * @throws Exception 返回结果
     */
    private String checkUpload(MultipartFile file) throws FinallyException {
        if (file == null) {
            throw new FinallyException(EnumException.PARAMS_ERROR.setErrMsg("文件内容错误"));
        }
        // 获取md5值
        MessageDigest messageDigest = null;
        String md5Val = "";
        try {
            messageDigest = MessageDigest.getInstance("md5");
            DigestInputStream digestInputStream = new DigestInputStream(file.getInputStream(), messageDigest);
            byte[] bytes = new byte[256 * 1024];
            while (digestInputStream.read(bytes) > 0) ;
            messageDigest = digestInputStream.getMessageDigest();
            byte[] resBytes = messageDigest.digest();
            md5Val = byteArrayToHex(resBytes);
            // 验证图片是否存在
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            System.out.println("上传图片错误，添加记录");
        }
        return md5Val;
    }


    private static String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }

    /**
     * 检测登录状态
     *
     * @param token token
     * @return 返回内容
     * @throws FinallyException 返回错误内容
     */
    private Map<String, Object> checkLogin(String token) throws FinallyException {
        if (token == null || token.equals("")) {
            throw new FinallyException(EnumException.USER_NOT_LOGIN);
        }
        Map<String, Object> user = redisTemplate.opsForHash().entries(token);
        if (user == null) {
            throw new FinallyException(EnumException.USER_NOT_LOGIN.setErrMsg("请重新登录"));
        }
        if (user.containsKey("otherLogin")) {
            throw new FinallyException(EnumException.USER_NOT_LOGIN.setErrMsg("帐号在其他端口登录"));
        }
        return user;
    }

    /**
     * 提取上传方法为公共方法
     *
     * @param file 参数
     * @return 返回图片的保存路径
     * @throws Exception 返回结果
     */
    private String executeUpload(MultipartFile file) throws FinallyException {
        //文件后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        // 验证文件是否是图片
//        String check_suffix = suffix.substring(suffix.lastIndexOf("."));
        checkImageMethodOne(suffix);
        // 验证图片大小是否合理
        if (file.getSize() > max_image_size) {
            throw new FinallyException(EnumException.PARAMS_ERROR.setErrMsg("图片大于5M"));
        }
        //上传文件名
        String fileName = UUID.randomUUID() + suffix;
        //服务端保存的文件对象
        File saveFile = new File(image_dir + fileName);
        // 检测是否存在目录
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }
        //将上传的文件写入到服务器端文件内
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("错误记录");
        }
        return fileName;
    }

    // 第一种方式，简单判断，通过图片后缀判断
    private boolean checkImageMethodOne(String suffix) throws FinallyException {
        if (suffix.equals("") || suffix == null) {
            throw new FinallyException(EnumException.PARAMS_ERROR.setErrMsg("图片内容错误"));
        }
        // 验证图片后缀
        if ("jpg".equals(suffix) || "png".equals(suffix) || "JPG".equals(suffix)) {
            return true;
        }
        // 判断是否在匹配格式范围
        if (image_regex.indexOf(suffix) < 0) {
            throw new FinallyException(EnumException.PARAMS_ERROR.setErrMsg("图片格式错误"));
        }
        return true;
    }
    // 第二种判断方式，通过文件头，
//    private String checkImageMethodTwo(){
//        return "";
//    }
    // 第三种判断方式，获取图片宽高判断
//    private String checkImageMethodThree(){
//        return "";
//    }


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
    public ImageVO convertToVO(PictureModel pictureModel) {
        if (pictureModel == null) {
            return null;
        }
        ImageVO imageVO = new ImageVO();
        BeanUtils.copyProperties(pictureModel, imageVO);
        return imageVO;
    }

}
