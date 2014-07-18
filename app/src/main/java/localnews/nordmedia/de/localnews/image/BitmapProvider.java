package localnews.nordmedia.de.localnews.image;

import android.graphics.Bitmap;

/**
 * Created by Oderik on 18.07.2014.
 */
public class BitmapProvider {
    interface OnBitmapAvailableListener {
        void onBitmapAvailable(String path, Bitmap bitmap);
    }

    public void request(String path, OnBitmapAvailableListener listener) {
        final Bitmap bitmap = BitmapCache.INSTANCE.get(path);
    }

}
