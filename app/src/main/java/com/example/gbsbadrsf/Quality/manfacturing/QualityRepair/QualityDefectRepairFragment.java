package com.example.gbsbadrsf.Quality.manfacturing.QualityRepair;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.gbsbadrsf.MyMethods.MyMethods.getCurrentDate2;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.BASKET_DATA;
import static com.example.gbsbadrsf.Quality.paint.QualityRepair.PaintQualityRepairFragment.QTY_READY_TO_MOVE;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.Model.ManufacturingDefect;
import com.example.gbsbadrsf.MyMethods.ReadyToMoveToBasketDialog;
import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.ProductionRepair.Data.SetOnRepairItemClicked;
import com.example.gbsbadrsf.Quality.Data.QualityDefectRepairViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentQualityDefectRepairBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.intermec.aidc.BarcodeReadListener;

import java.util.ArrayList;
import java.util.List;


public class QualityDefectRepairFragment extends Fragment implements SetOnRepairItemClicked, View.OnClickListener, BarcodeReader.BarcodeListener,BarcodeReader.TriggerListener, BarcodeReadListener {



    public QualityDefectRepairFragment() {
        // Required empty public constructor
    }


    public static QualityDefectRepairFragment newInstance() {

        return new QualityDefectRepairFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barCodeReader = new SetUpBarCodeReader(this,this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
    }
    FragmentQualityDefectRepairBinding binding;
    private BottomSheetBehavior moveToBasketBottomSheet;
    private static final String SAVED_SUCCESSFULLY = "Saved successfully";
    QualityDefectRepairViewModel viewModel;
//    @Inject
//    ViewModelProviderFactory provider;
    RepairQualityAdapter adapter;
    private SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQualityDefectRepairBinding.inflate(inflater,container,false);

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
        setUpMoveToBasketBottomSheet();
        observeMoveToBasket();
        setUpReadyToMoveToBasketDialog();
        clearInputLayoutError(binding.approvedQty);
        handleEditTextFocus(binding.moveToBasketBottomSheet.basketCode);
    }
    private ReadyToMoveToBasketDialog dialog;
    private void setUpReadyToMoveToBasketDialog() {
        dialog = new ReadyToMoveToBasketDialog(requireContext());
        dialog.setOnMoveToBasketClicked(() -> {
            moveToBasketBottomSheet.setState(STATE_EXPANDED);
            dialog.dismiss();
        });
        dialog.setOnCancelButtonClicked(()->{
            warningDialog(getContext(),getString(R.string.defected_and_ok_items_at_the_same_basket_so_you_should_put_ok_items_in_new_basket));
            dialog.dismiss();
        });
    }

