package com.cloud.titls.Controller;

import com.cloud.titls.Service.UserService;
import com.cloud.titls.ex.EmptyFileException;
import com.cloud.titls.ex.FileSizeException;
import com.cloud.titls.ex.FileTypeException;
import com.cloud.titls.ex.FileUploadException;
import com.cloud.titls.ex.UserNotFoundException;
import com.cloud.titls.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UploadController extends BaseController {

    @Autowired
    private UserService userService;

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;
    private static final List<String> ALLOWED_TYPES = Arrays.asList(
            "image/jpeg", "image/png", "image/gif", "image/bmp"
    );

    @PostMapping("/upload_avatar")
    public JsonResult<String> uploadAvatar(@RequestParam("file") MultipartFile file)
            throws EmptyFileException, FileSizeException, FileTypeException, FileUploadException, UserNotFoundException {

        if (file.isEmpty()) {
            throw new EmptyFileException("上传文件不能为空");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new FileSizeException("上传文件大小不能超过10MB");
        }

        String contentType = file.getContentType();
        if (!ALLOWED_TYPES.contains(contentType)) {
            throw new FileTypeException("只支持jpg、png、gif、bmp格式的图片");
        }

        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + suffix;

        String uploadDir = System.getProperty("user.dir") + "/upload/avatar/";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            File destFile = new File(dir, newFilename);
            file.transferTo(destFile);
        } catch (IOException e) {
            throw new FileUploadException("文件保存失败：" + e.getMessage());
        }

        String avatarPath = "/upload/avatar/" + newFilename;
        
        Integer uid = getUid();
        String username = getUsername();
        
        System.out.println("========== 头像上传调试信息 ==========");
        System.out.println("原始文件名: " + originalFilename);
        System.out.println("新文件名: " + newFilename);
        System.out.println("上传目录: " + uploadDir);
        System.out.println("头像路径: " + avatarPath);
        System.out.println("UID: " + uid);
        System.out.println("用户名: " + username);
        
        userService.changeAvatar(uid, avatarPath, username);
        
        System.out.println("数据库更新成功");
        System.out.println("==================================");

        return JsonResult.success("上传成功", avatarPath);
    }
}
