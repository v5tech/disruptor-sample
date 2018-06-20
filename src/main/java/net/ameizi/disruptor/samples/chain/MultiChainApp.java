package net.ameizi.disruptor.samples.chain;

import com.lmax.disruptor.dsl.Disruptor;
import disruptor.common.generic.BaseApp;
import disruptor.common.generic.GenericEvent;
import disruptor.common.generic.GenericEventHandler;

/**
 * disruptor链式依赖，多个链。
 * chain1和chain2之间没有依赖关系，并行执行，但chain1中的step1-1和step1-2有依赖关系顺序执行。chain2同理
 */
public class MultiChainApp extends BaseApp {

    public static void main(String[] args) {
        new MultiChainApp().run();
    }

    @Override
    public void addHandler(Disruptor<GenericEvent<String>> disruptor) {
        // chain1
        disruptor.handleEventsWith(new GenericEventHandler<String>("step1-1"))
                .then(new GenericEventHandler<String>("step1-2"));
        // chain2
        disruptor.handleEventsWith(new GenericEventHandler<String>("step2-1"))
                .then(new GenericEventHandler<String>("step2-2"));
    }

}
