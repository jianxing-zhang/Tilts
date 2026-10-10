package com.cloud.titls.Service;

import com.cloud.titls.Entity.User;
import com.cloud.titls.ex.InsertException;
import com.cloud.titls.ex.LoginException;
import com.cloud.titls.ex.PasswordNotMatchException;
import com.cloud.titls.ex.UpdateException;
import com.cloud.titls.ex.UsernameDuplicateException;
import com.cloud.titls.ex.FileUploadException;
import com.cloud.titls.ex.UserNotFoundException;

public interface UserService {
    
    void reg(User user) throws UsernameDuplicateException, InsertException;

    User login(String username, String password) throws LoginException;

    void changePassword(Integer uid, String oldPassword, String newPassword, String modifiedUser) 
            throws UserNotFoundException, PasswordNotMatchException, UpdateException;

    User getByUid(Integer uid) throws UserNotFoundException;

    void changeInfo(Integer uid, String phone, String email, Integer gender, String modifiedUser) 
            throws UserNotFoundException, UpdateException;

    void changeAvatar(Integer uid, String avatar, String modifiedUser) 
            throws UserNotFoundException, FileUploadException;
}
