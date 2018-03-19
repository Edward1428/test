package carSystem.com.utils;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Rico on 2017/6/22.
 */
public class FileUtils {

    public static String readFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            StringBuilder content = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                content.append(line);
                content.append(System.lineSeparator());
                line = br.readLine();
            }
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从resources里面获取该文件内容
     * @param filePath resources目录下的文件路径
     * @return 文件路径
     *
     * eg. filePath = "application.properties"
     */
    public static String readResource(String filePath) {
        try {
            InputStream out = FileUtils.class.getResourceAsStream(filePath);
            return IOUtils.toString(out, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
