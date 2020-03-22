package ru.otus.homework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.feed.dsl.Feed;
import org.springframework.integration.metadata.MetadataStore;
import org.springframework.integration.metadata.PropertiesPersistingMetadataStore;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.homework.transformer.FeedTransformer;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
@EnableIntegration
public class FeedReadingConfig {

    private static final String BBC_FEED = "bbc";
    private static final String CNN_FEED = "cnn";
    private static final String DW_FEED = "dw";
    private static final String INPUT_CHANNEL = "rss.input";
    private static final String CORONAVIRUS_NEWS_SERVICE = "coronavirusNewsService";
    private static final String OTHER_NEWS_SERVICE = "otherNewsService";
    private static final String SAVE_METHOD = "save";

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller(@Value("${config.poller.fixed-rate}") long period,
                                 @Value("${config.poller.max-messages-per-poll}") int count) {
        return Pollers.fixedRate(period)
                .maxMessagesPerPoll(count)
                .get();
    }

    @Bean
    public IntegrationFlow bbcFeed(FeedsConfig feedsConfig) throws MalformedURLException {
        URL url = new URL(feedsConfig.getFeeds().get(BBC_FEED));
        return IntegrationFlows
                .from(Feed.inboundAdapter(url, BBC_FEED))
                .channel(INPUT_CHANNEL)
                .get();
    }

    @Bean
    public IntegrationFlow cnnFeed(FeedsConfig feedsConfig) throws MalformedURLException {
        URL url = new URL(feedsConfig.getFeeds().get(CNN_FEED));
        return IntegrationFlows
                .from(Feed.inboundAdapter(url, CNN_FEED))
                .channel(INPUT_CHANNEL)
                .get();
    }

    @Bean
    public IntegrationFlow dwFeed(FeedsConfig feedsConfig) throws MalformedURLException {
        URL url = new URL(feedsConfig.getFeeds().get(DW_FEED));
        return IntegrationFlows
                .from(Feed.inboundAdapter(url, DW_FEED))
                .channel(INPUT_CHANNEL)
                .get();
    }

    @Bean
    public MetadataStore metadataStore() {
        return new PropertiesPersistingMetadataStore();
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
}
