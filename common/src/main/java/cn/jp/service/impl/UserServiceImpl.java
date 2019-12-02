package cn.jp.service.impl;

import cn.jp.bean.UserDO;
import cn.jp.bean.UserPasswordDO;
import cn.jp.dao.UserDOMapper;
import cn.jp.dao.UserPasswordDOMapper;
import cn.jp.exception.EnumException;
import cn.jp.exception.FinallyException;
import cn.jp.service.UserService;
import cn.jp.service.model.UserModel;
import cn.jp.utils.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional
    public void saveUser(UserModel userModel) throws FinallyException {
        UserDO userDO = (UserDO) CommonUtils.convertTo(userModel,new UserDO());
        userDOMapper.insertSelective(userDO);
        if(userDO.getId() < 0){
            throw new FinallyException(EnumException.DATA_INSERT_ERROR);
        }
        System.out.println(userDO.getId());
        UserPasswordDO passwordDO = (UserPasswordDO) CommonUtils.convertTo(userModel.getPasswordModel(),new UserPasswordDO());
        passwordDO.setUserId(userDO.getId());
        userPasswordDOMapper.insertSelective(passwordDO);
    }

    @Override
    public void login(UserModel userModel) throws FinallyException {
        UserDO userDO = (UserDO) CommonUtils.convertTo(userModel,new UserDO());
        UserDO resDO = userDOMapper.selectByUserDO(userDO);
        if(resDO == null){
            throw new FinallyException(EnumException.DATA_EMPTY.setErrMsg("用户不存在"));
        }
        // TODO 保存用户信息到Redis中
        // 查询用户密码
        redisTemplate.opsForHash().put("user_id_"+resDO.getId(),"id",resDO.getId());
        redisTemplate.opsForHash().put("user_id_"+resDO.getId(),"id",resDO.getId());

    }


    private UserDO convertToDo(UserModel origin) throws FinallyException {
        if(origin == null){
            throw new FinallyException(EnumException.PARAMS_IS_EMPTY.setErrMsg("对象转换是传参不能为空：" +
                    "CommonUtils.convertTo(Object origin,Object target)"));
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(origin,userDO);
        return userDO;
    }




}
