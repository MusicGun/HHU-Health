package com.test.examine.mapper;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RoleMapper {
    @Insert("insert into user_role (id,role,authority) values(#{id},#{role},#{authority})")
    int addRole(@Param("id") String id, @Param("role") String role, @Param("authority") String authority);


    @Delete("delete from user_role where id=#{id}")
    int deleteRole(@Param("id") String id);


}
