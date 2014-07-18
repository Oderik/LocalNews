package localnews.nordmedia.de.localnews.news;

import android.content.Context;
import android.location.Location;

import localnews.nordmedia.de.localnews.DataSource;

/**
 * Created by Oderik on 17.07.2014.
 */
public class FetchNewsFeedCallable implements FetchNewsListCallable {
    private final Context context;
    private final DataSource dataSource;
    private final JsonNewsFeedReader newsFeedReader = new JsonNewsFeedReader();

    public FetchNewsFeedCallable(final Context context, final Location location) {
        this.context = context.getApplicationContext();
        dataSource = new HttpDataSource(context, 9.814976, 52.321231);
//        dataSource = new AssetsDataSource(context);
    }

    @Override
    public NewsFeed call() throws Exception {
        return newsFeedReader.read(dataSource.open());
    }
}
