package me.sknz.kafka;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import me.sknz.kafka.input.AnswerInput;
import me.sknz.kafka.input.Question;
import me.sknz.kafka.util.KafkaThreadFactory;
import me.sknz.kafka.consumer.MessageConsumer;
import me.sknz.kafka.producer.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaApplication {

    private static Logger logger = LoggerFactory.getLogger(KafkaApplication.class);
    private static UUID identifier = UUID.randomUUID();

    private final MessageProducer producer;
    private final MessageConsumer consumer;

    public KafkaApplication(String host) {
        var threadpool = Executors.newScheduledThreadPool(2, new KafkaThreadFactory("Kafka-App"));
        this.producer = new MessageProducer(host, identifier, threadpool);
        this.consumer = new MessageConsumer(host, identifier, threadpool);
    }

    public MessageConsumer getConsumer() {
        return this.consumer;
    }

    public MessageProducer getProducer() {
        return this.producer;
    }

    public static void main(String[] args) {
        logger.info("Iniciando a aplicação!");

        var questions = List.of("Qual endereço do Kafka quer se conectar?");
        var answers = new AnswerInput(questions.stream()
                .map(Question::new)
                .collect(Collectors.toList()));

        answers.start();
        var host = answers.getAnswers()[0];
        new KafkaApplication(host);
    }

    public static UUID getIdentifier() {
        return identifier;
    }

    public static Logger getLogger() {
        return logger;
    }
}
