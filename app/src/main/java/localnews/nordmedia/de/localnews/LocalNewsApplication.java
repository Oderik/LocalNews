package localnews.nordmedia.de.localnews;

import android.app.Application;

import localnews.nordmedia.de.localnews.image.ImageCache;

/**
 * Created by Oderik on 18.07.2014.
 */
public class LocalNewsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageCache.init(this);
    }
}
