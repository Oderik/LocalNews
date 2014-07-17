package localnews.nordmedia.de.localnews.news;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Oderik on 17.07.2014.
 */
public class AssetsDataSource implements DataSource {
    private final Context context;

    public AssetsDataSource(final Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public InputStream open() throws IOException {
        return context.getAssets().open("demodata.json");

    }
}
