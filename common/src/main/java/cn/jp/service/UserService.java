package cn.jp.service;

import cn.jp.exception.FinallyException;
import cn.jp.service.model.UserModel;


public interface UserService {

    void saveUser(UserModel userModel) throws FinallyException;
    void login(UserModel userModel) throws FinallyException;


}
