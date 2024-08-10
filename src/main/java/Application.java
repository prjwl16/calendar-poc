import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import ttmm.database.DataBaseFactory;
import ttmm.middlewares.Jwt;
import ttmm.utils.ConfigManager;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class Application extends AbstractVerticle {

    public static void main(String[] args) {
        try {
            Vertx vertx = Vertx.vertx();
            vertx.deployVerticle(new Application());
        } catch (Exception e) {
            System.exit(1);
        }
    }

    @Override
    public void start() throws Exception {
        try {
            super.start();
            initialize();
            vertx.deployVerticle(HttpRouter.class.getName());
        } catch (Exception e) {
            log.error("Error starting verticle", e);
        }
    }

    private void initialize() throws Exception {
        try {
            log.info("Initializing config...");
            JsonObject config;
            try {
                File location = new File(".");
                String configPath;
                if(location.getPath().equals("/app")){
                    log.info("Running in docker");
                    configPath = "/app/main/prod.config.json";
                }else{
                    log.info("Running in local");
                    configPath = "src/main/resources/config.json";
                }
                File file = new File(configPath);
                if (file.exists() && !file.isDirectory()) {
                    String json = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
                    config = new JsonObject(json);
                    ConfigManager.INSTANCE.init(config);
                    log.info("ENV: {}", ConfigManager.INSTANCE.getEnv());
                } else {
                    throw new Exception("ERR Config file not found");
                }
            } catch (IOException e) {
                log.error("ERR Config " + e);
                throw new Exception("ERR Config " + e);
            }

            DataBaseFactory.INSTANCE.initializeDatabase();
            DataBaseFactory.INSTANCE.runMigration();
            Jwt.INSTANCE.inti(vertx);

            log.info("Config initialized");

        } catch (Exception e) {
            log.error("ERR " + e);
            throw new Exception("ERR " + e);
        }
    }
}
