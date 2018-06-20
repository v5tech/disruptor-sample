package net.ameizi.disruptor.samples.basic.app;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import net.ameizi.disruptor.samples.basic.StringEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * disruptor多线程用法
 */
public class MultiThreadApp {

    public static void main(String[] args) {
        Disruptor<StringEvent> disruptor = new Disruptor<StringEvent>(
                StringEvent::new, 8,
                Executors.defaultThreadFactory(),
                ProducerType.MULTI,
                new BlockingWaitStrategy());


        disruptor.handleEventsWith((
                (event, sequence, endOfBatch) -> {
                    System.out.println(Thread.currentThread().getName() + " handleEvent:" + event.value);
                    TimeUnit.SECONDS.sleep(2);
                })
        );

        disruptor.start();

        //multiple threads use one disruptor

        // for (int i = 0; i < 20; i++) {
        //     int threadNo = i;
        //     new Thread("thread-" + threadNo) {
        //         @Override
        //         public void run() {
        //             disruptor.getRingBuffer().publishEvent((event, sequence, arg) -> event.value = arg, "t-" + threadNo);
        //             System.out.println("已提交:"+threadNo);
        //         }
        //     }.start();
        // }

        // 使用线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    disruptor.getRingBuffer().publishEvent(new EventTranslatorOneArg<StringEvent, String>() {
                        @Override
                        public void translateTo(StringEvent event, long sequence, String arg0) {
                            event.value = arg0;
                        }
                    }, String.valueOf(i));
                    System.out.println("已提交:"+i);
                }
            }
        });

        System.out.println("...end...");
    }

}
