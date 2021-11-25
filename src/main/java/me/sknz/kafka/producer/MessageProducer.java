package me.sknz.kafka.producer;

import java.util.Properties;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageProducer {

    private final Logger logger = LoggerFactory.getLogger(MessageProducer.class);
    private final ScheduledExecutorService service;
    private final KafkaProducer<String, String> producer;
    private final UUID identifier;

    public MessageProducer(String host, UUID identifier, ScheduledExecutorService service) {
        this.producer = new KafkaProducer<>(this.config(host));
        this.service = service;
        this.identifier = identifier;
        this.start();
    }

    public UUID getIdentifier() {
        return this.identifier;
    }

    public String getIdentifierShort() {
        return this.getIdentifier().toString().substring(0, 8);
    }

    private void start() {
        this.logger.info("[{}] Iniciado Producer", this.getIdentifier());
        this.service.submit(() -> {
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String message = scanner.nextLine();
                this.producer.send(new ProducerRecord<>("KAFKA_IMPL", this.getIdentifierShort(), message));
                this.logger.info("[{}] Mensagem enviada", this.getIdentifier());
            }
        });
    }

    private Properties config(String host) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", host);
        props.setProperty("key.serializer", StringSerializer.class.getName());
        props.setProperty("value.serializer", StringSerializer.class.getName());
        props.setProperty("request.timeout.ms", "1000");
        return props;
    }
}
