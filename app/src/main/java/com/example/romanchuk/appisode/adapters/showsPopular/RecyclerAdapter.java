package com.example.romanchuk.appisode.adapters.showsPopular;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.romanchuk.appisode.R;
import com.example.romanchuk.appisode.ShowsIdFragment;
import com.example.romanchuk.appisode.adapters.showsPopular.viewholder.ProgressViewHolder;
import com.example.romanchuk.appisode.adapters.showsPopular.viewholder.RecyclerItemViewHolder;
import com.example.romanchuk.appisode.models.ShowDetailItem;
import com.example.romanchuk.appisode.models.ShowsItem;
import com.example.romanchuk.appisode.tasks.LoadShowsId;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<ShowsItem> Shows;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private int visibleThreshold = 9;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    private ShowsIdFragment popupDialog;
    private RecyclerView recyclerView;

    public RecyclerAdapter(Context context, ArrayList<ShowsItem> mShows, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        Shows = mShows;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    public void insertShow(ShowsItem Show)
    {
        Shows.add(0, Show);
        notifyItemChanged(0);
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

    public void setShows(ArrayList<ShowsItem> listShows)
    {
        for (int i = 0; i < listShows.size(); i++) {
            Shows.add(listShows.get(i));
            notifyItemInserted(Shows.size());
        }
    }

    public void dataSetRefresh() {
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.show_item, parent, false);
            return new RecyclerItemViewHolder(view, parent.getContext());
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(context).inflate(R.layout.progress_item, parent, false);
            return new ProgressViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof RecyclerItemViewHolder) {
            RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;
            holder.setAirDateDetailedMonths(Shows.get(position).getEpisode_air_date_detailed_months());
            holder.setAirDateDetailedDays(Shows.get(position).getEpisode_air_date_detailed_days());
            holder.setAirDateDetailedHours(Shows.get(position).getEpisode_air_date_detailed_hours());
            holder.setEpisodeNumberAired(Shows.get(position).getEpisode_number_aired());
            holder.setEpisodeAired(Shows.get(position).getEpisode_aired());
            holder.setAirDate(Shows.get(position).getEpisode_air_date());

            holder.setNameOriginal(Shows.get(position).getName_original());
            holder.setName(Shows.get(position).getName());
            holder.setPoster(Shows.get(position).getPoster());
            holder.setBackGround(Shows.get(position).getId());

            holder.iconCallButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    recyclerView.smoothScrollToPosition(position);
                    ShowDetailItem showItem = null;
                    try {
                        showItem = new LoadShowsId(Shows.get(position).getId()).execute().get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    popupDialog = new ShowsIdFragment(v.getContext(), showItem);
                    popupDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            notifyItemChanged(position);
                        }
                    });
                    popupDialog.show();
                }
            });
            holder.iconCallButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    recyclerView.smoothScrollToPosition(position);
                    ShowDetailItem showItem = null;
                    try {
                        showItem = new LoadShowsId(Shows.get(position).getId()).execute().get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    popupDialog = new ShowsIdFragment(v.getContext(), showItem);
                    popupDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            notifyItemChanged(position);
                        }
                    });
                    popupDialog.show();
                }
            });
            Animation anim = AnimationUtils.loadAnimation(context, R.anim.rotate_call);
            holder.iconCallButton.setAnimation(anim);
            holder.iconCallButton.startAnimation(anim);
        } else if (viewHolder instanceof ProgressViewHolder) {
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

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoaded() {
        loading = false;
    }
}
