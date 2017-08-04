package co.rgp.rgptest.EventBus;

import co.rgp.rgptest.Vo.userItemVo;

/**
 * Created by bslee on 2017-08-04.
 */

public class EventData {
    // the pushlist object being sent using the bus
    private co.rgp.rgptest.Vo.userItemVo item;

    public EventData(userItemVo item) {
        this.item = item;
    }

    /**
     * @return the pushlist
     */
    public userItemVo getItem() {
        return item;
    }
}
