package com.example.gbsbadrsf.Quality.paint.RandomQualityInception.PprListFragment;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.Quality.welding.RandomQualityInception.WeldingRandomQualityInceptionFragment.STATION_CODE;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.databinding.FragmentOnlineInspectionPprListBinding;
import com.example.gbsbadrsf.databinding.FragmentPaintQualityOperationPprListBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.intermec.aidc.BarcodeReadListener;

import java.util.ArrayList;
import java.util.List;


public class OnlineInspectionPprListFragment extends Fragment implements OnlineInspectionPprAdapter.OnPprItemClicked, BarcodeReader.BarcodeListener,BarcodeReader.TriggerListener, BarcodeReadListener {

    public static final String PAINT_PPR_LOADING_ID = "ppr_loading_id";
    public OnlineInspectionPprListViewModel viewModel;
//    @Inject
//    ViewModelProviderFactory provider;
//
//    @Inject
//    Gson gson;


    public OnlineInspectionPprListFragment() {
        // Required empty public constructor
    }


    public static OnlineInspectionPprListFragment newInstance() {
        return new OnlineInspectionPprListFragment();
    }
    private ProgressDialog progressDialog;
    private SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        viewModel = ViewModelProviders.of(this,provider).get(PaintSignOffPprListViewModel.class);

        progressDialog = loadingProgressDialog(getContext());
        barCodeReader = new SetUpBarCodeReader(this,this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
    }
    private FragmentOnlineInspectionPprListBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOnlineInspectionPprListBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(OnlineInspectionPprListViewModel.class);
        if (viewModel.getStationCode()!=null){
            binding.stationCode.getEditText().setText(viewModel.getStationCode());
            viewModel.GetStationInfoForQuality_Painting(viewModel.getStationCode());
        }
        setUpRecyclerView();
        observeGettingPprList();
        observeGettingPprListStatus();

    }

    private void observeGettingPprListStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    progressDialog.show();
                    break;
                case ERROR:
                    warningDialog(getContext(),getString(R.string.network_issue));
                    progressDialog.dismiss();
                    break;
                case SUCCESS:
                    progressDialog.dismiss();
                    break;
            }
        });
    }

    private OnlineInspectionPprAdapter adapter;
    private void setUpRecyclerView() {
        adapter = new OnlineInspectionPprAdapter(this);
        binding.pprList.setAdapter(adapter);
    }

    private void observeGettingPprList() {
        viewModel.getPaintStationWIP().observe(getViewLifecycleOwner(),apiResponseGetWIP_painting -> {
            if (apiResponseGetWIP_painting!=null){
                if (apiResponseGetWIP_painting.getResponseStatus().getIsSuccess()){
                    List<LastMovePaintingOnline> pprList = apiResponseGetWIP_painting.getLastMovePaintings();
                    if (!pprList.isEmpty()) {
                        adapter.setPprWipPaintList(pprList);
                        binding.noPprList.setVisibility(View.GONE);
                    } else {
                        adapter.setPprWipPaintList(new ArrayList<>());
                        binding.noPprList.setVisibility(View.VISIBLE);
                    }
                } else {
                    warningDialog(getContext(), apiResponseGetWIP_painting.getResponseStatus().getStatusMessage());
                    binding.noPprList.setText(apiResponseGetWIP_painting.getResponseStatus().getStatusMessage());
                    binding.noPprList.setVisibility(View.VISIBLE);
                    adapter.setPprWipPaintList(new ArrayList<>());
                }
                } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        barCodeReader.onResume();
//        changeTitle(getString(R.string.quality_ppr_list),(MainActivity) getActivity());
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            binding.stationCode.getEditText().setText(scannedText);
            viewModel.GetStationInfoForQuality_Painting(scannedText);
        });
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {

    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {
        barCodeReader.onTrigger(triggerStateChangeEvent);
    }

    @Override
    public void barcodeRead(com.intermec.aidc.BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReaderInterMec.scannedData(barcodeReadEvent);
            binding.stationCode.getEditText().setText(scannedText);
            viewModel.GetStationInfoForQuality_Painting(scannedText);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        barCodeReader.onPause();
        barCodeReaderInterMec.onPause();
    }

    @Override
    public void onPprItemClicked(int pprLoadingId) {
        Bundle bundle = new Bundle();
        bundle.putInt(PAINT_PPR_LOADING_ID,pprLoadingId);
        bundle.putString(STATION_CODE,binding.stationCode.getEditText().getText().toString().trim());
        Navigation.findNavController(getView()).navigate(R.id.action_fragment_paint_random_quality_inspection_to_fragment_paint_random_quality_inspection,bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        String stationCode = binding.stationCode.getEditText().getText().toString().trim();
        if (!stationCode.isEmpty()){
            viewModel.setStationCode(stationCode);
        }
    }
}