package com.example.gbsbadrsf.Paint.paintstation;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.gbsbadrsf.MyMethods.MyMethods.hideKeyboard;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.Paint.Basket;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.data.response.Baskets;
import com.example.gbsbadrsf.data.response.Pprpaint;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentPaintdstationBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.ScannerNotClaimedException;
import com.honeywell.aidc.ScannerUnavailableException;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.honeywell.aidc.UnsupportedPropertyException;
import com.intermec.aidc.BarcodeReadListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Paintdstation extends Fragment implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener, PaintStationAdapter.onCheckedChangedListener, BarcodeReadListener {
    public static final String PAINT_PPR_KEY = "paint_ppr";
    FragmentPaintdstationBinding binding;
    public RecyclerView recyclerView;
    private SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;
//    @Inject
//    ViewModelProviderFactory provider;
    CheckBox checkBox;

//    @Inject
//    Gson gson;
    PaintStationAdapter adapter;
    List<Pprpaint> Paintsequenceresponse;
    List<Baskets>basketlist;
    PaintstationViewModel viewModel;
    public static InfoForSelectedPaintViewModel infoForSelectedPaintViewModel;

    List<String> selectedsequence;
    Pprpaint clickedPprpaint = null;
    Baskets baskets;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barCodeReader = new SetUpBarCodeReader(this,this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaintdstationBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

//        viewModel = ViewModelProviders.of(this,provider).get(PaintstationViewModel.class);
        viewModel = new ViewModelProvider(this).get(PaintstationViewModel.class);
//        infoForSelectedPaintViewModel=ViewModelProviders.of(this,provider).get(InfoForSelectedPaintViewModel.class);
        infoForSelectedPaintViewModel = new ViewModelProvider(this).get(InfoForSelectedPaintViewModel.class);
        progressDialog = loadingProgressDialog(getContext());
        addTextWatcher();
        viewModel.getpaintsequence(USER_ID,DEVICE_SERIAL_NO);
//        binding.barcodeEdt.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
//                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
//                {
//                    String jobOrderName = binding.barcodeEdt.getText().toString().trim();
//                    if (jobOrderName.isEmpty())
//                        binding.jobOrderName.setError(null);
//                    else
//
//                    return true;
//                }
//                return false;
//            }
//        });

//        fragmentPaintdstationBinding.barcodeEdt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                viewModel.getpaintsequence("1","S123",fragmentPaintdstationBinding.barcodeEdt.getText().toString());
//
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//
//            }
//        });
//        addDummyBaskets();
        setUpRecyclerView();


        attachListeners();
        subscribeRequest();
        observeStatus();

        selectedsequence = new ArrayList<>();

    }

    Basket basket;
    private void addDummyBaskets() {
        basket = new Basket();
        basket.setBasketCode("001");
        basket.setBasketMoveId(1);
        basket.setDateSignIn("14/5/2022");
        basket.setJobOrderId(1);
        basket.setOperationId(5);
    }

    private void observeStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            if (status.equals(Status.LOADING))
                progressDialog.show();
            else if (status.equals(Status.SUCCESS))
                progressDialog.hide();
            else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    private void addTextWatcher() {
        clearInputLayoutError(binding.jobOrderName);
    }
    Bundle bundle = new Bundle();
    private void subscribeRequest() {
        infoForSelectedPaintViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), response -> {
            if (response!=null) {
                String statusMessage = response.getResponseStatus().getStatusMessage();
//                List<Basket> baskets = response.getBaskets();
                if (response.getResponseStatus().getIsSuccess()) {
                    if (!response.getBaskets().isEmpty()) {
//                        bundle.putString("operationrname", clickedPprpaint.getOperationEnName());
//                        bundle.putString("loadingqty", clickedPprpaint.getLoadingQty().toString());
//                        bundle.putString("parentdesc", clickedPprpaint.getParentDescription());
//                        bundle.putString("parentcode", clickedPprpaint.getParentCode());
//                        bundle.putString("parentid", clickedPprpaint.getParentID().toString());
//                        bundle.putInt("jobOrderId", clickedPprpaint.getJobOrderID());
                        //bundle.putString("basketcode",clickedPprwelding.getBaskets().getBasketCode());
                        // bundle.putString("ddd",baskets.getBasketCode());
                        // bundle.putString("slslsl",infoForSelectedStationViewModel.getBaskets().getValue().getJobOrderId().toString());
                        bundle.putParcelable(PAINT_PPR_KEY,clickedPprpaint);
                        Navigation.findNavController(getView()).navigate(R.id.action_paintdstation_to_machineLoadingpaintFragment, bundle);
                    } else
                        warningDialog(getContext(), getString(R.string.there_is_no_baskets_for_this_job_order));
                } else
                    binding.jobOrderName.setError(statusMessage);
            } else
                warningDialog(getContext(), getString(R.string.error_in_getting_data));
//            switch (staustype)
//            {
//                case gettingdatasuccesfully:
//
//
//                    break;
//
//                case noloadingquantityformachine:
//
////                        Toast.makeText(getContext(), "There is no loading quantity on the machine!", Toast.LENGTH_SHORT).show();
//                    binding.basketcodeEdt.setError("There is no loading quantity on the machine!");
//                    break;
//
//
//            }
        });

    }


    private void setUpRecyclerView() {
        Paintsequenceresponse = new ArrayList<>();
        adapter = new PaintStationAdapter(Paintsequenceresponse,this,getContext());
        binding.pprList.setAdapter(adapter);
        binding.pprList.setNestedScrollingEnabled(true);
        binding.pprList.setLayoutManager(new LinearLayoutManager(getContext()));



    }
    private void attachListeners() {

        viewModel.getPaintsequenceResponse().observe(getViewLifecycleOwner(), cuisines->{
//            productionsequenceresponse.clear();//malosh lazma
//            //if(cuisines!=null)
//            productionsequenceresponse.addAll(cuisines);
//            adapter.getproductionsequencelist(productionsequenceresponse);
            if (cuisines!=null){
                if (cuisines.isEmpty()) {
                    binding.noPprList.setVisibility(View.VISIBLE);
                    binding.noPprList.setText(getString(R.string.no_ppr_list));
                } else {
                    binding.noPprList.setVisibility(View.GONE);
                    binding.jobOrderName.setError(null);
                }
                adapter.getpaintsequencelist(cuisines);// today 23/11
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
//        binding.qtnokBtn.setOnClickListener(v -> {
//            String jobOrderName = binding.barcodeEdt.getText().toString().trim();
//            if (jobOrderName.isEmpty())
//                binding.jobOrderName.setError("Please scan or enter a valid job order name!");
//
//        });
        binding.qtnokBtn.setOnClickListener(__ -> {
            if (clickedPprpaint!=null) {
                infoForSelectedPaintViewModel.getselectedpaintsequence(USER_ID, DEVICE_SERIAL_NO, clickedPprpaint.getLoadingSequenceID().toString());
            } else
                warningDialog(getContext(),getString(R.string.please_select_at_least_one_ppr));
        });


    }


    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // update UI to reflect the data
                hideKeyboard(getActivity());
                String scannedText = barCodeReader.scannedData(barcodeReadEvent);
                binding.barcodeEdt.setText(scannedText);
                String jobOrderName = binding.barcodeEdt.getText().toString().trim();
                if (jobOrderName.isEmpty())
                    binding.jobOrderName.setError(null);
                else
                    viewModel.getpaintsequence(USER_ID,DEVICE_SERIAL_NO);
//if (TextUtils.isEmpty(fragmentProductionSequenceBinding.barcodeEdt.getText().toString())){





//}




            }
        });

    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
            }
        });
    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {
       barCodeReader.onTrigger(triggerStateChangeEvent);

    }
    @Override
    public void onResume() {
        super.onResume();
        barCodeReader.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        barCodeReader.onPause();
        barCodeReaderInterMec.onPause();
    }

    @Override
    public void onCheckedChanged(Pprpaint item) {
                clickedPprpaint = item;
    }


    @Override
    public void barcodeRead(com.intermec.aidc.BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // update UI to reflect the data
                hideKeyboard(getActivity());
                String scannedText = barCodeReaderInterMec.scannedData(barcodeReadEvent);
                binding.barcodeEdt.setText(scannedText);
                String jobOrderName = binding.barcodeEdt.getText().toString().trim();
                if (jobOrderName.isEmpty())
                    binding.jobOrderName.setError(null);
                else
                    viewModel.getpaintsequence(USER_ID,DEVICE_SERIAL_NO);
//if (TextUtils.isEmpty(fragmentProductionSequenceBinding.barcodeEdt.getText().toString())){





//}




            }
        });
    }
}
