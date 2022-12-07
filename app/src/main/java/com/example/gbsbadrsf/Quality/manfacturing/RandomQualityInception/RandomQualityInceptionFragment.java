package com.example.gbsbadrsf.Quality.manfacturing.RandomQualityInception;

import static android.content.ContentValues.TAG;
import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MainActivity.context;
import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop.MachineStopFragment.MACHINE_DATA;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.getDefectsPerQtyList;
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
import android.util.Log;
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
import com.example.gbsbadrsf.Model.ManufacturingDefect;
import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.Quality.AddBasketBottomSheet;
import com.example.gbsbadrsf.Quality.Data.FullInspectionQuality_OnlineData;
import com.example.gbsbadrsf.Quality.Data.LastMoveManufacturingMachineOnline;
import com.example.gbsbadrsf.Quality.Data.RandomQualityInceptionViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentRandomQualityInceptionBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.intermec.aidc.BarcodeReadListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class RandomQualityInceptionFragment extends Fragment implements BarcodeReadListener,
        View.OnClickListener, BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener, ManufacturingDefectsPerQtyAdapter.SetOnDefectsPerQtyItemClicked, ManufacturingDefectsPerQtyAdapter.SetOnDeleteButtonClicked, AddBasketBottomSheet.OnBasketCodeScanned {

    public static final String PRODUCTION_SEQUENCE_NO = "production_sequence_no";
    private RandomQualityInceptionViewModel viewModel;
//    @Inject
//    ViewModelProviderFactory provider;

    public RandomQualityInceptionFragment() {
        // Required empty public constructor
    }

    public static RandomQualityInceptionFragment newInstance() {
        return new RandomQualityInceptionFragment();
    }
    private AddBasketBottomSheet addBasketBottomSheet;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barCodeReader = new SetUpBarCodeReader(this,this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
        addBasketBottomSheet = new AddBasketBottomSheet(getContext(),getActivity(),this);
    }

    FragmentRandomQualityInceptionBinding binding;
    private String machineDieCode = "",defectedBasketCode="",rejectedBasketCode="";
    SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRandomQualityInceptionBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachButtonToListener();
        attachTextWatchers();
        initViewModel();
        handleEditTextFocus(binding.machineDieCode);
        if (viewModel.getMachineCode()!=null){
            binding.machineDieCode.getEditText().setText(viewModel.getMachineCode());
            getMachineDieInfo(viewModel.getMachineCode());
        }
        setUpDefectsRecyclerView();
        initProgressDialog();
        observeGettingMachineDieInfo();
        observeDeleteDefects();
        observeSavingRandomQualityInception();
        observeStatus();
    }

    private void observeDeleteDefects() {
        viewModel.getDeleteManufacturingDefectResponse().observe(getViewLifecycleOwner(),o -> {
            if (o!=null){
                String statusMessage = o.getResponseStatus().getStatusMessage();
                if (o.getResponseStatus().getIsSuccess()){
                    showSuccessAlerter(statusMessage,getActivity());
                    getMachineDieInfo(binding.machineDieCode.getEditText().getText().toString().trim());
                } else warningDialog(getContext(),statusMessage);
            } else warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private ManufacturingDefectsPerQtyAdapter adapter;
    private void setUpDefectsRecyclerView() {
        adapter = new ManufacturingDefectsPerQtyAdapter(context,this,this);
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
        binding.machineDieCode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.machineDieCode.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.machineDieCode.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.machineDieCode.setError(null);
            }
        });
        binding.machineDieCode.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    machineDieCode = binding.machineDieCode.getEditText().getText().toString().trim();
                    getMachineDieInfo(machineDieCode);
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


    private List<ManufacturingDefect> defectList = new ArrayList<>();
    private ArrayList<DefectsPerQty> defectsPerQty = new ArrayList<>();
    private void getMachineDieInfo(String machineDieCode) {
        viewModel.getInfoForQualityRandomInspection( machineDieCode);
        viewModel.getInfoForQualityRandomInspectionLiveData().observe(getViewLifecycleOwner(), apiResponseLastMoveManufacturing -> {
            if (apiResponseLastMoveManufacturing != null) {
                ResponseStatus responseStatus = apiResponseLastMoveManufacturing.getResponseStatus();
                String statusMessage = responseStatus.getStatusMessage();
                if (responseStatus.getIsSuccess()) {
                    lastMoveManufacturing = apiResponseLastMoveManufacturing.getLastMoveManufacturingMachineOnline().get(0);
                    binding.dataLayout.setVisibility(View.VISIBLE);
                    childCode = lastMoveManufacturing.getChildCode();
                    jobOrderName = lastMoveManufacturing.getJobOrderName();
//                    if (notes != lastMoveManufacturing.getQualityRandomInpectionNotes())
//                        notes = lastMoveManufacturing.getQualityRandomInpectionNotes().toString();
                    operationName = lastMoveManufacturing.getOperationEnName();
                    loadingQty = lastMoveManufacturing.getSignOffQty();
                    childId = lastMoveManufacturing.getChildId();
                    sampleQty = lastMoveManufacturing.getSampleQty();
                    jobOrderQty = lastMoveManufacturing.getJobOrderQty();
                    productionSequenceNo = lastMoveManufacturing.getProductionSequenceNo();
                    totalDefectedQty = Integer.parseInt(lastMoveManufacturing.getTotalQtyDefected());
                    totalRejectedQty = Integer.parseInt(lastMoveManufacturing.getTotalQtyRejected());
                    binding.machineDieCode.setError(null);
                    defectList = apiResponseLastMoveManufacturing.getManufacturingDefects();
                    if (!defectList.isEmpty()) {
                        defectsPerQty = (ArrayList<DefectsPerQty>) getDefectsPerQtyList(defectList);

                        binding.save.setEnabled(true);
                    } else {
                        binding.save.setEnabled(false);
                    }
                    adapter.setDefectsPerQtyList(defectsPerQty);
                    handleTableHeader();
                    handleAddBasketsLayout();
                } else {
                    binding.dataLayout.setVisibility(View.GONE);
                    childCode = "";
                    jobOrderName = "";
                    notes = "";
                    operationName = "";
                    loadingQty = 0;
                    childId = 0;
                    jobOrderQty = 0;
                    binding.machineDieCode.setError(statusMessage);
                }
            } else {
                childCode = "";
                jobOrderName = "";
                notes = "";
                operationName = "";
                loadingQty = 0;
                childId = 0;
                jobOrderQty = 0;
                binding.dataLayout.setVisibility(View.GONE);
                binding.machineDieCode.setError(getString(R.string.error_in_getting_data));
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

    LastMoveManufacturingMachineOnline lastMoveManufacturing;
    String childCode,jobOrderName,notes,operationName,sampleQty;
    int loadingQty,childId,jobOrderQty,totalDefectedQty,totalRejectedQty,productionSequenceNo;
    private void fillData() {
        binding.childesc.setText(childCode);
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
        viewModel = new ViewModelProvider(this).get(RandomQualityInceptionViewModel.class);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.save:{
                sampleQty = binding.sampleQty.getEditText().getText().toString().trim();
                machineDieCode = binding.machineDieCode.getEditText().getText().toString().trim();
                if (!defectList.isEmpty()){
                    if (!sampleQty.isEmpty()){
                        if (defectedBasketValid){
                            if (rejectedBasketValid){
                                FullInspectionQuality_OnlineData data = new FullInspectionQuality_OnlineData(
                                        USER_ID,
                                        DEVICE_SERIAL_NO,
                                        machineDieCode,
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
                if (!sampleQty.isEmpty()){
                    if (containsOnlyDigits(sampleQty)) {
                        if (Integer.parseInt(sampleQty)<=loadingQty&&Integer.parseInt(sampleQty)>0) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(MACHINE_DATA, lastMoveManufacturing);
                            bundle.putString(SAMPLE_QTY, sampleQty);
                            bundle.putInt(PRODUCTION_SEQUENCE_NO,productionSequenceNo);
                            Navigation.findNavController(getView()).navigate(R.id.action_fragment_random_quality_inception_to_fragment_add_defects_online_inspection, bundle);
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
            binding.machineDieCode.getEditText().setText(scannedText);
            getMachineDieInfo(scannedText);
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
            binding.machineDieCode.getEditText().setText(scannedText);
            getMachineDieInfo(scannedText);
        });
    }

    @Override
    public void onItemClicked(DefectsPerQty defect) {
        sampleQty = binding.sampleQty.getEditText().getText().toString().trim();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MACHINE_DATA, lastMoveManufacturing);
        bundle.putString(SAMPLE_QTY,sampleQty);
        bundle.putParcelable(DEFECT_PER_QTY, defect);
        bundle.putParcelableArrayList(DEFECT_PER_QTY_LIST, defectsPerQty);
        Navigation.findNavController(getView()).navigate(R.id.action_fragment_random_quality_inception_to_fragment_add_defects_online_inspection, bundle);
    }

    @Override
    public void onDeleteButtonClicked(int mainDefectsId, int groupId, int listSize) {
        warningDialogWithChoice(getContext(),getString(R.string.confirm),getString(R.string.are_you_sure_to_delete_this_group_of_defects),mainDefectsId,groupId);
    }
    private void warningDialogWithChoice(Context context, String title, String question, int mainDefectsId, int groupId) {
        CustomChoiceDialog dialog = new CustomChoiceDialog(context,title,question);
        dialog.setOnOkClicked(() -> {
            viewModel.DeleteManufacturingDefects(USER_ID,DEVICE_SERIAL_NO,groupId,mainDefectsId);
            dialog.dismiss();
        });
        dialog.setOnCancelClicked(dialog::dismiss);
        dialog.show();
    }

    @Override
    public void onBasketCodeScanned(String basketCode) {
        if (defectedBasket){
            if (!basketCode.equals(rejectedBasketCode)) {
                defectedBasketCode = basketCode;
                defectedBasket = false;
            } else
                warningDialog(getContext(),getString(R.string.please_scan_or_enter_different_basket));
        } else if (rejectedBasket){
            if (!basketCode.equals(defectedBasketCode)) {
                rejectedBasketCode = basketCode;
                rejectedBasket = false;
            } else warningDialog(getContext(),getString(R.string.please_scan_or_enter_different_basket));
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
        String machineCode = binding.machineDieCode.getEditText().getText().toString().trim();
        if (!machineCode.isEmpty()) viewModel.setMachineCode(machineCode);
    }
}