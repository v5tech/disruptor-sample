package net.ameizi.disruptor.samples.basic.app;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import net.ameizi.disruptor.samples.basic.StringEvent;

import java.util.UUID;
import java.util.concurrent.Executors;

/**
 * disruptor java8基础用法
 *
 */
public class BasicJava8App {

    public static void main(String[] args) {

        Disruptor<StringEvent> disruptor = new Disruptor<>(
                StringEvent::new,
                1024,
                Executors.defaultThreadFactory(),
                ProducerType.MULTI,
                new BlockingWaitStrategy()
        );

        disruptor.handleEventsWith((event, sequence, endOfBatch) -> System.out.println("handleEvent:" + event.value));
        disruptor.start();

        RingBuffer<StringEvent> ringBuffer = disruptor.getRingBuffer();
        ringBuffer.publishEvent((event, sequence, arg) -> event.value = arg, UUID.randomUUID().toString());

        // ringBuffer.publishEvent((event, sequence) -> event.value = UUID.randomUUID().toString());

    }

}