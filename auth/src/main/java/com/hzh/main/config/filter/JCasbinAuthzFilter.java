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
package com.hzh.main.config.filter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hzh.main.component.EnforcerCommonFactory;
import lombok.extern.slf4j.Slf4j;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dahuang
 * @version : JCasbinAuthzFilter.java, v 0.1 2023-07-30 16:19 dahuang
 */
@Slf4j
@Configuration
public class JCasbinAuthzFilter implements Filter,Ordered {
    private static Enforcer enforcer;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String user=request.getHeader("User");
        String path = request.getRequestURI();
        String method = request.getMethod();

        enforcer = EnforcerCommonFactory.getEnforcer();
        if (path.contains("anon")) {
            chain.doFilter(request, response);
        }else if (enforcer.enforce(user, path, method)) {
            chain.doFilter(request, response);
        } else {
            log.info("无权访问");
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("code", 1001);
            result.put("msg", "用户权限不足");
            result.put("data",null);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue));
        }

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}