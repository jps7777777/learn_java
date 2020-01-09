package cn.jp.service;

public interface PhoneCodeService {

    /**
     * 用户发送短信验证码
     * @param phone 电话号码
     * @param code 验证码
     */
    void sendPhoneCode(String phone,int code);

    boolean validateCode(String phone,int code);

}
