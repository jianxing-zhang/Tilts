package com.cloud.titls.ServiceImp;

import com.cloud.titls.Entity.User;
import com.cloud.titls.Mapper.UserMapper;
import com.cloud.titls.Service.UserService;
import com.cloud.titls.ex.InsertException;
import com.cloud.titls.ex.LoginException;
import com.cloud.titls.ex.PasswordNotMatchException;
import com.cloud.titls.ex.UpdateException;
import com.cloud.titls.ex.UsernameDuplicateException;
import com.cloud.titls.ex.FileUploadException;
import com.cloud.titls.ex.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) throws UsernameDuplicateException, InsertException {
        User existingUser = userMapper.findByUserName(user.getUsername());
        if (existingUser != null) {
            throw new UsernameDuplicateException("用户名已存在");
        }

        String salt = UUID.randomUUID().toString();
        String encryptedPassword = getMd5Password(user.getPassword(), salt);

        user.setPassword(encryptedPassword);
        user.setSalt(salt);
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setCreatedTime(LocalDateTime.now());
        user.setModifiedUser(user.getUsername());
        user.setModifiedTime(LocalDateTime.now());

        Integer rows = userMapper.insert(user);
        if (rows != 1) {
            throw new InsertException("用户注册失败");
        }
    }

    private String getMd5Password(String password, String salt) {
        String result = salt + password + salt;
        for (int i = 0; i < 3; i++) {
            result = DigestUtils.md5DigestAsHex(result.getBytes(StandardCharsets.UTF_8));
        }
        return result;
    }

    @Override
    public User login(String username, String password) throws LoginException {
        User user = userMapper.findByUserName(username);
        if (user == null) {
            throw new LoginException("用户名或密码错误");
        }

        String encryptedPassword = getMd5Password(password, user.getSalt());

        if (!encryptedPassword.equals(user.getPassword())) {
            throw new LoginException("用户名或密码错误");
        }

        if (user.getIsDelete() == 1) {
            throw new LoginException("账号已被禁用");
        }

        user.setPassword(null);
        user.setSalt(null);
        return user;
    }

    @Override
    public void changePassword(Integer uid, String oldPassword, String newPassword, String modifiedUser) 
            throws UserNotFoundException, PasswordNotMatchException, UpdateException {
        User user = userMapper.findByUid(uid);
        if (user == null || user.getIsDelete() == 1) {
            throw new UserNotFoundException("用户不存在或已被删除");
        }

        String encryptedOldPassword = getMd5Password(oldPassword, user.getSalt());
        if (!encryptedOldPassword.equals(user.getPassword())) {
            throw new PasswordNotMatchException("原密码错误");
        }

        String encryptedNewPassword = getMd5Password(newPassword, user.getSalt());
        
        Integer rows = userMapper.updatePasswordByUid(uid, encryptedNewPassword, modifiedUser, LocalDateTime.now());
        if (rows != 1) {
            throw new UpdateException("修改密码失败");
        }
    }

    @Override
    public User getByUid(Integer uid) throws UserNotFoundException {
        User user = userMapper.findByUid(uid);
        if (user == null || user.getIsDelete() == 1) {
            throw new UserNotFoundException("用户不存在或已被删除");
        }

        user.setPassword(null);
        user.setSalt(null);
        return user;
    }

    @Override
    public void changeInfo(Integer uid, String phone, String email, Integer gender, String modifiedUser) 
            throws UserNotFoundException, UpdateException {
        User user = userMapper.findByUid(uid);
        if (user == null || user.getIsDelete() == 1) {
            throw new UserNotFoundException("用户不存在或已被删除");
        }

        Integer rows = userMapper.updateInfoByUid(uid, phone, email, gender, modifiedUser, LocalDateTime.now());
        if (rows != 1) {
            throw new UpdateException("修改个人资料失败");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String modifiedUser) 
            throws UserNotFoundException, FileUploadException {
        User user = userMapper.findByUid(uid);
        if (user == null || user.getIsDelete() == 1) {
            throw new UserNotFoundException("用户不存在或已被删除");
        }

        Integer rows = userMapper.updateAvatarByUid(uid, avatar, modifiedUser, LocalDateTime.now());
        if (rows != 1) {
            throw new FileUploadException("更新头像失败");
        }
    }
}
