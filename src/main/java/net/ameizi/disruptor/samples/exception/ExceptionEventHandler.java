package net.ameizi.disruptor.samples.exception;

import com.lmax.disruptor.EventHandler;
import disruptor.common.generic.GenericEvent;

/**
 * disruptor事件处理器，当接收到ex字符串时，手动抛出异常
 * @param <T>
 */
public class ExceptionEventHandler<T> implements EventHandler<GenericEvent<T>> {

    protected String name;

    public ExceptionEventHandler(String name) {
        this.name = name;
    }

    @Override
    public void onEvent(GenericEvent<T> event, long sequence, boolean endOfBatch) throws Exception {
        if ("ex".equals(event.get().toString())) {
            throw new RuntimeException("手动异常");
        }
        System.out.println("消费者EventHandler(" + name + ")" + ":消息内容->" + event.get() + ":" + Thread.currentThread().getName());
    }

}
