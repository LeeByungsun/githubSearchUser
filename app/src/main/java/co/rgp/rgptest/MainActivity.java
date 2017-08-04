package co.rgp.rgptest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.rgp.rgptest.Adapter.searchRecyclerAdapter;
import co.rgp.rgptest.EventBus.EventData;
import co.rgp.rgptest.EventBus.EventProvider;
import co.rgp.rgptest.Vo.searchUserVo;
import co.rgp.rgptest.Vo.userItemVo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.search_text)
    EditText search_text;
    @BindView(R.id.search_btn)
    ImageButton search_btn;
    @BindView(R.id.result_recycleView)
    RecyclerView result_recyclerView;
    @BindView(R.id.switchBtn)
    Button switchBtn;
    int currentPage = 1;
    ArrayList<userItemVo> userItemList = new ArrayList<userItemVo>();
    private Context ctx = null;
    private searchRecyclerAdapter adapter;
    private int itemTotalCount = 0;
    private int totalCount;
    private String searchUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ctx = this;
        EventProvider.getInstance().register(this);
        scrollCheck();


    }


    @OnClick(R.id.search_btn)
    public void search() {
        String user = search_text.getText().toString();
        if (user.length() == 0) {
            shakeView(search_text, this);
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(search_text.getWindowToken(), 0);
            this.searchUser = user;
            getSearchUserData();
        }
    }

    @OnClick(R.id.switchBtn)
    public void switchB() {
        ArrayList<userItemVo> item;
        search_text.setText("");
        Intent i = new Intent(ctx, LikeListActivity.class);
        Bundle bundle = new Bundle();
        item = getLikeItem();
        bundle.putSerializable("userdata", item);
        Log.v(TAG, "size : " + item.size());
        i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        EventProvider.getInstance().unregister(this);
        super.onDestroy();
    }

    private void getSearchUserData() {


        Call<searchUserVo> call = ApplicationClass.apiInfo_GithubServer.searchUser(searchUser, currentPage, 20);
        call.enqueue(new Callback<searchUserVo>() {
            @Override
            public void onResponse(Call<searchUserVo> call, Response<searchUserVo> response) {
                if (userItemList.size() == 0) {
                    userItemList = response.body().getItems();
                } else {
                    userItemList.addAll(response.body().getItems());
                }
                totalCount = +response.body().getTotal_count();
                int size = userItemList.size();
                Log.e(TAG, "size = " + size);
                Log.e(TAG, "total count = " + totalCount);
                currentPage++;
                setRecyclerView();
            }

            @Override
            public void onFailure(Call<searchUserVo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * 입력값 누락 시, 뷰 흔들어서 알려주는 애니메이션 유틸
     *
     * @param view
     * @param context
     */
    private void shakeView(View view, Context context) {
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));
    }

    private void setRecyclerView() {
        if (adapter == null) {
            adapter = new searchRecyclerAdapter(ctx, userItemList, R.layout.user_item);
            result_recyclerView.setLayoutManager(new LinearLayoutManager(this));
            result_recyclerView.setItemAnimator(new DefaultItemAnimator());
            result_recyclerView.setAdapter(adapter);
            result_recyclerView.setVisibility(View.VISIBLE);
//            adapter.setOnItemClickListener(new searchRecyclerAdapter.ClickListener() {
//                @Override
//                public void onItemClick(int position, View v) {
//                    Log.e(TAG, "postion = " + position);
//                }
//            });
            adapter.setItemClick(new searchRecyclerAdapter.ItemClick() {
                @Override
                public void onClick(List<userItemVo> itemsList, int position) {
                    Log.e(TAG, "position = " + position);
                    Log.e(TAG, "login  = " + userItemList.get(position).getLogin());
                    userItemList.get(position).setLike(true);
                    userItemList.get(position).setPostion(position);
                }
            });
        }
        if (userItemList.size() != 0) {
            result_recyclerView.scrollToPosition(itemTotalCount);
        }

        adapter.notifyDataSetChanged();

    }

    private void scrollCheck() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        result_recyclerView.setLayoutManager(layoutManager);
        result_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();

                itemTotalCount = recyclerView.getAdapter().getItemCount() - 1;
                Log.e(TAG, "lastVisibleItemPosition =" + lastVisibleItemPosition);
                if (lastVisibleItemPosition != -1) {
                    if (lastVisibleItemPosition == itemTotalCount) {
                        getSearchUserData();

                    } else {
                        Log.e(TAG, "Last Position");
                    }
                }
            }

        });
    }

    private ArrayList<userItemVo> getLikeItem() {
        ArrayList<userItemVo> likeItem = new ArrayList<userItemVo>();
        for (userItemVo item : userItemList) {
            if (item.isLike()) {
                likeItem.add(item);
            }
        }
        return likeItem;

    }

    @Subscribe
    public void changeLike(EventData changeItem) {
        Log.e(TAG, "chage Item");
        boolean like = changeItem.getItem().isLike();
        int position = changeItem.getItem().getPostion();
        userItemList.get(position).setLike(like);
        adapter.notifyDataSetChanged();

    }


}