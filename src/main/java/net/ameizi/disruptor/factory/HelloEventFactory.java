package net.ameizi.disruptor.factory;

import com.lmax.disruptor.EventFactory;
import net.ameizi.disruptor.event.HelloEvent;

public class HelloEventFactory implements EventFactory<HelloEvent> {

    @Override
    public HelloEvent newInstance() {
        return new HelloEvent();
    }

}
