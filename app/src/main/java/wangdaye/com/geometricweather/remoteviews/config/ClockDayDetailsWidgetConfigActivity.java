package wangdaye.com.geometricweather.remoteviews.config;

import android.view.View;
import android.widget.RemoteViews;

import dagger.hilt.android.AndroidEntryPoint;
import wangdaye.com.geometricweather.R;
import wangdaye.com.geometricweather.remoteviews.presenters.ClockDayDetailsWidgetIMP;

/**
 * Clock day details widget config activity.
 * */

@AndroidEntryPoint
public class ClockDayDetailsWidgetConfigActivity extends AbstractWidgetConfigActivity {

    @Override
    public void initData() {
        super.initData();

        String[] clockFonts = getResources().getStringArray(R.array.clock_font);
        String[] clockFontValues = getResources().getStringArray(R.array.clock_font_values);

        this.clockFontValueNow = "light";
        this.clockFonts = new String[] {clockFonts[0], clockFonts[1], clockFonts[2]};
        this.clockFontValues = new String[] {clockFontValues[0], clockFontValues[1], clockFontValues[2]};
    }

    @Override
    public void initView() {
        super.initView();

        mViewTypeContainer.setVisibility(View.VISIBLE);
        mCardStyleContainer.setVisibility(View.VISIBLE);
        mCardAlphaContainer.setVisibility(View.VISIBLE);
        mTextColorContainer.setVisibility(View.VISIBLE);
        mTextSizeContainer.setVisibility(View.VISIBLE);
        mClockFontContainer.setVisibility(View.VISIBLE);
        mHideLunarContainer.setVisibility(isHideLunarContainerVisible());
    }

    @Override
    public RemoteViews getRemoteViews() {
        return ClockDayDetailsWidgetIMP.getRemoteViews(
                this,
                getLocationNow(),
                cardStyleValueNow, cardAlpha,
                textColorValueNow, textSize, clockFontValueNow, hideLunar
        );
    }

    @Override
    public String getSharedPreferencesName() {
        return getString(R.string.sp_widget_clock_day_details_setting);
    }
}