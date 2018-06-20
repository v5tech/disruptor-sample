package net.ameizi.disruptor.samples.monitor;

import com.lmax.disruptor.EventHandler;
import disruptor.common.generic.GenericEvent;

import java.util.concurrent.TimeUnit;

/**
 * disruptor事件处理类，每隔1秒钟消费一次
 * @param <T>
 */
public class MonitorEventHandler<T> implements EventHandler<GenericEvent> {

    protected String name;

    public MonitorEventHandler(String name) {
        this.name = name;
    }

    @Override
    public void onEvent(GenericEvent event, long sequence, boolean endOfBatch) throws Exception {
        TimeUnit.SECONDS.sleep(1);
        System.out.println("消费者EventHandler(" + name + ")" + ":" + event.get() + ":" + Thread.currentThread().getName());
    }

}
