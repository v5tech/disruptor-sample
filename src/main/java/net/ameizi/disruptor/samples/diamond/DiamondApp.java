package net.ameizi.disruptor.samples.diamond;

import com.lmax.disruptor.dsl.Disruptor;
import disruptor.common.generic.BaseApp;
import disruptor.common.generic.GenericEvent;
import disruptor.common.generic.GenericEventHandler;

/**
 * 菱形事件处理
 *
 * step1-1、step1-2为并行执行。待step1-1、step1-2执行完接着执行step2
 *
 */
public class DiamondApp extends BaseApp{

    public static void main(String[] args) {
        new DiamondApp().run();
    }

    @Override
    public void addHandler(Disruptor<GenericEvent<String>> disruptor) {
        disruptor.handleEventsWith(
                new GenericEventHandler<>("step1-1"),
                new GenericEventHandler<>("step1-2")
        ).then(new GenericEventHandler<>("step2")); // 注意这里使用的是then
    }

}
