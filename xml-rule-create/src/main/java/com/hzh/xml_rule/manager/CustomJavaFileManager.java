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
package com.hzh.xml_rule.manager;

/**
 * @author dahuang
 * @version : CustomJavaFileManager.java, v 0.1 2023-06-14 14:35 dahuang
 */
import javax.tools.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CustomJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {
    private final Path outputDirectory;

    public CustomJavaFileManager(JavaFileManager fileManager, Path outputDirectory) {
        super(fileManager);
        this.outputDirectory = outputDirectory;
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        Path outputPath = outputDirectory.resolve(className + kind.extension);
        Files.createDirectories(outputPath.getParent());
        return super.getJavaFileForOutput(location, className, kind, sibling);
    }
}