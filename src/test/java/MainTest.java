import org.mingxuan.Main;
import org.mingxuan.config.AppConfig;

import java.io.IOException;

public class MainTest {
    public static void main(String[] args) throws IOException {
        AppConfig.CONFIG.getPath().setMetaOutput("src/test/java/meta");
        Main.main(null);
    }
}
