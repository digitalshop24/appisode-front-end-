package com.example.romanchuk.appisode;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.romanchuk.appisode.models.EpisodeItem;
import com.example.romanchuk.appisode.models.ShowDetailItem;
import com.example.romanchuk.appisode.myapplication.MyApplication;
import com.example.romanchuk.appisode.tasks.SubscribeEpisode;
import com.example.romanchuk.appisode.tasks.SubscribeSeason;
import com.example.romanchuk.appisode.tools.InternetConnection;

/**
 * Created by romanchuk on 05.01.2017.
 */

public class ShowsIdFragment extends Dialog implements
        View.OnClickListener {

    ShowDetailItem showDetailItem;

    public int showId;

    Typeface custom_font;
    public Context context;
    public HorizontalScrollView horScrollView;
    public ViewGroup hourButtonLayout;
    public RadioGroup newRadioGroup;
    public ImageButton acceptButton, cancelButton;
    public ImageView iconCallLittle, iconCallBig;
    public CheckBox cbSeasons, cbEpisodes;
    public RelativeLayout layoutEpisodes;
    public LinearLayout layoutSeasons;
    public TextView tvSeasonNumberAired, tvSeasonAired, tvAirDate, tvAirDateMonths, tvAirDateDays, tvAirDateHours,
            textView6, textView7, textView8, tvAirDate1, tvClose1, tvClose2, tvClose3;
    public LinearLayout layoutAiring, layoutClosed;

    public ShowsIdFragment(Context c, ShowDetailItem item) {
        super(c);
        this.context = c;
        this.showDetailItem = item;
    }

    public void onShowsIdLoaded() {
        if (showDetailItem != null) {
            tvSeasonNumberAired.setText(showDetailItem.getSeason_number_aired());
            tvSeasonAired.setText(showDetailItem.getSeason_aired());
            tvAirDate.setText(showDetailItem.getSeason_air_date());
            tvAirDateMonths.setText(showDetailItem.getSeason_air_date_detailed_months());
            tvAirDateDays.setText(showDetailItem.getSeason_air_date_detailed_days());
            tvAirDateHours.setText(showDetailItem.getSeason_air_date_detailed_hours());
            if (showDetailItem.getEpisodes() != null && showDetailItem.getEpisodes().length > 0) {
                final EpisodeItem[] episodeItems = showDetailItem.getEpisodes();
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT, 1);
                param.height = (int) getContext().getResources().getDimension(R.dimen.radio_button_height);
                param.width = (int) getContext().getResources().getDimension(R.dimen.radio_button_width);

                for (int jIndex = 0; jIndex < episodeItems.length; jIndex++) {
                    RadioButton rb = new RadioButton(getContext());
                    rb.setTag(R.id.episodePosition, Integer.valueOf(jIndex));
                    rb.setText(String.valueOf(jIndex + 1));
                    rb.setButtonDrawable(new StateListDrawable());
                    rb.setBackgroundResource(R.drawable.episode_rbutton);
                    if (episodeItems[jIndex].isEnable())
                        rb.setTextColor(getContext().getResources().getColorStateList(R.color.rb_colors_enable));
                    else
                        rb.setTextColor(getContext().getResources().getColorStateList(R.color.rb_colors_disable));
                    rb.setGravity(Gravity.CENTER);
                    rb.setTypeface(custom_font);
                    rb.setTextSize((float) 20.0);

                    rb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            int viewLeft = v.getLeft();
                            int viewWidth = v.getWidth();
                            int horScrollViewWidth = horScrollView.getWidth();
                            int center = horScrollViewWidth / 2;
                            int x = viewLeft + viewWidth / 2 - center;
                            ObjectAnimator animator = ObjectAnimator.ofInt(horScrollView, "scrollX", x);
                            animator.setDuration(400);
                            animator.start();

                            int index = newRadioGroup.indexOfChild(findViewById(newRadioGroup.getCheckedRadioButtonId()));
                            String air_date = episodeItems[index].getDetail_air_date();
                            boolean isEnable = episodeItems[index].isEnable();
                            tvAirDate1.setText(air_date);
                            acceptButton.setEnabled(isEnable);

                            int selected_episode_id = episodeItems[index].getId();
                            showDetailItem.setSelected_episode_id(selected_episode_id);
                        }
                    });
                    hourButtonLayout.addView(rb, jIndex, param);
                }
                horScrollView = (HorizontalScrollView) findViewById(R.id.horScrollView);

                if (showDetailItem.getNext_episodes_number() != -1) {
                    RadioButton radioButton = (RadioButton) newRadioGroup.getChildAt(showDetailItem.getNext_episodes_number() - 1);
                    View rb = (View) newRadioGroup.getChildAt(showDetailItem.getNext_episodes_number() - 1);
                    if (radioButton != null) {
                        radioButton.setChecked(true);

                        final int index = newRadioGroup.indexOfChild(findViewById(newRadioGroup.getCheckedRadioButtonId()));
                        String air_date = episodeItems[index].getDetail_air_date();
                        boolean isEnable = episodeItems[index].isEnable();
                        tvAirDate1.setText(air_date);
                        acceptButton.setEnabled(isEnable);
                        int selected_episode_id = episodeItems[index].getId();
                        showDetailItem.setSelected_episode_id(selected_episode_id);
                        mHandler.postDelayed(new Runnable() {
                            public void run() {
                                int scrollTo = 0;
                                for (int i = 0; i < index; i++) {
                                    View rb = (View) newRadioGroup.getChildAt(i);
                                    scrollTo += rb.getWidth();
                                }
                                ObjectAnimator animator = ObjectAnimator.ofInt(horScrollView, "scrollX", scrollTo);
                                animator.setDuration(800);
                                animator.start();
                            }
                        }, 1000);
                    }
                }
