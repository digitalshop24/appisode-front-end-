package com.example.romanchuk.appisode.adapters.subscriptions.viewholder;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.example.romanchuk.appisode.R;
import com.example.romanchuk.appisode.view.AccentArcView;
import com.squareup.picasso.Picasso;

public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

    final Context Context;
    public final FrameLayout SubMainLayout;
    public final SwipeLayout swipe_layout;
    public final LinearLayout SubInfoLayout, SubDeleteLayout;
    public final RelativeLayout SubCircleLayout;
    public final ImageView imgSubsBackground, iconCallButton1, rotateLine, btnSubDelete1, btnSubDelete2, btnSubDelete3, btnSubRemove;
    public final AccentArcView imgArc;
    public final TextView textViewSubsName, textViewSubsNameOriginal, textViewSubsStatusLeft, textViewSubsCount, textViewSubsStatusRight;

    public RecyclerItemViewHolder(final View parent, Context context) {
        super(parent);
        this.Context = context;

        SubMainLayout = (FrameLayout) itemView.findViewById(R.id.SubMainLayout);
        swipe_layout = (SwipeLayout) itemView.findViewById(R.id.swipe_layout);
        SubInfoLayout = (LinearLayout) itemView.findViewById(R.id.SubInfoLayout);
        SubDeleteLayout = (LinearLayout) itemView.findViewById(R.id.SubDeleteLayout);
        SubCircleLayout = (RelativeLayout) itemView.findViewById(R.id.SubCircleLayout);
        imgSubsBackground = (ImageView) parent.findViewById(R.id.imgSubsBackground);
        btnSubDelete1 = (ImageView) parent.findViewById(R.id.btnSubDelete1);
        btnSubDelete2 = (ImageView) parent.findViewById(R.id.btnSubDelete2);
        btnSubDelete3 = (ImageView) parent.findViewById(R.id.btnSubDelete3);
        btnSubRemove = (ImageView) parent.findViewById(R.id.btnSubRemove);
        imgArc = (AccentArcView) parent.findViewById(R.id.imgArc);
        iconCallButton1 = (ImageView) parent.findViewById(R.id.iconCallButton1);
        rotateLine = (ImageView) parent.findViewById(R.id.rotateLine);

        textViewSubsName = (TextView) parent.findViewById(R.id.textViewSubsName);
        textViewSubsNameOriginal = (TextView) parent.findViewById(R.id.textViewSubsNameOriginal);
        textViewSubsStatusLeft = (TextView) parent.findViewById(R.id.textViewSubsStatusLeft);
        textViewSubsCount = (TextView) parent.findViewById(R.id.textViewSubsCount);
        textViewSubsStatusRight = (TextView) parent.findViewById(R.id.textViewSubsStatusRight);

        Typeface custom_font = Typeface.createFromAsset(parent.getContext().getAssets(), "bebas-neue-bold.ttf");

        textViewSubsName.setTypeface(custom_font);
        textViewSubsNameOriginal.setTypeface(custom_font);
        textViewSubsStatusLeft.setTypeface(custom_font);
        textViewSubsCount.setTypeface(custom_font);
        textViewSubsStatusRight.setTypeface(custom_font);
    }

    public void setSubsStatusLeft(CharSequence text) {
        textViewSubsStatusLeft.setText(text);
    }

    public void setSubsCount(CharSequence text) {
        textViewSubsCount.setText(text);
    }

    public void setSubsStatusRight(CharSequence text) {
        textViewSubsStatusRight.setText(text);
    }

    public void setSubsName(CharSequence text) {
        textViewSubsName.setText(text);
    }

    public void setSubsNameOriginal(CharSequence text) {
        textViewSubsNameOriginal.setText(text);
    }

    public void setVisibilityRotateLine(boolean show) {
        if (show)
            rotateLine.setVisibility(View.VISIBLE);
        else
            rotateLine.setVisibility(View.GONE);
    }

    public void setVisibilityArc(boolean show) {
        if (show)
            imgArc.setVisibility(View.VISIBLE);
        else
            imgArc.setVisibility(View.GONE);
    }

    public void setAngle(float angle) {
        imgArc.setAngle(angle);
    }

    public void setImageBackground(String text) {
        Picasso.with(Context)
                .load(text)
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder)
                .into(imgSubsBackground);
    }
}
