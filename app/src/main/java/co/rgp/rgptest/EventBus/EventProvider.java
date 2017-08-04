package co.rgp.rgptest.EventBus;

import com.squareup.otto.Bus;

/**
 * Created by bslee on 2017-08-04.
 */

public final class EventProvider {
    private static final Bus event = new Bus();

    private EventProvider() {
        // No instances.
    }

    public static Bus getInstance() {
        return event;
    }
}
