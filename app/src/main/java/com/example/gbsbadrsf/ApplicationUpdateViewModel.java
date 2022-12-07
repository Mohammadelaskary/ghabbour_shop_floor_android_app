package com.example.gbsbadrsf;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.gbsbadrsf.Download.Download;
import com.example.gbsbadrsf.Download.DownloadProgressListener;
import com.example.gbsbadrsf.Download.DownloadService;
import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiFactoryDownload;
import com.example.gbsbadrsf.repository.ApiInterface;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class ApplicationUpdateViewModel extends AndroidViewModel  {
    private DownloadService downloadApiInterface;
    private ApiInterface apiInterface;
    private MutableLiveData<Status> status;
    private MutableLiveData<ApiResponseGetMobileVersion> getMobileVersion;
    private MutableLiveData<String> getMobileVersionError;
    private Application application;
    private MutableLiveData<Integer> progress;
    private CompositeDisposable disposable;
    public ApplicationUpdateViewModel(@NonNull Application application) {
        super(application);
        disposable = new CompositeDisposable();
        this.application = application;
//        downloadApiInterface = ApiFactoryDownload.getClient(listener).create(DownloadService.class);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        status = new MutableLiveData<>();
        progress = new MutableLiveData<>();
        getMobileVersion = new MutableLiveData<>();
        getMobileVersionError = new MutableLiveData<>();
    }

//    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        // There are no request codes
//                        Intent data = result.getData();
//                        doSomeOperations();
//                    }
//                }
//            });

    public void GetMobileVersion(){
        disposable.add(apiInterface.GetMobileVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ ->status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            getMobileVersion.postValue(response);
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                            getMobileVersionError.postValue(throwable.getLocalizedMessage());
                        }
                ));
    }
    public void downloadFile(String fileName){
        downloadApiInterface.download(fileName).subscribeOn(Schedulers.io()).flatMap(new Function<Response<ResponseBody>, ObservableSource<?>>() {
            @Override
            public ObservableSource<File> apply(Response<ResponseBody> responseBodyResponse) throws Exception {
                return new Observable<File>() {
                    @Override
                    protected void subscribeActual(Observer<? super File> observer) {
                        try {
                            String PATH = Objects.requireNonNull(application.getExternalFilesDir(null)).getAbsolutePath();
//                            File file = new File(PATH);
//                            boolean isCreate = file.mkdirs();
                            File dir = new File("/sdcard/ghabbour_apks");
                            if(!dir.exists()){
                                dir.mkdirs();
                            }
//                            if (dir.exists()) {
//                                boolean isDelete = dir.delete();
//                            }
                            writeFileOnInternalStorage("my_apk.apk",responseBodyResponse);

                            installApk();
//                            uninstallCurrentApk();
                        } catch (Exception e) {
                            Log.e("UpdateAPP", "Update error! " + e.getMessage());
                        }
                    }
                };
            }
        }).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                status.postValue(Status.LOADING);
            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("UpdateAPP", "Update error! " + e.getMessage());
                status.postValue(Status.ERROR);
            }

            @Override
            public void onComplete() {
                Log.e("UpdateAPPFinish", "Update Finosh! ");

            }
        });
    }


    void installApk() {
        try {
            String PATH = Objects.requireNonNull(application.getExternalFilesDir(null)).getAbsolutePath();
            File file = new File( "/sdcard/ghabbour_apks/my_apk.apk");
            Log.d("UpdateAPP",PATH);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= 24) {
                Uri downloaded_apk = FileProvider.getUriForFile(application.getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", file);
                intent.setDataAndType(downloaded_apk, "application/vnd.android.package-archive");
                List<ResolveInfo> resInfoList = application.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    application.grantUriPermission(BuildConfig.APPLICATION_ID + ".provider", downloaded_apk, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                application.startActivity(intent);
            } else {
                intent.setAction(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            }
            application.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void writeFileOnInternalStorage(String sFileName, Response<ResponseBody> responseBodyResponse){
        File dir = new File("/sdcard/ghabbour_apks");
        if(!dir.exists()){
            dir.mkdirs();
        }
        try {
            File file = new File(dir,sFileName);
            FileOutputStream fos = new FileOutputStream(file);

            InputStream is = responseBodyResponse.body().byteStream();

            byte[] buffer = new byte[1024];
//            progress.postValue(0);
            int len1;
            int total = 0;
            while ((len1 = is.read(buffer)) != -1) {
                total += len1;
//                progress.postValue( (int)(len1*100)/total);
                fos.write(buffer, 0, len1);
//                                publishProgress((int) ((total * 100) / lenghtOfFile));
            }
            fos.close();
            is.close();
            status.postValue(Status.SUCCESS);
            Log.d("UpdateApp","saved");
        } catch (Exception e){
            e.printStackTrace();
            Log.d("UpdateApp",e.getMessage());
//            showErrorAlerter(e.getMessage(),activity);
        }
//        progress.postValue(100);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void writeFileOnInternalStorage(String sFileName, String apkData){
        File dir = new File("/sdcard/ghabbour_apks");
        if(!dir.exists()){
            dir.mkdirs();
        }
        try {
            File file = new File(dir,sFileName);
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);

//            InputStream is = responseBodyResponse.body().byteStream();

//            byte[] buffer = new byte[1024];
            progress.postValue(0);

            byte[] buffer = Base64.decode(apkData,Base64.DEFAULT);
            InputStream is = new ByteArrayInputStream(buffer);
            int len1;
            int total = 0;
            while ((len1 = is.read(buffer)) != -1) {
                total += len1;
                progress.postValue( (int)(len1*100)/total);
                fos.write(buffer, 0, len1);
//                                publishProgress((int) ((total * 100) / lenghtOfFile));
            }
            fos.close();
            is.close();
            status.postValue(Status.SUCCESS);
            Log.d("UpdateApp","saved");
        } catch (Exception e){
            e.printStackTrace();
            Log.d("UpdateApp",e.getMessage());
//            showErrorAlerter(e.getMessage(),activity);
        }
//        progress.postValue(100);
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<Integer> getProgress() {
        return progress;
    }

    DownloadProgressListener listener = new DownloadProgressListener() {
        @Override
        public void update(long bytesRead, long contentLength, boolean done) {
            Download download = new Download();
            download.setTotalFileSize(contentLength);
            download.setCurrentFileSize(bytesRead);
            int progressNum = (int) ((bytesRead * 100) / contentLength);
//            download.setProgress(progress);
//            sendNotification(download);
            progress.postValue(progressNum);
        }
    };

    public MutableLiveData<ApiResponseGetMobileVersion> getGetMobileVersion() {
        return getMobileVersion;
    }

    public MutableLiveData<String> getGetMobileVersionError() {
        return getMobileVersionError;
    }
}