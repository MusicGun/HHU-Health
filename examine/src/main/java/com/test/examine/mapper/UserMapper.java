package com.test.examine.mapper;


import com.test.examine.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    @Select("select password,enable,role,authority from user,user_role where user.id=user_role.id and user.id=#{id}")
    User findUserById(@Param("id") String id);

    @Select("select id from user where id=#{id}")
    String checkId(String id);

    @Insert("insert into user(id,password,enable) values(#{u.id},#{u.password},#{u.enable})")
    int addUser(@Param("u") User u);

    @Update("update user set enable=1 where id=#{id} and enable=0")
    int enableUser(@Param("id") String id);

    @Delete("delete from user where id=#{id} and enable=0")
    int deleteUser(@Param("id") String id);

    @Delete("delete from user where id=#{id} and enable=1")
    int deleteAdmin(@Param("id")String id);

}
