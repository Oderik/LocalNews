package localnews.nordmedia.de.localnews.image;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import localnews.nordmedia.de.localnews.LocalNewsApplication;

/**
 * Created by Oderik on 18.07.2014.
 */
public class ImageCache {

    private static ImageCache INSTANCE;

    private final Set<String> filesLoading = new HashSet<String>();
    public static final String TAG = ImageCache.class.getSimpleName();
    private final File externalCacheDir;

    public ImageCache(final Context context) {
        externalCacheDir = context.getExternalCacheDir();
    }

    public static void init(final Context context) {
        INSTANCE = new ImageCache(context);
    }

    enum State {
        MISSING, LOADING, PRESENT
    }

    public State stateOf(final String path) {
        synchronized (filesLoading) {
            if (filesLoading.contains(path)) {
                return State.LOADING;
            }
        }

        if (isCacheFilePresent(createCacheFileOfPath(path))) {
            return State.PRESENT;
        }
        return State.MISSING;
    }

    public void put(final String path, final InputStream inputStream) throws IOException {
        synchronized (filesLoading) {
            if (!filesLoading.add(path)) {
                return;
            }
        }
        final File cacheFile = createCacheFileOfPath(path);
        final FileOutputStream outputStream = new FileOutputStream(cacheFile);

        final byte[] buffer = new byte[64 * 1024];
        int read;
        try {
            while ((read = inputStream.read(buffer)) >= 0) {
                outputStream.write(buffer, 0, read);
            }
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                Log.w(TAG, "Could not close file", e);
            }

            try {
                inputStream.close();
            } catch (IOException e) {
                Log.w(TAG, "Could not close input stream", e);
            }
            synchronized (filesLoading) {
                filesLoading.remove(path);
            }
        }

    }

    private File createCacheFileOfPath(final String path) {
        return new File(externalCacheDir, cacheFileNameOfPath(path));
    }

    private String cacheFileNameOfPath(final String path) {
        return hash(path) + extensionOfPath(path);
    }

    public File get(final String path) {
        synchronized (filesLoading) {
            if (filesLoading.contains(path)) {
                return null;
            }
        }
        final File file = createCacheFileOfPath(path);
        if (isCacheFilePresent(file)) {
            return file;
        }
        return null;
    }

    private boolean isCacheFilePresent(final File file) {
        return file.exists() && file.canRead();
    }


    private String hash(final String string) {
        return Integer.toString(string.hashCode());
    }

    private String extensionOfPath(final String path) {
        return path.substring(path.lastIndexOf("."), path.length());
    }

}
