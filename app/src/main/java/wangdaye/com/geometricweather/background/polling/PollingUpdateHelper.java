package wangdaye.com.geometricweather.background.polling;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.Toast;

import java.util.List;

import wangdaye.com.geometricweather.R;
import wangdaye.com.geometricweather.common.basic.models.Location;
import wangdaye.com.geometricweather.common.basic.models.weather.Weather;
import wangdaye.com.geometricweather.db.DatabaseHelper;
import wangdaye.com.geometricweather.location.LocationHelper;
import wangdaye.com.geometricweather.common.utils.helpers.IntentHelper;
import wangdaye.com.geometricweather.weather.WeatherHelper;

/**
 * Polling updateRotation helper.
 * */

public class PollingUpdateHelper {

    private final Context mContext;

    private final LocationHelper mLocationHelper;
    private final WeatherHelper mWeatherHelper;

    private final List<Location> mLocationList;

    private OnPollingUpdateListener mListener;

    public interface OnPollingUpdateListener {
        void onUpdateCompleted(@NonNull Location location, @Nullable Weather old,
                               boolean succeed, int index, int total);
        void onPollingCompleted();
    }

    public PollingUpdateHelper(Context context,
                               LocationHelper locationHelper,
                               WeatherHelper weatherHelper,
                               List<Location> locationList) {
        mContext = context;
        mLocationHelper = locationHelper;
        mWeatherHelper = weatherHelper;
        mLocationList = locationList;
    }

    // control.

    public void pollingUpdate() {
        requestData(0, false);
    }

    public void cancel() {
        mLocationHelper.cancel();
        mWeatherHelper.cancel();
    }

    private void requestData(int position, boolean located) {
        Weather old = DatabaseHelper.getInstance(mContext).readWeather(mLocationList.get(position));
        if (old != null && old.isValid(0.25F)) {
            mLocationList.get(position).setWeather(old);
            new RequestWeatherCallback(old, position, mLocationList.size()).requestWeatherSuccess(
                    mLocationList.get(position));
            return;
        }
        if (mLocationList.get(position).isCurrentPosition() && !located) {
            mLocationHelper.requestLocation(mContext, mLocationList.get(position), true,
                    new RequestLocationCallback(position, mLocationList.size()));
        } else {
            mWeatherHelper.requestWeather(mContext, mLocationList.get(position),
                    new RequestWeatherCallback(old, position, mLocationList.size())
            );
        }
    }

    // interface.

    public void setOnPollingUpdateListener(OnPollingUpdateListener l) {
        mListener = l;
    }

    // on request location listener.

    private class RequestLocationCallback implements LocationHelper.OnRequestLocationListener {

        private final int mIndex;
        private final int mTotal;

        RequestLocationCallback(int index, int total) {
            mIndex = index;
            mTotal = total;
        }

        @Override
        public void requestLocationSuccess(Location requestLocation) {
            mLocationList.set(mIndex, requestLocation);

            if (requestLocation.isUsable()) {
                requestData(mIndex, true);
            } else {
                requestLocationFailed(requestLocation);
                Toast.makeText(
                        mContext,
                        mContext.getString(R.string.feedback_not_yet_location),
                        Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void requestLocationFailed(Location requestLocation) {
            if (mLocationList.get(mIndex).isUsable()) {
                requestData(mIndex, true);
            } else {
                new RequestWeatherCallback(null, mIndex, mTotal)
                        .requestWeatherFailed(mLocationList.get(mIndex));
            }
        }
    }

    // on request weather listener.

    private class RequestWeatherCallback implements WeatherHelper.OnRequestWeatherListener {

        private final @Nullable Weather mOld;
        private final int mIndex;
        private final int mTotal;

        RequestWeatherCallback(@Nullable Weather old, int index, int total) {
            mOld = old;
            mIndex = index;
            mTotal = total;
        }

        @Override
        public void requestWeatherSuccess(@NonNull Location requestLocation) {
            mLocationList.set(mIndex, requestLocation);

            Weather weather = requestLocation.getWeather();
            if (weather != null
                    && (mOld == null
                    || weather.getBase().getTimeStamp() != mOld.getBase().getTimeStamp())) {
                if (mListener != null) {
                    mListener.onUpdateCompleted(requestLocation, mOld, true, mIndex, mTotal);
                }
                IntentHelper.sendBackgroundUpdateBroadcast(mContext, requestLocation);

                if (mIndex + 1 < mTotal) {
                    requestData(mIndex + 1, false);
                } else if (mListener != null) {
                    mListener.onPollingCompleted();
                }
            } else {
                requestWeatherFailed(requestLocation);
            }
        }

        @Override
        public void requestWeatherFailed(@NonNull Location requestLocation) {
            mLocationList.set(mIndex, requestLocation);

            if (mListener != null) {
                mListener.onUpdateCompleted(requestLocation, mOld, false, mIndex, mTotal);
            }
            if (mIndex + 1 < mTotal) {
                requestData(mIndex + 1, false);
            } else if (mListener != null) {
                mListener.onPollingCompleted();
            }
        }
    }
}
