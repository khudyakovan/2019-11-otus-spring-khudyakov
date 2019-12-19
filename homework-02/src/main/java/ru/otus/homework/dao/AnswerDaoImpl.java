package ru.otus.homework.dao;

import org.springframework.core.io.Resource;
import ru.otus.homework.domain.Answer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnswerDaoImpl implements AnswerDao {

    private final Resource answerFilePath;
    private static final String SEPARATOR = ";";

    public AnswerDaoImpl(Resource answerFilePath) {
        this.answerFilePath = answerFilePath;
    }

    @Override
    public List<Answer> getAllAnswers() {
        String nextLine;
        List<Answer> answers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(this.answerFilePath.getFile()))) {
            while ((nextLine = br.readLine()) != null) {
                answers.add(this.getAnswer(nextLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answers;
    }

    @Override
    public List<Answer> getAllAnswersByQuestionUid(int questionUid) {
        String nextLine;
        List<Answer> answers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(this.answerFilePath.getFile()))) {
            while ((nextLine = br.readLine()) != null) {
                Answer answerBean = this.getAnswer(nextLine);
                if (answerBean.getQuestionUid() == questionUid)
                    answers.add(answerBean);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answers;
    }

    @Override
    public boolean isUserAnswerCorrect(int questionUid, int answerNumber) {
        List<Answer> answers = this.getAllAnswersByQuestionUid(questionUid);

        return answers.stream()
                .filter(a -> a.getAnswerNumber() == answerNumber)
                .map(Answer::isCorrect)
                .findAny()
                .orElse(false);
    }

    private Answer getAnswer(String line) {
        String[] array = line.split(SEPARATOR);
        Answer answer = new Answer();
        if (array.length == 5) {
            answer.setUid(Integer.parseInt(array[0]));
            answer.setQuestionUid(Integer.parseInt(array[1]));
            answer.setAnswerNumber(Integer.parseInt(array[2]));
            answer.setAnswer(array[3]);
            answer.setCorrect(Boolean.parseBoolean(array[4]));
        } else {
            answer.setAnswer("Wrong answer file format!");
        }
        return answer;
    }
}