    private void observeMoveToBasket() {
        viewModel.getDefectedQualityOk_Manufacturing().observe(getViewLifecycleOwner(),apiResponseDefectedQualityOk_manufacturing -> {
            if (apiResponseDefectedQualityOk_manufacturing!=null){
                String statusMessage = apiResponseDefectedQualityOk_manufacturing.getResponseStatus().getStatusMessage();
                if (apiResponseDefectedQualityOk_manufacturing.getResponseStatus().getIsSuccess()){
                    showSuccessAlerter(statusMessage,getActivity());
                    hideMoveToBasketBottomSheet();
                    readyQtyToMove = 0;
                    String readyItemsToMove = readyQtyToMove+getString(R.string.items_are_ready_to_move_to_new_basket);
                    binding.moveToBasketQty.setText(readyItemsToMove);
                }
                else
                    binding.moveToBasketBottomSheet.basketCode.setError(statusMessage);
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void setUpMoveToBasketBottomSheet() {
        moveToBasketBottomSheet =  BottomSheetBehavior.from(binding.moveToBasketBottomSheet.getRoot());
        moveToBasketBottomSheet.setDraggable(true);
        hideMoveToBasketBottomSheet();
        binding.moveToBasketBottomSheet.save.setOnClickListener(v->{
            String newBasketCode = binding.moveToBasketBottomSheet.basketCode.getEditText().getText().toString().trim();
            if (!newBasketCode.isEmpty()){
                viewModel.DefectedQualityOk_Manufacturing(
                        USER_ID,
                        DEVICE_SERIAL_NO,
                        basketData.getBasketCode(),
                        newBasketCode,
                        defectsManufacturing.getDefectGroupId(),
                        getCurrentDate2()
                );
            } else
                binding.moveToBasketBottomSheet.basketCode.setError(getString(R.string.please_scan_or_enter_a_valid_basket_code));
        });
        moveToBasketBottomSheet.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState==STATE_HIDDEN){
                    binding.disable.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void hideMoveToBasketBottomSheet() {
        moveToBasketBottomSheet.setState(STATE_HIDDEN);
        binding.disable.setVisibility(View.GONE);
    }
    private void showMoveToBasketBottomSheet() {
        moveToBasketBottomSheet.setState(STATE_EXPANDED);
        binding.disable.setVisibility(View.VISIBLE);
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.loading_3dots));
    }

    ProgressDialog progressDialog;
    private void observeAddingDefectRepairStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(), status -> {
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
    private int readyQtyToMove;
    private void observeAddingDefectRepairResponse() {
        viewModel.getAddManufacturingRepairQuality().observe(getViewLifecycleOwner(),response-> {
            String statusMessage = response.getResponseStatus().getStatusMessage();
            if (response.getResponseStatus().getIsSuccess()){
                showSuccessAlerter(statusMessage,getActivity());
//                Toast.makeText(getContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                boolean moveToBasket = response.isMoveToOkBasket();
                readyQtyToMove = response.getMinQtyApproved();
                String readyItemsToMove = readyQtyToMove+getString(R.string.items_are_ready_to_move_to_new_basket);
                binding.moveToBasketQty.setText(readyItemsToMove);
                if (!moveToBasket) {
                    binding.moveToBasketQty.setVisibility(View.VISIBLE);
                    if (readyQtyToMove > 0){
                        dialog.setMessage(readyQtyToMove+getString(R.string.items_are_ready_to_move_to_new_basket));
                        dialog.show();
                    }
                } else binding.moveToBasketQty.setVisibility(View.GONE);
                approvedQty = response.getApprovedQty();
                repairedQty = response.getRepairedQty();
                pendingRepair = response.getPendingRepair();
                pendingApprove = response.getPendingApprove();
                updateRecyclerView();
                binding.approvedQty.getEditText().setText("");
//                back(QualityDefectRepairFragment.this);
            } else
                warningDialog(getContext(),statusMessage);
        });
    }

    private void updateRecyclerView() {
            defectsManufacturing.setApprovedQty(approvedQty);
            defectsManufacturing.setRepairedQty(repairedQty);
            defectsManufacturing.setPendingRepair(pendingRepair);
            defectsManufacturing.setPendingApprove(pendingApprove);
            defectsManufacturingList.remove(position);
            defectsManufacturingList.add(position,defectsManufacturing);
            adapter.notifyDataSetChanged();
    }

    private void initViewModel() {
//        viewModel = ViewModelProviders.of(this, provider).get(QualityDefectRepairViewModel.class);
        viewModel = new ViewModelProvider(this).get(QualityDefectRepairViewModel.class);

    }

    private void attachButtonToListener() {
        binding.saveBtn.setOnClickListener(this);
        binding.moveToBasket.setOnClickListener(this);
    }

    private void setUpRecyclerView() {
        adapter = new RepairQualityAdapter(this,getContext());
        binding.defectsDetailsList.setAdapter(adapter);
    }
    private void fillData() {
        String childDesc = basketData.getChildDescription();
        String operationName = basketData.getOperationEnName();
        String defectedQty   = defectsManufacturingList.get(0).getQtyDefected().toString();
        int basketQty   = basketData.getSignOffQty();
        String basketDefectedQty=basketQty+"/"+defectedQty;
        binding.parentDesc.setText(childDesc);
        binding.operation.setText(operationName);
        binding.defectedData.qty.setText(basketDefectedQty);
    }

    LastMoveManufacturingBasket basketData;
    List<ManufacturingDefect> defectsManufacturingList = new ArrayList<>();
    private void getReceivedData() {
        if (getArguments()!=null){
            basketData = getArguments().getParcelable(BASKET_DATA);
            defectsManufacturingList = getArguments().getParcelableArrayList("selectedDefectsManufacturing");
            readyQtyToMove = getArguments().getInt(QTY_READY_TO_MOVE);
            String readyItemsToMove = readyQtyToMove+getString(R.string.items_are_ready_to_move_to_new_basket);
            binding.moveToBasketQty.setText(readyItemsToMove);
            adapter.setDefectsManufacturingList(defectsManufacturingList);
            adapter.notifyDataSetChanged();
        }
    }
    ManufacturingDefect defectsManufacturing;
    int position,repairedQty;
    @Override
    public void onRepairItemClicked(ManufacturingDefect defectsManufacturing,int position,int pending) {
        this.position = position;
        this.defectsManufacturing = defectsManufacturing;
        repairedQty = defectsManufacturing.getRepairedQty();
        pendingApprove = defectsManufacturing.getPendingApprove();
        defectsManufacturingDetailsId = defectsManufacturing.getDefectsManufacturingDetailsId();
        if (pendingApprove!=0) {
            binding.approvedQty.getEditText().setText(String.valueOf(pendingApprove));
            defectStatus = defectsManufacturing.getDefectStatus();
        } else
            binding.approvedQty.getEditText().setText(R.string.there_is_no_pending_approve_quantity);
    }
    int userId = USER_ID,defectsManufacturingDetailsId=-1,defectStatus;
    String notes="", deviceSerialNumber=DEVICE_SERIAL_NO;
            int approvedQty,pendingRepair,pendingApprove;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.save_btn:{
                if (defectsManufacturingDetailsId!=-1){
                    if (!binding.approvedQty.getEditText().getText().toString().trim().isEmpty()) {
                        try {
                            approvedQty = Integer.parseInt(binding.approvedQty.getEditText().getText().toString().trim());
                            if (repairedQty != 0) {
                                if (approvedQty >= 0) {
                                    if (containsOnlyDigits(String.valueOf(approvedQty))  && approvedQty <= repairedQty) {
                                        viewModel.addManufacturingRepairQuality(
                                                userId,
                                                deviceSerialNumber,
                                                defectsManufacturingDetailsId,
                                                notes,
                                                defectStatus,
                                                approvedQty
                                        );
                                    } else {
                                        binding.approvedQty.setError(getString(R.string.please_enter_valid_approved_qty));
                                    }
                                } else {
                                    binding.approvedQty.setError(getString(R.string.approved_qty_must_be_more_than_or_equal_0));
                                }
                            } else
                                binding.approvedQty.setError(getString(R.string.the_selected_defect_havent_repaired_yet));
                        } catch (NumberFormatException ex){
                            binding.approvedQty.setError(getString(R.string.the_selected_defect_havent_repaired_yet));
                        }
                    } else {
                        binding.approvedQty.setError(getString(R.string.please_enter_valid_approved_qty));
                    }
                } else {
                    binding.approvedQty.setError(getString(R.string.please_first_select_defect_to_repair));
                }
            } break;
            case R.id.move_to_basket:
                if (moveToBasketBottomSheet.getState()!= STATE_EXPANDED){
                    showMoveToBasketBottomSheet();
                }
        }
    }

    private boolean containsOnlyDigits(String s) {
        return s.matches("\\d+");
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            binding.moveToBasketBottomSheet.basketCode.getEditText().setText(scannedText);
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
    public void barcodeRead(com.intermec.aidc.BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReaderInterMec.scannedData(barcodeReadEvent);
            binding.moveToBasketBottomSheet.basketCode.getEditText().setText(scannedText);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        barCodeReader.onPause();
        barCodeReaderInterMec.onPause();
    }
}