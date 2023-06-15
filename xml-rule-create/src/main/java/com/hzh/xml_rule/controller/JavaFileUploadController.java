
package com.hzh.xml_rule.controller;

/**
 * @author dahuang
 * @version : JavaFileUploadController.java, v 0.1 2023-06-14 14:24 dahuang
 */
import com.hzh.xml_rule.manager.CustomJavaFileManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

@RestController
public class JavaFileUploadController {

    @PostMapping("/upload/{fullClassName}")
    public String uploadJavaFile(@RequestBody String javaCode,@PathVariable String fullClassName) {
        // 设置编译器参数
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

        //解析完整类名
        String[] parts = fullClassName.split("\\.");
        String packagePath = String.join("/", Arrays.copyOfRange(parts, 0, parts.length - 1));
        String className = parts[parts.length - 1];

        // 创建一个内存中的 Java 文件
        JavaSourceFromString javaFileObject = new JavaSourceFromString(className, javaCode);

        // 创建编译任务
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(javaFileObject);
        Path outputDirectory = Paths.get("/Users/dahuang/IdeaProjects/LiteFlow-Redis-improver/xml-rule-create/target/classes/"+packagePath);
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
            return "Java file compiled successfully.";
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
            return "Java file compilation failed. Errors: " + errorOutput.toString();
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
