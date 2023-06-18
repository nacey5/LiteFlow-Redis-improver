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
package com.hzh.xml_rule.config;

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