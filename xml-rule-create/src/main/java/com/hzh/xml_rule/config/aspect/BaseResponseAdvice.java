/*
 * Aloudata.com Inc.
 * Copyright (c) 2021-2023 All Rights Reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hzh.xml_rule.config.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
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
