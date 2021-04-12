package com.sy.dao;

import com.sy.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface UserDao {
    @Select("select * from login where username=#{username}")
    User findUser(User user);

    @Insert("insert into login(username,password,headImg) values(#{username},#{password},#{headImg})")
    int addUser(User user);

    @Select("select * from login where username=#{username} and password=#{password}")
    User findUser2(User user);
}
