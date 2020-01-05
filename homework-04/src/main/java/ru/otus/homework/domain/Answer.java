package ru.otus.homework.domain;

public class Answer {
    private int uid;
    private int questionUid;
    private int answerNumber;
    private String answer;
    private boolean isCorrect;

    public Answer() {
    }

    public Answer(int uid, int questionUid, int answerNumber, String answer, boolean isCorrect) {
        this.uid = uid;
        this.questionUid = questionUid;
        this.answerNumber = answerNumber;
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getQuestionUid() {
        return questionUid;
    }

    public void setQuestionUid(int questionUid) {
        this.questionUid = questionUid;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public int getAnswerNumber() {
        return answerNumber;
    }

    public void setAnswerNumber(int answerNumber) {
        this.answerNumber = answerNumber;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "uid=" + uid +
                ", questionUid=" + questionUid +
                ", answerNumber=" + answerNumber +
                ", answer='" + answer + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
