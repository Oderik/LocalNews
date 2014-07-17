package localnews.nordmedia.de.localnews.news;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Oderik on 17.07.2014.
 */
public class News implements Serializable {

    public String title, teaser, url, imageUrl;
    public Date time;

    public News() {
    }

}
