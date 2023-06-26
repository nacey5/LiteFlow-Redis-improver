
package com.hzh.common;

import org.junit.Test;

/**
 * @author dahuang
 * @version : Trans.java, v 0.1 2023-06-18 22:33 dahuang
 */
public class Trans {
    public static String formatString(String input) {
        StringBuilder sb = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (c == '\n') {
                sb.append("\\n");
            } else if (c == '"') {
                sb.append("\\\"");
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    @Test
    public void getString(){
        System.out.println(formatString(
            "package com.hzh.loader2;\n" + "\n" + "import java.time.LocalTime;\n" + "import com.hzh.xml_rule.manager.BaseManager;\n" + "import com.yomahub.liteflow.core.NodeComponent;\n" + "\n" + "public class HelloCmp extends NodeComponent implements BaseManager {\n" + "\n" + "    @Override\n" + "    public void logic() {\n" + "        System.out.println(LocalTime.now() + \": Java类的热加载\");\n" + "        this.process();\n" + "    }\n" + "\n" + "    @Override\n" + "    public void process() {\n" + "        //do your business\n" + "         System.out.println(\"HelloCmp3\");\n" + "    }\n" + "}"));
    }
}