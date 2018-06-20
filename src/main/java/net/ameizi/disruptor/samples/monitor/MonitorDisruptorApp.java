package net.ameizi.disruptor.samples.monitor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import disruptor.common.generic.BaseApp;
import disruptor.common.generic.GenericEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 使用ringBuffer.remainingCapacity()监控负载(环上还有多少个slot可用)
 */
public class MonitorDisruptorApp extends BaseApp {

    @Override
    public void addHandler(Disruptor<GenericEvent<String>> disruptor) {
        disruptor.handleEventsWith(new MonitorEventHandler<>("MonitorEventHandler"));
    }

    @Override
    protected void doAfterDisruptorStart(RingBuffer<GenericEvent<String>> ringBuffer) {
        // 每2秒钟打印一次剩余槽位
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(()-> System.out.println("剩余槽位:" + ringBuffer.remainingCapacity()),1000,2000, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) {
        new MonitorDisruptorApp().run();
    }

}
