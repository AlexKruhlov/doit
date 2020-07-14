package ua.com.rafael.doit.config;

import com.github.mongobee.Mongobee;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.com.rafael.doit.feature.persistence.migration.HelpChangeLog;

@Configuration
@RequiredArgsConstructor
@Getter
@Setter
public class MongoDataMigrationConfig {

    @Value(value = "${spring.data.mongodb.database}")
    private String databaseName;

    @Value(value = "mongodb://${spring.data.mongodb.host}:${spring.data.mongodb.port}/${spring.data.mongodb.database}")
    private String mongoDbUrl;

    @Bean
    public Mongobee mongobee() {
        Mongobee runner = new Mongobee();
        runner.setDbName(databaseName);
        runner.setChangeLogsScanPackage(HelpChangeLog.class.getPackage().getName());
        return runner;
    }
}
