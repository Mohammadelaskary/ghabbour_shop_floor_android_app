package com.example.gbsbadrsf.Quality.welding.QualityRepair;

import static android.content.ContentValues.TAG;
import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.gbsbadrsf.MyMethods.MyMethods.getCurrentDate2;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.Quality.paint.QualityRepair.PaintQualityRepairFragment.QTY_READY_TO_MOVE;
import static com.example.gbsbadrsf.Quality.welding.WeldingQualityOperationFragment.BASKET_DATA;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.MyMethods.ReadyToMoveToBasketDialog;
import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.Quality.Data.WeldingDefect;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.Quality.welding.ViewModel.WeldingQualityDefectRepairViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.WeldingQualityDefectRepairFragmentBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.intermec.aidc.BarcodeReadListener;

import java.util.ArrayList;
import java.util.List;

public class WeldingQualityDefectRepairFragment extends Fragment implements BarcodeReadListener, SetOnWeldingRepairItemClicked, View.OnClickListener, BarcodeReader.BarcodeListener,BarcodeReader.TriggerListener  {



    public WeldingQualityDefectRepairFragment() {
        // Required empty public constructor
    }


    public static WeldingQualityDefectRepairFragment newInstance() {
        return new WeldingQualityDefectRepairFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    WeldingQualityDefectRepairFragmentBinding binding;
    private BottomSheetBehavior moveToBasketBottomSheet;
    private static final String SAVED_SUCCESSFULLY = "Saved successfully";
    WeldingQualityDefectRepairViewModel viewModel;
//    @Inject
//    ViewModelProviderFactory provider;
    WeldingRepairQualityAdapter adapter;
    private SetUpBarCodeReader barCodeReader2;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = WeldingQualityDefectRepairFragmentBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barCodeReader2 = new SetUpBarCodeReader(this, this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
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
        observeMoveToBasketStatus();
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

    private void observeMoveToBasketStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status ->{
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

    private void observeMoveToBasket() {
        viewModel.getApiResponseDefectedQualityOk_welding().observe(getViewLifecycleOwner(),apiResponseDefectedQualityOk_manufacturing -> {
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
                viewModel.DefectedQualityOk_Welding(
                        USER_ID,
                        DEVICE_SERIAL_NO,
                        oldBasketCode,
                        newBasketCode,
                        defectsWelding.getDefectGroupId(),
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
        moveToBasketBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
        binding.disable.setVisibility(View.VISIBLE);
    }


    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.loading_3dots));
    }

    ProgressDialog progressDialog;
    private void observeAddingDefectRepairStatus() {
        viewModel.getAddWeldingRepairQualityStatus().observe(getViewLifecycleOwner(),status -> {
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
        viewModel.getAddWeldingRepairQuality().observe(getViewLifecycleOwner(),response-> {
            String statusMessage = response.getResponseStatus().getStatusMessage();
            if (response.getResponseStatus().getIsSuccess()){
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
                MyMethods.showSuccessAlerter(statusMessage,getActivity());
                binding.approvedQty.getEditText().setText("");
            } else
                warningDialog(getContext(),statusMessage);
//            Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
        });
    }

    private void updateRecyclerView() {
        defectsWelding.setApprovedQty(approvedQty);
        defectsWelding.setRepairedQty(repairedQty);
        defectsWelding.setPendingRepair(pendingRepair);
        defectsWelding.setPendingApprove(pendingApprove);
        defectsWeldingList.remove(position);
        defectsWeldingList.add(position,defectsWelding);
        adapter.notifyDataSetChanged();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(WeldingQualityDefectRepairViewModel.class);
    }

    private void attachButtonToListener() {
        binding.saveBtn.setOnClickListener(this);
        binding.moveToBasket.setOnClickListener(this);
    }

    private void setUpRecyclerView() {
        adapter = new WeldingRepairQualityAdapter(this,getContext());
        binding.defectsDetailsList.setAdapter(adapter);
    }
    private void fillData() {
        String parentCode = basketData.getParentCode();
        String parentDesc = basketData.getParentDescription();
        String operationName = basketData.getOperationEnName();
        String defectedQty   = String.valueOf(defectsWeldingList.get(0).getQtyDefected());
        int basketQty   = basketData.getSignOffQty();
        String basketDefectedQty=basketQty+"/"+defectedQty;
        binding.parentDesc.setText(parentDesc);
        binding.operation.setText(operationName);
        binding.defectedData.qty.setText(basketDefectedQty);
    }

    LastMoveWeldingBasket basketData;
    String oldBasketCode;
    List<WeldingDefect> defectsWeldingList = new ArrayList<>();
    private void getReceivedData() {
        if (getArguments()!=null){
            basketData = getArguments().getParcelable(BASKET_DATA);
            oldBasketCode = basketData.getBasketCode();
            defectsWeldingList = getArguments().getParcelableArrayList("selectedDefectsWelding");
            readyQtyToMove = getArguments().getInt(QTY_READY_TO_MOVE);
            String readyItemsToMove = readyQtyToMove+getString(R.string.items_are_ready_to_move_to_new_basket);
            binding.moveToBasketQty.setText(readyItemsToMove);
            adapter.setDefectsWeldingList(defectsWeldingList);
            adapter.notifyDataSetChanged();
        }
    }


    int userId = USER_ID, defectsWeldingDetailsId =-1,defectStatus,approvedQty;
    String notes="", deviceSerialNumber=DEVICE_SERIAL_NO;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.save_btn:{
                if (defectsWeldingDetailsId!=-1){
                    if (!binding.approvedQty.getEditText().getText().toString().trim().isEmpty()) {
                        try {
                            approvedQty = Integer.parseInt(binding.approvedQty.getEditText().getText().toString().trim());
                            if (repairedQty != 0) {
                                if (approvedQty >= 0) {
                                    if (containsOnlyDigits(String.valueOf(approvedQty))  && approvedQty <= repairedQty) {
                                        viewModel.addWeldingRepairQuality(
                                                userId,
                                                deviceSerialNumber,
                                                defectsWeldingDetailsId,
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
                if (moveToBasketBottomSheet.getState()!=BottomSheetBehavior.STATE_EXPANDED){
                    showMoveToBasketBottomSheet();
                }
        }
    }

    private boolean containsOnlyDigits(String s) {
        return s.matches("\\d+");
    }
    WeldingDefect defectsWelding;
    int position,repairedQty,pendingRepair,pendingApprove;
    @Override
    public void onWeldingRepairItemClicked(WeldingDefect defectsWelding,int position) {
        this.defectsWelding = defectsWelding;
        this.position = position;
        repairedQty = defectsWelding.getRepairedQty();
        approvedQty = defectsWelding.getApprovedQty();
        pendingApprove = defectsWelding.getPendingApprove();
        defectsWeldingDetailsId = defectsWelding.getDefectsWeldingDetailsId();
        Log.d("PendingApprove",pendingApprove+"");
        if (pendingApprove!=0) {
            binding.approvedQty.getEditText().setText(String.valueOf(pendingApprove));
            defectStatus = defectsWelding.getDefectStatus();
        } else
            binding.approvedQty.getEditText().setText(R.string.there_is_no_pending_approve_quantity);
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(() -> {
            String scannedText = barCodeReader2.scannedData(barcodeReadEvent);
            binding.moveToBasketBottomSheet.basketCode.getEditText().setText(scannedText);
        });
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {

    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {
        barCodeReader2.onTrigger(triggerStateChangeEvent);
    }

    @Override
    public void onResume() {
        super.onResume();
        barCodeReader2.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        barCodeReader2.onPause();
        barCodeReaderInterMec.onPause();
    }

    @Override
    public void barcodeRead(com.intermec.aidc.BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(() -> {
            String scannedText = barCodeReaderInterMec.scannedData(barcodeReadEvent);
            binding.moveToBasketBottomSheet.basketCode.getEditText().setText(scannedText);
        });
    }
}