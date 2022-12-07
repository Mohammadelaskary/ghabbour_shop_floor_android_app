package com.example.gbsbadrsf.ProductionRepair.PaintProductionRepair;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
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

import com.example.gbsbadrsf.ProductionRepair.PaintProductionRepair.ViewModel.PaintProductionDefectRepairViewModel;
import com.example.gbsbadrsf.ProductionRepair.ProductionDefectRepairFragment;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.paint.Model.PaintingDefect;
import com.example.gbsbadrsf.Quality.paint.QualityRepair.SetOnPaintingRepairItemClicked;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentPaintProductionDefectRepairBinding;

import java.util.ArrayList;
import java.util.List;

public class PaintProductionDefectRepairFragment extends Fragment implements SetOnPaintingRepairItemClicked, View.OnClickListener {


    private static final String SAVED_SUCCESSFULLY = "Saved successfully";
    PaintProductionDefectRepairViewModel viewModel;
//    @Inject
//    ViewModelProviderFactory provider;
    public PaintProductionDefectRepairFragment() {
        // Required empty public constructor
    }


    public static ProductionDefectRepairFragment newInstance() {
        return new ProductionDefectRepairFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentPaintProductionDefectRepairBinding binding;
    PaintRepairProductionAdapter adapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaintProductionDefectRepairBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initProgressDialog();
        setUpRecyclerView();
        getReceivedData();
        fillData();
        attachButtonToListener();
        initViewModel();
        observeAddingDefectRepairResponse();
        observeAddingDefectRepairStatus();
        clearInputLayoutError(binding.repairedQty);
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.loading_3dots));
    }

    ProgressDialog progressDialog;
    private void observeAddingDefectRepairStatus() {
        viewModel.getAddPaintingRepairProductionStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING){
                progressDialog.show();
            } else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)){
                warningDialog(getContext(),getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    private void observeAddingDefectRepairResponse() {
        viewModel.getAddPaintingRepairProduction().observe(getViewLifecycleOwner(),response-> {
            String statusMessage = response.getResponseStatus().getStatusMessage();
            if (response.getResponseStatus().getIsSuccess()){
                showSuccessAlerter(statusMessage,getActivity());
//                Toast.makeText(getContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                pendingRepair = response.getPendingRepair();
                pendingApprove = response.getPendingApprove();
                binding.repairedQty.getEditText().setText(String.valueOf(pendingRepair));
                updateRecyclerView();
            }
        });
    }

    private void updateRecyclerView() {
        defectsPainting.setRepairedQty(repairedQty);
        defectsPainting.setPendingRepair(pendingRepair);
        defectsPainting.setPendingApprove(pendingApprove);
        defectsPaintingList.remove(position);
        defectsPaintingList.add(position,defectsPainting);
        adapter.notifyDataSetChanged();
    }

    private void initViewModel() {
//        viewModel = ViewModelProviders.of(this,provider).get(PaintProductionDefectRepairViewModel.class);
        viewModel = new ViewModelProvider(this).get(PaintProductionDefectRepairViewModel.class);

    }

    private void attachButtonToListener() {
        binding.saveBtn.setOnClickListener(this);
    }

    private void setUpRecyclerView() {
        adapter = new PaintRepairProductionAdapter(this);
        binding.defectsDetailsList.setAdapter(adapter);
    }
    private void fillData() {
        String parentCode = basketData.getParentCode();
        String parentDesc = basketData.getParentDescription();
        String operationName = basketData.getOperationEnName();
        String defectedQty   = defectsPaintingList.get(0).getQtyDefected().toString();
        int basketQty   = basketData.getSignOffQty();
        String basketDefectedQty=basketQty+"/"+defectedQty;
        binding.parentDesc.setText(parentDesc);
        binding.operation.setText(operationName);
        binding.defectedData.qty.setText(basketDefectedQty);
    }

    LastMovePaintingBasket basketData;
    List<PaintingDefect> defectsPaintingList = new ArrayList<>();
    private void getReceivedData() {
        if (getArguments()!=null){
            basketData = getArguments().getParcelable("basketData");
            defectsPaintingList = getArguments().getParcelableArrayList("selectedDefectsPainting");
            adapter.setDefectsPaintingList(defectsPaintingList);
            adapter.notifyDataSetChanged();
        }
    }
    int position,defectedQty;
    PaintingDefect defectsPainting;
    @Override
    public void onPaintingRepairItemClicked(PaintingDefect defectsPainting,int position) {
        this.defectsPainting = defectsPainting;
        this.position = position;
        binding.repairedQty.getEditText().setText(String.valueOf(defectsPainting.getPendingRepair()));
        defectsManufacturingDetailsId = defectsPainting.getDefectsPaintingDetailsId();
        defectStatus = defectsPainting.getDefectStatus();
        defectedQty = defectsPainting.getQtyDefected();
    }
    int userId = USER_ID,defectsManufacturingDetailsId=-1,defectStatus,repairedQty,pendingRepair,pendingApprove;
    String notes="", deviceSerialNumber=DEVICE_SERIAL_NO;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.save_btn:{
                if (defectsManufacturingDetailsId!=-1){
                    repairedQty =Integer.parseInt(binding.repairedQty.getEditText().getText().toString().trim());
                    if (repairedQty>0) {
                        if (containsOnlyDigits(String.valueOf(repairedQty)) && repairedQty <= defectedQty) {
                            viewModel.addPaintingRepairProduction(
                                    userId,
                                    deviceSerialNumber,
                                    defectsManufacturingDetailsId,
                                    notes,
                                    defectStatus,
                                    repairedQty
                            );
                        } else {
                            binding.repairedQty.setError(getString(R.string.please_enter_a_valid_repaired_qty));
                        }
                    } else
                        binding.repairedQty.setError(getString(R.string.reoaired_qty_must_be_more_than_0));
                } else {
//                    Toast.makeText(getContext(), "Please first select defect to repair!", Toast.LENGTH_SHORT).show();
                    warningDialog(getContext(),getString(R.string.please_first_select_defect_to_repair));
                }
            } break;
        }
    }


}