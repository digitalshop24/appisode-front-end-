package com.example.romanchuk.appisode;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.romanchuk.appisode.models.ShowDetailItem;
import com.example.romanchuk.appisode.models.ShowsItem;
import com.example.romanchuk.appisode.myapplication.MyApplication;
import com.example.romanchuk.appisode.tasks.LoadShowsId;
import com.example.romanchuk.appisode.tasks.LoadSubId;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutionException;

public class SerialDetailActivity extends AppCompatActivity {

    private ShowsItem showsItem;
    public ImageView iconCallButton, iconCallButton1, imgMyImage;
    public TextView textViewNameOriginal, textViewAirDateDetailedMonths, textViewAirDateDetailedDays, textViewAirDateDetailedHours, textView3, textView4, textView5;
    public TextView textViewName, textViewEpisodeNumberAired, textViewEpisodeAired, textViewAirDate;
    public LinearLayout layoutAiring, layoutClosed;

    int showsId = -1;
    ShowDetailItem showDetailItem = null;
    ShowsIdFragment popupDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle("Страница сериала");

        Intent intent = getIntent();
        showsId = intent.getIntExtra("showsId", -1);

        try {
            showsItem = new LoadSubId(this, showsId).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        iconCallButton = (ImageView) findViewById(R.id.iconCallButton);
        iconCallButton1 = (ImageView) findViewById(R.id.iconCallButton1);
        imgMyImage = (ImageView) findViewById(R.id.imgMyImage);

        textViewAirDateDetailedMonths = (TextView) findViewById(R.id.textViewAirDateDetailedMonths);
        textViewAirDateDetailedDays = (TextView) findViewById(R.id.textViewAirDateDetailedDays);
        textViewAirDateDetailedHours = (TextView) findViewById(R.id.textViewAirDateDetailedHours);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);

        textViewEpisodeNumberAired = (TextView) findViewById(R.id.textViewEpisodeNumberAired);
        textViewEpisodeAired = (TextView) findViewById(R.id.textViewEpisodeAired);
        textViewAirDate = (TextView) findViewById(R.id.textViewAirDate);

        textViewNameOriginal = (TextView) findViewById(R.id.textViewNameOriginal);
        textViewName = (TextView) findViewById(R.id.textViewName);
        layoutAiring = (LinearLayout) findViewById(R.id.layoutAiring);
        layoutClosed = (LinearLayout) findViewById(R.id.layoutClosed);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "bebas-neue-bold.ttf");

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

        if (showsItem != null) {

            setAirDateDetailedMonths(showsItem.getEpisode_air_date_detailed_months());
            setAirDateDetailedDays(showsItem.getEpisode_air_date_detailed_days());
            setAirDateDetailedHours(showsItem.getEpisode_air_date_detailed_hours());
            setEpisodeNumberAired(showsItem.getEpisode_number_aired());
            setEpisodeAired(showsItem.getEpisode_aired());
            setAirDate(showsItem.getEpisode_air_date());

            setNameOriginal(showsItem.getName_original());
            setName(showsItem.getName());
            setPoster(showsItem.getPoster());
            setBackGround(showsItem.getId());

            Animation anim = AnimationUtils.loadAnimation(this, R.anim.rotate_call);
            iconCallButton.setAnimation(anim);
            iconCallButton.startAnimation(anim);

            iconCallButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        showDetailItem = new LoadShowsId(showsId).execute().get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }

                    popupDialog = new ShowsIdFragment(v.getContext(), showDetailItem);
                    popupDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            setBackGround(showsItem.getId());
                        }
                    });
                    popupDialog.show();
                }
            });
            iconCallButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        showDetailItem = new LoadShowsId(showsId).execute().get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    popupDialog = new ShowsIdFragment(v.getContext(), showDetailItem);
                    popupDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            setBackGround(showsItem.getId());
                        }
                    });
                    popupDialog.show();
                }
            });
        }
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
            iconCallButton1.setBackgroundDrawable(getResources().getDrawable(R.drawable.sub_show_item));
        else
            iconCallButton1.setBackgroundDrawable(getResources().getDrawable(R.drawable.unsub_show_item));
    }

    public void setName(CharSequence text) {
        textViewName.setText(text);
    }

    public void setPoster(String text) {
        Picasso.with(this)
                .load(text)
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder)
                .into(imgMyImage);
    }
}
