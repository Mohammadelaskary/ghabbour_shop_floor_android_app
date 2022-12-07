package com.example.gbsbadrsf.Quality.paint.QualityRepair;

import static android.content.ContentValues.TAG;
import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.gbsbadrsf.MyMethods.MyMethods.getCurrentDate2;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.Quality.paint.PaintQualityOperationFragment.BASKET_DATA;
import static com.example.gbsbadrsf.Quality.paint.QualityRepair.PaintQualityRepairFragment.QTY_READY_TO_MOVE;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gbsbadrsf.MyMethods.ReadyToMoveToBasketDialog;
import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.paint.Model.PaintingDefect;
import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintQualityRepairViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentPaintQualityDefectRepairBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.intermec.aidc.BarcodeReadListener;

import java.util.ArrayList;
import java.util.List;

public class PaintQualityDefectRepairFragment extends Fragment implements BarcodeReadListener, SetOnPaintingRepairItemClicked, View.OnClickListener, BarcodeReader.BarcodeListener,BarcodeReader.TriggerListener  {



    public PaintQualityDefectRepairFragment() {
        // Required empty public constructor
    }


    public static PaintQualityDefectRepairFragment newInstance() {
        return new PaintQualityDefectRepairFragment();
    }
    private SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barCodeReader = new SetUpBarCodeReader(this,this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
    }
    private BottomSheetBehavior moveToBasketBottomSheet;
    FragmentPaintQualityDefectRepairBinding binding;
    private static final String SAVED_SUCCESSFULLY = "Saved successfully";
    PaintQualityRepairViewModel viewModel;
    PaintRepairQualityAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaintQualityDefectRepairBinding.inflate(inflater,container,false);

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

    private void observeMoveToBasket() {
        viewModel.getDefectedQualityOk_Painting().observe(getViewLifecycleOwner(), apiResponseDefectedQualityOk_manufacturing -> {
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
        moveToBasketBottomSheet
                .setDraggable(true);
        hideMoveToBasketBottomSheet();
        binding.moveToBasketBottomSheet.save.setOnClickListener(v->{
            String newBasketCode = binding.moveToBasketBottomSheet.basketCode.getEditText().getText().toString().trim();
            Log.d(TAG, "setUpMoveToBasketBottomSheetonclick: "+oldBasketCode);
            if (!newBasketCode.isEmpty()){
                viewModel.DefectedQualityOk_Painting(
                        USER_ID,
                        DEVICE_SERIAL_NO,
                        oldBasketCode,
                        newBasketCode,
                        defectsPainting.getDefectGroupId(),
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
        viewModel.getAddPaintingRepairQualityStatus().observe(getViewLifecycleOwner(), status -> {
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
    private void observeMoveToBasketStatus() {
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
        viewModel.getAddPaintingRepairQuality().observe(getViewLifecycleOwner(), response-> {
            String statusMessage = response.getResponseStatus().getStatusMessage();
            if (response.getResponseStatus().getIsSuccess()){

                boolean moveToBasket = response.isMoveToOkBasket();
                Log.d(TAG, "observeAddingDefectRepairResponse: move to basket "+moveToBasket);
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
                showSuccessAlerter(statusMessage,getActivity());
                binding.approvedQty.getEditText().setText("");
            }else
                warningDialog(getContext(),statusMessage);
//            Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
        });
    }

    private void updateRecyclerView() {
        defectsPainting.setApprovedQty(approvedQty);
        defectsPainting.setRepairedQty(repairedQty);
        defectsPainting.setPendingRepair(pendingRepair);
        defectsPainting.setPendingApprove(pendingApprove);
        defectsPaintingList.remove(position);
        defectsPaintingList.add(position,defectsPainting);
        adapter.notifyDataSetChanged();
    }

    private void initViewModel() {
        viewModel = PaintQualityRepairFragment.viewModel;
    }

    private void attachButtonToListener() {
        binding.saveBtn.setOnClickListener(this);
        binding.moveToBasket.setOnClickListener(this);
    }

    private void setUpRecyclerView() {
        adapter = new PaintRepairQualityAdapter(this,getContext());
        binding.defectsDetailsList.setAdapter(adapter);
    }
    String oldBasketCode;
    private void fillData() {
        String parentCode = basketData.getParentCode();
        String parentDesc = basketData.getParentDescription();
        String operationName = basketData.getOperationEnName();
        oldBasketCode = basketData.getBasketCode();
        String defectedQty   = String.valueOf(defectsPaintingList.get(0).getQtyDefected());
        int basketQty   = basketData.getSignOffQty();
        String basketDefectedQty=basketQty+"/"+defectedQty;
        binding.parentDesc.setText(parentDesc);
        binding.operation.setText(operationName);
        binding.defectedQtnEdt.qty.setText(basketDefectedQty);
    }

    LastMovePaintingBasket basketData;
    List<PaintingDefect> defectsPaintingList = new ArrayList<>();
    private void getReceivedData() {
        if (getArguments()!=null){
            basketData = getArguments().getParcelable(BASKET_DATA);
            defectsPaintingList = getArguments().getParcelableArrayList("selectedDefectsPainting");
            readyQtyToMove = getArguments().getInt(QTY_READY_TO_MOVE);
            String readyItemsToMove = readyQtyToMove+getString(R.string.items_are_ready_to_move_to_new_basket);
            binding.moveToBasketQty.setText(readyItemsToMove);
            adapter.setDefectsPaintingList(defectsPaintingList);
            adapter.notifyDataSetChanged();
        }
    }


    int userId = USER_ID, defectsPaintingDetailsId =-1,defectStatus,approvedQty;
    String notes="", deviceSerialNumber=DEVICE_SERIAL_NO;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.save_btn:{
                if (defectsPaintingDetailsId!=-1){
                    if (!binding.approvedQty.getEditText().getText().toString().trim().isEmpty()) {
                        try {
                            approvedQty = Integer.parseInt(binding.approvedQty.getEditText().getText().toString().trim());
                            if (repairedQty != 0) {
                                if (approvedQty >= 0) {
                                    if (containsOnlyDigits(String.valueOf(approvedQty))  && approvedQty <= repairedQty) {
                                        viewModel.addPaintingRepairQuality(
                                                userId,
                                                deviceSerialNumber,
                                                defectsPaintingDetailsId,
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
                if (moveToBasketBottomSheet.getState()!= BottomSheetBehavior.STATE_EXPANDED){
                    showMoveToBasketBottomSheet();
                }
        }
    }

    private boolean containsOnlyDigits(String s) {
        return s.matches("\\d+");
    }
    int position,repairedQty,pendingRepair,pendingApprove;
    PaintingDefect defectsPainting;
    @Override
    public void onPaintingRepairItemClicked(PaintingDefect defectsPainting,int position) {
        this.position = position;
        this.defectsPainting = defectsPainting;
        repairedQty = defectsPainting.getRepairedQty();
        pendingApprove = defectsPainting.getPendingApprove();
        defectsPaintingDetailsId = defectsPainting.getDefectsPaintingDetailsId();
        if (pendingApprove!=0) {
            binding.approvedQty.getEditText().setText(String.valueOf(pendingApprove));
            defectStatus = defectsPainting.getDefectStatus();
        } else
            binding.approvedQty.getEditText().setText(R.string.there_is_no_pending_approve_quantity);
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
        barCodeReaderInterMec.onPause();
        barCodeReader.onPause();
    }
}