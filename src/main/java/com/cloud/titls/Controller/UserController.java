package com.cloud.titls.Controller;

import com.cloud.titls.Entity.User;
import com.cloud.titls.Service.UserService;
import com.cloud.titls.ex.InsertException;
import com.cloud.titls.ex.LoginException;
import com.cloud.titls.ex.PasswordNotMatchException;
import com.cloud.titls.ex.UpdateException;
import com.cloud.titls.ex.UsernameDuplicateException;
import com.cloud.titls.ex.UserNotFoundException;
import com.cloud.titls.result.JsonResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public JsonResult<String> test() {
        return JsonResult.success("服务正常运行", "Hello World");
    }

    @PostMapping("/reg")
    public JsonResult<Void> reg(User user) throws UsernameDuplicateException, InsertException {
        userService.reg(user);
        return JsonResult.success("注册成功", null);
    }

    @PostMapping("/login")
    public JsonResult<User> login(String username, String password, HttpSession session) throws LoginException {
        User user = userService.login(username, password);
        session.setAttribute("uid", user.getUid());
        session.setAttribute("username", user.getUsername());
        return JsonResult.success("登录成功", user);
    }

    @PostMapping("/change_password")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword) 
            throws UserNotFoundException, PasswordNotMatchException, UpdateException {
        Integer uid = getUid();
        String username = getUsername();
        userService.changePassword(uid, oldPassword, newPassword, username);
        return JsonResult.success("修改密码成功", null);
    }

    @GetMapping("/get_by_uid")
    public JsonResult<User> getByUid() throws UserNotFoundException {
        Integer uid = getUid();
        User user = userService.getByUid(uid);
        return JsonResult.success("查询成功", user);
    }

    @PostMapping("/change_info")
    public JsonResult<Void> changeInfo(String phone, String email, Integer gender) 
            throws UserNotFoundException, UpdateException {
        Integer uid = getUid();
        String username = getUsername();
        userService.changeInfo(uid, phone, email, gender, username);
        return JsonResult.success("修改资料成功", null);
    }
}
