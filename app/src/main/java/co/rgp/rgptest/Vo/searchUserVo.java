package co.rgp.rgptest.Vo;

import java.util.ArrayList;

/**
 * Created by bslee on 2017-08-03.
 */

public class searchUserVo {
    int total_count;
    boolean incomplete_results;
    ArrayList<co.rgp.rgptest.Vo.userItemVo> items;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public ArrayList<co.rgp.rgptest.Vo.userItemVo> getItems() {
        return items;
    }

    public void setItems(ArrayList<co.rgp.rgptest.Vo.userItemVo> items) {
        this.items = items;
    }

    public searchUserVo(int total_count, boolean incomplete_results, ArrayList<co.rgp.rgptest.Vo.userItemVo> items) {

        this.total_count = total_count;
        this.incomplete_results = incomplete_results;
        this.items = items;
    }
}
