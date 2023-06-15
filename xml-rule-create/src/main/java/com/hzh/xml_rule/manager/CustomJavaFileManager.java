
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