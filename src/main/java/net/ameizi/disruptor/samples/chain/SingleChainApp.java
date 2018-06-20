package net.ameizi.disruptor.samples.chain;

import com.lmax.disruptor.dsl.Disruptor;
import disruptor.common.generic.BaseApp;
import disruptor.common.generic.GenericEvent;
import disruptor.common.generic.GenericEventHandler;

/**
 * disruptor链式依赖，单链。严格遵守step-1->step-2->step-3事件处理顺序
 *
 */
public class SingleChainApp extends BaseApp{

    public static void main(String[] args) {
        new SingleChainApp().run();
    }

    @Override
    public void addHandler(Disruptor<GenericEvent<String>> disruptor) {
        disruptor.handleEventsWith(new GenericEventHandler<>("step-1"))
                .then(new GenericEventHandler<>("step-2")) // then
                .then(new GenericEventHandler<>("step-3")); // then
    }

}
