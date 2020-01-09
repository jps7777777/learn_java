package cn.jp.service;

import cn.jp.exception.FinallyException;
import cn.jp.service.model.UserModel;


public interface UserService {

    /**
     * 注册用户
     * @param userModel 用户对象
     * @throws FinallyException 错误处理
     */
    void saveUser(UserModel userModel) throws FinallyException;

    /**
     * 用户密码登录
     * @param userModel 用户对象
     * @throws FinallyException 错误处理
     */
    void login(UserModel userModel) throws FinallyException;

    /**
     * 根据token获取用户信息
     * @param token 用户唯一标识token
     * @return 返回信息
     * @throws FinallyException 错误处理
     */
    UserModel getUserInfoByToken(String token) throws FinallyException;

    /**
     * 修改用户密码
     * @param userModel 用户对象
     * @throws FinallyException 错误处理
     */
    void updatePwd(UserModel userModel) throws FinallyException;
}
