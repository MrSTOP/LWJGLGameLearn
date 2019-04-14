package ykw.engine;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Utils {
    public static String loadResource(String fileName) throws ClassNotFoundException, IOException {
        String result = null;
        try (InputStream in = Class.forName(Utils.class.getName()).getResourceAsStream(fileName);
             Scanner scanner = new Scanner(in, "UTF-8")){
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    }
}
