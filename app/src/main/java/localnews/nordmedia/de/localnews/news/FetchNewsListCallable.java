package localnews.nordmedia.de.localnews.news;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Oderik on 17.07.2014.
 */
public interface FetchNewsListCallable extends Callable<NewsFeed> {
}
