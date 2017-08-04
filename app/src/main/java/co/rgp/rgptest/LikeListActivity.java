package co.rgp.rgptest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.rgp.rgptest.Adapter.likeRecyclerAdapter;
import co.rgp.rgptest.EventBus.EventData;
import co.rgp.rgptest.EventBus.EventProvider;
import co.rgp.rgptest.Vo.userItemVo;

/**
 * Created by bslee on 2017-08-03.
 */

public class LikeListActivity extends AppCompatActivity {
    private static final String TAG = "LikeListActivity";

    @BindView(R.id.search_text)
    EditText search_text;
    @BindView(R.id.search_btn)
    ImageButton search_btn;
    @BindView(R.id.result_recycleView)
    RecyclerView like_recyclerView;
    @BindView(R.id.switchBtn)
    Button swtichbtn;
    ArrayList<userItemVo> userItemList = new ArrayList<userItemVo>();
    private Context ctx = null;
    private likeRecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ctx = this;
        search_btn.setVisibility(View.GONE);
        search_text.setVisibility(View.GONE);
        swtichbtn.setVisibility(View.GONE);

        Intent i = getIntent();
        userItemList = (ArrayList<userItemVo>) i.getSerializableExtra("userdata");
        Log.i(TAG, "size : " + userItemList.size());
        Glide.get(this).setMemoryCategory(MemoryCategory.HIGH);
        setRecyclerView();
    }

    private void setRecyclerView() {
        if (adapter == null) {
            adapter = new likeRecyclerAdapter(ctx, userItemList, R.layout.user_item);
            like_recyclerView.setLayoutManager(new LinearLayoutManager(this));
            like_recyclerView.setItemAnimator(new DefaultItemAnimator());
            like_recyclerView.setAdapter(adapter);
            like_recyclerView.setVisibility(View.VISIBLE);
        }
        adapter.setItemClick(new likeRecyclerAdapter.ItemClick() {
            @Override
            public void onClick(List<userItemVo> itemsList, int position) {
                Log.i(TAG,"position = "+position);
                Log.i(TAG,"login  = "+userItemList.get(position).getLogin());
                if(userItemList.get(position).isLike()){
                    userItemList.get(position).setLike(false);
                }else{
                    userItemList.get(position).setLike(true);
                }
                EventProvider.getInstance().post(new EventData(userItemList.get(position)));
                adapter.notifyDataSetChanged();
            }
        });
        adapter.notifyDataSetChanged();
    }
}
