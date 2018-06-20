package net.ameizi.disruptor.samples.exception;

import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.dsl.Disruptor;
import disruptor.common.generic.BaseApp;
import disruptor.common.generic.GenericEvent;

/**
 * 模拟disruptor异常处理
 */
public class SimulationExceptionApp extends BaseApp{

    public static void main(String[] args) {
        new SimulationExceptionApp().run();
    }

    @Override
    public void addHandler(Disruptor<GenericEvent<String>> disruptor) {

        disruptor.handleEventsWith(new ExceptionEventHandler<String>("ExceptionEventHandler"));

        disruptor.setDefaultExceptionHandler(new ExceptionHandler<GenericEvent<String>>() {
            @Override
            public void handleEventException(Throwable ex, long sequence, GenericEvent<String> event) {
                System.err.println("处理异常:" + event);
                ex.printStackTrace();
            }

            @Override
            public void handleOnStartException(Throwable ex) {
                System.err.println("启动时产生异常:" + ex.getMessage());
                ex.printStackTrace();
            }

            @Override
            public void handleOnShutdownException(Throwable ex) {
                System.err.println("停止时产生异常:" + ex.getMessage());
                ex.printStackTrace();
            }
        });

    }

}
