package com.example.romanchuk.appisode.adapters.showsPopular.viewholder;

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
    public ImageView iconCallButton, iconCallButton1, imgMyImage;
    public TextView textViewNameOriginal, textViewAirDateDetailedMonths, textViewAirDateDetailedDays, textViewAirDateDetailedHours, textView3, textView4, textView5;
    public TextView textViewName, textViewEpisodeNumberAired, textViewEpisodeAired, textViewAirDate;

    public RecyclerItemViewHolder(final View parent, Context context) {
        super(parent);

        this.Context = context;
        iconCallButton = (ImageView) parent.findViewById(R.id.iconCallButton);
        iconCallButton1 = (ImageView) parent.findViewById(R.id.iconCallButton1);
        imgMyImage = (ImageView) parent.findViewById(R.id.imgMyImage);

        textViewAirDateDetailedMonths = (TextView) parent.findViewById(R.id.textViewAirDateDetailedMonths);
        textViewAirDateDetailedDays = (TextView) parent.findViewById(R.id.textViewAirDateDetailedDays);
        textViewAirDateDetailedHours = (TextView) parent.findViewById(R.id.textViewAirDateDetailedHours);
        textView3 = (TextView) parent.findViewById(R.id.textView3);
        textView4 = (TextView) parent.findViewById(R.id.textView4);
        textView5 = (TextView) parent.findViewById(R.id.textView5);

        textViewEpisodeNumberAired = (TextView) parent.findViewById(R.id.textViewEpisodeNumberAired);
        textViewEpisodeAired = (TextView) parent.findViewById(R.id.textViewEpisodeAired);
        textViewAirDate = (TextView) parent.findViewById(R.id.textViewAirDate);

        textViewNameOriginal = (TextView) parent.findViewById(R.id.textViewNameOriginal);
        textViewName = (TextView) parent.findViewById(R.id.textViewName);
        Typeface custom_font = Typeface.createFromAsset(parent.getContext().getAssets(),  "bebas-neue-bold.ttf");

        textViewAirDateDetailedMonths.setTypeface(custom_font);
        textViewAirDateDetailedDays.setTypeface(custom_font);
        textViewAirDateDetailedHours.setTypeface(custom_font);
        textViewEpisodeNumberAired.setTypeface(custom_font);
        textViewEpisodeAired.setTypeface(custom_font);
        textViewAirDate.setTypeface(custom_font);
        textViewNameOriginal.setTypeface(custom_font);
        textViewName.setTypeface(custom_font);
        textView3.setTypeface(custom_font);
        textView4.setTypeface(custom_font);
        textView5.setTypeface(custom_font);
    }

    public void setAirDateDetailedMonths(CharSequence text) {
        textViewAirDateDetailedMonths.setText(text);
    }

    public void setAirDateDetailedDays(CharSequence text) {
        textViewAirDateDetailedDays.setText(text);
    }
    public void setAirDateDetailedHours(CharSequence text) {
        textViewAirDateDetailedHours.setText(text);
    }
    public void setEpisodeNumberAired(CharSequence text) {
        textViewEpisodeNumberAired.setText(text);
    }
    public void setEpisodeAired(CharSequence text) {
        textViewEpisodeAired.setText(text);
    }
    public void setAirDate(CharSequence text) {
        textViewAirDate.setText(text);
    }
    public void setNameOriginal(CharSequence text) {
        textViewNameOriginal.setText(text);
    }

    public void setBackGround(Integer show_id) {
        if (MyApplication.showInSubs(show_id))
            iconCallButton1.setBackgroundDrawable(Context.getResources().getDrawable(R.drawable.sub_show_item));
        else
            iconCallButton1.setBackgroundDrawable(Context.getResources().getDrawable(R.drawable.unsub_show_item));
    }

    public void setName(CharSequence text) {
        textViewName.setText(text);
    }

    public void setPoster(String text) {
        Picasso.with(Context)
                .load(text)
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder)
                .into(imgMyImage);
    }
}
