package net.ameizi.disruptor.samples.after;

import com.lmax.disruptor.dsl.Disruptor;
import disruptor.common.generic.BaseApp;
import disruptor.common.generic.GenericEvent;
import disruptor.common.generic.GenericEventHandler;

/**
 * disruptor after用法
 */
public class AfterApp extends BaseApp{

    public static void main(String[] args) {

        new AfterApp().run();

    }


    @Override
    public void addHandler(Disruptor<GenericEvent<String>> disruptor) {

        // 声明三个事件处理类
        GenericEventHandler eventHandler1 = new GenericEventHandler<>("eventHandler1");
        GenericEventHandler eventHandler2 = new GenericEventHandler<>("eventHandler2");
        GenericEventHandler eventHandler3 = new GenericEventHandler<>("eventHandler3");

        // 这行代码很重要，务必先注册，否则抛java.lang.IllegalArgumentException: The event handler disruptor.common.generic.GenericEventHandler@34340fab is not processing events.

        disruptor.handleEventsWith(eventHandler1,eventHandler2); // 并行

        // disruptor.handleEventsWith(eventHandler1).then(eventHandler2); // 串行

        // 确保eventHandler1、eventHandler2在eventHandler3之前执行，务必先注册eventHandler1,eventHandler2否则报错
        disruptor.after(eventHandler1,eventHandler2).handleEventsWith(eventHandler3);

    }
}
