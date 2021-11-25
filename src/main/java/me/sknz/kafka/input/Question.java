package me.sknz.kafka.input;

import java.util.function.Function;

public class Question {
    private final String message;
    private final Function<String, Boolean> process;

    public Question(String message) {
        this(message, null);
    }

    public Question(String message, Function<String, Boolean> process) {
        this.message = message;
        this.process = process == null ? (msg) -> true : process;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isValidAnswer(String answer) {
        return this.process.apply(answer);
    }
}