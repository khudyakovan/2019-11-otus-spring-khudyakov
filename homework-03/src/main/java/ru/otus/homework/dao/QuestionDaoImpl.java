package ru.otus.homework.dao;

import org.springframework.core.io.Resource;
import ru.otus.homework.domain.Question;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoImpl implements QuestionDao {

    private final Resource questionFilePath;
    private final AnswerDao answerDao;
    private static final String SEPARATOR = ";";
    private List<Question> questions;

    public QuestionDaoImpl(Resource questionFilePath, AnswerDao answerDao) {
        this.questionFilePath = questionFilePath;
        this.answerDao = answerDao;
        this.setQuestionsList();
    }

    @Override
    public List<Question> getAllQuestions() {
        return this.questions;
    }

    @Override
    public Question getQuestionByUid(int uid) {
        return this
                .questions
                .stream()
                .filter(q -> uid == q.getUid())
                .findAny()
                .orElse(null);

    }

    private void setQuestionsList() {
        String nextLine;
        this.questions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(this.questionFilePath.getFile()))) {
            while ((nextLine = br.readLine()) != null) {
                questions.add(this.getQuestion(nextLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Question getQuestion(String line) {
        String[] array = line.split(SEPARATOR);
        Question question = new Question();
        if (array.length == 2) {
            question.setUid(Integer.parseInt(array[0]));
            question.setQuestion(array[1]);
            question.setAnswers(this.answerDao.getAllAnswersByQuestionUid(Integer.parseInt(array[0])));
        }
        return question;
    }

}
