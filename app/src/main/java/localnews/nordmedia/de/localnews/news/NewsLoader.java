package localnews.nordmedia.de.localnews.news;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
* Created by Oderik on 17.07.2014.
*/
public class NewsLoader extends AsyncTaskLoader<NewsFeed> {

    private final FetchNewsListCallable callable;
    private NewsFeed newsFeed;

    public NewsLoader(Context context) {
        super(context);
        callable = new FetchNewsFeedCallable(context);
    }

    @Override
    public NewsFeed loadInBackground() {
        try {
            newsFeed = callable.call();
            return newsFeed;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onStartLoading() {
        if (newsFeed != null) {
            deliverResult(newsFeed);
        } else {
            forceLoad();
        }
    }
}
