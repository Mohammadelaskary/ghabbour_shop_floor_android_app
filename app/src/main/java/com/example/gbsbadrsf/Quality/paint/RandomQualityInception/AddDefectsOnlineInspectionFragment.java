package com.example.gbsbadrsf.Quality.paint.RandomQualityInception;

import static android.content.ContentValues.TAG;
import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop.MachineStopFragment.MACHINE_DATA;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.DEFECT_PER_QTY;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.DEFECT_PER_QTY_LIST;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.SAMPLE_QTY;
import static com.example.gbsbadrsf.Quality.manfacturing.RandomQualityInception.RandomQualityInceptionFragment.PRODUCTION_SEQUENCE_NO;
import static com.example.gbsbadrsf.Quality.paint.RandomQualityInception.PprListFragment.OnlineInspectionPprListFragment.PAINT_PPR_LOADING_ID;
import static com.example.gbsbadrsf.Quality.welding.RandomQualityInception.WeldingRandomQualityInceptionFragment.STATION_CODE;
import static com.example.gbsbadrsf.Quality.welding.RandomQualityInception.WeldingRandomQualityInceptionFragment.STATION_DATA;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gbsbadrsf.Model.DefectsPerQty;
import com.example.gbsbadrsf.Quality.DefectsListAdapter;
import com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects.SetOnManufacturingAddDefectDetailsButtonClicked;
import com.example.gbsbadrsf.Quality.paint.RandomQualityInception.PprListFragment.LastMovePaintingOnline;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.databinding.FragmentAddDefectsOnlineInspectionPaintingBinding;

import java.util.ArrayList;
import java.util.List;

public class AddDefectsOnlineInspectionFragment extends Fragment implements SetOnManufacturingAddDefectDetailsButtonClicked, View.OnClickListener {

    private AddPaintingDefectsOnlineInspectionViewModel viewModel;

