package com.cloud.titls.Mapper;

import com.cloud.titls.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO t_user (username, password, salt, phone, email, gender, avatar, is_delete, created_user, created_time, modified_user, modified_time) " +
            "VALUES (#{username}, #{password}, #{salt}, #{phone}, #{email}, #{gender}, #{avatar}, #{isDelete}, #{createdUser}, #{createdTime}, #{modifiedUser}, #{modifiedTime})")
    Integer insert(User user);

    @Select("SELECT uid, username, password, salt, phone, email, gender, avatar, is_delete AS isDelete, created_user AS createdUser, created_time AS createdTime, modified_user AS modifiedUser, modified_time AS modifiedTime FROM t_user WHERE username = #{username}")
    User findByUserName(@Param("username") String username);

    @Select("SELECT uid, username, password, salt, phone, email, gender, avatar, is_delete AS isDelete, created_user AS createdUser, created_time AS createdTime, modified_user AS modifiedUser, modified_time AS modifiedTime FROM t_user WHERE uid = #{uid}")
    User findByUid(@Param("uid") Integer uid);

    @Update("UPDATE t_user SET password = #{password}, modified_user = #{modifiedUser}, modified_time = #{modifiedTime} WHERE uid = #{uid}")
    Integer updatePasswordByUid(@Param("uid") Integer uid, 
                                 @Param("password") String password, 
                                 @Param("modifiedUser") String modifiedUser, 
                                 @Param("modifiedTime") java.time.LocalDateTime modifiedTime);

    @Update("<script>" +
            "UPDATE t_user SET " +
            "<if test='phone != null'>phone = #{phone},</if>" +
            "<if test='email != null'>email = #{email},</if>" +
            "<if test='gender != null'>gender = #{gender},</if>" +
            "modified_user = #{modifiedUser}, " +
            "modified_time = #{modifiedTime} " +
            "WHERE uid = #{uid}" +
            "</script>")
    Integer updateInfoByUid(@Param("uid") Integer uid,
                            @Param("phone") String phone,
                            @Param("email") String email,
                            @Param("gender") Integer gender,
                            @Param("modifiedUser") String modifiedUser,
                            @Param("modifiedTime") java.time.LocalDateTime modifiedTime);

    @Update("UPDATE t_user SET avatar = #{avatar}, modified_user = #{modifiedUser}, modified_time = #{modifiedTime} WHERE uid = #{uid}")
    Integer updateAvatarByUid(@Param("uid") Integer uid,
                              @Param("avatar") String avatar,
                              @Param("modifiedUser") String modifiedUser,
                              @Param("modifiedTime") java.time.LocalDateTime modifiedTime);
}
