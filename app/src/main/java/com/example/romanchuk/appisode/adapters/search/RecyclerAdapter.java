package com.example.romanchuk.appisode.adapters.search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.romanchuk.appisode.R;
import com.example.romanchuk.appisode.SearchResultsActivity;
import com.example.romanchuk.appisode.adapters.search.viewholder.ProgressViewHolder;
import com.example.romanchuk.appisode.adapters.search.viewholder.RecyclerItemViewHolder;
import com.example.romanchuk.appisode.models.ShowsItem;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    private ArrayList<ShowsItem> Shows;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private int visibleThreshold = 9;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    RecyclerView recyclerView;

    final int DIALOG = 1;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildLayoutPosition(v);
            int showsId = Shows.get(itemPosition).getId();

//            if (showsId != -1) {
//                ((SearchResultsActivity) context).onBackPressed();
//            }
            if (showsId != -1) {

                ((SearchResultsActivity) context).finishWithResult(showsId);
//                Intent intent = new Intent(context, SerialDetailActivity.class);
//                intent.putExtra("showsId", showsId); //Optional parameters
//                context.startActivity(intent);
            }
        }
    };

    public RecyclerAdapter(Context context, ArrayList<ShowsItem> mShows, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        Shows = mShows;
        if(recyclerView.getLayoutManager() instanceof LinearLayoutManager){
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if(!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)){
                        if(onLoadMoreListener != null){
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    public void swapData(ArrayList<ShowsItem> listShows) {
        Shows = listShows;
        notifyDataSetChanged();
    }

    public void clearShows() {
        int size = this.Shows.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.Shows.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    public void setShows(ArrayList<ShowsItem> listShows) {

        for (int i = 0; i < listShows.size(); i++) {
            Shows.add(listShows.get(i));
            notifyItemInserted(Shows.size());
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.search_item, parent, false);
            view.setOnClickListener(mOnClickListener);
            return new RecyclerItemViewHolder(view, parent.getContext());
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(context).inflate(R.layout.progress_item, parent, false);
            return new ProgressViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        if(viewHolder instanceof RecyclerItemViewHolder){
            final RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;

            holder.setStatusLeft(Shows.get(position).getSearchStatusLeft());
            if (Shows.get(position).getSearchCount() != null && Shows.get(position).getSearchCount().length() > 0){
                holder.setCount(Shows.get(position).getSearchCount());
                holder.setVisibilityCount(true);
            }
            else
                holder.setVisibilityCount(false);
            if (Shows.get(position).getSearchStatusRight() != null && Shows.get(position).getSearchStatusRight().length() > 0) {
                holder.setStatusRight(Shows.get(position).getSearchStatusRight());
                holder.setVisibilityStatusRight(true);
            }
            else
                holder.setVisibilityStatusRight(false);

            if (Shows.get(position).isShowClosed()){
                holder.setStatusBottom(Shows.get(position).getSearchStatusBottom());
                holder.setVisibilityStatusBottom(true);
            }
            else
                holder.setVisibilityStatusBottom(false);

            holder.setNameOriginal(Shows.get(position).getName_original());
            holder.setName(Shows.get(position).getName());
            holder.setPoster(Shows.get(position).getPoster());
            holder.setIconBackGround(Shows.get(position).getId());
        }else if (viewHolder instanceof ProgressViewHolder) {
            ProgressViewHolder loadingViewHolder = (ProgressViewHolder) viewHolder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return Shows.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return Shows == null ? 0 : Shows.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener){
        this.onLoadMoreListener = onLoadMoreListener;
    }
    public interface OnLoadMoreListener {
        void onLoadMore();
    }
    public void setLoaded(boolean b) {
        loading = b;
    }
}
