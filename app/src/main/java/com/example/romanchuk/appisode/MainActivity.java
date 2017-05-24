package com.example.romanchuk.appisode;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.romanchuk.appisode.listeners.ShowsNewLoadedListener;
import com.example.romanchuk.appisode.listeners.ShowsPopularLoadedListener;
import com.example.romanchuk.appisode.listeners.SubscriptionsLoadedListener;
import com.example.romanchuk.appisode.listeners.UpdateShowPopular;
import com.example.romanchuk.appisode.models.NotificationItem;
import com.example.romanchuk.appisode.models.ShowsItem;
import com.example.romanchuk.appisode.models.SubscriptionsItem;
import com.example.romanchuk.appisode.tasks.LoadNotifications;
import com.example.romanchuk.appisode.tasks.LoadSearchShow;
import com.example.romanchuk.appisode.tasks.LoadShowsNew;
import com.example.romanchuk.appisode.tasks.LoadShowsPopular;
import com.example.romanchuk.appisode.tasks.LoadSubscriptions;
import com.example.romanchuk.appisode.tasks.ReadNotification;
import com.example.romanchuk.appisode.tasks.TestPush;
import com.example.romanchuk.appisode.tools.Config;
import com.example.romanchuk.appisode.tools.InternetConnection;
import com.example.romanchuk.appisode.tools.NotificationUtils;
import com.example.romanchuk.appisode.tools.Utils;
import com.example.romanchuk.appisode.view.MyCustomLayoutManager;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    public static int showsId = -1;

    private static final String TAG = MainActivity.class.getSimpleName();

    static com.example.romanchuk.appisode.adapters.shows.RecyclerAdapter mNewAdapter;
    static com.example.romanchuk.appisode.adapters.showsPopular.RecyclerAdapter mPopularAdapter;
    static com.example.romanchuk.appisode.adapters.subscriptions.RecyclerAdapter mSubsAdapter;

    private static int sNotificationId = 1;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    static int pageNew = 1;
    static int pagePopular = 1;
    static int pageSubsscriptions = 1;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    SearchView searchView;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Toolbar searchToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(searchToolBar);
        setTitle(getString(R.string.app_name));
        searchToolBar.setTitleTextColor(getResources().getColor(android.R.color.white));
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        FragmentManager fm = getSupportFragmentManager();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                mViewPager.setCurrentItem(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        new TaskLoadShowsPopular(this, new ShowsPopularFragment(), pagePopular, false).execute();

        mViewPager.setOffscreenPageLimit(1);

        mViewPager.setCurrentItem(1);
        mSectionsPagerAdapter.notifyDataSetChanged();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
//        tabLayout.addOnTabSelectedListener(this);

        Bundle intent_extras = getIntent().getExtras();
        showsId = -1;
        if (intent_extras != null && intent_extras.containsKey("com.example.romanchuk.appisode.show_id")) {
            if (intent_extras.containsKey("com.example.romanchuk.appisode.notifyId")) {
                int notification_id = intent_extras.getInt("com.example.romanchuk.appisode.notifyId", -1);
                new ReadNotification(notification_id).execute();
            }
            showsId = intent_extras.getInt("com.example.romanchuk.appisode.show_id", -1);
        } else {
            try {
                ArrayList<NotificationItem> list = new LoadNotifications().execute().get();
                for (int jIndex = 0; jIndex < list.size(); jIndex++) {
                    new sendNotification(this, list.get(jIndex).getId(),
                            list.get(jIndex).getShow_id(),
                            list.get(jIndex).getMessage(),
                            "Appisode",
                            list.get(jIndex).getImage()).execute();
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleDataMessage(int id, String title, String message) {
        try {
            String imageUrl = "";
            String timestamp = "2000";

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);


            if (!true) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(this, MainActivity.class);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                resultIntent.putExtra("com.example.romanchuk.appisode.notifyId", id);
                resultIntent.putExtra("message", message);

                NotificationUtils notificationUtils = new NotificationUtils(this);
//                resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


                notificationUtils.showNotificationMessage(title, message, timestamp, resultIntent);
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private class sendNotification extends AsyncTask<String, Void, Bitmap> {

        Context ctx;
        int id, show_id;
        String message;
        String title;
        String icon;

        public sendNotification(Context context, int id, int show_id, String message, String title, String icon) {
            super();
            this.ctx = context;
            this.id = id;
            this.show_id = show_id;
            this.message = message;
            this.title = title;
            this.icon = icon;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            InputStream in;
            try {

                URL url = new URL(icon);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {

            super.onPostExecute(result);
            Intent intent = new Intent(ctx, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("com.example.romanchuk.appisode.notifyId", id);
            intent.putExtra("com.example.romanchuk.appisode.show_id", show_id);
            PendingIntent pendingIntent = PendingIntent.getActivity(ctx, sNotificationId /* Request code */, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

            inboxStyle.addLine(message);

            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            bigText.bigText(message);
            bigText.setBigContentTitle(getString(R.string.app_name));

            NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx);
            Notification notification = null;
            notification = builder.setSmallIcon(R.mipmap.ic_launcher).setTicker(title).setWhen(0)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setColor(getResources().getColor(R.color.color_accent))
                    .setContentTitle("Appisode")
                    .setContentIntent(pendingIntent)
                    .setContentText(message)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setStyle(inboxStyle)
                    .setSmallIcon(R.drawable.small_icon)
                    .setSound(defaultSoundUri).build();

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(sNotificationId++, notification);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

//        mViewPager.setCurrentItem(tab.getPosition());

        switch (tab.getPosition()) {
            case 0:
//                pageNew = 1;
//                new TaskLoadShowsNew(this, new ShowsNewFragment(), pageNew, false).execute();
                break;
            case 1:
//                pagePopular = 1;
//                new TaskLoadShowsPopular(this, new ShowsPopularFragment(), pagePopular, false).execute();
                break;
            case 2:
//                pageSubsscriptions = 1;
//                new TaskLoadSubscriptions(this, new SubscriptionsFragment(), pageSubsscriptions, false).execute();
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

//        // Associate searchable configuration with the SearchView
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        searchView = (SearchView) menu.findItem(R.id.action1_search)
//                .getActionView();
//        searchView.setSearchableInfo(searchManager
//                .getSearchableInfo(getComponentName()));
//
//        EditText txtSearch = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
//        txtSearch.setHint(getResources().getString(R.string.search_hint));
//        txtSearch.setHintTextColor(Color.LTGRAY);
//        txtSearch.setTextColor(Color.WHITE);
//
//        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//                searchView.clearFocus();
//                return true;
//            }
//        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action1_search:
//                searchView.requestFocus();

                Intent intent = new Intent(this, SearchResultsActivity.class);
                startActivityForResult(intent, 10);
                return true;
            case R.id.testDataPush:
                new TestPush(this, Utils.GetPushToken(this), "пуш типа data", "data").execute();
                return true;
            case R.id.testNotificationPush:
                new TestPush(this, Utils.GetPushToken(this), "пуш типа notification", "notification").execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 10) {
            if (resultCode == Activity.RESULT_OK) {
                Integer showsId = data.getIntExtra("showsId", -1);
                if (showsId != -1) {
                    ShowsPopularFragment fragment2 =
                            (ShowsPopularFragment) getSupportFragmentManager().findFragmentByTag(
                                    "android:switcher:" + R.id.container + ":1");

                    this.showsId = showsId;
                    fragment2.setShowsId();
                    mViewPager.setCurrentItem(1);
                    mSectionsPagerAdapter.notifyDataSetChanged();

                    if (fragment2 != null) {
                        fragment2.onActivityResult(requestCode, resultCode, data);
                    }
                    super.onActivityResult(requestCode, resultCode, data);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public void setPageItem(int showsId) {
        ShowsPopularFragment fragment2 =
                (ShowsPopularFragment) getSupportFragmentManager().findFragmentByTag(
                        "android:switcher:" + R.id.container + ":1");

        this.showsId = showsId;
        fragment2.setShowsId();
        mViewPager.setCurrentItem(1);
        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();

        public void addFragment(Fragment fragment) {
            fragmentList.add(fragment);
        }

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = ShowsNewFragment.newInstance(position + 1);
                    break;
                case 1:
                    fragment = ShowsPopularFragment.newInstance(position + 1);
                    break;
                case 2:
                    fragment = SubscriptionsFragment.newInstance(position + 1);
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.tab_1).toUpperCase(l);
                case 1:
                    return getString(R.string.tab_2).toUpperCase(l);
                case 2:
                    return getString(R.string.tab_3).toUpperCase(l);
            }
            return null;
        }

        public void update() {
//            notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            if (object instanceof UpdateShowPopular) {
                ((UpdateShowPopular) object).updateShows();
            }
            //don't return POSITION_NONE, avoid fragment recreation.
            return super.getItemPosition(object);
        }
    }

    public static class SubscriptionsFragment extends Fragment implements SubscriptionsLoadedListener {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String KEY_LAYOUT_MANAGER = "layoutManager";

        private enum LayoutManagerType {
            LINEAR_LAYOUT_MANAGER
        }

        LayoutManagerType mCurrentLayoutManagerType;

        private ArrayList<SubscriptionsItem> subscriptionsItems;

        private Handler handler;
        RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;

        public SubscriptionsFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static SubscriptionsFragment newInstance(int sectionNumber) {
            SubscriptionsFragment fragment = new SubscriptionsFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onSubscriptionsLoaded(ArrayList<SubscriptionsItem> listMovies) {
            mSubsAdapter.setSubscriptions(listMovies);
            mSubsAdapter.setLoaded();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.subscriptions_fragment, container, false);

            subscriptionsItems = new ArrayList<>();

            if (InternetConnection.checkConnection(getContext())) {

                pageSubsscriptions = 1;
                new LoadSubscriptions(getContext(), this, pageSubsscriptions, false).execute();
                mLayoutManager = new LinearLayoutManager(getActivity());

                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

                if (savedInstanceState != null) {
                    mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                            .getSerializable(KEY_LAYOUT_MANAGER);
                }

                mRecyclerView = (RecyclerView) layout.findViewById(R.id.fragmSubs);
                handler = new Handler();
                setRecyclerViewLayoutManager();
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mSubsAdapter = new com.example.romanchuk.appisode.adapters.subscriptions.RecyclerAdapter(getContext(), subscriptionsItems, mRecyclerView);
                mRecyclerView.setAdapter(mSubsAdapter);
//                setLeftItemTouchHelper();
//                setRightItemTouchHelper();
//                setUpAnimationDecoratorHelper();

                mSubsAdapter.setOnLoadMoreListener(new com.example.romanchuk.appisode.adapters.subscriptions.RecyclerAdapter.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
//                        final Runnable r = new Runnable() {
//                            public void run() {
//                                subscriptionsItems.add(null);
//                                mSubsAdapter.notifyItemInserted(subscriptionsItems.size() - 1);
//                                subscriptionsItems.remove(subscriptionsItems.size() - 1);
//                                mSubsAdapter.notifyItemRemoved(subscriptionsItems.size());
//                                pageSubsscriptions++;
//                                LoadSearch();
//                            }
//                        };
//                        handler.post(r);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                subscriptionsItems.add(null);
                                mSubsAdapter.notifyItemInserted(subscriptionsItems.size() - 1);
                                subscriptionsItems.remove(subscriptionsItems.size() - 1);
                                mSubsAdapter.notifyItemRemoved(subscriptionsItems.size());
                                pageSubsscriptions++;
                                getWebServiceData();
                            }
                        }, 2000);
                        System.out.println(String.format("load subscriptions page %d", pageSubsscriptions - 1));
                    }
                });
            } else {
                Toast toast = Toast.makeText(getContext(), getResources().getString(R.string.no_connection), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 100);
                toast.show();
            }
            return mRecyclerView;
        }

        public void setRecyclerViewLayoutManager() {
            int scrollPosition = 0;

            if (mRecyclerView.getLayoutManager() != null) {
                scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                        .findFirstCompletelyVisibleItemPosition();
            }

            mLayoutManager = new LinearLayoutManager(getActivity());
            mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.scrollToPosition(scrollPosition);
        }

        @Override
        public void onSaveInstanceState(Bundle savedInstanceState) {
            savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
            super.onSaveInstanceState(savedInstanceState);
        }

        public void getWebServiceData() {
            if (InternetConnection.checkConnection(getContext())) {
                new LoadSubscriptions(getContext(), this, pageSubsscriptions, true).execute();
            } else {
                Toast toast = Toast.makeText(getContext(), getResources().getString(R.string.no_connection), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 100);
                toast.show();
            }
        }

        private void setLeftItemTouchHelper() {

            ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

                // we want to cache these and not allocate anything repeatedly in the onChildDraw method
                Drawable background;
                Drawable xMark;
                int xMarkMargin;
                boolean initiated;

                private void init() {
                    background = new ColorDrawable(Color.BLACK);
                    xMark = ContextCompat.getDrawable(getContext(), R.drawable.icon_trash);
                    xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                    xMarkMargin = (int) getContext().getResources().getDimension(R.dimen.ic_clear_margin);
                    initiated = true;
                }

                // not important, we don't want drag & drop
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                    int position = viewHolder.getAdapterPosition();
                    com.example.romanchuk.appisode.adapters.subscriptions.RecyclerAdapter testAdapter = (com.example.romanchuk.appisode.adapters.subscriptions.RecyclerAdapter) recyclerView.getAdapter();
                    if (testAdapter.isPendingRemoval(position)) {
                        return 0;
                    }
                    return super.getSwipeDirs(recyclerView, viewHolder);
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                    int swipedPosition = viewHolder.getAdapterPosition();
                    com.example.romanchuk.appisode.adapters.subscriptions.RecyclerAdapter adapter = (com.example.romanchuk.appisode.adapters.subscriptions.RecyclerAdapter) mRecyclerView.getAdapter();
                    adapter.onItemRemove(viewHolder, mRecyclerView);
                }

                @Override
                public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                    View itemView = viewHolder.itemView;

                    // not sure why, but this method get's called for viewholder that are already swiped away
                    if (viewHolder.getAdapterPosition() == -1) {
                        // not interested in those
                        return;
                    }

                    if (!initiated) {
                        init();
                    }

                    // draw red background
                    background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                    background.draw(c);

                    // draw x mark
                    int itemHeight = itemView.getBottom() - itemView.getTop();
                    int intrinsicWidth = xMark.getIntrinsicWidth();
                    int intrinsicHeight = xMark.getIntrinsicHeight();

                    int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
                    int xMarkRight = itemView.getRight() - xMarkMargin;
                    int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
                    int xMarkBottom = xMarkTop + intrinsicHeight;
                    xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

                    Paint p = new Paint();
                    if (dX > 0) {
                        p.setColor(Color.RED);
                        c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                                (float) itemView.getBottom(), p);
                    } else {
                        p.setColor(Color.RED);
                        c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                                (float) itemView.getRight(), (float) itemView.getBottom(), p);
                    }
                    xMark.draw(c);
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }

            };
            ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
            mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        }

        private void setRightItemTouchHelper() {

            ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

                // we want to cache these and not allocate anything repeatedly in the onChildDraw method
                Drawable background;
                Drawable xMark;
                int xMarkMargin;
                boolean initiated;

                private void init() {
                    background = new ColorDrawable(Color.BLACK);
                    xMark = ContextCompat.getDrawable(getContext(), R.drawable.trash_bin);
                    xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                    xMarkMargin = (int) getContext().getResources().getDimension(R.dimen.ic_clear_margin);
                    initiated = true;
                }

                // not important, we don't want drag & drop
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                    int position = viewHolder.getAdapterPosition();
                    com.example.romanchuk.appisode.adapters.subscriptions.RecyclerAdapter testAdapter = (com.example.romanchuk.appisode.adapters.subscriptions.RecyclerAdapter) recyclerView.getAdapter();
                    if (testAdapter.isPendingRemoval(position)) {
                        return 0;
                    }
                    return super.getSwipeDirs(recyclerView, viewHolder);
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
//                    int swipedPosition = viewHolder.getAdapterPosition();
//                    com.example.romanchuk.appisode.adapters.subscriptions.RecyclerAdapter adapter = (com.example.romanchuk.appisode.adapters.subscriptions.RecyclerAdapter) mRecyclerView.getAdapter();
//                    adapter.onItemRemove(viewHolder, mRecyclerView);
                }

                @Override
                public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                    View itemView = viewHolder.itemView;

                    // not sure why, but this method get's called for viewholder that are already swiped away
                    if (viewHolder.getAdapterPosition() == -1) {
                        // not interested in those
                        return;
                    }

                    if (!initiated) {
                        init();
                    }

                    // draw red background
                    background.setBounds(itemView.getLeft() + (int) dX, itemView.getTop(), itemView.getLeft(), itemView.getBottom());
                    background.draw(c);

                    // draw x mark
                    int itemHeight = itemView.getBottom() - itemView.getTop();
                    int intrinsicWidth = xMark.getIntrinsicWidth();
                    int intrinsicHeight = xMark.getIntrinsicHeight();

                    int xMarkLeft = xMarkMargin;
                    int xMarkRight = xMarkMargin + intrinsicWidth;
                    int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
                    int xMarkBottom = xMarkTop + intrinsicHeight;
                    xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

                    Paint p = new Paint();
                    if (dX > 0) {
                        p.setColor(Color.RED);
                        c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                                (float) itemView.getBottom(), p);
                    } else {
                        p.setColor(Color.RED);
                        c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                                (float) itemView.getRight(), (float) itemView.getBottom(), p);
                    }
                    xMark.draw(c);
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }

            };
            ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
            mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        }

        private void setUpAnimationDecoratorHelper() {
            mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

                // we want to cache this and not allocate anything repeatedly in the onDraw method
                Drawable background;
                boolean initiated;

                private void init() {
                    background = new ColorDrawable(Color.BLACK);
                    initiated = true;
                }

                @Override
                public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

                    if (!initiated) {
                        init();
                    }

                    // only if animation is in progress
                    if (parent.getItemAnimator().isRunning()) {

                        // some items might be animating down and some items might be animating up to close the gap left by the removed item
                        // this is not exclusive, both movement can be happening at the same time
                        // to reproduce this leave just enough items so the first one and the last one would be just a little off screen
                        // then remove one from the middle

                        // find first child with translationY > 0
                        // and last one with translationY < 0
                        // we're after a rect that is not covered in recycler-view views at this point in time
                        View lastViewComingDown = null;
                        View firstViewComingUp = null;

                        // this is fixed
                        int left = 0;
                        int right = parent.getWidth();

                        // this we need to find out
                        int top = 0;
                        int bottom = 0;

                        // find relevant translating views
                        int childCount = parent.getLayoutManager().getChildCount();
                        for (int i = 0; i < childCount; i++) {
                            View child = parent.getLayoutManager().getChildAt(i);
                            if (child.getTranslationY() < 0) {
                                // view is coming down
                                lastViewComingDown = child;
                            } else if (child.getTranslationY() > 0) {
                                // view is coming up
                                if (firstViewComingUp == null) {
                                    firstViewComingUp = child;
                                }
                            }
                        }

                        if (lastViewComingDown != null && firstViewComingUp != null) {
                            // views are coming down AND going up to fill the void
                            top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                            bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                        } else if (lastViewComingDown != null) {
                            // views are going down to fill the void
                            top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                            bottom = lastViewComingDown.getBottom();
                        } else if (firstViewComingUp != null) {
                            // views are coming up to fill the void
                            top = firstViewComingUp.getTop();
                            bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                        }

                        background.setBounds(left, top, right, bottom);
                        background.draw(c);

                    }
                    super.onDraw(c, parent, state);
                }

            });
        }
    }

    public static class ShowsNewFragment extends Fragment implements ShowsNewLoadedListener {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String KEY_LAYOUT_MANAGER = "layoutManager";

        private enum LayoutManagerType {
            LINEAR_LAYOUT_MANAGER
        }

        LayoutManagerType mCurrentLayoutManagerType;

        private ArrayList<ShowsItem> showsNewItems;

        private Handler handler;
        RecyclerView mRecyclerView;
        MyCustomLayoutManager mLayoutManager;

        public ShowsNewFragment() {
        }

        public static ShowsNewFragment newInstance(int sectionNumber) {
            ShowsNewFragment fragment = new ShowsNewFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onShowsNewLoaded(ArrayList<ShowsItem> list) {
            mNewAdapter.setShows(list);
            mNewAdapter.setLoaded();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.showsnew_fragment, container, false);

            showsNewItems = new ArrayList<>();

            if (InternetConnection.checkConnection(getContext())) {
                pageNew = 1;
//                if (fromSubs)
//                    new TaskLoadShowsNewCopy(getContext(), this, pageNew, false).execute();
//                else

                new LoadShowsNew(getContext(), this, pageNew, showsId, false).execute();
                mLayoutManager = new MyCustomLayoutManager(getActivity());

                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

                if (savedInstanceState != null) {
                    mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                            .getSerializable(KEY_LAYOUT_MANAGER);
                }

                mRecyclerView = (RecyclerView) layout.findViewById(R.id.fragmNew);
                handler = new Handler();
                setRecyclerViewLayoutManager();
                mRecyclerView.setLayoutManager(mLayoutManager);
                mNewAdapter = new com.example.romanchuk.appisode.adapters.shows.RecyclerAdapter(getContext(), showsNewItems, mRecyclerView);
                mRecyclerView.setAdapter(mNewAdapter);

                mNewAdapter.setOnLoadMoreListener(new com.example.romanchuk.appisode.adapters.shows.RecyclerAdapter.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {

//                        final Runnable r = new Runnable() {
//                            public void run() {
//                                showsNewItems.add(null);
//                                mNewAdapter.notifyItemInserted(showsNewItems.size() - 1);
//                                showsNewItems.remove(showsNewItems.size() - 1);
//                                mNewAdapter.notifyItemRemoved(showsNewItems.size());
//                                pageNew++;
//                                LoadSearch();
//                            }
//                        };
//                        handler.post(r);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showsNewItems.add(null);
                                mNewAdapter.notifyItemInserted(showsNewItems.size() - 1);
                                showsNewItems.remove(showsNewItems.size() - 1);
                                mNewAdapter.notifyItemRemoved(showsNewItems.size());
                                pageNew++;
                                getWebServiceData();
                            }
                        }, 2000);
                        System.out.println(String.format("load showsNew page %d", pageNew - 1));
                    }
                });

            } else {
                Toast toast = Toast.makeText(getContext(), getResources().getString(R.string.no_connection), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 100);
                toast.show();
            }
            return mRecyclerView;
        }

        public void setRecyclerViewLayoutManager() {
            int scrollPosition = 0;

            if (mRecyclerView.getLayoutManager() != null) {
                scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                        .findFirstCompletelyVisibleItemPosition();
            }

            mLayoutManager = new MyCustomLayoutManager(getActivity());
            mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.scrollToPosition(scrollPosition);
        }

        @Override
        public void onSaveInstanceState(Bundle savedInstanceState) {
            savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
            super.onSaveInstanceState(savedInstanceState);
        }

        public void getWebServiceData() {
            if (InternetConnection.checkConnection(getContext())) {
                new LoadShowsNew(getContext(), this, pageNew, showsId, true).execute();
            } else {
                Toast toast = Toast.makeText(getContext(), getResources().getString(R.string.no_connection), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 100);
                toast.show();
            }
        }
    }

    public static class ShowsPopularFragment extends Fragment implements ShowsPopularLoadedListener, UpdateShowPopular {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String KEY_LAYOUT_MANAGER = "layoutManager";

        public void setShowsId() {
//            this.showsId = showsId;
            mPopularAdapter.dataSetRefresh();
        }

        @Override
        public void updateShows() {
//            new LoadSearchShow(getContext(), this, showsId).execute();
            mPopularAdapter.clearShows();
            pagePopular = 1;
            new LoadShowsPopular(getContext(), this, pagePopular, showsId, true).execute();
            showsId = -1;
        }

        private enum LayoutManagerType {
            LINEAR_LAYOUT_MANAGER
        }

        LayoutManagerType mCurrentLayoutManagerType;

        private ArrayList<ShowsItem> showsPopularItems;

        private Handler handler;
        RecyclerView mRecyclerView;
        MyCustomLayoutManager mLayoutManager;

        public ShowsPopularFragment() {
        }

        public static ShowsPopularFragment newInstance(int sectionNumber) {
            ShowsPopularFragment fragment = new ShowsPopularFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onShowsLoaded(ArrayList<ShowsItem> list) {
            mPopularAdapter.setShows(list);
            mPopularAdapter.setLoaded();
        }

        @Override
        public void onSearchShowsLoaded(ShowsItem showsItem) {
            mPopularAdapter.insertShow(showsItem);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.showsnew_fragment, container, false);

            showsPopularItems = new ArrayList<>();

            if (InternetConnection.checkConnection(getContext())) {
                pagePopular = 1;
                new LoadShowsPopular(getContext(), this, pagePopular, showsId, false).execute();
                showsId = -1;
                mLayoutManager = new MyCustomLayoutManager(getActivity());

                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

                if (savedInstanceState != null) {
                    mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                            .getSerializable(KEY_LAYOUT_MANAGER);
                }

                mRecyclerView = (RecyclerView) layout.findViewById(R.id.fragmNew);
                handler = new Handler();
                mRecyclerView.setLayoutManager(mLayoutManager);
                mPopularAdapter = new com.example.romanchuk.appisode.adapters.showsPopular.RecyclerAdapter(getContext(), showsPopularItems, mRecyclerView);
                mRecyclerView.setAdapter(mPopularAdapter);

                mPopularAdapter.setOnLoadMoreListener(new com.example.romanchuk.appisode.adapters.showsPopular.RecyclerAdapter.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {

//                        final Runnable r = new Runnable() {
//                            public void run() {
//                                showsPopularItems.add(null);
//                                mPopularAdapter.notifyItemInserted(showsPopularItems.size() - 1);
//                                showsPopularItems.remove(showsPopularItems.size() - 1);
//                                mPopularAdapter.notifyItemRemoved(showsPopularItems.size());
//                                pagePopular++;
//                                LoadSearch();
//                            }
//                        };
//                        handler.post(r);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showsPopularItems.add(null);
                                mPopularAdapter.notifyItemInserted(showsPopularItems.size() - 1);
                                showsPopularItems.remove(showsPopularItems.size() - 1);
                                mPopularAdapter.notifyItemRemoved(showsPopularItems.size());
                                pagePopular++;
                                getWebServiceData();
                            }
                        }, 2000);
                        System.out.println(String.format("load showsPopular page %d", pagePopular - 1));
                    }
                });

            } else {
                Toast toast = Toast.makeText(getContext(), getResources().getString(R.string.no_connection), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 100);
                toast.show();
            }
            return mRecyclerView;
        }

        @Override
        public void onSaveInstanceState(Bundle savedInstanceState) {
            savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
            super.onSaveInstanceState(savedInstanceState);
        }


        public void getWebServiceData() {
            if (InternetConnection.checkConnection(getContext())) {
                new LoadShowsPopular(getContext(), this, pagePopular, showsId, true).execute();
            } else {
                Toast toast = Toast.makeText(getContext(), getResources().getString(R.string.no_connection), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 100);
                toast.show();
            }
        }
    }
}
