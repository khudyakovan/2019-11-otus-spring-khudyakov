package ru.otus.homework.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.feed.dsl.Feed;
import org.springframework.integration.metadata.MetadataStore;
import org.springframework.integration.metadata.PropertiesPersistingMetadataStore;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.homework.transformer.FeedTransformer;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

@Configuration
@EnableIntegration
@RequiredArgsConstructor
@ConfigurationProperties("config")
public class FeedReadingConfig {

    private static final String INPUT_CHANNEL = "rss.input";
    private static final String CORONAVIRUS_NEWS_SERVICE = "coronavirusNewsService";
    private static final String OTHER_NEWS_SERVICE = "otherNewsService";
    private static final String SAVE_METHOD = "save";

    private final IntegrationFlowContext flowContext;
    private HashMap<String, String> feeds;

    @PostConstruct
    public void createAllFeedsEndpoints() {
        feeds.forEach((k, u) -> {
            try {
                flowContext
                        .registration(feedFlow(u, k))
                        .register();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller(@Value("${config.poller.fixed-rate}") long period,
                                 @Value("${config.poller.max-messages-per-poll}") int count) {
        return Pollers.fixedRate(period)
                .maxMessagesPerPoll(count)
                .get();
    }

    private IntegrationFlow feedFlow(String u, String metadataKey) throws MalformedURLException {
        URL url = new URL(u);
        return IntegrationFlows
                .from(Feed.inboundAdapter(url, metadataKey))
                .channel(INPUT_CHANNEL)
                .get();
    }

    @Bean
    public MetadataStore metadataStore() {
        PropertiesPersistingMetadataStore metadataStore = new PropertiesPersistingMetadataStore();
        metadataStore.setBaseDirectory(System.getProperty("user.dir"));
        return metadataStore;
    }

    @Bean
    public IntegrationFlow feedReadingFlow() {
        return IntegrationFlows
                .from(INPUT_CHANNEL)
                .transform(new FeedTransformer())
                .route("payload.aboutCoronavirus", r -> r
                        .subFlowMapping(true, subFlow -> subFlow
                                .handle(CORONAVIRUS_NEWS_SERVICE, SAVE_METHOD))
                        .subFlowMapping(false, subFlow -> subFlow
                                .handle(OTHER_NEWS_SERVICE, SAVE_METHOD))
                )
                .log()
                .get();
    }

    public void setFeeds(HashMap<String, String> feeds) {
        this.feeds = feeds;
    }
}
