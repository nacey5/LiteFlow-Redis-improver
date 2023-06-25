
package com.hzh.mmon;

import lombok.Data;

/**
 * @author dahuang
 * @version : BaseResponse.java, v 0.1 2023-06-18 14:04 dahuang
 */
@Data
public class BaseResponse<T> {

    /**
     * response data
     */
    private T data;

    private Boolean success;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误详情
     */
    private String message;

    /**
     * trace id
     */
    private String traceId;

    /**
     * 成功返回
     */
    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.data = data;
        baseResponse.success = true;
        return baseResponse;
    }

    /**
     * 失败返回
     */
    public static BaseResponse<?> failed(String code, String message) {
        BaseResponse<?> baseResponse = new BaseResponse<>();
        baseResponse.code = code;
        baseResponse.message = message;
        baseResponse.success = false;
        return baseResponse;
    }

}