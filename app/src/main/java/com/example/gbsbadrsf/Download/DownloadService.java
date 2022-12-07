package com.example.gbsbadrsf.Download;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface DownloadService {
    @GET
    @Streaming
    Observable<Response<ResponseBody>> download(@Url String fileName);
}
