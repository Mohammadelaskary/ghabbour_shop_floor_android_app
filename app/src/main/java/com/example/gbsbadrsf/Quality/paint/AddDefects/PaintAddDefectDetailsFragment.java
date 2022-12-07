package com.example.gbsbadrsf.Quality.paint.AddDefects;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.BASKET_DATA;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.DEFECT_PER_QTY;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.DEFECT_PER_QTY_LIST;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.IS_FULL_INSPECTION;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.SAMPLE_QTY;
import static com.example.gbsbadrsf.Quality.paint.PprListQualityOperation.PaintSignOffPprAdapter.PAINT_PPR;
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
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gbsbadrsf.Model.DefectsPerQty;
import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.Paint.PaintSignOff.PaintSignOffPprListViewModel;
import com.example.gbsbadrsf.Quality.Data.Defect;
import com.example.gbsbadrsf.Quality.paint.Model.AddPaintingDefectData;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.paint.Model.UpdatePaintingDefectsData;
import com.example.gbsbadrsf.Quality.paint.PprListQualityOperation.Ppr;
import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintAddDefectsDetailsViewModel;
import com.example.gbsbadrsf.Quality.welding.Model.AddWeldingDefectData;
import com.example.gbsbadrsf.Quality.welding.Model.UpdateWeldingDefectsData;
import com.example.gbsbadrsf.Quality.welding.ViewModel.WeldingAddDefectsDetailsViewModel;
import com.example.gbsbadrsf.Quality.welding.WeldingAddDefects.SetOnWeldingAddDefectDetailsButtonClicked;
import com.example.gbsbadrsf.Quality.welding.WeldingAddDefects.WeldingDefectsListAdapter;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentPaintAddDefectDetailsBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class PaintAddDefectDetailsFragment extends Fragment implements View.OnClickListener, SetOnWeldingAddDefectDetailsButtonClicked {



    public PaintAddDefectDetailsFragment() {
        // Required empty public constructor
    }


    public static PaintAddDefectDetailsFragment newInstance() {
        return new PaintAddDefectDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentPaintAddDefectDetailsBinding binding;
    LastMovePaintingBasket basketData;
    private Ppr ppr;
    PaintAddDefectsDetailsViewModel viewModel;
//    @Inject
//    ViewModelProviderFactory provider;

    List<Defect> allDefectsList = new ArrayList<>();
    WeldingDefectsListAdapter adapter;

    ProgressDialog progressDialog;
    NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPaintAddDefectDetailsBinding.inflate(
                inflater,
                container,
                false
        );

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        navController = NavHostFragment.findNavController(this);
        attachListeners();
        setUpDefectsRecyclerView();
        getReceivedData();
        fillData();
        initViewModel();
        getAllDefectsList();
        observeGettingDefectsListStatus();

        observeAddingManufacturingDefectsResponse();
        observeAddingWeldingDefectsStatus();
        observeUpdatingManufacturingDefectsResponse();
        clearInputLayoutError(binding.defectedQtyEdt);
    }

    private void observeUpdatingManufacturingDefectsResponse() {
        viewModel.getUpdatePaintingDefectsResponse().observe(getViewLifecycleOwner(), response -> {
            if (response!=null) {
                String responseMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()) {
                    MyMethods.back(PaintAddDefectDetailsFragment.this);
                    showSuccessAlerter(responseMessage,getActivity());
                } else {
                    warningDialog(getContext(),responseMessage);
                }
//                Toast.makeText(getContext(), responseMessage, Toast.LENGTH_SHORT).show();
            } else
//                Toast.makeText(getContext(), "Error in connection", Toast.LENGTH_SHORT).show();
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void observeAddingManufacturingDefectsResponse() {
        viewModel.getAddPaintingDefectsResponse().observe(getViewLifecycleOwner(), response -> {
            if (response!=null) {
                String responseMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()) {
                    back(this);
                    showSuccessAlerter(responseMessage,getActivity());
                } else {
                    warningDialog(getContext(),responseMessage);
                }
//                Toast.makeText(getContext(), responseMessage, Toast.LENGTH_SHORT).show();
            } else
//                Toast.makeText(getContext(), "Error in connection", Toast.LENGTH_SHORT).show();
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void observeGettingDefectsListStatus() {
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.loading_3dots));
        viewModel.getDefectsListStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING){
                progressDialog.show();
            } else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }


    private void setUpDefectsRecyclerView() {
        adapter = new WeldingDefectsListAdapter(getContext(),this);
        binding.defectsSelectList.setAdapter(adapter);
    }

    private void initViewModel() {
//        viewModel = ViewModelProviders.of(this,provider).get(PaintAddDefectsDetailsViewModel.class);
        viewModel = new ViewModelProvider(this).get(PaintAddDefectsDetailsViewModel.class);

    }

    private void fillData() {
        binding.sampleQtyEdt.getEditText().setText(String.valueOf(sampleQty));
        binding.childesc.setText(parentDescription);
        binding.operation.setText(operationName);
        binding.jobOrderData.jobordernum.setText(basketData.getJobOrderName());
        binding.jobOrderData.Joborderqtn.setText(String.valueOf(basketData.getJobOrderQty()));
//        binding.sampleQtyEdt.getEditText().setText(String.valueOf(sampleQty));
        if (defectedQty!=0)
            binding.defectedQtyEdt.getEditText().setText(String.valueOf(defectedQty));
        else
            binding.defectedQtyEdt.getEditText().setText("");
    }
    //    boolean newSample = false;
    int remainingQty,groupId,pprId,loadingPaintingSignOutTransactionId,mainDefectsId;
    private boolean isUpdate = false;
    private ArrayList<DefectsPerQty> defects = new ArrayList<>();
    private void getReceivedData() {
        if (getArguments()!=null) {
            basketData = getArguments().getParcelable(BASKET_DATA);
            ppr = getArguments().getParcelable(PAINT_PPR);
            pprId = ppr.getLoadingSequenceID();
            loadingPaintingSignOutTransactionId= ppr.getLoadingPaintingSignOutTransactionId();
            operationId = basketData.getOperationId();
            parentCode = basketData.getParentCode();
            parentDescription = basketData.getParentDescription();
            parentId = basketData.getChildId();
            jobOrderId       = basketData.getJobOrderId();
            operationName      = basketData.getOperationEnName();
            sampleQty        = getArguments().getString(SAMPLE_QTY);
//            newSample        = getArguments().getBoolean("newSample");
            basketCode       = basketData.getBasketCode();
//            newBasketCode       = getArguments().getString(NEW_BASKET_CODE);
//            remainingQty     = getArguments().getInt(REMAINING_QTY);
            isFullInspection = getArguments().getBoolean(IS_FULL_INSPECTION);
            parentId       = basketData.getParentId();
            defects = getArguments().getParcelableArrayList(DEFECT_PER_QTY_LIST);
            binding.isRejected.setEnabled(getArguments().getBoolean(IS_FULL_INSPECTION));
            if (getArguments().getParcelable(DEFECT_PER_QTY)!=null){
                DefectsPerQty defect = getArguments().getParcelable(DEFECT_PER_QTY);
                mainDefectsId = defect.getMainDefectId();
                groupId = defect.getGroupId();
                defectsIds = defect.getDefectsIds();
                adapter.setCheckedDefectsIds(defectsIds);
                defectedQty = defect.getQty();
                isRejected = defect.isRejected();
                binding.isRejected.setChecked(isRejected);
                isUpdate = true;
                binding.addDefects.setText(getString(R.string.update));
            }
            remainingQty = basketData.getSignOffQty()+defectedQty-Integer.parseInt(basketData.getTotalQtyDefected())-Integer.parseInt(basketData.getTotalQtyRejected());
        }
    }


    private void attachListeners() {
        binding.addDefects.setOnClickListener(this);
        binding.defectsListLayout.setOnClickListener(this);
    }
    String parentCode, parentDescription,sampleQty,notes = "",deviceSerialNumber=DEVICE_SERIAL_NO,operationName,basketCode,newBasketCode ;
    int parentId,
            defectedQty = 0,
            jobOrderId,
            operationId,
            userId=USER_ID;
    boolean isRejected,isFullInspection;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.add_defects:{
                String defectedQtyString = binding.defectedQtyEdt.getEditText().getText().toString().trim();
                isRejected = binding.isRejected.isChecked();
                if (!defectedQtyString.isEmpty()) {
                    if (containsOnlyDigits(defectedQtyString)){
                        defectedQty = Integer.parseInt(defectedQtyString);
                        boolean validDefectedQty = defectedQty <= remainingQty;
                        if (validDefectedQty) {
                            if (!defectsIds.isEmpty()) {
                                if (!isUpdate) {
                                    AddPaintingDefectData data = new AddPaintingDefectData(
                                            userId,
                                            deviceSerialNumber,
                                            jobOrderId,
                                            parentId, parentId, operationId, defectedQty, notes, Integer.parseInt(sampleQty), defectsIds, pprId,loadingPaintingSignOutTransactionId, isRejected, true,isFullInspection,
                                            LocaleHelper.getLanguage(getContext()));
                                    viewModel.addPaintingDefectResponseViewModel(data);
                                } else {
                                    UpdatePaintingDefectsData data = new UpdatePaintingDefectsData(
                                            USER_ID,
                                            DEVICE_SERIAL_NO,
                                            mainDefectsId,
                                            groupId,
                                            defectedQty,
                                            defectsIds,
                                            true,
                                            isRejected,
                                            LocaleHelper.getLanguage(getContext())
                                    );
                                    viewModel.updateWeldingDefectResponseViewModel(data);
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

            } break;
//            case R.id.defects_list_layout:{
//                if(binding.defectsSelectList.getVisibility()==View.VISIBLE){
//                    binding.listDownArrow.setRotation(0);
//                    binding.defectsSelectList.setVisibility(View.GONE);
//                } else {
//                    binding.listDownArrow.setRotation(180);
//                    binding.defectsSelectList.setVisibility(View.VISIBLE);
//                }
//            } break;
        }
    }

    private void observeAddingWeldingDefectsStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(), status -> {
            if ((status == Status.LOADING)) {
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        });
    }

    private void getAllDefectsList() {
        viewModel.getDefectsListViewModel(operationId);
        viewModel.getDefectsListLiveData().observe(getViewLifecycleOwner(), apiResponseDefectsList -> {
            if (apiResponseDefectsList!=null) {
                ResponseStatus responseStatus = apiResponseDefectsList.getResponseStatus();
                String statusMessage = responseStatus.getStatusMessage();
                if (responseStatus.getIsSuccess()) {
                    allDefectsList.clear();
                    allDefectsList.addAll(apiResponseDefectsList.getDefectsList());
                    adapter.setDefectList(allDefectsList);
                    adapter.notifyDataSetChanged();
                }
            } else {
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
            }
        });
    }

    List<Integer> defectsIds = new ArrayList<>();
    @Override
    public void onWeldingAddDefectDetailsButtonClicked(List<Integer> defectsIds) {
        this.defectsIds = defectsIds;
    }
}