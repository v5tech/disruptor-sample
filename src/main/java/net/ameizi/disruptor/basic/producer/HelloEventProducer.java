package net.ameizi.disruptor.basic.producer;

import com.lmax.disruptor.RingBuffer;
import net.ameizi.disruptor.basic.event.HelloEvent;

import java.nio.ByteBuffer;

public class HelloEventProducer {

    private final RingBuffer<HelloEvent> ringBuffer;

    public HelloEventProducer(RingBuffer<HelloEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(ByteBuffer byteBuffer)
    {
        long sequence = ringBuffer.next();
        try{
            HelloEvent event = ringBuffer.get(sequence);
            event.setValue(String.valueOf(byteBuffer.getLong(0)));
        }finally{
            ringBuffer.publish(sequence);
        }
    }

}
