package cn.jp.service.impl;

import cn.jp.service.PhoneCodeService;

public class PhoneCodeServiceImpl implements PhoneCodeService {



    @Override
    public void sendPhoneCode(String phone, int code) {

    }

    @Override
    public boolean validateCode(String phone, int code) {
        return false;
    }
}
