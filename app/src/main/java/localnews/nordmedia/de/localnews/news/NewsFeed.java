package localnews.nordmedia.de.localnews.news;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Oderik on 17.07.2014.
 */
public class NewsFeed implements Serializable {

    public final ArrayList<News> news;

    public NewsFeed(ArrayList<News> news) {
        this.news = news;
    }
}
