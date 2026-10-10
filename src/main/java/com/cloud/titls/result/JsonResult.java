package com.cloud.titls.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult<T> {
    
    private Integer state;
    private String message;
    private T data;

    /**
     * 成功返回（带数据）
     */
    public static <T> JsonResult<T> success(T data) {
        return new JsonResult<>(200, "操作成功", data);
    }

    /**
     * 成功返回（不带数据）
     */
    public static <T> JsonResult<T> success() {
        return new JsonResult<>(200, "操作成功", null);
    }

    /**
     * 成功返回（自定义消息）
     */
    public static <T> JsonResult<T> success(String message, T data) {
        return new JsonResult<>(200, message, data);
    }

    /**
     * 失败返回
     */
    public static <T> JsonResult<T> error(String message) {
        return new JsonResult<>(500, message, null);
    }

    /**
     * 失败返回（自定义状态码）
     */
    public static <T> JsonResult<T> error(Integer state, String message) {
        return new JsonResult<>(state, message, null);
    }
}
