package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertyLoader {
    private static Properties props = null;

    private static void loadProps() throws IOException, URISyntaxException {
        Path path = Paths.get(ClassLoader.getSystemResource("data.properties").toURI());
        InputStream fs = new FileInputStream(path.toFile());
        props = new Properties();
        props.load(fs);
    }

    public static String getProp(String name) throws URISyntaxException, IOException {
        if (props == null) {
            loadProps();
        }
        return props.getProperty(name);
    }
}
