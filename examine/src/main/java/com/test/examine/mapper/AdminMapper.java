package com.test.examine.mapper;


import com.test.examine.entity.Admin;
import com.test.examine.entity.Lab;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {


    @Select("select admin.id,email,name,authority from admin,user_role" +
            "   where admin.id=user_role.id")
    List<Admin> findAdmin();

    @Select("select admin.id,name,email,role from admin,user_role where admin.id=user_role.id and admin.id=#{id}")
    Admin findAdminById(@Param("id")String id);

    //更新权限
    @Update("update authority set authority=#{p} where id=#{id} and authority=#{pre}")
    int updateAuthority(@Param("p")String p,@Param("id") String id,@Param("pre") String pre);

    @Insert("insert into admin(id,name,email) values(#{a.id},#{a.name},#{a.email})")
    int addAdmin(@Param("a")Admin admin);

    @Select("select email from admin where email=#{email}")
    String check(@Param("email")String email);



}