//                    layoutManager.scrollToPosition(showDetailItem.getNext_episodes_number());
            }
        } else {
            Toast toast = Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.show_detail_popup);

        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        hourButtonLayout = (ViewGroup) findViewById(R.id.newRadioGroup);
        newRadioGroup = (RadioGroup) findViewById(R.id.newRadioGroup);

        layoutAiring = (LinearLayout) findViewById(R.id.layoutAiring);
        layoutClosed = (LinearLayout) findViewById(R.id.layoutClosed);

        layoutEpisodes = (RelativeLayout) findViewById(R.id.layoutEpisodes);
        layoutSeasons = (LinearLayout) findViewById(R.id.layoutSeasons);
        cbEpisodes = (CheckBox) findViewById(R.id.cbEpisodes);
        cbSeasons = (CheckBox) findViewById(R.id.cbSeasons);
        acceptButton = (ImageButton) findViewById(R.id.btn_yes);
        cancelButton = (ImageButton) findViewById(R.id.btn_no);
        iconCallLittle = (ImageView) findViewById(R.id.iconCallLittle);
        iconCallBig = (ImageView) findViewById(R.id.iconCallBig);

        tvSeasonNumberAired = (TextView) findViewById(R.id.tvSeasonNumberAired);
        tvSeasonAired = (TextView) findViewById(R.id.tvSeasonAired);
        tvAirDate = (TextView) findViewById(R.id.tvAirDate);
        tvAirDateMonths = (TextView) findViewById(R.id.tvAirDateMonths);
        tvAirDateDays = (TextView) findViewById(R.id.tvAirDateDays);
        tvAirDateHours = (TextView) findViewById(R.id.tvAirDateHours);
        textView6 = (TextView) findViewById(R.id.textView6);
        textView7 = (TextView) findViewById(R.id.textView7);
        textView8 = (TextView) findViewById(R.id.textView8);
        tvAirDate1 = (TextView) findViewById(R.id.tvAirDate1);
        tvClose1 = (TextView) findViewById(R.id.tvClose1);
        tvClose2 = (TextView) findViewById(R.id.tvClose2);
        tvClose3 = (TextView) findViewById(R.id.tvClose3);

        custom_font = Typeface.createFromAsset(getContext().getAssets(), "bebas-neue-bold.ttf");
        tvSeasonNumberAired.setTypeface(custom_font);
        tvSeasonAired.setTypeface(custom_font);
        tvAirDate.setTypeface(custom_font);
        tvAirDateMonths.setTypeface(custom_font);
        tvAirDateDays.setTypeface(custom_font);
        tvAirDateHours.setTypeface(custom_font);
        textView6.setTypeface(custom_font);
        textView7.setTypeface(custom_font);
        textView8.setTypeface(custom_font);
        tvAirDate1.setTypeface(custom_font);
        tvClose1.setTypeface(custom_font);
        tvClose2.setTypeface(custom_font);
        tvClose3.setTypeface(custom_font);
        cbSeasons.setTypeface(custom_font);
        cbEpisodes.setTypeface(custom_font);

        tvClose1.setText("сериал закрыт".toUpperCase());
        tvClose2.setText("уведомить меня, если".toUpperCase());
        tvClose3.setText("будет продолжение".toUpperCase());

        ifShowClosedOrHiatus(showDetailItem.isShowClosed() || showDetailItem.isShowHiatus());

        acceptButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        Animation anim1 = AnimationUtils.loadAnimation(getContext(), R.anim.scale_call_big);
        Animation anim2 = AnimationUtils.loadAnimation(getContext(), R.anim.scale_call_little);
        iconCallLittle.startAnimation(anim2);
        iconCallBig.startAnimation(anim1);
    }

    public void ifShowClosedOrHiatus(boolean closed) {
        if (closed) {
            cbSeasons.setChecked(true);
            cbEpisodes.setChecked(false);
            layoutAiring.setVisibility(View.INVISIBLE);
            layoutClosed.setVisibility(View.VISIBLE);
            if (showDetailItem.isShowHiatus()){
                tvClose2.setText("уведомить меня, когда".toUpperCase());
                tvClose3.setText("анонсируют " + showDetailItem.getNext_season_number() + " сезон".toUpperCase());
            }
        }
        else{
            layoutAiring.setVisibility(View.VISIBLE);
            layoutClosed.setVisibility(View.INVISIBLE);

            cbEpisodes.setOnClickListener(this);
            cbSeasons.setOnClickListener(this);
            onShowsIdLoaded();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                dismiss();
                if (cbEpisodes.isChecked())
                    if (InternetConnection.checkConnection(context)) {
                        MyApplication.addSubsId(showDetailItem.getId());
                        new SubscribeEpisode(context, showDetailItem.getId(), showDetailItem.getSelected_episode_id()).execute();
                    } else {
                        Toast toast = Toast.makeText(context, context.getResources().getString(R.string.no_connection), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.BOTTOM, 0, 100);
                        toast.show();
                    }
                if (cbSeasons.isChecked())
                    if (InternetConnection.checkConnection(context)) {
                        MyApplication.addSubsId(showDetailItem.getId());
                        new SubscribeSeason(context, showDetailItem.getId()).execute();
                    } else {
                        Toast toast = Toast.makeText(context, context.getResources().getString(R.string.no_connection), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.BOTTOM, 0, 100);
                        toast.show();
                    }
//                new TaskCheckSubscriptions(context).execute();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            case R.id.cbEpisodes:
                cbSeasons.setChecked(!cbEpisodes.isChecked());
                if (cbSeasons.isChecked()) {
//                    TranslateAnimation animate = new TranslateAnimation(0,layoutSeasons.getWidth(),0,0);
//                    animate.setDuration(500);
//                    animate.setFillAfter(true);
//                    layoutSeasons.startAnimation(animate);
//                    layoutSeasons.startAnimation(animate);

                    layoutEpisodes.setVisibility(View.INVISIBLE);
                    layoutSeasons.setVisibility(View.VISIBLE);
                } else {

//                    TranslateAnimation animate = new TranslateAnimation(0,layoutEpisodes.getWidth(),0,0);
//                    animate.setDuration(500);
//                    animate.setFillAfter(true);
//                    layoutEpisodes.startAnimation(animate);
//                    layoutSeasons.startAnimation(animate);

                    layoutSeasons.setVisibility(View.INVISIBLE);
                    layoutEpisodes.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.cbSeasons:
                cbEpisodes.setChecked(!cbSeasons.isChecked());
                if (cbEpisodes.isChecked()) {
                    layoutSeasons.setVisibility(View.INVISIBLE);
                    layoutEpisodes.setVisibility(View.VISIBLE);
                } else {
                    layoutSeasons.setVisibility(View.VISIBLE);
                    layoutEpisodes.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                dismiss();
                break;
        }
    }

    private Handler mHandler = new Handler();
}
