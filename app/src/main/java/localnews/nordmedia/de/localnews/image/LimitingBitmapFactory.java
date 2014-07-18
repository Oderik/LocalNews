package localnews.nordmedia.de.localnews.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;

import localnews.nordmedia.de.localnews.DataSource;

/**
 * Created by Oderik on 18.07.2014.
 */
public class LimitingBitmapFactory extends BitmapFactory{
    public static Bitmap decodeDataSource(final DataSource dataSource, final int threshold) {
        try {
            final Options options = new Options();
            options.inJustDecodeBounds = true;
            decodeStream(dataSource.open(), null, options);

            final int maxDimension = Math.max(options.outWidth, options.outHeight);
            options.inSampleSize = Math.max(1, maxDimension / threshold);
            options.inJustDecodeBounds = false;

            return decodeStream(dataSource.open(), null, options);
        } catch (IOException e) {
            Log.e(LimitingBitmapFactory.class.getSimpleName(), "Could not decode bitmap", e);
            return null;
        }
    }
}
