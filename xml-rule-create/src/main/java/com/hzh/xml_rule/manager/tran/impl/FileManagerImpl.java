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
package com.hzh.xml_rule.manager.tran.impl;

import com.hzh.xml_rule.controller.bean.JavaFileUploadController;
import com.hzh.xml_rule.manager.CustomJavaFileManager;
import com.hzh.xml_rule.manager.tran.FileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

/**
 * @author dahuang
 * @version : FileManagerImpl.java, v 0.1 2023-06-18 14:57 dahuang
 */

@Slf4j
@Component
public class FileManagerImpl implements FileManager {

    public static final String OUTPUT_DIRECTORY="/Users/dahuang/IdeaProjects/LiteFlow-Redis-improver/xml-rule-create/target/classes/";
    @Override
    public Boolean uploadJavaFile(String javaCode, String fullClassName) {

        //解析完整类名
        String[] parts = fullClassName.split("\\.");
        String packagePath = String.join("/", Arrays.copyOfRange(parts, 0, parts.length - 1));
        String className = parts[parts.length - 1];
        // 创建一个内存中的 Java 文件
        JavaSourceFromString javaFileObject = new JavaSourceFromString(className, javaCode);

        // 设置编译器参数
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

        // 创建编译任务
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(javaFileObject);
        Path outputDirectory = Paths.get(OUTPUT_DIRECTORY+packagePath);
        JavaFileManager customFileManager = new CustomJavaFileManager(fileManager, outputDirectory);
        JavaCompiler.CompilationTask task = compiler.getTask(null, customFileManager, diagnostics, null, null, compilationUnits);

        // 编译 Java 文件
        boolean success = task.call();

        if (success) {
            //todo 应该异步写盘
            Path classFilePath = Paths.get(className+".class");
            Path destinationPath = outputDirectory.resolve(classFilePath.getFileName());
            try {
                //这个地方应该要有完整包名
                Files.createDirectories(outputDirectory);
                Files.copy(classFilePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                // 处理文件复制异常
            }
            log.info("Java file compiled successfully.");
            return true;
        } else {
            // 获取编译错误信息
            ByteArrayOutputStream errorOutput = new ByteArrayOutputStream();
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                try {
                    errorOutput.write(diagnostic.toString().getBytes());
                    errorOutput.write(System.lineSeparator().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            log.warn("Java file compilation failed. Errors: {}",errorOutput);
            return false;
        }
    }

    // 自定义 JavaFileObject，用于将代码从字符串中传递给 Java 编译器
    static class JavaSourceFromString extends SimpleJavaFileObject {
        final String code;
        JavaSourceFromString(String name, String code) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }
}