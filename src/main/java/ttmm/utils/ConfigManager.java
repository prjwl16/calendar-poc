package ttmm.utils;


import io.vertx.core.json.JsonObject;

public enum ConfigManager {
    INSTANCE;

    private JsonObject config;

    public void init(JsonObject config) {
        this.config = config;
    }

    public JsonObject getMainConfig(String key) {
        return config.getJsonObject(key);
    }

    public JsonObject getDbConfig() {
        return config.getJsonObject("database");
    }

    public JsonObject getAuthConfig() {
        return config.getJsonObject("gauth");
    }

    public int getPort() {
        return config.getInteger("port");
    }

    public String getFrontEndUri() {
        return config.getString("front_end_uri");
    }

    public String getEnv() {
        return config.getString("ENV");
    }

    public String getJwtSecret() {
        return config.getString("jwt_secret");
    }
}
