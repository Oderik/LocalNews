package localnews.nordmedia.de.localnews.news;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.content.AsyncTaskLoader;

/**
* Created by Oderik on 17.07.2014.
*/
public class NewsLoader extends AsyncTaskLoader<NewsFeed> implements LocationListener {

    private NewsFeed newsFeed;

    public NewsLoader(Context context) {
        super(context);
    }

    private FetchNewsFeedCallable createNewsFeedCallable(final Context context, final Location location) {
        return new FetchNewsFeedCallable(context, location);
    }

    @Override
    public NewsFeed loadInBackground() {
        final Location location = getLocation();
        final FetchNewsFeedCallable fetchNewsFeedCallable = createNewsFeedCallable(getContext(), location);
        try {
            newsFeed = fetchNewsFeedCallable.call();
            return newsFeed;
        } catch (Exception e) {
            return null;
        }
    }

    private Location getLocation() {
        Looper.prepare();
        final LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        final Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        locationManager.removeUpdates(this);
        return lastKnownLocation;
    }

    @Override
    protected void onStartLoading() {
        if (newsFeed != null) {
            deliverResult(newsFeed);
        } else {
            forceLoad();
        }
    }

    @Override
    public void onLocationChanged(final Location location) {

    }

    @Override
    public void onStatusChanged(final String provider, final int status, final Bundle extras) {

    }

    @Override
    public void onProviderEnabled(final String provider) {

    }

    @Override
    public void onProviderDisabled(final String provider) {

    }
}
