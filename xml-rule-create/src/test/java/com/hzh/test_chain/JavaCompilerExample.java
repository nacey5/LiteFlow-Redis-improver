
package com.hzh.test_chain;

/**
 * @author dahuang
 * @version : JavaCompilerExample.java, v 0.1 2023-06-14 01:18 dahuang
 */

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class JavaCompilerExample {

    public static void main(String[] args) {
        String javaFilePath = "/path/to/YourClass.java";
        String className = "YourClass";
        String outputDirectory = "/path/to/output";

        try {
            // 读取 Java 文件内容
            Path javaFilePathObj = Paths.get(javaFilePath);
            byte[] javaFileContent = Files.readAllBytes(javaFilePathObj);

            // 编译 Java 文件并输出 .class 文件
            compileAndSaveClassFile(javaFileContent, className, outputDirectory);

            System.out.println("Compilation and saving successful!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void compileAndSaveClassFile(byte[] javaFileContent, String className, String outputDirectory)
        throws IOException {
        // 创建一个新的 Java 文件对象
        SimpleJavaFileObject javaFileObject = new SimpleJavaFileObject(
            URI.create("string:///" + className.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension),
            JavaFileObject.Kind.SOURCE) {
            @Override
            public CharSequence getCharContent(boolean ignoreEncodingErrors) {
                return new String(javaFileContent);
            }
        };

        // 创建编译任务
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        // 设置编译输出的位置
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Collections.singletonList(new File(outputDirectory)));

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        JavaCompiler.CompilationTask task =
            compiler.getTask(null, fileManager, diagnostics, null, null, Collections.singletonList(javaFileObject));

        // 执行编译任务
        if (task.call()) {
            // 编译成功，获取编译后的类文件
            JavaFileObject classFileObject =
                fileManager.getJavaFileForOutput(StandardLocation.CLASS_OUTPUT, className, JavaFileObject.Kind.CLASS,
                    null);

            // 读取类文件的内容
            byte[] classFileContent = Files.readAllBytes(Paths.get(classFileObject.toUri()));

            // 保存类文件到指定目录
            Path classFilePath = Paths.get(outputDirectory, className.replace('.', '/') + ".class");
            Files.createDirectories(classFilePath.getParent());
            Files.write(classFilePath, classFileContent);
        } else {
            // 编译失败，输出诊断信息
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                System.err.println(diagnostic.getMessage(null));
            }
        }

        fileManager.close();
    }

}
