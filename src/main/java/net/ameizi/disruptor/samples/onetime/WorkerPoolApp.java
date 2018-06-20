package net.ameizi.disruptor.samples.onetime;

import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import disruptor.common.generic.BaseApp;
import disruptor.common.generic.GenericEvent;

/**
 * WorkerPool模式处理消息事件，一次只触发一个WorkHandler
 */
public class WorkerPoolApp extends BaseApp{

    public static void main(String[] args) {
        new WorkerPoolApp().run();
    }

    @Override
    public void addHandler(Disruptor<GenericEvent<String>> disruptor) {

        disruptor.handleEventsWithWorkerPool(new MyWorkHandler<>("workHandler-1"),
                new MyWorkHandler<>("workHandler-2"),
                new MyWorkHandler<>("workHandler-3"),
                new MyWorkHandler<>("workHandler-4"),
                new MyWorkHandler<>("workHandler-5"));

        // 必须像上面那样一次将所有的WorkHandler添加进去，才可以使每个WorkHandler执行一次，如使用下面的方法，在接收到事件后会触发每一个WorkHandler
        // disruptor.handleEventsWithWorkerPool(new MyWorkHandler<>("workHandler-1"))
        //         .handleEventsWithWorkerPool(new MyWorkHandler<>("workHandler-2"))
        //         .handleEventsWithWorkerPool(new MyWorkHandler<>("workHandler-3"))
        //         .handleEventsWithWorkerPool(new MyWorkHandler<>("workHandler-4"))
        //         .handleEventsWithWorkerPool(new MyWorkHandler<>("workHandler-5"));
    }

    private class MyWorkHandler<T> implements WorkHandler<GenericEvent<T>> {

        private String name;

        public MyWorkHandler(String name) {
            this.name = name;
        }

        @Override
        public void onEvent(GenericEvent<T> event) throws Exception {
            System.out.println("消费者workHandler(" + name + ")" + ":" + event.get() + ":" + Thread.currentThread().getName());
        }

    }

}
