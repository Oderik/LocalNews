package localnews.nordmedia.de.localnews.news;

import android.content.Context;

import java.io.InputStream;

/**
 * Created by Oderik on 17.07.2014.
 */
public class FetchNewsFeedCallable implements FetchNewsListCallable {
    private final Context context;
    private final DataSource dataSource;
    private final JsonNewsFeedReader newsFeedReader = new JsonNewsFeedReader();

    public FetchNewsFeedCallable(final Context context) {
        this.context = context.getApplicationContext();
        dataSource = new AssetsDataSource(context);
    }

    @Override
    public NewsFeed call() throws Exception {
        return newsFeedReader.read(dataSource.open());
    }
}
