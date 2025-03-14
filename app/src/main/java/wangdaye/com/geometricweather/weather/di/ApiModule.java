package wangdaye.com.geometricweather.weather.di;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import wangdaye.com.geometricweather.BuildConfig;
import wangdaye.com.geometricweather.weather.apis.AccuWeatherApi;
import wangdaye.com.geometricweather.weather.apis.AtmoAuraIqaApi;
import wangdaye.com.geometricweather.weather.apis.CNWeatherApi;
import wangdaye.com.geometricweather.weather.apis.CaiYunApi;
import wangdaye.com.geometricweather.weather.apis.MfWeatherApi;
import wangdaye.com.geometricweather.common.retrofit.interceptors.GzipInterceptor;

@InstallIn(ApplicationComponent.class)
@Module
public class ApiModule {

    @Provides
    public AccuWeatherApi provideAccuWeatherApi(OkHttpClient client,
                                                GzipInterceptor interceptor,
                                                GsonConverterFactory converterFactory,
                                                RxJava2CallAdapterFactory callAdapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.ACCU_WEATHER_BASE_URL)
                .client(client.newBuilder().addInterceptor(interceptor).build())
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .build()
                .create((AccuWeatherApi.class));
    }

    @Provides
    public CaiYunApi provideCaiYunApi(OkHttpClient client,
                                      GzipInterceptor interceptor,
                                      GsonConverterFactory converterFactory,
                                      RxJava2CallAdapterFactory callAdapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.CAIYUN_WEATHER_BASE_URL)
                .client(client.newBuilder().addInterceptor(interceptor).build())
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .build()
                .create((CaiYunApi.class));
    }

    @Provides
    public CNWeatherApi provideCNWeatherApi(OkHttpClient client,
                                            GzipInterceptor interceptor,
                                            GsonConverterFactory converterFactory,
                                            RxJava2CallAdapterFactory callAdapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.CN_WEATHER_BASE_URL)
                .client(client.newBuilder().addInterceptor(interceptor).build())
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .build()
                .create((CNWeatherApi.class));
    }

    @Provides
    public MfWeatherApi provideMfWeatherApi(OkHttpClient client,
                                            GzipInterceptor interceptor,
                                            GsonConverterFactory converterFactory,
                                            RxJava2CallAdapterFactory callAdapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.MF_WSFT_BASE_URL)
                .client(client.newBuilder().addInterceptor(interceptor).build())
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .build()
                .create((MfWeatherApi.class));
    }

    @Provides
    public AtmoAuraIqaApi provideAtmoAuraIqaApi(OkHttpClient client,
                                                GzipInterceptor interceptor,
                                                GsonConverterFactory converterFactory,
                                                RxJava2CallAdapterFactory callAdapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.IQA_ATMO_AURA_URL)
                .client(client.newBuilder().addInterceptor(interceptor).build())
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .build()
                .create((AtmoAuraIqaApi.class));
    }
}
