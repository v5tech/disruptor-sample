package net.ameizi.disruptor.basic.handler;

import com.lmax.disruptor.EventHandler;
import net.ameizi.disruptor.basic.event.HelloEvent;

public class HelloEventHandler implements EventHandler<HelloEvent> {

    @Override
    public void onEvent(HelloEvent event, long sequence, boolean endOfBatch) throws Exception {
        // 实际开发应用中的业务处理逻辑代码，发送消息到MQ、存储数据到数据库等
        System.out.println("Event: " + event.getValue());
    }

}
