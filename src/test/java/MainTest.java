import meta.FmulTestMeta;
import meta.HtestMeta;
import meta.SmulTestMeta;
import meta.VtestMeta;
import org.mingxuan.Main;
import org.mingxuan.config.AppConfig;
import org.mingxuan.project.MetaLoad;

import java.io.IOException;
import java.nio.file.Path;

public class MainTest {

    public static void main(String[] args) throws IOException {
        AppConfig.CONFIG.getPath().setMetaOutput("src/test/java/meta");

        Main.main(null);

        String jsonOutput = AppConfig.CONFIG.getPath().getJsonOutput();
        Path jsonDirPath = Path.of(jsonOutput);
        MetaLoad.load(jsonDirPath, "meta");

        System.out.println("导出完成");
        System.out.println(HtestMeta.getAll());
        System.out.println(FmulTestMeta.getAll());
        System.out.println(SmulTestMeta.getAll());
        System.out.println(VtestMeta.meta());
    }

}
