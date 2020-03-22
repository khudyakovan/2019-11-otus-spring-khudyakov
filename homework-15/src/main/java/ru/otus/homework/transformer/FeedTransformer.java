package ru.otus.homework.transformer;

import com.rometools.rome.feed.synd.SyndEntry;
import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.messaging.Message;
import ru.otus.homework.dto.RssFeedMessage;

import java.util.Arrays;

public class FeedTransformer extends AbstractTransformer {

    private static final String[] BUZZWORDS = {"coronavirus", "covid-19"};

    @Override
    protected Object doTransform(Message<?> message) {
        SyndEntry syndEntry = (SyndEntry) message.getPayload();
        RssFeedMessage rssFeedMessage = new RssFeedMessage();
        rssFeedMessage.setTitle(syndEntry.getTitle());
        rssFeedMessage.setDescription(syndEntry.getDescription().getValue());
        if (this.containsItemFromArray(syndEntry.getTitle(), BUZZWORDS) ||
                (this.containsItemFromArray(syndEntry.getDescription().getValue(), BUZZWORDS))) {
            rssFeedMessage.setAboutCoronavirus(true);
        }

        return rssFeedMessage;
    }

    private boolean containsItemFromArray(String inputString, String[] items) {
        return Arrays.stream(items).anyMatch(inputString.toLowerCase()::contains);
    }
}
