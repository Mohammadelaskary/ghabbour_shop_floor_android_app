package com.example.gbsbadrsf.Quality.welding.RandomQualityInception;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MainActivity.context;
import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop.MachineStopFragment.MACHINE_DATA;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.getDefectsPerQtyList;
import static com.example.gbsbadrsf.MyMethods.MyMethods.getDefectsPerQtyList_Welding;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.DEFECT_PER_QTY;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.DEFECT_PER_QTY_LIST;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.SAMPLE_QTY;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gbsbadrsf.CustomChoiceDialog;
import com.example.gbsbadrsf.Model.DefectsPerQty;
import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.Quality.AddBasketBottomSheet;
import com.example.gbsbadrsf.Quality.Data.FullInspectionQuality_OnlineData;
import com.example.gbsbadrsf.Quality.Data.WeldingDefect;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.Quality.welding.ViewModel.WeldingRandomQualityInceptionViewModel;
import com.example.gbsbadrsf.Quality.welding.WeldingDefectsPerQtyAdapter;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentWeldingRandomQualityInspectionBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.intermec.aidc.BarcodeReadListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class WeldingRandomQualityInceptionFragment extends Fragment implements AddBasketBottomSheet.OnBasketCodeScanned, WeldingDefectsPerQtyAdapter.SetOnDefectsPerQtyItemClicked, WeldingDefectsPerQtyAdapter.SetOnDeleteButtonClicked, BarcodeReadListener,View.OnClickListener, BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener {
    private static final String GOT_DATA_SUCCESSFULLY = "Getting data successfully";
    private static final String SAVED_SUCCESSFULLY = "Saved successfully";
    public static final String STATION_DATA = "station_data";
    public static final String STATION_CODE = "station_code";
    WeldingRandomQualityInceptionViewModel viewModel;
//    @Inject
//    ViewModelProviderFactory provider;

    public WeldingRandomQualityInceptionFragment() {
        // Required empty public constructor
    }

    public static WeldingRandomQualityInceptionFragment newInstance() {
        return new WeldingRandomQualityInceptionFragment();
    }


    private AddBasketBottomSheet addBasketBottomSheet;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barCodeReader = new SetUpBarCodeReader(this,this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
        addBasketBottomSheet = new AddBasketBottomSheet(getContext(),getActivity(),this);
    }

    FragmentWeldingRandomQualityInspectionBinding binding;
    private SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;
    private String stationCode = "",defectedBasketCode="",rejectedBasketCode="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWeldingRandomQualityInspectionBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachButtonToListener();
        attachTextWatchers();
        initViewModel();
        observeStationInfo();
        if (viewModel.getStationCode()!=null){
            binding.stationCode.getEditText().setText(viewModel.getStationCode());
            viewModel.getInfoForQualityRandomInspection(viewModel.getStationCode());
        }
        setUpDefectsRecyclerView();
        initProgressDialog();
        observeGettingMachineDieInfo();
        observeDeleteDefects();
        observeSavingRandomQualityInception();
        observeStatus();
        handleEditTextFocus(binding.stationCode);
    }

    private void observeDeleteDefects() {
        viewModel.getDeleteWeldingDefectResponse().observe(getViewLifecycleOwner(), o -> {
            if (o!=null){
                String statusMessage = o.getResponseStatus().getStatusMessage();
                if (o.getResponseStatus().getIsSuccess()){
                    showSuccessAlerter(statusMessage,getActivity());
                    viewModel.getInfoForQualityRandomInspection(binding.stationCode.getEditText().getText().toString().trim());
                } else warningDialog(getContext(),statusMessage);
            } else warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private WeldingDefectsPerQtyAdapter adapter;
    private void setUpDefectsRecyclerView() {
        adapter = new WeldingDefectsPerQtyAdapter(context,this,this);
        binding.defectsPerQty.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        binding.defectsPerQty.setLayoutManager(linearLayoutManager);
    }

    private void attachTextWatchers() {
        binding.stationCode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.stationCode.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.stationCode.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.stationCode.setError(null);
            }
        });
        binding.stationCode.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    stationCode = binding.stationCode.getEditText().getText().toString().trim();
                    viewModel.getInfoForQualityRandomInspection(stationCode);
                    return true;
                }
                return false;
            }
        });

        binding.sampleQty.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.sampleQty.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.sampleQty.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.sampleQty.setError(null);
            }
        });

    }
    private boolean defectedBasket,rejectedBasket;
    private void attachButtonToListener() {
//        binding.addBaskets.setOnClickListener(this);
        binding.addDefects.setOnClickListener(this);
        binding.save.setOnClickListener(this);
        binding.defectedRejectedBaskets.addDefecedBasket.setOnClickListener(v -> {
            defectedBasket = true;
            rejectedBasket = false;
            barCodeReader.onPause();
            barCodeReaderInterMec.onPause();
            addBasketBottomSheet.setBasketCode(defectedBasketCode);
            addBasketBottomSheet.show();
        });
        binding.defectedRejectedBaskets.addRejectedBasket.setOnClickListener(v -> {
            rejectedBasket = true;
            defectedBasket = false;
            barCodeReader.onPause();
            barCodeReaderInterMec.onPause();
            addBasketBottomSheet.setBasketCode(rejectedBasketCode);
            addBasketBottomSheet.show();
        });
    }

    private void observeStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            if (status==Status.LOADING){
                progressDialog.show();
            } else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    private void observeSavingRandomQualityInception() {
        NavController navController = NavHostFragment.findNavController(this);
        viewModel.getSaveRandomQualityInceptionMutableLiveData().observe(getViewLifecycleOwner(),apiResponseSaveRandomQualityInception -> {
            String statusMessage = apiResponseSaveRandomQualityInception.getResponseStatus().getStatusMessage();
            if (apiResponseSaveRandomQualityInception.getResponseStatus().getIsSuccess()){
//                Toast.makeText(getContext(), SAVED_SUCCESSFULLY, Toast.LENGTH_SHORT).show();
                showSuccessAlerter(statusMessage,getActivity());
                navController.popBackStack();
            } else {
                warningDialog(getContext(),statusMessage);
            }
        });
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.loading_3dots));
    }

    ProgressDialog progressDialog;
    private void observeGettingMachineDieInfo() {
        viewModel.getStatus().observe(getViewLifecycleOwner(), status -> {
            if (status== Status.LOADING){
                progressDialog.show();
            } else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }


    private List<WeldingDefect> defectList = new ArrayList<>();
    private ArrayList<DefectsPerQty> defectsPerQty = new ArrayList<>();
    private void observeStationInfo() {
        viewModel.getInfoForQualityRandomInspectionLiveData().observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                ResponseStatus responseStatus = response.getResponseStatus();
                String statusMessage = responseStatus.getStatusMessage();
                if (responseStatus.getIsSuccess()) {
                    lastMoveWelding = response.getLastMoveWeldingBaskets().get(0);
                    binding.dataLayout.setVisibility(View.VISIBLE);
                    parentDesc = lastMoveWelding.getParentDescription();
                    jobOrderName = lastMoveWelding.getJobOrderName();
//                    if (notes != lastMoveManufacturing.getQualityRandomInpectionNotes())
//                        notes = lastMoveManufacturing.getQualityRandomInpectionNotes().toString();
                    operationName = lastMoveWelding.getOperationEnName();
                    loadingQty = lastMoveWelding.getSignOffQty();
                    sampleQty = lastMoveWelding.getSampleQty();
                    jobOrderQty = lastMoveWelding.getJobOrderQty();
                    totalDefectedQty = Integer.parseInt(lastMoveWelding.getTotalQtyDefected());
                    totalRejectedQty = Integer.parseInt(lastMoveWelding.getTotalQtyRejected());
                    binding.stationCode.setError(null);
                    defectList = response.getWeldingDefects();
                    if (!defectList.isEmpty()) {
                        defectsPerQty = (ArrayList<DefectsPerQty>) getDefectsPerQtyList_Welding(defectList);

                        binding.save.setEnabled(true);
                    } else {
                        binding.save.setEnabled(false);
                    }
                    adapter.setDefectsPerQtyList(defectsPerQty);
                    handleTableHeader();
                    handleAddBasketsLayout();
                } else {
                    binding.dataLayout.setVisibility(View.GONE);
                    parentDesc = "";
                    jobOrderName = "";
                    notes = "";
                    operationName = "";
                    loadingQty = 0;
                    jobOrderQty = 0;
                    binding.stationCode.setError(statusMessage);
                }
            } else {
                parentDesc = "";
                jobOrderName = "";
                notes = "";
                operationName = "";
                loadingQty = 0;
                jobOrderQty = 0;
                binding.dataLayout.setVisibility(View.GONE);
                binding.stationCode.setError(getString(R.string.error_in_getting_data));
            }
            fillData();
        });
    }
    private boolean defectedBasketValid,rejectedBasketValid;
    private void handleAddBasketsLayout() {
        if (totalDefectedQty>0){
            if (!defectedBasketCode.isEmpty()){
                binding.defectedRejectedBaskets.defectedText.setTextColor(requireContext().getResources().getColor(R.color.done));
                binding.defectedRejectedBaskets.addDefecedBasket.setIcon(requireContext().getResources().getDrawable(R.drawable.ic_edit));
                defectedBasketValid = true;
            } else {
                binding.defectedRejectedBaskets.defectedText.setTextColor(requireContext().getResources().getColor(R.color.black));
                binding.defectedRejectedBaskets.addDefecedBasket.setIcon(requireContext().getResources().getDrawable(R.drawable.ic_add));
                defectedBasketValid = false;
            }
            binding.defectedRejectedBaskets.defectedQty.setText(String.valueOf(totalDefectedQty));
            binding.defectedRejectedBaskets.addDefecedBasket.setEnabled(true);
        } else {
            binding.defectedRejectedBaskets.defectedQty.setText(String.valueOf(totalDefectedQty));
            binding.defectedRejectedBaskets.addDefecedBasket.setEnabled(false);
            binding.defectedRejectedBaskets.defectedText.setTextColor(requireContext().getResources().getColor(R.color.disable));
            defectedBasketValid = true;
        }
        if (totalRejectedQty>0){
            if (!rejectedBasketCode.isEmpty()){
                binding.defectedRejectedBaskets.rejectedText.setTextColor(requireContext().getResources().getColor(R.color.done));
                binding.defectedRejectedBaskets.addRejectedBasket.setIcon(requireContext().getResources().getDrawable(R.drawable.ic_edit));
                rejectedBasketValid = true;
            } else {
                binding.defectedRejectedBaskets.rejectedText.setTextColor(requireContext().getResources().getColor(R.color.black));
                binding.defectedRejectedBaskets.addRejectedBasket.setIcon(requireContext().getResources().getDrawable(R.drawable.ic_add));
                rejectedBasketValid = false;
            }
            binding.defectedRejectedBaskets.rejectedQty.setText(String.valueOf(totalRejectedQty));
            binding.defectedRejectedBaskets.addRejectedBasket.setEnabled(true);
        } else {
            binding.defectedRejectedBaskets.rejectedQty.setText(String.valueOf(totalRejectedQty));
            binding.defectedRejectedBaskets.addRejectedBasket.setEnabled(false);
            binding.defectedRejectedBaskets.rejectedText.setTextColor(requireContext().getResources().getColor(R.color.disable));
            rejectedBasketValid = true;
        }
    }

    private void handleTableHeader() {
        if (defectList.isEmpty()){
            binding.manufacturingDefectsPerQtyTitle.getRoot().setVisibility(View.GONE);
        } else {
            binding.manufacturingDefectsPerQtyTitle.getRoot().setVisibility(View.VISIBLE);
        }
    }

    LastMoveWeldingBasket lastMoveWelding;
    String parentDesc,jobOrderName,notes,operationName,sampleQty;
    int loadingQty,jobOrderQty,totalDefectedQty,totalRejectedQty;
    private void fillData() {
        binding.childesc.setText(parentDesc);
        binding.jobOrderData.jobordernum.setText(jobOrderName);
        binding.jobOrderData.Joborderqtn.setText(String.valueOf(jobOrderQty));
        binding.loadingQtyData.qty.setText(String.valueOf(loadingQty));
        binding.operation.setText(operationName);
        if (!Objects.equals(sampleQty, "0"))
            binding.sampleQty.getEditText().setText(sampleQty);
        else
            binding.sampleQty.getEditText().setText("");

        if (loadingQty!=0)
            binding.loadingQtyData.qty.setText(String.valueOf(loadingQty));
        else
            binding.loadingQtyData.qty.setText("");
        if (jobOrderQty!=0)
            binding.jobOrderData.Joborderqtn.setText(String.valueOf(jobOrderQty));
        else
            binding.jobOrderData.Joborderqtn.setText("");

    }

    private void initViewModel() {
//        viewModel = ViewModelProviders.of(this,provider).get(RandomQualityInceptionViewModel.class);
        viewModel = new ViewModelProvider(this).get(WeldingRandomQualityInceptionViewModel.class);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.save:{
                sampleQty = binding.sampleQty.getEditText().getText().toString().trim();
                stationCode = binding.stationCode.getEditText().getText().toString().trim();
                if (!defectList.isEmpty()){
                    if (!sampleQty.isEmpty()){
                        if (defectedBasketValid){
                            if (rejectedBasketValid){
                                FullInspectionQuality_Online_Welding_Data data = new FullInspectionQuality_Online_Welding_Data(
                                        USER_ID,
                                        DEVICE_SERIAL_NO,
                                        stationCode,
                                        defectedBasketCode,
                                        rejectedBasketCode,
                                        LocaleHelper.getLanguage(getContext())
                                );
                                viewModel.SaveQualityRandomInspection(data);
                            } else
                                warningDialog(getContext(),getString(R.string.please_scan_or_enter_rejected_basket_code));
                        } else
                            warningDialog(getContext(),getString(R.string.please_scan_or_enter_defected_basket_code));
                    } else {
                        warningDialog(getContext(),getString(R.string.please_enter_sample_qty));
                    }
                } else {
                    warningDialog(getContext(),getString(R.string.please_add_found_defects));
                }
            } break;
            case R.id.add_defects:
                sampleQty = binding.sampleQty.getEditText().getText().toString().trim();
                stationCode = binding.stationCode.getEditText().getText().toString().trim();
                if (!sampleQty.isEmpty()){
                    if (containsOnlyDigits(sampleQty)) {
                        if (Integer.parseInt(sampleQty)<=loadingQty&&Integer.parseInt(sampleQty)>0) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(STATION_DATA, lastMoveWelding);
                            bundle.putString(SAMPLE_QTY, sampleQty);
                            bundle.putString(STATION_CODE,stationCode);
//                            bundle.putInt(PRODUCTION_SEQUENCE_NO,productionSequenceNo);
                            Navigation.findNavController(getView()).navigate(R.id.action_fragment_welding_random_quality_inspection_to_fragment_add_defects_online_inspection_welding, bundle);
                        } else
                            binding.sampleQty.setError(getString(R.string.please_enter_a_valid_sample_qty));
                    } else binding.sampleQty.setError(getString(R.string.please_enter_a_valid_sample_qty));
                } else binding.sampleQty.setError(getString(R.string.please_enter_sample_qty));
                break;

        }
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            binding.stationCode.getEditText().setText(scannedText);
            viewModel.getInfoForQualityRandomInspection(scannedText);
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
    public void barcodeRead(com.intermec.aidc.BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReaderInterMec.scannedData(barcodeReadEvent);
            binding.stationCode.getEditText().setText(scannedText);
            viewModel.getInfoForQualityRandomInspection(scannedText);
        });
    }

    @Override
    public void onItemClicked(DefectsPerQty defect) {
        sampleQty = binding.sampleQty.getEditText().getText().toString().trim();
        stationCode = binding.stationCode.getEditText().getText().toString().trim();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATION_DATA, lastMoveWelding);
        bundle.putString(STATION_CODE,stationCode);
        bundle.putString(SAMPLE_QTY,sampleQty);
        bundle.putParcelable(DEFECT_PER_QTY, defect);
        bundle.putParcelableArrayList(DEFECT_PER_QTY_LIST, defectsPerQty);
        Navigation.findNavController(getView()).navigate(R.id.action_fragment_welding_random_quality_inspection_to_fragment_add_defects_online_inspection_welding, bundle);
    }

    @Override
    public void onDeleteButtonClicked(int mainDefectId, int groupId, int listSize) {
        warningDialogWithChoice(getContext(),getString(R.string.confirm),getString(R.string.are_you_sure_to_delete_this_group_of_defects),groupId,mainDefectId);
    }
    private void warningDialogWithChoice(Context context, String title, String question, int groupId,int mainDefectId) {
        CustomChoiceDialog dialog = new CustomChoiceDialog(context,title,question);
        dialog.setOnOkClicked(() -> {
            viewModel.DeleteWeldingDefects(USER_ID,DEVICE_SERIAL_NO,groupId,mainDefectId);
            dialog.dismiss();
        });
        dialog.setOnCancelClicked(dialog::dismiss);
        dialog.show();
    }

    @Override
    public void onBasketCodeScanned(String basketCode) {
        if (defectedBasket){
            if (!Objects.equals(basketCode, rejectedBasketCode)) {
                defectedBasketCode = basketCode;
                defectedBasket = false;
            } else {
                warningDialog(getContext(),getString(R.string.please_scan_or_enter_different_basket));
            }
        } else if (rejectedBasket){
            if (!basketCode.equals(defectedBasketCode)) {
                rejectedBasketCode = basketCode;
                rejectedBasket = false;
            } else {
                warningDialog(getContext(),getString(R.string.please_scan_or_enter_different_basket));
            }
        }
        handleAddBasketsLayout();
    }

//    private void handleAddBasketsLayoutButtons() {
//        if (defectedBasketCode.isEmpty()){
//            binding.defectedRejectedBaskets.addDefecedBasket.setIcon(getContext().getResources().getDrawable(R.drawable.ic_add));
//        } else {
//            binding.defectedRejectedBaskets.addDefecedBasket.setIcon(getContext().getResources().getDrawable(R.drawable.ic_edit));
//        }
//        if (totalDefectedQty>0){
//            if ()
//        } else {
//            binding.defectedRejectedBaskets.addDefecedBasket.setEnabled(false);
//
//        }
//        if (rejectedBasketCode.isEmpty()){
//            binding.defectedRejectedBaskets.addRejectedBasket.setIcon(getContext().getResources().getDrawable(R.drawable.ic_add));
//        } else {
//            binding.defectedRejectedBaskets.addRejectedBasket.setIcon(getContext().getResources().getDrawable(R.drawable.ic_edit));
//        }
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        String stationCode = binding.stationCode.getEditText().getText().toString().trim();
        if (!stationCode.isEmpty()) viewModel.setStationCode(stationCode);
    }
}