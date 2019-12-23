package ru.otus.homework.dao;

import org.springframework.core.io.Resource;
import ru.otus.homework.domain.Answer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AnswerDaoImpl implements AnswerDao {

    private final Resource answerFilePath;
    private static final String SEPARATOR = ";";
    private List<Answer> answers;

    public AnswerDaoImpl(Resource answerFilePath) {

        this.answerFilePath = answerFilePath;
        this.setAnswerList();
    }

    @Override
    public List<Answer> getAllAnswers() {
        return this.answers;
    }

    @Override
    public List<Answer> getAllAnswersByQuestionUid(int questionUid) {
        return this.answers
                .stream()
                .filter(a -> questionUid == a.getQuestionUid())
                .collect(Collectors.toList());
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

    private void setAnswerList() {
        String nextLine;
        answers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(this.answerFilePath.getFile()))) {
            while ((nextLine = br.readLine()) != null) {
                answers.add(this.getAnswer(nextLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
