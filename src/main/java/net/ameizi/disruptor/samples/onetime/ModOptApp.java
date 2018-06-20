package net.ameizi.disruptor.samples.onetime;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import disruptor.common.generic.BaseApp;
import disruptor.common.generic.GenericEvent;

/**
 * 采用取模运算的方式确保一次只被一个handler处理
 */
public class ModOptApp extends BaseApp{


    public static void main(String[] args) {
        new  ModOptApp().run();
    }

    @Override
    public void addHandler(Disruptor<GenericEvent<String>> disruptor) {

        //批量构建一批handler
        int handlerCounts = 10;
        ModOptEventHandler[] handlers = new ModOptEventHandler[handlerCounts];
        for (int i = 0; i < handlerCounts; i++) {
            handlers[i] = new ModOptEventHandler("[ModOptEventHandler-" + i + "]", i, handlerCounts);
        }
        disruptor.handleEventsWith(handlers);

    }


    private class ModOptEventHandler<T> implements EventHandler<GenericEvent<T>> {

        private final long ordinal;

        private final long numberOfConsumers;

        private String name;

        public ModOptEventHandler(String name, long ordinal, long numberOfConsumers) {
            this.name = name;
            this.ordinal = ordinal;
            this.numberOfConsumers = numberOfConsumers;
        }

        @Override
        public void onEvent(GenericEvent<T> event, long sequence, boolean endOfBatch) throws Exception {
            if ((sequence % numberOfConsumers) != ordinal){
                // 限定只能是自己的EventHandler来处理自己的Event
                return;
            }
            System.out.println("消费者EventHandler(" + this.name + ")" + ":" + event.get()+":sequence:"+sequence + ":" + Thread.currentThread().getName());
        }

    }
}
