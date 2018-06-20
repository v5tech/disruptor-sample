package disruptor.common.generic;

import com.lmax.disruptor.RingBuffer;

public class GenericEventProducer<T> {

    private final RingBuffer<GenericEvent<T>> ringBuffer;

    public GenericEventProducer(RingBuffer<GenericEvent<T>> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(T data) {
        long sequence = ringBuffer.next();
        try {
            GenericEvent<T> event = ringBuffer.get(sequence);
            event.set(data);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
