package com.sy.service;

import com.sy.domain.User;
import com.vo.ToServiceVo;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserService {

    ToServiceVo addUser(User user);

    User findUser(User user);
}
