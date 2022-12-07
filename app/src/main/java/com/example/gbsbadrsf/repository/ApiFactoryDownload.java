package com.example.gbsbadrsf.repository;

import static com.example.gbsbadrsf.Util.Constant.BASE_DOWNLOAD_URL;
import static com.example.gbsbadrsf.Util.Constant.BASE_URL;
import static com.example.gbsbadrsf.signin.SigninFragment.DOWNLOAD_IP;
import static com.example.gbsbadrsf.signin.SigninFragment.DOWNLOAD_PORT_NO;

import android.os.FileUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.gbsbadrsf.Download.DownloadProgressInterceptor;
import com.example.gbsbadrsf.Download.DownloadProgressListener;
import com.example.gbsbadrsf.Download.DownloadService;
import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.signin.SigninFragment;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.apache.http.client.utils.URIBuilder;
import org.reactivestreams.Subscriber;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactoryDownload {
    private static final String TAG = "DownloadAPI";
    private static final int DEFAULT_TIMEOUT = 15;

//    public ApiFactoryDownload(String url, DownloadProgressListener listener) {
//
//
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(interceptor)
//                .retryOnConnectionFailure(true)
//                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .build();
//
//        retrofit = new Retrofit.Builder()
//                .baseUrl(url)
//                .client(client)
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//    }

//    public void downloadAPK(@NonNull String url, final File file, Subscriber subscriber) {
//        Log.d(TAG, "downloadAPK: " + url);
//
//        retrofit.create(DownloadService.class)
//                .download(url)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .map(new Func1<ResponseBody, InputStream>() {
//                    @Override
//                    public InputStream call(ResponseBody responseBody) {
//                        return responseBody.byteStream();
//                    }
//                })
//                .observeOn(Schedulers.computation())
//                .doOnNext(new Action1<InputStream>() {
//                    @Override
//                    public void call(InputStream inputStream) {
//                        try {
//                            FileUtils.writeFile(inputStream, file);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            throw new CustomizeException(e.getMessage(), e);
//                        }
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//    }


    private static Retrofit retrofit = null;
    private static int REQUEST_TIMEOUT = 60;
    private static OkHttpClient okHttpClient;
    private static DownloadProgressInterceptor interceptor;
    public static Retrofit getClient( DownloadProgressListener listener) {

        interceptor = new DownloadProgressInterceptor(listener);
        URIBuilder builder = new URIBuilder();
//        try {
//            builder.setScheme("http")
//                    .setHost(DOWNLOAD_IP)
//                    .setPort(Integer.parseInt(DOWNLOAD_PORT_NO))
////                    .setPath("/GB-Badr-Shopfloor.apk/")
//                    .build();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
        // .fragment("section-name");
        String myUrl = builder.toString();
        if (okHttpClient == null)
            initOkHttp();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(myUrl)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static void initOkHttp() {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(interceptor);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/apk")
                        .addHeader("Content-Type", "application/apk");

                // Adding Authorization token (API Key)
                // Requests will be denied without API key
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        okHttpClient = httpClient.build();
    }
}
