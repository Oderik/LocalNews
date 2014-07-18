package localnews.nordmedia.de.localnews;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import localnews.nordmedia.de.localnews.news.News;
import localnews.nordmedia.de.localnews.news.NewsFeed;
import localnews.nordmedia.de.localnews.news.NewsLoader;

/**
 * A list fragment representing a list of News. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link NewsDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class NewsListFragment extends ListFragment {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;
    private Loader<NewsFeed> newsLoader;
    private View emptyView;
    private LoaderManager.LoaderCallbacks<NewsFeed> loaderCallbacks;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         *
         * @param news
         */
        public void onItemSelected(News news);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(News news) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NewsListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ;
        LoaderManager loaderManager = getLoaderManager();
        loaderCallbacks = new LoaderManager.LoaderCallbacks<NewsFeed>() {
            @Override
            public NewsLoader onCreateLoader(int id, Bundle args) {
                return new NewsLoader(NewsListFragment.this.getActivity(), args.getDouble("radius", 25));
            }

            @Override
            public void onLoadFinished(Loader<NewsFeed> loader, NewsFeed data) {
                getListAdapter().setData(data);
            }

            @Override
            public void onLoaderReset(Loader<NewsFeed> loader) {
                Log.d("tag", "onLoaderReset");
            }
        };
        final Bundle bundle = new Bundle();
        bundle.putDouble("radius", 25);
        newsLoader = loaderManager.initLoader(1, bundle, loaderCallbacks);

        setListAdapter(new NewsAdapter());
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        newsLoader.startLoading();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(getListAdapter().getItem(position));
    }

    @Override
    public NewsAdapter getListAdapter() {
        return (NewsAdapter) super.getListAdapter();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                refresh();
                return true;
            case R.id.radius25:
                setRadius(25);
                return true;
            case R.id.radius100:
                setRadius(100);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setRadius(final int radius) {
        final Bundle args = new Bundle();
        args.putDouble("radius", radius);
        getLoaderManager().restartLoader(1, args, loaderCallbacks);
    }

    private void preferences() {
    }

    private void refresh() {
        newsLoader.forceLoad();
    }
}
