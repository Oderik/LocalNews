package localnews.nordmedia.de.localnews.news;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Oderik on 17.07.2014.
 */
public class JsonNewsFeedReader {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-d'T'HH:mm:ssZ");

    public NewsFeed read(final InputStream inputStream) throws IOException, JSONException {
        return read(new InputStreamReader(inputStream));
    }

    public NewsFeed read(final Reader reader) throws IOException, JSONException {
        final BufferedReader bufferedReader = new BufferedReader(reader);

        final ArrayList<News> newsList = new ArrayList<News>();
        try {
            final StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            final JSONObject responseObject = new JSONObject(stringBuilder.toString());
            final JSONObject resultObject = responseObject.getJSONObject("result");
            final JSONArray newsArray = resultObject.getJSONArray("news");

            for (int i = 0; i < newsArray.length(); i++) {
                final JSONObject newsObject = newsArray.getJSONObject(i);
                final News news = new News();
                news.title = getString(newsObject, "title");
                news.teaser = getString(newsObject, "teaser");
                news.url = getString(newsObject, "url");
                news.imageUrl = getString(newsObject,"imageUrl");
                final String dateString = getString(newsObject, "date");
                if (dateString != null) {
                    try {
                        news.time = DATE_FORMAT.parse(dateString);
                    } catch (ParseException e) {
                        Log.i(JsonNewsFeedReader.class.getSimpleName(), "Unparsable date " + dateString, e);
                    }
                }
                newsList.add(news);
            }
        } finally {
            bufferedReader.close();
        }


        return new NewsFeed(newsList);
    }

    private String getString(final JSONObject jsonObject, final String key) {
        return jsonObject.isNull(key) ? null : jsonObject.optString(key);
    }

}
