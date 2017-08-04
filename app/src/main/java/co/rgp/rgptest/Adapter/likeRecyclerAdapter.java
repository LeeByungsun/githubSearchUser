package co.rgp.rgptest.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.rgp.rgptest.R;
import co.rgp.rgptest.Vo.userItemVo;

/**
 * Created by bslee on 2017-08-03.
 */

public class likeRecyclerAdapter extends RecyclerView.Adapter<likeRecyclerAdapter.ViewHolder> {
    private static final String TAG = "likeRecyclerAdapter";
    private List<userItemVo> itemsList;
    private int itemLayout;
    private Context context;

    private ItemClick itemClick;

    /**
     * @param context
     * @param itemsList
     * @param itemLayout
     */
    public likeRecyclerAdapter(Context context, List<userItemVo> itemsList, int itemLayout) {
        this.itemsList = itemsList;
        this.itemLayout = itemLayout;
        this.context = context;
    }

    /**
     * 아이템 클릭시 실행 함수 등록 함수
     *
     * @param itemClick
     */
    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.e(TAG, "onBindViewHolder : " + position);
        final userItemVo item = itemsList.get(position);
        final int pos = position;
        int px;
        String avatarUrl = item.getAvatar_url();
        holder.login.setText(item.getLogin());
        holder.url.setText(item.getHtml_url());
        if (item.isLike()) {
            holder.like.setVisibility(View.VISIBLE);
        } else {
            holder.like.setVisibility(View.INVISIBLE);
        }
        holder.rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null) {
                    itemClick.onClick(itemsList, pos);
                }
            }
        });
        Picasso picasso = new Picasso.Builder(context)
                .memoryCache(new LruCache(24000))
                .build();
        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, context.getResources().getDisplayMetrics());
        picasso.with(context)
                .load(avatarUrl)
                .resize(px, px)
                .into(holder.avatar,
                        new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError() {

                            }
                        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    /**
     * ItemClick interface
     */
    public interface ItemClick {
        public void onClick(List<userItemVo> itemsList, int position);
    }

    /**
     * ViewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.login)
        TextView login;
        @BindView(R.id.url)
        TextView url;
        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.like)
        ImageView like;
        @BindView(R.id.itemview)
        RelativeLayout rowView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
