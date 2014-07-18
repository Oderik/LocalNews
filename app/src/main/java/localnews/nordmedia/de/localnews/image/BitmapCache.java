package localnews.nordmedia.de.localnews.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Oderik on 18.07.2014.
 */
public class BitmapCache extends LruCache<String, Bitmap> {

    public static final BitmapCache INSTANCE = new BitmapCache();
    public static final String TAG = BitmapCache.class.getSimpleName();

    private BitmapCache() {
        super((int) (Runtime.getRuntime().totalMemory() / (8 * 1024)));
    }

    @Override
    protected int sizeOf(final String key, final Bitmap value) {
        return byteCount(value) / 1024;
    }

    private int byteCount(final Bitmap value) {
        if (Build.VERSION.SDK_INT < 19) {
            return value.getByteCount();
        } else {
            return value.getAllocationByteCount();
        }
    }

    @Override
    protected Bitmap create(final String key) {
        try {
            return BitmapFactory.decodeStream(new URL(key).openStream());
        } catch (IOException e) {
            Log.e(TAG, "Could not load image " + key, e);
            return null;
        }
    }
}
