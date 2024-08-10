package ttmm.database;

import io.ebean.Database;
import io.ebean.DatabaseBuilder;
import io.ebean.DatabaseFactory;
import io.ebean.annotation.Platform;
import io.ebean.config.ContainerConfig;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import io.ebean.dbmigration.DbMigration;
import io.ebean.migration.MigrationConfig;
import io.ebean.migration.MigrationRunner;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;
import ttmm.utils.ConfigManager;

import java.io.IOException;

@Slf4j
public enum DataBaseFactory {

    INSTANCE;

    public Database db;

    public void initializeDatabase() {
        try {
            log.info("Initializing Database...");
            JsonObject config = ConfigManager.INSTANCE.getDbConfig();
            DataSourceConfig dataSourceConfig = new DataSourceConfig();
            dataSourceConfig.setUsername(config.getString("username"));
            dataSourceConfig.setPassword(config.getString("password"));
            dataSourceConfig.setUrl(config.getString("url"));
            DatabaseConfig databaseConfig = new DatabaseConfig();
            databaseConfig.setDataSourceConfig(dataSourceConfig);
            databaseConfig.setDefaultServer(true);
            db = DatabaseFactory.create(databaseConfig);

            log.info("Database Initialized...!!!");

        } catch (Exception e) {
            log.error("DB Error:: " + e);
            throw new RuntimeException(e);
        }
    }

    public void runMigration() {
        try {
            log.info("Running Migration...");
            DbMigration dbMigration = DbMigration.create();
            dbMigration.setPlatform(Platform.POSTGRES);
            dbMigration.generateMigration();

            MigrationConfig migrationConfig = new MigrationConfig();
            migrationConfig.setDbUrl(ConfigManager.INSTANCE.getDbConfig().getString("url"));
            migrationConfig.setDbUsername(ConfigManager.INSTANCE.getDbConfig().getString("username"));
            migrationConfig.setDbPassword(ConfigManager.INSTANCE.getDbConfig().getString("password"));
            migrationConfig.setDbSchema(ConfigManager.INSTANCE.getDbConfig().getString("schema"));

            MigrationRunner migrationRunner = new MigrationRunner(migrationConfig);
            migrationRunner.run();
            log.info("Migration Completed...!!!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
