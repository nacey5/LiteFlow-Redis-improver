
package com.hzh.xml_rule.config.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hzh.xml_rule.config.BaseResponse;
import lombok.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.IOException;

/**
 * @author dahuang
 * @version : BaseResponseAdvice.java, v 0.1 2023-06-18 14:01 dahuang
 */
@ControllerAdvice
public class BaseResponseAdvice implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(@NonNull MethodParameter methodParameter,
        @NonNull Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter methodParameter, @NonNull MediaType mediaType,
        @NonNull Class<? extends HttpMessageConverter<?>> aClass, @NonNull ServerHttpRequest request,
        @NonNull ServerHttpResponse response) {

        if (body instanceof String) {
            ObjectMapper om = new ObjectMapper();
            om.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
            try {
                return om.writeValueAsString(BaseResponse.success(body));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (body instanceof BaseResponse) {
            return body;
        }
        return BaseResponse.success(body);
    }

}
