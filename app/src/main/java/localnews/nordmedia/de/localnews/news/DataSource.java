package localnews.nordmedia.de.localnews.news;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Oderik on 17.07.2014.
 */
public interface DataSource {

    public InputStream open() throws IOException;
}
