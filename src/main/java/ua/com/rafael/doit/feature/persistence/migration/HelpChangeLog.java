package ua.com.rafael.doit.feature.persistence.migration;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.google.common.collect.ImmutableList;
import com.mongodb.*;

import org.jongo.Jongo;
import ua.com.rafael.doit.feature.persistence.entity.Help;
import ua.com.rafael.doit.feature.persistence.entity.HelpParameter;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;
import static ua.com.rafael.doit.feature.persistence.entity.PersistanceConstants.CollectionNames.HELP_PARAMETERS;

@ChangeLog(order = "0001")
public class HelpChangeLog {

    @ChangeSet(order = "00001", id = "00001-create_help_collection", author = "Aleksandr Kruhlov")
    public void createHelpsCollection(Jongo jongo) {
      String json = readFileAsString("migration-init/first.json");
      jongo.getCollection(HELP_PARAMETERS).insert(json);
    }

    @ChangeSet(order = "00002", id = "00002-create_help_parameters_collection", author = "Aleksandr Kruhlov")
    public void createHelpParametersCollection(DB db) {
        db.createCollection(HELP_PARAMETERS, null);
    }

    @ChangeSet(order = "00003", id = "00003-init_with_test_help_info", author = "Aleksandr Kruhlov")
    public void initWithTestHelpInfo(Jongo jongo) {
        HelpParameter helpParameter = new HelpParameter(null, "all", 'a', "select all items");
        Help help = new Help(null, "Test something", "doit test -[params]",
                "Test short description", "Full description",
                ImmutableList.of(helpParameter));
    }

    public static String readFileAsString(String filePath) {
        notBlank(filePath);
        URL url = ClassLoader.getSystemClassLoader().getResource(filePath);
        notNull(url);
        try {
            return new String(Files.readAllBytes(Paths.get(url.getPath())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url.getPath();
    }
}
