package com.example.gbsbadrsf;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.UserInfo;
import com.example.gbsbadrsf.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;
import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.BarcodeReader;
import com.intermec.aidc.BarcodeReaderException;
import com.intermec.aidc.VirtualWedge;
import com.intermec.aidc.VirtualWedgeException;

public class MainActivity extends AppCompatActivity  {
    private static final String MY_PREFS_NAME = "database_url";
    private static ActivityMainBinding activityMainBinding;
    private static BarcodeReader barcodeReader;
    private static BarcodeReader barcodeReaderSequence;
    private static com.intermec.aidc.BarcodeReader bcr;
    private static VirtualWedge wedge;
    public static  String DEVICE_SERIAL_NO;
    private AidcManager manager;
    public static String IP;
    public static String USER_NAME = "";
    public static UserInfo userInfo = new UserInfo();
    public static Context context ;
    private static MediaPlayer warningMediaPlayer;
    public static void refreshUi(MainActivity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBaseUrlFromSharedPreferences();
        context = this;
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        warningMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.warning_tone);
        LocaleHelper.onCreate(this);
        if (LocaleHelper.getLanguage(this).equals("ar")) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        installToolbar();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Log.d(TAG, "onCreate: "+getDeviceName());
        DEVICE_SERIAL_NO = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.d(TAG, "onCreate: "+getDeviceName());
        if (getDeviceName().equals("Honeywell EDA51")) {
            AidcManager.create(this, aidcManager -> {
                manager = aidcManager;
                barcodeReader = manager.createBarcodeReader();
                barcodeReaderSequence = manager.createBarcodeReader();
                Log.d("BArcodeConnecction","connected");

            });
        } else if (getDeviceName().equals("Foxconn International Holdings Limited CN51 NCU")) {
            com.intermec.aidc.AidcManager.connectService(this, new com.intermec.aidc.AidcManager.IServiceListener() {
                public void onConnect()
                {

                    // The depended service is connected and it is ready
                    // to receive bar code requests and virtual wedge
                    try {
                        //Initial bar code reader instance
                        bcr = new com.intermec.aidc.BarcodeReader();
                        //disable virtual wedge
                        wedge = new VirtualWedge();
                        wedge.setEnable(false);
                        Log.d("BArcodeConnecction","connected");

                    } catch (BarcodeReaderException e)
                    {
                        e.printStackTrace();
                        Log.d("BArcodeConnecction","disconnected"+e.getErrorMessage());
                    }
                    catch (VirtualWedgeException e)
                    {
                        e.printStackTrace();
                        Log.d("BArcodeConnecction","disconnected"+e.getErrorMessage());
                    }

                    //set action for other activities
                    //                ActivitySetting();
                }

                public void onDisconnect()
                {
                    //add disconnect message/action here
                }

            });
        }


    }

    public static VirtualWedge getWedge() {
        return wedge;
    }

    private void installToolbar() {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_24);

        // showing the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public static BarcodeReader getBarcodeObject() {
        return barcodeReader;
    }
    public static com.intermec.aidc.BarcodeReader getBarcodeObjectIntermec() {
        return bcr;
    }
    public static BarcodeReader getBarcodeObjectsequence() {
        return barcodeReaderSequence;
    }
    public void getBaseUrlFromSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        if (prefs.contains("base_url"))
            IP = prefs.getString("base_url", "No name defined");
        else
            IP = "45.241.58.79:97";
    }
    public static ActivityMainBinding getBinding() {
        return activityMainBinding;
    }

//    public static Observable<Boolean> isConnected = new MutableLiveData<>();
//    public static void isConnectedToServer() {
//        try {
//            URL url = new URL(Constant.BASE_URL);   // Change to "http://google.com" for www  test.
//            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
//            urlc.setConnectTimeout(60 * 1000);          // 60 s.
//            urlc.connect();
//            if (urlc.getResponseCode() == 200) {        // 200 = "OK" code (http connection is fine).
//                isConnected.postValue(true);
//            } else {
//                isConnected.postValue(false);
//            }
//        } catch (MalformedURLException e1) {
//            isConnected.postValue(false);
//        } catch (IOException e) {
//            isConnected.postValue(false);
//        }
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

//        if (barcodeReader != null) {
//            // close BarcodeReader to clean up resources.
//            barcodeReader.close();
//            barcodeReader = null;
//        }
//        if (barcodeReaderSequence != null) {
//            // close BarcodeReader to clean up resources.
//            barcodeReaderSequence.close();
//            barcodeReaderSequence = null;
//        }
//
//
//        if (manager != null) {
////            // close AidcManager to disconnect from the scanner service.
//            // once closed, the object can no longer be used.
//            manager.close();
//        }

            //disconnect from data collection service
//            com.intermec.aidc.AidcManager.disconnectService();

  }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: onBackPressed(); break;
            case R.id.logout:{
                finish();
                startActivity(getIntent());
            } break;
            case R.id.change_password:{
                NavController navController = Navigation.findNavController(this, R.id.myNavhostfragment);
//                navController.navigateUp();
                navController.navigate(R.id.fragment_change_password);
            } break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public static MediaPlayer getWarningMediaPlayer(){
        return warningMediaPlayer;
    }
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }
//    private static final int TIME_DELAY = 2000;
//    private static long back_pressed;
//    @Override
//    public void onBackPressed() {
//        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
//            super.onBackPressed();
//        } else {
//            Snackbar.make(activityMainBinding.getRoot(), R.string.press_once_again_to_exit,Snackbar.LENGTH_LONG).show();
//        }
//        back_pressed = System.currentTimeMillis();
//    }

}