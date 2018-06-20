package net.ameizi.disruptor.samples.basic.app;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import net.ameizi.disruptor.samples.basic.LongEvent;
import net.ameizi.disruptor.samples.basic.LongEventFactory;
import net.ameizi.disruptor.samples.basic.LongEventProducer;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * disruptor基本用法
 *
 */
public class BasicApp {

    public static void main(String[] args) {

        // 指定ringBuffer环的大小
        int ringBufferSize = 1024;

        // 生产event工厂，在disruptor启动的时候用来在ringBuffer环中占坑
        LongEventFactory factory = new LongEventFactory();

        // 创建disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory, ringBufferSize, Executors.defaultThreadFactory());

        // 注册事件处理器，用来处理消费事件
        disruptor.handleEventsWith(new EventHandler<LongEvent>() {
            @Override
            public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
                System.out.println("消费者EventHandler:" + Thread.currentThread().getName() + ":" + event.getValue());
            }
        });

        // 启动disruptor
        disruptor.start();

        // 获取ringBuffer
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        // 构建生产者，用来生产事件
        LongEventProducer producer = new LongEventProducer(ringBuffer);
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                ByteBuffer byteBuffer = ByteBuffer.allocate(ringBufferSize);
                for (long l = 0; l < 20; l++) {
                    byteBuffer.putLong(0,l);
                    producer.onData(byteBuffer);
                }
            }
        });

        // 使用EventTranslatorOneArg
        // LongEventProducerWithTranslator translator = new LongEventProducerWithTranslator(ringBuffer);
        // ExecutorService executorService = Executors.newFixedThreadPool(100);
        // executorService.submit(new Runnable() {
        //     @Override
        //     public void run() {
        //         ByteBuffer byteBuffer = ByteBuffer.allocate(ringBufferSize);
        //         for (long l = 0; l < 20; l++) {
        //             byteBuffer.putLong(0,l);
        //             translator.onData(byteBuffer);
        //         }
        //     }
        // });

        System.out.println("...end...");
    }

}





