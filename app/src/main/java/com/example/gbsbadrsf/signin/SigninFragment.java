package com.example.gbsbadrsf.signin;

import static android.content.ContentValues.TAG;
import static com.example.gbsbadrsf.MainActivity.refreshUi;
import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.hideToolBar;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showToolBar;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.Util.Constant.BASE_DOWNLOAD_URL;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.BuildConfig;
import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.APIResponseSignin;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.data.response.UserInfo;
import com.example.gbsbadrsf.databinding.FragmentSigninBinding;

import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.Locale;

public class SigninFragment extends Fragment {
    public static String DOWNLOAD_PORT_NO ;
    public static String DOWNLOAD_IP ;
    public static String DOWNLOAD_URL;
    public static String APK_DATA;

    FragmentSigninBinding fragmentSigninBinding;
//    @Inject
//    ViewModelProviderFactory providerFactory;
    //private LoadingDialog dialog;
    SignInViewModel viewModel;
    ProgressDialog progressDialog;
    public static int USER_ID = -1;

    public SigninFragment() {
        // Required empty public constructor
    }



    public static SigninFragment newInstance(String param1, String param2) {
        SigninFragment fragment = new SigninFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleHelper.onCreate(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentLang = LocaleHelper.getLanguage(getContext());
        fragmentSigninBinding = FragmentSigninBinding.inflate(inflater, container, false);

        //attachListeners();
//        signinviewmodel = ViewModelProviders.of(this, providerFactory).get(SignInViewModel.class);
        viewModel = new ViewModelProvider(this).get(SignInViewModel.class);
        progressDialog = loadingProgressDialog(getContext());
        fragmentSigninBinding.dateVersion.setText(getString(R.string.build_date)+" Version "+getVersionNo());

        fragmentSigninBinding.loginBtn.setOnClickListener(v -> {

            if (fragmentSigninBinding.UsernameNewedttxt.getText().toString().trim().equals("")) {
                fragmentSigninBinding.usrEdt.setError(getString(R.string.uservalidationerror));
            } else if (fragmentSigninBinding.passwordedittext.getText().toString().trim().equals("")) {
                fragmentSigninBinding.passwordedittext.setError(getString(R.string.passwordvalidationerror));
            } else {
//
                if (fragmentSigninBinding.UsernameNewedttxt.getText().toString().equals("admin")
                        && fragmentSigninBinding.passwordedittext.getText().toString().equals("admin")){
                    Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_change_ip);
//                    Navigation.findNavController(requireView()).navigate(R.id.action_signinFragment_to_mainmenuFragment);
                } else {
                    viewModel.login(fragmentSigninBinding.UsernameNewedttxt.getText().toString(),
                            fragmentSigninBinding.passwordedittext.getText().toString());
                }
            }

        });

        defaultLanguage = Locale.getDefault().getLanguage();
        fragmentSigninBinding.language.setOnClickListener(v->{
            if (currentLang.equals("ar")) {
                LocaleHelper.setLocale(getContext(),"en");
                refreshUi((MainActivity) getActivity());
            } else if (currentLang.equals("en")){
                LocaleHelper.setLocale(getContext(),"ar");
                refreshUi((MainActivity) getActivity());
            }
            refreshUi((MainActivity) getActivity());
        });
        return fragmentSigninBinding.getRoot();

    }
    private String defaultLanguage,currentLang;
    private void handleLanguageButton() {
        Log.d("language",currentLang+" lang");
        if (defaultLanguage.equals("ar")) {
            fragmentSigninBinding.language.setText("E");
        } else if (currentLang.equals("en")){
            fragmentSigninBinding.language.setText("ع");
        }
    }
    private void observereUserName() {
        viewModel.getUserName().observe(getViewLifecycleOwner(), userName->MainActivity.USER_NAME = userName.toString());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        observeSignInError();
        observeSignInStatus();
//        obderveUserId();
        observereUserName();
        subscribeRequest();
        Log.d(TAG, "onViewCreated: "+getVersionNo());
    }

