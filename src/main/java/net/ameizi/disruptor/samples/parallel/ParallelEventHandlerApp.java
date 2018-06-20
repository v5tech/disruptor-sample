package net.ameizi.disruptor.samples.parallel;

import com.lmax.disruptor.dsl.Disruptor;
import disruptor.common.generic.BaseApp;
import disruptor.common.generic.GenericEvent;
import disruptor.common.generic.GenericEventHandler;

/**
 * disruptor并行事件处理。多个事件处理器之间是并行的，没有顺序性
 */
public class ParallelEventHandlerApp extends BaseApp {

    public static void main(String[] args) {
        new ParallelEventHandlerApp().run();
    }

    @Override
    public void addHandler(Disruptor<GenericEvent<String>> disruptor) {
        disruptor.handleEventsWith(
                new GenericEventHandler("step-1"),
                new GenericEventHandler("step-2"),
                new GenericEventHandler("step-3")
        );
    }

}
