package com.application.geomaster;

public class Question  {
    public String questionText;
    public String answer0;
    public String answer1;
    public String answer2;
    public String answer3;
    public int correctAnswer;
    public int playerAnswer = -1; // Default to -1 if no answer has been chosen

    public Question(String questionText, String answer0, String answer1, String answer2, String answer3, int correctAnswer) {
        this.questionText = questionText;
        this.answer0 = answer0;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.correctAnswer = correctAnswer;
    }


    public boolean isCorrect() {
        return playerAnswer == correctAnswer;
    }
}
