package me.sknz.kafka.input;

import java.util.List;
import java.util.Scanner;
import me.sknz.kafka.KafkaApplication;

public class AnswerInput {

    private final List<Question> questions;
    private final String[] answers;
    private int answer;

    public AnswerInput(List<Question> questions) {
        this.questions = questions;
        this.answers = new String[questions.size()];
        this.answer = 0;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while(this.answer < this.questions.size()) {
            Question question = this.questions.get(this.answer);
            KafkaApplication.getLogger().info(this.questions.get(this.answer).getMessage());
            if (scanner.hasNextLine()) {
                String response = scanner.nextLine();
                if (question.isValidAnswer(response)) {
                    this.answers[this.answer] = response;
                    ++this.answer;
                }
            }
        }

    }

    public String[] getAnswers() {
        return this.answers;
    }
}
