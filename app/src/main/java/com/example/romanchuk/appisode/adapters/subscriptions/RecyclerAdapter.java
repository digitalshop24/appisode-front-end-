package com.example.romanchuk.appisode.adapters.subscriptions;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.example.romanchuk.appisode.MainActivity;
import com.example.romanchuk.appisode.R;
import com.example.romanchuk.appisode.adapters.subscriptions.viewholder.ProgressViewHolder;
import com.example.romanchuk.appisode.adapters.subscriptions.viewholder.RecyclerItemViewHolder;
import com.example.romanchuk.appisode.models.SubscriptionsItem;
import com.example.romanchuk.appisode.myapplication.MyApplication;
import com.example.romanchuk.appisode.tasks.CheckSubscriptions;
import com.example.romanchuk.appisode.tasks.TaskUnsubscribe;
import com.example.romanchuk.appisode.tools.InternetConnection;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private RecyclerView recyclerView;
    private ArrayList<SubscriptionsItem> SubscriptionItems;
    private ArrayList<SubscriptionsItem> SubscriptionItemsPendingRemoval;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private int visibleThreshold = 2;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    private static final int PENDING_REMOVAL_TIMEOUT = 4000;

    private int showsId = 0;
    private Handler handler = new Handler();
    HashMap<SubscriptionsItem, Runnable> pendingRunnables = new HashMap<>();

    public RecyclerAdapter(Context context, ArrayList<SubscriptionsItem> mSubscriptionItems, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        SubscriptionItems = mSubscriptionItems;
        SubscriptionItemsPendingRemoval = new ArrayList<>();

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

    public void setSubscriptions(ArrayList<SubscriptionsItem> listShows) {

        for (int i = 0; i < listShows.size(); i++) {
            SubscriptionItems.add(listShows.get(i));
            notifyItemInserted(SubscriptionItems.size());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.subscription_item, parent, false);
//            view.setOnClickListener(mOnClickListener);
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
            final RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;

            holder.SubCircleLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (holder.swipe_layout.getOpenStatus() == SwipeLayout.Status.Open) {
                        holder.swipe_layout.close(true);
                    }
                    else {
                        showsId = SubscriptionItems.get(position).getShow().getId();

                        if (showsId != -1) {
                            ((MainActivity) context).setPageItem(showsId);
                        }
                    }
                }
            });
            holder.SubInfoLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.swipe_layout.getOpenStatus() == SwipeLayout.Status.Open) {
                        holder.swipe_layout.close(true);
                    }
                    else {
                        showsId = SubscriptionItems.get(position).getShow().getId();

                        if (showsId != -1) {
                            ((MainActivity) context).setPageItem(showsId);
                        }
                    }
                }
            });
            holder.SubDeleteLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.swipe_layout.open(true);
                }
            });
            holder.btnSubRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemRemove(holder, recyclerView);
                }
            });
            holder.setSubsStatusLeft(SubscriptionItems.get(position).getSubsStatusLeft());
            holder.setSubsCount(SubscriptionItems.get(position).getSubsCount());
            holder.setSubsStatusRight(SubscriptionItems.get(position).getSubsStatusRight());
            holder.setSubsName(SubscriptionItems.get(position).getName());
            holder.setSubsNameOriginal(SubscriptionItems.get(position).getName_original());
            holder.setVisibilityRotateLine(SubscriptionItems.get(position).needRotateLine());
            if (!SubscriptionItems.get(position).needRotateLine()) {
                holder.setVisibilityArc(true);
                holder.setAngle(SubscriptionItems.get(position).getShowSchedule());
            } else
                holder.setVisibilityArc(false);
            holder.setImageBackground(SubscriptionItems.get(position).getShow().getSubscription_image());
        } else if (viewHolder instanceof ProgressViewHolder) {
            ProgressViewHolder loadingViewHolder = (ProgressViewHolder) viewHolder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }


    }

    @Override
    public int getItemViewType(int position) {
        return SubscriptionItems.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void add(SubscriptionsItem newItem, int position) {
        SubscriptionItems.add(position, newItem);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return SubscriptionItems == null ? 0 : SubscriptionItems.size();
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

    public void onItemRemove(final RecyclerView.ViewHolder viewHolder, final RecyclerView recyclerView) {
        final int adapterPosition = viewHolder.getAdapterPosition();
        final SubscriptionsItem item = SubscriptionItems.get(adapterPosition);
        Snackbar snackbar = Snackbar
                .make(recyclerView, this.context.getResources().getString(R.string.subs_delete), Snackbar.LENGTH_LONG)
                .setAction(this.context.getResources().getString(R.string.cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        undo(adapterPosition, item);
                        recyclerView.scrollToPosition(adapterPosition);
                    }
                });
        snackbar.show();
        pendingRemoval(adapterPosition);
    }

    public void undo(int position, SubscriptionsItem item) {
        Runnable pendingRemovalRunnable = pendingRunnables.get(item);
        pendingRunnables.remove(item);
        if (pendingRemovalRunnable != null) handler.removeCallbacks(pendingRemovalRunnable);
        SubscriptionItemsPendingRemoval.remove(item);
        SubscriptionItems.add(position, item);
        notifyItemInserted(position);
    }

    public void pendingRemoval(int position) {
        final SubscriptionsItem item = SubscriptionItems.get(position);
        SubscriptionItems.remove(position);
        notifyItemRemoved(position);
        if (!SubscriptionItemsPendingRemoval.contains(item)) {
            SubscriptionItemsPendingRemoval.add(item);
            notifyItemChanged(position);
            Runnable pendingRemovalRunnable = new Runnable() {
                @Override
                public void run() {
                    remove(item);
                }
            };
            handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
            pendingRunnables.put(item, pendingRemovalRunnable);
        }
    }

    public void remove(int position) {
        SubscriptionsItem item = SubscriptionItems.get(position);
        if (SubscriptionItemsPendingRemoval.contains(item)) {
            SubscriptionItemsPendingRemoval.remove(item);
        }
        if (SubscriptionItems.contains(item)) {
            if (InternetConnection.checkConnection(context)) {
                new TaskUnsubscribe(context, item.getId(), item.getShow().getId()).execute();
                new CheckSubscriptions(context).execute();
            }
            SubscriptionItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void remove(SubscriptionsItem item) {
        if (InternetConnection.checkConnection(context)) {
            MyApplication.removeSubsId(item.getShow().getId());
            new TaskUnsubscribe(context, item.getId(), item.getShow().getId()).execute();
            new CheckSubscriptions(context).execute();
        } else {
            Toast toast = Toast.makeText(context, context.getResources().getString(R.string.no_connection), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.show();
        }
    }

    public boolean isPendingRemoval(int position) {
        SubscriptionsItem item = SubscriptionItems.get(position);
        return SubscriptionItemsPendingRemoval.contains(item);
    }
}
