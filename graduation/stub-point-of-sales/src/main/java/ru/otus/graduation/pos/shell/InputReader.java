package ru.otus.graduation.pos.shell;

import org.jline.reader.LineReader;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class InputReader {

    public InputReader(@Lazy LineReader lineReader) {
        this.lineReader = lineReader;
    }

    private final LineReader lineReader;

    public String prompt(String  prompt) {
        return prompt(prompt, "0", true);
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
