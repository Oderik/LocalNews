package localnews.nordmedia.de.localnews.news;

import android.content.Context;
import android.location.Location;
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
    private final Location location;

    public HttpDataSource(final Context context, final Location location) {
        this.location = location;
        httpClient = AndroidHttpClient.newInstance(System.getProperty("http.agent"), context);
    }

    @Override
    public InputStream open() throws IOException {
        final HttpGet httpGet = new HttpGet("http://hackt.sichwarm.de:8080/news/list?"+formatLocation());
        final HttpResponse httpResponse = httpClient.execute(httpGet);
        return httpResponse.getEntity().getContent();
    }

    private String formatLocation() {
        return String.format("long=%f&lat=%f", location.getLongitude(), location.getLatitude());
    }
}
