package com.sy.service.impl;

import com.sy.dao.UserDao;
import com.sy.domain.User;
import com.sy.service.UserService;
import com.vo.ToServiceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userdao;
    @Override
    public ToServiceVo addUser(User user) {
       User user1= userdao.findUser(user);
        ToServiceVo toServiceVo = new ToServiceVo();
       if(user1==null){
           int i = userdao.addUser(user);

           if(i!=1){
               toServiceVo.setSuccess(false);
               toServiceVo.setMsg("注册失败");
           }
       }else {
           toServiceVo.setSuccess(false);
           toServiceVo.setMsg("该用户以存在");
       }

        return toServiceVo;
    }

    @Override
    public User findUser(User user) {

        return userdao.findUser2(user);
    }
}
