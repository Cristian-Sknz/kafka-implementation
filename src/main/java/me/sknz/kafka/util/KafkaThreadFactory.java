package me.sknz.kafka.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class KafkaThreadFactory implements ThreadFactory {

    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final String groupName;

    public KafkaThreadFactory(String groupName) {
        this.group = new ThreadGroup(groupName);
        this.groupName = groupName;
    }

    public Thread newThread(Runnable runnable) {
        return new Thread(this.group, runnable, String.format("%s-%s", this.groupName, this.threadNumber.getAndIncrement()));
    }
}
