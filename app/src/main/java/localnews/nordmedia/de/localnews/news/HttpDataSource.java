package localnews.nordmedia.de.localnews.news;

import android.content.Context;
import android.net.http.AndroidHttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Oderik on 17.07.2014.
 */
public class HttpDataSource implements DataSource {

    private final AndroidHttpClient httpClient;

    public HttpDataSource(final Context context) {
        httpClient = AndroidHttpClient.newInstance(System.getProperty("http.agent"), context);
    }

    @Override
    public InputStream open() throws IOException {
        final HttpGet httpGet = new HttpGet("http://hackt.sichwarm.de:8080/news/list");
        final HttpResponse httpResponse = httpClient.execute(httpGet);
        return httpResponse.getEntity().getContent();
    }
}
