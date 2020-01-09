package cn.jp.config;

import com.alibaba.druid.util.StringUtils;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class SendmsgConfig {

        /**
         * 产品名称:云通信短信API产品,开发者无需替换
         */
        static final String product = "Dysmsapi";
        /**
         * 产品域名,开发者无需替换
         */
        static final String domain = "dysmsapi.aliyuncs.com";

        /**
         * TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找，上方申请的)
         * 暂时现在使用叠金科技短信验证码
         */
//        static final String accessKeyId = "yourAccessKeyId";
//        static final String accessKeySecret = "yourAccessKeySecret";
        static final String accessKeyId = "LTAIEl7kVAmyHsOU";
        static final String accessKeySecret = "tHfwEG0wSzrKV8GqDxiOE7zD6SOUCg";

        public static SendSmsResponse sendSms(String phone,int code) throws ClientException {

            //可自助调整超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");

            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            if(StringUtils.isEmpty(phone)){
                return null;
            }
            //必填:待发送手机号
            request.setPhoneNumbers(phone);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName("叠金球");
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode("SMS_152885332");
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            request.setTemplateParam("{\"name\":\"something\", \"code\":"+ code +"}");

            //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");

            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            //request.setOutId("yourOutId");

            //hint 此处可能会抛出异常，注意catch
            SendSmsResponse sendSmsResponse = null;
            try {
                sendSmsResponse = acsClient.getAcsResponse(request);
            } catch (ClientException e) {
                e.printStackTrace();
            }

            return sendSmsResponse;
        }
}
