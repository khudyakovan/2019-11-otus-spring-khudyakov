package ru.otus.graduation.orders.config;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.graduation.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@ChangeLog(order = "001")
public class MongoDbInit {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDbInit.class);
    private static final String HIERARCHY_LEVELS_FILE="levels.csv";
    private static final String PLU_AND_STOCK_FILE="plu.csv";
    private static final boolean INIT_DATABASE = true;
    private static final String SEPARATOR = ";";

    private final Resource HIERARCHY_LEVELS = new ClassPathResource(HIERARCHY_LEVELS_FILE);
    private final Resource PLU_AND_STOCK = new ClassPathResource(PLU_AND_STOCK_FILE);

    @ChangeSet(order = "000", id = "dropDB", author = "Alexey Khudyakov", runAlways = INIT_DATABASE)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "populateDatabase", author = "Alexey Khudyakov", runAlways = INIT_DATABASE)
    public void populateDatabase(MongoTemplate mongoTemplate) {
        this.populateLevels(mongoTemplate);
        this.populatePlu(mongoTemplate);
        this.populateProposal(mongoTemplate);
        this.populateOrder(mongoTemplate);
        LOGGER.info("Database was initialized");
    }

    private void populateLevels(MongoTemplate mongoTemplate ){
        String nextLine;
        try (BufferedReader br = new BufferedReader(new FileReader(HIERARCHY_LEVELS.getFile()))) {
            while ((nextLine = br.readLine()) != null){
                String[] array = nextLine.split(SEPARATOR, -1);
                Level level = new Level(array[2], array[3], new ParentLevel(array[0], array[1]));
                mongoTemplate.save(level);
            }
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
    }

    private void populatePlu(MongoTemplate mongoTemplate){
        String nextLine;
        try (BufferedReader br = new BufferedReader(new FileReader(PLU_AND_STOCK.getFile()))) {
            while ((nextLine = br.readLine()) != null){
                String[] array = nextLine.split(SEPARATOR, -1);
                Product plu = new Product(
                        array[2],
                        array[3],
                        new ParentLevel(array[0], array[1]),
                        Float.parseFloat(array[4]),
                        Float.parseFloat(array[5])
                );
                mongoTemplate.save(plu);
            }
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
    }

    private void populateProposal(MongoTemplate mongoTemplate){
        Proposal proposal = new Proposal();
        proposal.setId(new ObjectId());
        proposal.setProposalNumber(1000L);
        mongoTemplate.save(proposal);
    }

    private void populateOrder(MongoTemplate mongoTemplate){
        Order order = new Order();
        order.setId(new ObjectId());
        order.setOrderNumber(1000L);
        order.setProposalNumber(1000L);
        order.setMobilePhone("9174704000");
        mongoTemplate.save(order);
    }
}
