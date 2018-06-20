package disruptor.common.generic;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.Scanner;
import java.util.concurrent.Executors;

/**
 * 抽象基类
 */
public abstract class BaseApp {

    private static final int BUFFER_SIZE = 1024;

    public final void run(){

        GenericEventFactory<GenericEvent<String>> eventFactory = new GenericEventFactory<>();

        Disruptor<GenericEvent<String>> disruptor = new Disruptor(eventFactory, BUFFER_SIZE, Executors.defaultThreadFactory());

        addHandler(disruptor);

        disruptor.start();

        RingBuffer<GenericEvent<String>> ringBuffer = disruptor.getRingBuffer();

        doAfterDisruptorStart(ringBuffer);

        GenericEventProducer<String> producer = new GenericEventProducer(ringBuffer);

        for (; ;) {
            Scanner scan = new Scanner(System.in);
            String msg = scan.nextLine();
            if ("exit".equals(msg)) {
                System.exit(0);
            }
            producer.onData(msg);
        }

    }


    protected void doAfterDisruptorStart(final RingBuffer<GenericEvent<String>> ringBuffer) {

    }

    public abstract void addHandler(Disruptor<GenericEvent<String>> disruptor);

}
