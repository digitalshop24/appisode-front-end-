package com.example.romanchuk.appisode.adapters.search.viewholder;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.romanchuk.appisode.R;
import com.example.romanchuk.appisode.myapplication.MyApplication;
import com.squareup.picasso.Picasso;

public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

    Context Context;
    public ImageView imgSearchPoster, imgSearchIcon;
    public final TextView textViewName, textViewNameOriginal, textViewStatusLeft, textViewCount, textViewStatusRight, textViewStatusBottom;

    public RecyclerItemViewHolder(final View parent, Context context) {
        super(parent);

        this.Context = context;
        imgSearchPoster = (ImageView) parent.findViewById(R.id.imgSearchPoster);
        imgSearchIcon = (ImageView) parent.findViewById(R.id.imgSearchIcon);

        textViewStatusLeft = (TextView) parent.findViewById(R.id.textViewStatusLeft);
        textViewCount = (TextView) parent.findViewById(R.id.textViewCount);
        textViewStatusRight = (TextView) parent.findViewById(R.id.textViewStatusRight);
        textViewStatusBottom = (TextView) parent.findViewById(R.id.textViewStatusBottom);

        textViewNameOriginal = (TextView) parent.findViewById(R.id.textViewNameOriginal);
        textViewName = (TextView) parent.findViewById(R.id.textViewName);
        Typeface custom_font = Typeface.createFromAsset(parent.getContext().getAssets(),  "bebas-neue-bold.ttf");

        textViewStatusLeft.setTypeface(custom_font);
        textViewCount.setTypeface(custom_font);
        textViewStatusRight.setTypeface(custom_font);
        textViewStatusBottom.setTypeface(custom_font);
        textViewNameOriginal.setTypeface(custom_font);
        textViewName.setTypeface(custom_font);
    }

    public void setVisibilityCount(boolean show) {
        if (show)
            textViewCount.setVisibility(View.VISIBLE);
        else
            textViewCount.setVisibility(View.GONE);
    }

    public void setVisibilityStatusRight(boolean show) {
        if (show)
            textViewStatusRight.setVisibility(View.VISIBLE);
        else
            textViewStatusRight.setVisibility(View.GONE);
    }

    public void setVisibilityStatusBottom(boolean show) {
        if (show)
            textViewStatusBottom.setVisibility(View.VISIBLE);
        else
            textViewStatusBottom.setVisibility(View.GONE);
    }

    public void setStatusLeft(CharSequence text) {
        textViewStatusLeft.setText(text);
    }
    public void setCount(CharSequence text) {
        textViewCount.setText(text);
    }
    public void setStatusRight(CharSequence text) {
        textViewStatusRight.setText(text);
    }
    public void setStatusBottom(CharSequence text) {
        textViewStatusBottom.setText(text);
    }
    public void setNameOriginal(CharSequence text) {
        if (text.length() > 45)
            text=text.toString().substring(0,45) + "...";;
        textViewNameOriginal.setText(text);
    }
    public void setName(CharSequence text) {
        if (text.length() > 45)
            text=text.toString().substring(0,45) + "...";
        textViewName.setText(text);
    }

    public void setIconBackGround(Integer show_id) {
        if (MyApplication.showInSubs(show_id))
            imgSearchIcon.setBackgroundDrawable(Context.getResources().getDrawable(R.drawable.search_icon_subs));
        else
            imgSearchIcon.setBackgroundDrawable(Context.getResources().getDrawable(R.drawable.search_icon));
    }

    public void setPoster(String text) {
        Picasso.with(Context)
                .load(text)
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder)
                .into(imgSearchPoster);
    }
}
