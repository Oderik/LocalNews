package localnews.nordmedia.de.localnews.news;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.InputStream;

import localnews.nordmedia.de.localnews.DataSource;

/**
 * Created by Oderik on 17.07.2014.
 */
public class HttpDataSource implements DataSource {

    public static final String TAG = HttpDataSource.class.getSimpleName();
    private final AndroidHttpClient httpClient;
    private double longitude;
    private double latitude;

    public HttpDataSource(final Context context, final double longitude, final double latitude) {
        httpClient = AndroidHttpClient.newInstance(System.getProperty("http.agent"), context);
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public InputStream open() throws IOException {
        final String uri = "http://hackt.sichwarm.de:8080/news/list?" + formatLocation();
        Log.d(TAG, "Requesting " + uri);
        final HttpGet httpGet = new HttpGet(uri);
        final HttpResponse httpResponse = httpClient.execute(httpGet);
        return httpResponse.getEntity().getContent();
    }

    private String formatLocation() {
        return String.format("longitude=%s&latitude=%s&radius=50.0", longitude, latitude);
    }
}
