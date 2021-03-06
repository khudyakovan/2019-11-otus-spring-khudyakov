package ru.otus.homework.shell;

import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.otus.homework.utils.TranslationService;

@Component
public class InputReader {

    private final LineReader lineReader;
    private final TranslationService translationService;

    @Autowired
    public InputReader(@Lazy LineReader lineReader,
                       TranslationService translationService) {
        this.lineReader = lineReader;
        this.translationService = translationService;
    }

    public String prompt(String  prompt) {
        return prompt(prompt, null, true);
    }

    public String promptTranslated(String  property) {
        String prompt = translationService.getTranslation(property,"");
        return prompt(prompt, null, true);
    }

    public String prompt(String  prompt, String defaultValue) {
        return prompt(prompt, defaultValue, true);
    }

    public String promptTranslated(String  property, String defaultValue) {
        String prompt = translationService.getTranslation(property,"");
        return prompt(prompt, defaultValue, true);
    }

    public String prompt(String  prompt, String defaultValue, boolean echo) {
        String answer = "";
        if (echo) {
            answer = lineReader.readLine(prompt + ">>> ");
        }
        if (StringUtils.isEmpty(answer)) {
            return defaultValue;
        }
        return answer;
    }
}
