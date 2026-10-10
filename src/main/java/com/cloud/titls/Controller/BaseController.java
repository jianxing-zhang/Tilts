package com.cloud.titls.Controller;

import com.cloud.titls.ex.EmptyFileException;
import com.cloud.titls.ex.FileSizeException;
import com.cloud.titls.ex.FileTypeException;
import com.cloud.titls.ex.FileUploadException;
import com.cloud.titls.ex.InsertException;
import com.cloud.titls.ex.LoginException;
import com.cloud.titls.ex.PasswordNotMatchException;
import com.cloud.titls.ex.ServiceException;
import com.cloud.titls.ex.UpdateException;
import com.cloud.titls.ex.UsernameDuplicateException;
import com.cloud.titls.ex.UserNotFoundException;
import com.cloud.titls.result.JsonResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseController {

    @Autowired
    private HttpServletRequest request;

    protected Integer getUid() {
        HttpSession session = request.getSession();
        return (Integer) session.getAttribute("uid");
    }

    protected String getUsername() {
        HttpSession session = request.getSession();
        return (String) session.getAttribute("username");
    }

    @ExceptionHandler(UsernameDuplicateException.class)
    public JsonResult<Void> handleUsernameDuplicateException(UsernameDuplicateException e) {
        return JsonResult.error(400, e.getMessage());
    }

    @ExceptionHandler(InsertException.class)
    public JsonResult<Void> handleInsertException(InsertException e) {
        return JsonResult.error(500, e.getMessage());
    }

    @ExceptionHandler(LoginException.class)
    public JsonResult<Void> handleLoginException(LoginException e) {
        return JsonResult.error(400, e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public JsonResult<Void> handleUserNotFoundException(UserNotFoundException e) {
        return JsonResult.error(404, e.getMessage());
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public JsonResult<Void> handlePasswordNotMatchException(PasswordNotMatchException e) {
        return JsonResult.error(400, e.getMessage());
    }

    @ExceptionHandler(UpdateException.class)
    public JsonResult<Void> handleUpdateException(UpdateException e) {
        return JsonResult.error(500, e.getMessage());
    }

    @ExceptionHandler(EmptyFileException.class)
    public JsonResult<Void> handleEmptyFileException(EmptyFileException e) {
        return JsonResult.error(400, e.getMessage());
    }

    @ExceptionHandler(FileSizeException.class)
    public JsonResult<Void> handleFileSizeException(FileSizeException e) {
        return JsonResult.error(400, e.getMessage());
    }

    @ExceptionHandler(FileTypeException.class)
    public JsonResult<Void> handleFileTypeException(FileTypeException e) {
        return JsonResult.error(400, e.getMessage());
    }

    @ExceptionHandler(FileUploadException.class)
    public JsonResult<Void> handleFileUploadException(FileUploadException e) {
        return JsonResult.error(500, e.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    public JsonResult<Void> handleServiceException(ServiceException e) {
        return JsonResult.error(500, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public JsonResult<Void> handleException(Exception e) {
        return JsonResult.error(500, "系统错误：" + e.getMessage());
    }
}
