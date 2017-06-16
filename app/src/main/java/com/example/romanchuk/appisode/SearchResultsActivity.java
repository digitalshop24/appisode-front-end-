package com.example.romanchuk.appisode;

/**
 * Created by romanchuk on 14.01.2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.CollapsingToolbarLayout;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.romanchuk.appisode.adapters.search.RecyclerAdapter;
import com.example.romanchuk.appisode.listeners.SearchLoadedListener;
import com.example.romanchuk.appisode.models.ShowsItem;
import com.example.romanchuk.appisode.tasks.LoadSearch;
import com.example.romanchuk.appisode.tasks.LoadSearchBySuggestion;
import com.example.romanchuk.appisode.tools.DateTimeManager;
import com.example.romanchuk.appisode.tools.InternetConnection;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity implements BaseExampleFragment.BaseExampleFragmentCallbacks {

    static RecyclerAdapter mAdapter;

    static Activity activity = null;
    static String query;
    static int page = 1;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.AppThemeBlue);
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search_results);
        activity = this;
        showFragment(new SearchResultsFragment());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById((R.id.collapsing_toolbar));
        getSupportActionBar().hide();
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setTitle("Поиск");
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment).commit();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onAttachSearchViewToDrawer(FloatingSearchView searchView) {

    }

    public void finishWithResult(Integer showsId)
    {
        Bundle conData = new Bundle();
        conData.putInt("showsId", showsId);
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        finish();
    }

    public static class SearchResultsFragment extends BaseExampleFragment implements SearchLoadedListener {
        private final String TAG = "BlankFragment";
        public static final long FIND_SUGGESTION_SIMULATED_DELAY = 250;


        private Handler handler;
        private FloatingSearchView mSearchView;

        private ArrayList<ShowsItem> list;
        RecyclerView mRecyclerView;

        TextView textViewSerialsCount1, textViewSerialsCount2, textViewSerialsCount3;
        LinearLayout layoutTotalSearchCount;

        private String mLastQuery = "";


        public SearchResultsFragment() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_search_results, container, false);

        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            layoutTotalSearchCount = (LinearLayout) view.findViewById(R.id.layoutTotalSearchCount);
            textViewSerialsCount1 = (TextView) view.findViewById(R.id.textViewSerialsCount1);
            textViewSerialsCount2 = (TextView) view.findViewById(R.id.textViewSerialsCount2);
            textViewSerialsCount3 = (TextView) view.findViewById(R.id.textViewSerialsCount3);
            Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "bebas-neue-bold.ttf");
            textViewSerialsCount1.setTypeface(custom_font);
            textViewSerialsCount2.setTypeface(custom_font);
            textViewSerialsCount3.setTypeface(custom_font);
            layoutTotalSearchCount.setVisibility(View.GONE);

            mSearchView = (FloatingSearchView) view.findViewById(R.id.floating_search_view);
            list = new ArrayList<>();

            if (InternetConnection.checkConnection(getContext())) {

                mRecyclerView = (RecyclerView) view.findViewById(R.id.search_results_list);
                handler = new Handler();
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mAdapter = new RecyclerAdapter(getActivity(), list, mRecyclerView);
                mRecyclerView.setAdapter(mAdapter);

                mAdapter.setOnLoadMoreListener(new RecyclerAdapter.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        list.add(null);
                        mAdapter.notifyItemInserted(list.size() - 1);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (list.size() > 0) {
                                    list.remove(list.size() - 1);
                                    mAdapter.notifyItemRemoved(list.size());
                                }
                                page++;
                                LoadSearch();
                            }
                        }, 2000);
                        System.out.println("load");
                    }
                });

            } else {
                Toast toast = Toast.makeText(getContext(), getResources().getString(R.string.no_connection), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 100);
                toast.show();
            }
            setupFloatingSearch();
            setupDrawer();
            mSearchView.setSearchFocused(true);
        }

        private void setupFloatingSearch() {
            mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {

                @Override
                public void onSearchTextChanged(String oldQuery, final String newQuery) {

                    if (!oldQuery.equals("") && newQuery.equals(""))
                        mSearchView.clearSuggestions();
                    else {
                        mSearchView.showProgress();
                        page = 1;
                        mAdapter.setLoaded(true);
                        mAdapter.clearShows();
                        query = newQuery;
                        LoadSearch();
                        Log.d(TAG, "onSearchTextChanged()");
                        mLastQuery = newQuery;
                    }
                }
            });

            mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
                @Override
                public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {

                    Log.d(TAG, "onSuggestionClicked()");

                    mLastQuery = searchSuggestion.getBody();

                    page = 1;
                    mAdapter.setLoaded(true);
                    mAdapter.clearShows();
                    query = mLastQuery;
                    LoadSearchBySuggestion();
                }

                @Override
                public void onSearchAction(String query) {
                    mLastQuery = query;

                    Log.d(TAG, "onSearchAction()");
                }
            });

            mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
                @Override
                public void onFocus() {
                    mSearchView.swapSuggestions(DataHelper.getHistory(getActivity(), 3));
//                    mSearchView.setSearchText(mLastQuery);
//                    mSearchView.setSearchBarTitle(mLastQuery);
                    Log.d(TAG, "onFocus()");
                }

                @Override
                public void onFocusCleared() {

                    //set the title of the bar so that when focus is returned a new query begins
                    mSearchView.setSearchBarTitle(mLastQuery);

                    //you can also set setSearchText(...) to make keep the query there when not focused and when focus returns
                    mSearchView.setSearchText(mLastQuery);

                    Log.d(TAG, "onFocusCleared()");
                }
            });

            //use this listener to listen to menu clicks when app:floatingSearch_leftAction="showHome"
            mSearchView.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
                @Override
                public void onHomeClicked() {

                    activity.finish();
                    Log.d(TAG, "onHomeClicked()");
                }
            });

        /*
         * Here you have access to the left icon and the text of a given suggestion
         * item after as it is bound to the suggestion list. You can utilize this
         * callback to change some properties of the left icon and the text. For example, you
         * can load the left icon images using your favorite image loading library, or change text color.
         *
         *
         * Important:
         * Keep in mind that the suggestion list is a RecyclerView, so views are reused for different
         * items in the list.
         */
            mSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
                @Override
                public void onBindSuggestion(View suggestionView, ImageView leftIcon,
                                             TextView textView, SearchSuggestion item, int itemPosition) {

                }

            });

            //listen for when suggestion list expands/shrinks in order to move down/up the
            //search results list
            mSearchView.setOnSuggestionsListHeightChanged(new FloatingSearchView.OnSuggestionsListHeightChanged() {
                @Override
                public void onSuggestionsListHeightChanged(float newHeight) {
                }
            });
        }

        @Override
        public boolean onActivityBackPress() {
            //if mSearchView.setSearchFocused(false) causes the focused search
            //to close, then we don't want to close the activity. if mSearchView.setSearchFocused(false)
            //returns false, we know that the search was already closed so the call didn't change the focus
            //state and it makes sense to call supper onBackPressed() and close the activity
            if (!mSearchView.setSearchFocused(false)) {
                return false;
            }
            return true;
        }

        private void setupDrawer() {
            attachSearchViewActivityDrawer(mSearchView);
        }

        public void LoadSearch() {
            if (getContext() == null)
                return;
            if (InternetConnection.checkConnection(getContext())) {
                new LoadSearch(getContext(), this, page, query, true).execute();
            } else {
                Toast toast = Toast.makeText(getContext(), getResources().getString(R.string.no_connection), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 100);
                toast.show();
            }
        }

        public void LoadSearchBySuggestion() {
            if (InternetConnection.checkConnection(getContext())) {
                new LoadSearchBySuggestion(getContext(), this, page, query, true).execute();
            } else {
                Toast toast = Toast.makeText(getContext(), getResources().getString(R.string.no_connection), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 100);
                toast.show();
            }
        }

        @Override
        public void onSearchLoaded(ArrayList<ShowsItem> listMovies, Integer totalCount) {
            mAdapter.setShows(listMovies);

            DataHelper.addSuggestions(listMovies);
            DataHelper.findSuggestions(getActivity(), mLastQuery, 5,
                    FIND_SUGGESTION_SIMULATED_DELAY, new DataHelper.OnFindSuggestionsListener() {

                        @Override
                        public void onResults(List<ShowSuggestion> results) {

                            //this will swap the data and
                            //render the collapse/expand animations as necessary
                            mSearchView.swapSuggestions(results);

                            //let the users know that the background
                            //process has completed
                            mSearchView.hideProgress();
                        }
                    });

            mAdapter.setLoaded(false);
            textViewSerialsCount1.setText(DateTimeManager.DeclOfNumJustText2(totalCount));
            textViewSerialsCount2.setText(String.valueOf(totalCount));
            textViewSerialsCount3.setText(DateTimeManager.DeclOfNumJustText3(totalCount));
            layoutTotalSearchCount.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSearchLoadedBySuggestion(ArrayList<ShowsItem> listMovies, Integer totalCount) {
            mAdapter.setShows(listMovies);
            mAdapter.setLoaded(false);
            textViewSerialsCount1.setText(DateTimeManager.DeclOfNumJustText2(totalCount));
            textViewSerialsCount2.setText(String.valueOf(totalCount));
            textViewSerialsCount3.setText(DateTimeManager.DeclOfNumJustText3(totalCount));
            layoutTotalSearchCount.setVisibility(View.VISIBLE);
        }
    }
}
