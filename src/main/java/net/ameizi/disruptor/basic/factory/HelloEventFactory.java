package net.ameizi.disruptor.basic.factory;

import com.lmax.disruptor.EventFactory;
import net.ameizi.disruptor.basic.event.HelloEvent;

public class HelloEventFactory implements EventFactory<HelloEvent> {

    @Override
    public HelloEvent newInstance() {
        return new HelloEvent();
    }

}
