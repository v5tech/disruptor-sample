package net.ameizi.disruptor.samples.basic;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

public class LongEventProducerWithTranslator {

    private RingBuffer<LongEvent> ringBuffer;

    public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    private static final EventTranslatorOneArg<LongEvent, ByteBuffer> TRANSLATOR =
            new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
                @Override
                public void translateTo(LongEvent event, long sequence, ByteBuffer byteBuffer) {
                    event.setValue(byteBuffer.getLong(0));
                }
            };

    public void onData(ByteBuffer byteBuffer) {
        ringBuffer.publishEvent(TRANSLATOR, byteBuffer);
    }

}
