package com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.BASKET_DATA;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.DEFECT_PER_QTY;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.DEFECT_PER_QTY_LIST;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.IS_FULL_INSPECTION;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.SAMPLE_QTY;
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
import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.Paint.PaintSignOff.PaintSignOffPprListViewModel;
import com.example.gbsbadrsf.Quality.Data.AddManufacturingDefectData;
import com.example.gbsbadrsf.Quality.Data.Defect;
import com.example.gbsbadrsf.Quality.Data.ManufacturingAddDefectsDetailsViewModel;
import com.example.gbsbadrsf.Quality.Data.UpdateManufacturingDefectsData;
import com.example.gbsbadrsf.Quality.DefectsListAdapter;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentManufacturingAddDefectDetailsBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ManufacturingAddDefectDetailsFragment extends Fragment implements View.OnClickListener,SetOnManufacturingAddDefectDetailsButtonClicked {



    public ManufacturingAddDefectDetailsFragment() {
        // Required empty public constructor
    }


    public static ManufacturingAddDefectDetailsFragment newInstance() {
        ManufacturingAddDefectDetailsFragment fragment = new ManufacturingAddDefectDetailsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentManufacturingAddDefectDetailsBinding binding;
    LastMoveManufacturingBasket basketData;
    ManufacturingAddDefectsDetailsViewModel viewModel;
//    @Inject
//    ViewModelProviderFactory provider;

    List<Defect> allDefectsList = new ArrayList<>();
    DefectsListAdapter adapter;

    ProgressDialog progressDialog;
    NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentManufacturingAddDefectDetailsBinding.inflate(
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
        observeAddingManufacturingDefectsStatus();
        observeUpdatingManufacturingDefectsResponse();
        clearInputLayoutError(binding.defectedQtyEdt);
    }

    private void observeUpdatingManufacturingDefectsResponse() {
        viewModel.getUpdateManufacturingDefectsResponse().observe(getViewLifecycleOwner(), response -> {
            if (response!=null) {
                String responseMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()) {
                    MyMethods.back(ManufacturingAddDefectDetailsFragment.this);
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
        viewModel.getAddManufacturingDefectsResponse().observe(getViewLifecycleOwner(), response -> {
            if (response!=null) {
                String responseMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()) {
                    navController.popBackStack();
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
        adapter = new DefectsListAdapter(getContext(),this);
        binding.defectsSelectList.setAdapter(adapter);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ManufacturingAddDefectsDetailsViewModel.class);

//        viewModel = ViewModelProviders.of(this,provider).get(ManufacturingAddDefectsDetailsViewModel.class);;
    }

    private void fillData() {
        binding.sampleQtyEdt.getEditText().setText(String.valueOf(sampleQty));
        binding.childesc.setText(childDescription);
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
    int remainingQty,groupId,mainDefectsId;
    private boolean isUpdate = false;
    private ArrayList<DefectsPerQty> defects = new ArrayList<>();
    private void getReceivedData() {
        if (getArguments()!=null) {
            basketData = getArguments().getParcelable(BASKET_DATA);
            operationId = basketData.getOperationId();
            childCode        = basketData.getChildCode();
            childDescription = basketData.getChildDescription();
            childId          = basketData.getChildId();
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
                groupId = defect.getGroupId();
                defectsIds = defect.getDefectsIds();
                adapter.setCheckedDefectsIds(defectsIds);
                defectedQty = defect.getQty();
                isRejected = defect.isRejected();
                binding.isRejected.setChecked(isRejected);
                isUpdate = true;
                mainDefectsId = defect.getMainDefectId();
                binding.addDefects.setText(R.string.update);
            }
            remainingQty = basketData.getSignOffQty()+defectedQty-Integer.parseInt(basketData.getTotalQtyDefected())-Integer.parseInt(basketData.getTotalQtyRejected());
        }
    }


    private void attachListeners() {
        binding.addDefects.setOnClickListener(this);
        binding.defectsListLayout.setOnClickListener(this);
    }
    String childCode,childDescription,sampleQty,notes = "",deviceSerialNumber=DEVICE_SERIAL_NO,operationName,basketCode,newBasketCode ;
    int     childId,
            defectedQty = 0,
            jobOrderId,
            operationId,
            parentId,
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
                                AddManufacturingDefectData data = new AddManufacturingDefectData(
                                        basketData.getProductionSequenceNo(),
                                        userId,
                                        deviceSerialNumber,
                                        jobOrderId,
                                        parentId,
                                        childId,
                                        operationId,
                                        defectedQty,
                                        notes,
                                        Integer.parseInt(sampleQty),
                                        defectsIds,
                                        basketCode,
                                        isRejected,
                                        true,
                                        isFullInspection,
                                        LocaleHelper.getLanguage(getContext()));
                                viewModel.addManufacturingDefectResponseViewModel(data);
                            } else {
                                UpdateManufacturingDefectsData data = new UpdateManufacturingDefectsData(
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
                                viewModel.updateManufacturingDefectResponseViewModel(data);
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

    private void observeAddingManufacturingDefectsStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(), status -> {
            if ((status == Status.LOADING)) {
                progressDialog.show();
            } else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
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
                if (apiResponseDefectsList.getResponseStatus().getIsSuccess()) {
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
    public void onManufacturingAddDefectDetailsButtonClicked(List<Integer> defectsIds) {
        this.defectsIds = defectsIds;
    }
}