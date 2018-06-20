package disruptor.common.generic;

import com.lmax.disruptor.EventFactory;

public class GenericEventFactory<T> implements EventFactory<GenericEvent<T>> {

    @Override
    public GenericEvent<T> newInstance() {
        return new GenericEvent();
    }

}
