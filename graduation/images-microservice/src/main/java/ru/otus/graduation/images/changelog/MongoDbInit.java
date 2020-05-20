package ru.otus.graduation.images.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.graduation.model.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@ChangeLog(order = "001")
public class MongoDbInit {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDbInit.class);
    private static final boolean INIT_DATABASE = true;
    private static final String IMAGES_FOLDER = "/opt/tmp/images";
    private static final String SEPARATOR = "_";

    @ChangeSet(order = "000", id = "dropDB", author = "Alexey Khudyakov", runAlways = INIT_DATABASE)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "populateDatabase", author = "Alexey Khudyakov", runAlways = INIT_DATABASE)
    public void populateDatabase(MongoTemplate mongoTemplate) {
        try (Stream<Path> walk = Files.walk(Paths.get(IMAGES_FOLDER))) {
            walk.filter(Files::isRegularFile).forEach(path -> {
                try {
                    Image image = new Image(new ObjectId(),
                            path.toString().split(SEPARATOR)[1],
                            new Binary(Files.readAllBytes(path)));
                    mongoTemplate.save(image);
                } catch (IOException e) {
                    LOGGER.error(e.getLocalizedMessage());
                }
            });
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
        LOGGER.info("Database was initialized");
    }
}