    public static AddDefectsOnlineInspectionFragment newInstance() {
        return new AddDefectsOnlineInspectionFragment();
    }
    private FragmentAddDefectsOnlineInspectionPaintingBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddDefectsOnlineInspectionPaintingBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    private ProgressDialog progressDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddPaintingDefectsOnlineInspectionViewModel.class);
        progressDialog = loadingProgressDialog(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setUpDefectsRecycler();
        getReceivedData();
        fillData();

        viewModel.getDefects(stationData.getOperationId());
        observeGettingDefects();
        observeStatus();
        observeAddingDefects();
        observeUpdatingDefects();
        binding.addDefects.setOnClickListener(this);

    }

    private void observeUpdatingDefects() {
        viewModel.getUpdatePaintingDefectsResponse().observe(getViewLifecycleOwner(), response ->{
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()){
                    showSuccessAlerter(statusMessage,getActivity());
                    back(this);
                } else warningDialog(getContext(),statusMessage);
            } else warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void observeAddingDefects() {
        viewModel.getAddPaintingDefectsResponse().observe(getViewLifecycleOwner(), apiResponseAddingManufacturingDefect_online -> {
            if (apiResponseAddingManufacturingDefect_online!=null){
                String statusMessage = apiResponseAddingManufacturingDefect_online.getResponseStatus().getStatusMessage();
                if (apiResponseAddingManufacturingDefect_online.getResponseStatus().getIsSuccess()){
                    showSuccessAlerter(statusMessage,getActivity());
                    back(this);
                } else
                    warningDialog(getContext(),statusMessage);
            } else warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }


    private void fillData() {
        binding.childesc.setText(stationData.getParentDescription());
        binding.jobOrderData.jobordernum.setText(stationData.getJobOrderName());
        binding.jobOrderData.Joborderqtn.setText(String.valueOf(stationData.getJobOrderQty()));
        binding.operation.setText(stationData.getOperationEnName());
        binding.sampleQtyEdt.getEditText().setText(sampleQty);
        binding.isRejected.setChecked(isRejected);
        if (defectedQty!=0){
            binding.defectedQtyEdt.getEditText().setText(String.valueOf(defectedQty));
        } else
            binding.defectedQtyEdt.getEditText().setText("");
//        adapter.setCheckedDefectsIds(selectedDefectsIds);
    }

    private void observeStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    progressDialog.show();
                    break;
                case SUCCESS:
                    progressDialog.hide();
                    break;
                case ERROR:
                    progressDialog.hide();
                    warningDialog(getContext(),getString(R.string.network_issue));
                    break;
            }
        });
    }

    private DefectsListAdapter adapter;
    private void setUpDefectsRecycler() {
        adapter = new DefectsListAdapter(getContext(),this);
        binding.defectsSelectList.setAdapter(adapter);
    }

    private void observeGettingDefects() {
        viewModel.getDefectsList().observe(getViewLifecycleOwner(),defectsListResponse ->{
            if (defectsListResponse!=null){
                String statusMessage = defectsListResponse.getResponseStatus().getStatusMessage();
                if (defectsListResponse.getResponseStatus().getIsSuccess()){
                    adapter.setDefectList(defectsListResponse.getDefectsList());
                } else warningDialog(getContext(),statusMessage);
            } else warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private LastMovePaintingOnline stationData;
    private String sampleQty,stationCode;
    private List<DefectsPerQty> defects;
    private int groupId,defectedQty,remainingQty,mainDefectsId,productionSequenceNo,pprId;
    private boolean isRejected,isUpdate;
    private void getReceivedData() {
        if (getArguments()!=null) {
            stationData = getArguments().getParcelable(STATION_DATA);
            sampleQty        = getArguments().getString(SAMPLE_QTY);
            defects = getArguments().getParcelableArrayList(DEFECT_PER_QTY_LIST);
            productionSequenceNo = getArguments().getInt(PRODUCTION_SEQUENCE_NO);
            pprId = getArguments().getInt(PAINT_PPR_LOADING_ID,pprId);
            stationCode = getArguments().getString(STATION_CODE);
            if (getArguments().getParcelable(DEFECT_PER_QTY)!=null){
                DefectsPerQty defect = getArguments().getParcelable(DEFECT_PER_QTY);
                Log.d(TAG, "onItemClicked: "+defect.getDefectsIds().toString());
                mainDefectsId = defect.getMainDefectId();
                groupId = defect.getGroupId();
                selectedDefectsIds = defect.getDefectsIds();
                adapter.setCheckedDefectsIds(selectedDefectsIds);
                defectedQty = defect.getQty();
                isRejected = defect.isRejected();
                binding.isRejected.setChecked(isRejected);
                isUpdate = true;
                binding.addDefects.setText(R.string.update);
            }
            remainingQty = Integer.parseInt(sampleQty)+defectedQty-Integer.parseInt(stationData.getTotalQtyDefected())-Integer.parseInt(stationData.getTotalQtyRejected());
        }
    }
    private List<Integer> selectedDefectsIds = new ArrayList<>();
    @Override
    public void onManufacturingAddDefectDetailsButtonClicked(List<Integer> defectsIds) {
        this.selectedDefectsIds = defectsIds;
    }
    private String notes = "";
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_defects:
                String defectedQtyString = binding.defectedQtyEdt.getEditText().getText().toString().trim();
                isRejected = binding.isRejected.isChecked();
                if (!defectedQtyString.isEmpty()) {
                    if (containsOnlyDigits(defectedQtyString)){
                        defectedQty = Integer.parseInt(defectedQtyString);
                        boolean validDefectedQty = defectedQty <= remainingQty;
                        if (validDefectedQty) {
                            if (!selectedDefectsIds.isEmpty()) {
                                if (!isUpdate) {
                                    AddPaintingDefectData_Online data = new AddPaintingDefectData_Online(
                                            USER_ID,
                                            DEVICE_SERIAL_NO,
                                            stationData.getJobOrderId(),
                                            stationData.getParentId(),
                                            pprId,
                                            stationData.getOperationId(),
                                            productionSequenceNo,
                                            defectedQty,
                                            stationCode,
                                            Integer.parseInt(sampleQty),
                                            selectedDefectsIds,
                                            true,
                                            isRejected,
                                            false,
                                            notes,
                                            LocaleHelper.getLanguage(getContext())
                                    );
                                    viewModel.addPaintingDefectResponseViewModel(data);
                                } else {
                                    UpdatePaintingDefectsData_Online data = new UpdatePaintingDefectsData_Online(
                                            USER_ID,
                                            DEVICE_SERIAL_NO,
                                            defectedQty,
                                            groupId,
                                            mainDefectsId,
                                            selectedDefectsIds,
                                            true,
                                            isRejected,
                                            LocaleHelper.getLanguage(getContext())
                                    );
                                    viewModel.updatePaintingDefectResponseViewModel(data);
                                }
                            } else {
                                warningDialog(getContext(), getString(R.string.please_select_the_found_defects));
                            }
                        } else {
                            binding.defectedQtyEdt.setError(getString(R.string.total_defected_qty_must_be_less_than_or_equal_sample_qty));
                        }
//                    warningDialog(getContext(),"Please enter defected quantity!");
                    } else {
                        warningDialog(getContext(),getString(R.string.please_enter_a_valid_qty));
                        binding.defectedQtyEdt.setError(getString(R.string.please_enter_a_valid_qty));
                    }
                } else {
                    warningDialog(getContext(),getString(R.string.please_enter_the_defected_qty));
                    binding.defectedQtyEdt.setError(getString(R.string.please_enter_the_defected_qty));
                }

//                    warningDialog(getContext(),"Total defected Quantity must be less than or equal sample quantity!");
        break;
        }
    }
}