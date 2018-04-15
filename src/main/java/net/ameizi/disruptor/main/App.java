package net.ameizi.disruptor.main;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import net.ameizi.disruptor.event.HelloEvent;
import net.ameizi.disruptor.factory.HelloEventFactory;
import net.ameizi.disruptor.handler.HelloEventHandler;
import net.ameizi.disruptor.producer.HelloEventProducer;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App
{
    public static void main( String[] args )
    {
        // 创建线程池
        ExecutorService executor  = Executors.newFixedThreadPool(5);

        // 等待策略
        WaitStrategy blockingWaitStrategy = new BlockingWaitStrategy();
        WaitStrategy sleepingWaitStrategy = new SleepingWaitStrategy();
        WaitStrategy yieldingWaitStrategy = new YieldingWaitStrategy();
        WaitStrategy busySpinWaitStrategy = new BusySpinWaitStrategy();

        // 事件工厂
        EventFactory<HelloEvent> eventFactory = new HelloEventFactory();

        // RingBuffer大小，必须是2的N次方
        int ringBufferSize = 1024 * 1024;

        // 创建
        Disruptor<HelloEvent> disruptor = new Disruptor<HelloEvent>(eventFactory,ringBufferSize,executor, ProducerType.SINGLE,blockingWaitStrategy);

        // 事件处理器
        EventHandler<HelloEvent> eventHandler = new HelloEventHandler();
        disruptor.handleEventsWith(eventHandler);

        // 启动disruptor
        disruptor.start();

        // 获取RingBuffer
        RingBuffer<HelloEvent> ringBuffer = disruptor.getRingBuffer();

        // // 请求下一个事件序号
        // long sequence = ringBuffer.next();
        //
        // try{
        //     // 获取该序号对应的事件对象
        //     HelloEvent event = ringBuffer.get(sequence);
        //     // 设置事件内容
        //     event.setValue("Hello,Disruptor!");
        // }finally {
        //     // 发布事件
        //     ringBuffer.publish(sequence);
        // }


        HelloEventProducer producer = new HelloEventProducer(ringBuffer);

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++)
        {
            bb.putLong(0, l);
            producer.onData(bb);
            // try {
            //     Thread.sleep(1000);
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
        }

    }
}