    private void observeSignInError() {
        viewModel.getSignInError().observe(getViewLifecycleOwner(),throwable -> {
            warningDialog(getContext(),getString(R.string.network_issue));
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        hideToolBar((MainActivity) getActivity());
    }

    private void obderveUserId() {
        viewModel.getUserId().observe(getViewLifecycleOwner(), userId->USER_ID= (int) userId);

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        showToolBar((MainActivity) getActivity());
    }

    private void observeSignInStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(), status -> {
            if (status.equals(Status.LOADING)) progressDialog.show();
            else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    private void subscribeRequest() {
        viewModel.getResponseLiveData().observe(getViewLifecycleOwner(), new Observer<APIResponseSignin>() {
            @Override
            public void onChanged(APIResponseSignin responseSignin) {
//                if (userInfo != Usertype.wrongusernameorpassword)
//                    Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_mainmenuFragment);
//                else {
//                    warningDialog(getContext(),getString(R.string.wrong_username_or_password));
//                }
                if (responseSignin!=null){
                    if (responseSignin.getResponseStatus().getIsSuccess()){
                        Log.d(TAG, "onChangedCurrent: "+getVersionNo());
                        Log.d(TAG, "onChangedApi: "+responseSignin.getMobileVersionNo());
                        if (getVersionNo()>=responseSignin.getMobileVersionNo()) {
                            showSuccessAlerter(responseSignin.getResponseStatus().getStatusMessage(), getActivity());
                            MainActivity.userInfo = responseSignin.getUserInfo();
                            USER_ID = userInfo.getUserId();
                            if (userInfo.getMobileFirstLogIn())
                                Navigation.findNavController(requireView()).navigate(R.id.action_signinFragment_to_changePasswordFragment);
                            else
                                Navigation.findNavController(requireView()).navigate(R.id.action_signinFragment_to_mainmenuFragment);
                        } else {
                            DOWNLOAD_PORT_NO = responseSignin.getApkPORT();
                            DOWNLOAD_IP      = responseSignin.getApkIP();
                            DOWNLOAD_URL     = responseSignin.getApkUrl();
//                            URIBuilder builder = new URIBuilder();
//                            try {
//                                builder.setScheme("http")
//                                        .setHost(SigninFragment.DOWNLOAD_IP)
//                                        .setPort(Integer.parseInt(SigninFragment.DOWNLOAD_PORT_NO))
//                                        .build();
//                            } catch (URISyntaxException e) {
//                                e.printStackTrace();
//                            }
//                            Log.d(TAG, "onChanged: "+builder.toString());
//                            warningDialog(getContext(),"current version:"+getVersionNo()+"\n new version:"+responseSignin.getMobileVersionNo()+"\n update url:"+builder.toString());
                            Navigation.findNavController(requireView()).navigate(R.id.action_signinFragment_to_fragment_application_update);
                        }
                    } else {
                        warningDialog(getContext(),responseSignin.getResponseStatus().getStatusMessage());
                    }
                } else {
                    warningDialog(getContext(),getString(R.string.error_in_getting_data));
                }

//                switch (usertype)
//                {
//                    case All:
//                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_mainmenuFragment);
//                        break;
//                    case wrongusernameorpassword:
//                        warningDialog(getContext(),getString(R.string.wrong_username_or_password));
//                        break;
//                    case ProductionUser:
//                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_production);
//                        break;
//                    case QualityControlUser:
//                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_Qc);
//                        break;
//                    case Qcmanufaturing:
//                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_Qcmanfacturing);
//                        break;
//                    case Qcwelding:
//                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_Qcwelding);
//                        break;
//                    case Qcpainting:
//                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_Qcpainting);
//                        break;
//                    case ProductionManufaturing:
//                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_productionmanfacturing);
//                        break;
//                    case ProductionWelding:
//                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_productionwelding);
//                        break;
//                    case ProductionPainting:
//                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_productionpainting);
//                        break;
//                    case WAREHOUSE_USER:
//                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_warehouseFragment);
//                        break;
//                    case HANDLING_USER:
//                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_countingFragment);
//                        break;
//                    case CONNECTION_ERROR:
//                        warningDialog(getContext(),getString(R.string.error_in_getting_data));
//                        break;
//                }
            }
        });
    }

    public int getVersionNo(){
        return BuildConfig.VERSION_CODE;
    }

}