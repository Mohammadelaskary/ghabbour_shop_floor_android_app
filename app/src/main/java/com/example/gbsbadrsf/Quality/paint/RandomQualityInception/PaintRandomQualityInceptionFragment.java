package com.example.gbsbadrsf.Quality.paint.RandomQualityInception;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MainActivity.context;
import static com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop.MachineStopFragment.MACHINE_DATA;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.getDefectsPerQtyList_Painting;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.DEFECT_PER_QTY;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.DEFECT_PER_QTY_LIST;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.SAMPLE_QTY;
import static com.example.gbsbadrsf.Quality.paint.RandomQualityInception.PprListFragment.OnlineInspectionPprListFragment.PAINT_PPR_LOADING_ID;
import static com.example.gbsbadrsf.Quality.welding.RandomQualityInception.WeldingRandomQualityInceptionFragment.STATION_CODE;
import static com.example.gbsbadrsf.Quality.welding.RandomQualityInception.WeldingRandomQualityInceptionFragment.STATION_DATA;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.gbsbadrsf.Quality.AddBasketBottomSheet;
import com.example.gbsbadrsf.Quality.manfacturing.RandomQualityInception.ManufacturingDefectsPerQtyAdapter;
import com.example.gbsbadrsf.Quality.paint.Model.PaintingDefect;
import com.example.gbsbadrsf.Quality.paint.RandomQualityInception.PprListFragment.LastMovePaintingOnline;
import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintRandomQualityInceptionViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentPaintRandomQualityInspectionBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PaintRandomQualityInceptionFragment extends Fragment implements PaintingDefectsPerQtyAdapter.SetOnDefectsPerQtyItemClicked, PaintingDefectsPerQtyAdapter.SetOnDeleteButtonClicked, ManufacturingDefectsPerQtyAdapter.SetOnDefectsPerQtyItemClicked, ManufacturingDefectsPerQtyAdapter.SetOnDeleteButtonClicked, AddBasketBottomSheet.OnBasketCodeScanned, View.OnClickListener {

    public static final String PRODUCTION_SEQUENCE_NO = "production_sequence_no";
    public static final String PPR_ID = "ppr_id";
    private PaintRandomQualityInceptionViewModel viewModel;
//    @Inject
//    ViewModelProviderFactory provider;

    public PaintRandomQualityInceptionFragment() {
        // Required empty public constructor
    }

    public static PaintRandomQualityInceptionFragment newInstance() {
        return new PaintRandomQualityInceptionFragment();
    }
    private AddBasketBottomSheet addBasketBottomSheet;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addBasketBottomSheet = new AddBasketBottomSheet(getContext(),getActivity(),this);
    }

    FragmentPaintRandomQualityInspectionBinding binding;
    private String machineDieCode = "",defectedBasketCode="",rejectedBasketCode="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPaintRandomQualityInspectionBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }
    private int pprId;
    private String stationCode;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachButtonToListener();
        attachTextWatchers();
        initViewModel();
        if (getArguments()!=null){
            pprId = getArguments().getInt(PAINT_PPR_LOADING_ID);
            stationCode = getArguments().getString(STATION_CODE);
            viewModel.getInfoForQualityRandomInspection(pprId,stationCode);
        }
        setUpDefectsRecyclerView();
        initProgressDialog();
        observeGettingStationInfo();
        observeGettingMachineDieInfo();
        observeDeleteDefects();
        observeSavingRandomQualityInception();
        observeStatus();
    }

    private void observeDeleteDefects() {
        viewModel.getDeletePaintingDefectResponse().observe(getViewLifecycleOwner(), o -> {
            if (o!=null){
                String statusMessage = o.getResponseStatus().getStatusMessage();
                if (o.getResponseStatus().getIsSuccess()){
                    showSuccessAlerter(statusMessage,getActivity());
                    viewModel.getInfoForQualityRandomInspection(pprId,stationCode);
                } else warningDialog(getContext(),statusMessage);
            } else warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private PaintingDefectsPerQtyAdapter adapter;
    private void setUpDefectsRecyclerView() {
        adapter = new PaintingDefectsPerQtyAdapter(context,this,this);
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
            addBasketBottomSheet.setBasketCode(defectedBasketCode);
            addBasketBottomSheet.show();
        });
        binding.defectedRejectedBaskets.addRejectedBasket.setOnClickListener(v -> {
            rejectedBasket = true;
            defectedBasket = false;
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


    private List<PaintingDefect> defectList = new ArrayList<>();
    private ArrayList<DefectsPerQty> defectsPerQty = new ArrayList<>();
    private int operationId;
    private void observeGettingStationInfo() {
        viewModel.getInfoForQualityRandomInspectionLiveData().observe(getViewLifecycleOwner(), apiResponseLastMovePainting -> {
            if (apiResponseLastMovePainting != null) {
                ResponseStatus responseStatus = apiResponseLastMovePainting.getResponseStatus();
                String statusMessage = responseStatus.getStatusMessage();
                if (responseStatus.getIsSuccess()) {
                    lastMovePainting = apiResponseLastMovePainting.getLastMovePaintings().get(0);
                    binding.dataLayout.setVisibility(View.VISIBLE);
                    parentDesc = lastMovePainting.getParentDescription();
                    jobOrderName = lastMovePainting.getJobOrderName();
                    operationId = lastMovePainting.getOperationId();
//                    if (notes != lastMoveManufacturing.getQualityRandomInpectionNotes())
//                        notes = lastMoveManufacturing.getQualityRandomInpectionNotes().toString();
                    operationName = lastMovePainting.getOperationEnName();
                    loadingQty = lastMovePainting.getSignOffQty();
                    childId = lastMovePainting.getChildId();
                    sampleQty = lastMovePainting.getSampleQty();
                    jobOrderQty = lastMovePainting.getJobOrderQty();
                    productionSequenceNo = lastMovePainting.getProductionSequenceNo();
                    totalDefectedQty = Integer.parseInt(lastMovePainting.getTotalQtyDefected());
                    totalRejectedQty = Integer.parseInt(lastMovePainting.getTotalQtyRejected());
                    defectList = apiResponseLastMovePainting.getPaintingDefects();
                    if (!defectList.isEmpty()) {
                        defectsPerQty = (ArrayList<DefectsPerQty>) getDefectsPerQtyList_Painting(defectList);

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
                    childId = 0;
                    jobOrderQty = 0;
                    warningDialog(getContext(),statusMessage);
                }
            } else {
                parentDesc = "";
                jobOrderName = "";
                notes = "";
                operationName = "";
                loadingQty = 0;
                childId = 0;
                jobOrderQty = 0;
                binding.dataLayout.setVisibility(View.GONE);
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
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
            binding.defectedRejectedBaskets.defectedText.setTextColor(getResources().getColor(R.color.disable));
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
            binding.defectedRejectedBaskets.rejectedText.setTextColor(getResources().getColor(R.color.disable));
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

    LastMovePaintingOnline lastMovePainting;
    String parentDesc,jobOrderName,notes,operationName,sampleQty;
    int loadingQty,childId,jobOrderQty,totalDefectedQty,totalRejectedQty,productionSequenceNo;
    private void fillData() {
        binding.parentDesc.setText(parentDesc);
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
        viewModel = new ViewModelProvider(this).get(PaintRandomQualityInceptionViewModel.class);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.save:{
                sampleQty = binding.sampleQty.getEditText().getText().toString().trim();
                if (!defectList.isEmpty()){
                    if (!sampleQty.isEmpty()){
                        if (defectedBasketValid){
                            if (rejectedBasketValid){
                                FullInspectionQuality_Online_Painting_Data data = new FullInspectionQuality_Online_Painting_Data(
                                        USER_ID,
                                        DEVICE_SERIAL_NO,
                                        stationCode,
                                        lastMovePainting.getJobOrderId(),
                                        pprId,
                                        operationId,
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
                            bundle.putParcelable(STATION_DATA, lastMovePainting);
                            bundle.putString(SAMPLE_QTY, sampleQty);
                            bundle.putInt(PAINT_PPR_LOADING_ID,pprId);
                            bundle.putString(STATION_CODE,stationCode);
                            bundle.putInt(PRODUCTION_SEQUENCE_NO,productionSequenceNo);
                            Navigation.findNavController(getView()).navigate(R.id.action_fragment_paint_random_quality_inspection_to_fragment_add_defects_online_inspection_painting, bundle);
                        } else
                            binding.sampleQty.setError(getString(R.string.please_enter_a_valid_sample_qty));
                    } else binding.sampleQty.setError(getString(R.string.please_enter_a_valid_sample_qty));
                } else binding.sampleQty.setError(getString(R.string.please_enter_sample_qty));
                break;

        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onItemClicked(DefectsPerQty defect) {
        sampleQty = binding.sampleQty.getEditText().getText().toString().trim();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATION_DATA, lastMovePainting);
        bundle.putString(SAMPLE_QTY,sampleQty);
        bundle.putParcelable(DEFECT_PER_QTY, defect);
        bundle.putParcelableArrayList(DEFECT_PER_QTY_LIST, defectsPerQty);
        bundle.putInt(PAINT_PPR_LOADING_ID,pprId);
        bundle.putString(STATION_CODE,stationCode);
        bundle.putInt(PRODUCTION_SEQUENCE_NO,productionSequenceNo);
        Navigation.findNavController(getView()).navigate(R.id.action_fragment_paint_random_quality_inspection_to_fragment_add_defects_online_inspection_painting, bundle);
    }

    @Override
    public void onDeleteButtonClicked(int mainDefectId, int groupId, int listSize) {
        warningDialogWithChoice(getContext(),getString(R.string.confirm),getString(R.string.are_you_sure_to_delete_this_group_of_defects),mainDefectId,groupId);
    }
    private void warningDialogWithChoice(Context context, String title, String question, int mainDefectId, int groupId) {
        CustomChoiceDialog dialog = new CustomChoiceDialog(context,title,question);
        dialog.setOnOkClicked(() -> {
            viewModel.DeletePaintingDefects(USER_ID,DEVICE_SERIAL_NO,groupId,mainDefectId);
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
            } else
                warningDialog(getContext(),getString(R.string.please_scan_or_enter_different_basket));
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


}