package localnews.nordmedia.de.localnews.image;

import android.graphics.Bitmap;
import android.os.AsyncTask;

/**
* Created by Oderik on 18.07.2014.
*/
public class LoadBitmapTask extends AsyncTask<String, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(final String... params) {
        return BitmapCache.INSTANCE.get(params[0]);
    }

}
