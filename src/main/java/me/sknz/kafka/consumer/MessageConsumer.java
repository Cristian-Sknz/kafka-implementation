package me.sknz.kafka.consumer;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageConsumer {

    private final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
    private final KafkaConsumer<String, String> consumer;
    private final ScheduledExecutorService service;
    private final UUID identifier;

    public MessageConsumer(String host, UUID identifier, ScheduledExecutorService service) {
        this.consumer = new KafkaConsumer<>(this.config(host));
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
        this.logger.info("[{}] Iniciado Consumer", this.getIdentifier());
        this.consumer.subscribe(List.of("KAFKA_IMPL"));
        this.service.scheduleAtFixedRate(() -> {
            ConsumerRecords<String, String> records = this.consumer.poll(Duration.ofMillis(100L));
            for (var record : records) {
                this.logger.info("[{}] Consumindo uma mensagem: [{}] Key: {}, Value: {}",
                        this.getIdentifierShort(),
                        record.offset(),
                        record.key(),
                        record.value());

                TopicPartition topicPartition = new TopicPartition(record.topic(), record.partition());
                OffsetAndMetadata offsetAndMetadata = new OffsetAndMetadata(record.offset() + 1L);
                this.consumer.commitSync(Map.of(topicPartition, offsetAndMetadata));
            }
        }, 0L, 100L, TimeUnit.MILLISECONDS);
    }

    private Properties config(String host) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", host);
        props.setProperty("group.id", "default");
        props.setProperty("enable.auto.commit", "false");
        props.setProperty("key.deserializer", StringDeserializer.class.getName());
        props.setProperty("value.deserializer", StringDeserializer.class.getName());
        return props;
    }
}
