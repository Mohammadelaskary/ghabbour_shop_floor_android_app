package com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop;

import static android.content.ContentValues.TAG;
import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop.MachineStopFragment.MACHINE_DATA;
import static com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop.MachineStopFragment.STOP_REASONS;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.getHoldSignOffRemainingQty;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.databinding.FragmentMachineHold2Binding;

import java.util.ArrayList;
import java.util.List;

public class MachineHoldFragment extends Fragment implements AddBasketsBottomSheet.OnAddBasketsBottomSheetButtonsClicked, View.OnClickListener {


    public static final String MACHINE_CODE = "machine_code";
    private MachineHoldViewModel viewModel;
    private FragmentMachineHold2Binding binding;
    private ProgressDialog progressDialog;
    public static MachineHoldFragment newInstance() {
        return new MachineHoldFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMachineHold2Binding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    private MachineData machineData;
    private AddBasketsBottomSheet addBasketsBottomSheetHold;
    private AddBasketsBottomSheet addBasketsBottomSheetOk;
    private ArrayAdapter<StopReason> stopReasonAdapter;
    private ArrayList<StopReason> stopReasons = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MachineHoldViewModel.class);
        addBasketsBottomSheetHold = new AddBasketsBottomSheet(viewModel,this);
        addBasketsBottomSheetHold.setCancelable(false);
        addBasketsBottomSheetOk = new AddBasketsBottomSheet(viewModel,this);
        addBasketsBottomSheetOk.setCancelable(false);
        progressDialog = loadingProgressDialog(getContext());
    }
    private int machineQty;
    private String machineCode;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments()!=null){
            machineData = getArguments().getParcelable(MACHINE_DATA);
            machineQty = machineData.getMachineQty();
            stopReasons = getArguments().getParcelableArrayList(STOP_REASONS);
            setUpReasonAdapter();
            machineCode = getArguments().getString(MACHINE_CODE);
            addBasketsBottomSheetHold.setMachineData(machineData);
            addBasketsBottomSheetOk.setMachineData(machineData);
            fillViews();
        }
        observeSaveMachineHold();
        attachButtonsToListeners();
        observeStatus();
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
                    warningDialog(getContext(),getString(R.string.network_issue));
                    progressDialog.hide();
                    break;
            }
        });
    }

    private void observeSaveMachineHold() {
        viewModel.getSaveMachineHold().observe(getViewLifecycleOwner(),apiResponseMachineHold -> {
            if (apiResponseMachineHold!=null){
                String statusMessage = apiResponseMachineHold.getResponseStatus().getStatusMessage();
                if (apiResponseMachineHold.getResponseStatus().getIsSuccess()){
                    showSuccessAlerter(statusMessage,getActivity());
                    back(this);
                } else
                    warningDialog(getContext(),statusMessage);
            } else warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private int selectedReasonId = -1;
    private void setUpReasonAdapter() {
        stopReasonAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, stopReasons);
        binding.stopReasonSpinner.setAdapter(stopReasonAdapter);
        binding.stopReasonSpinner.setOnItemClickListener((parent, view, position, id) -> {
            selectedReasonId = stopReasons.get(position).getReasonId();
        });
    }

    private void fillViews() {
        binding.childesc.setText(machineData.getChildDescription());
        binding.jobordernum.setText(machineData.getJobOrderName());
        binding.Joborderqtn.setText(String.valueOf(machineData.getJobOrderQty()));
        binding.operation.setText(machineData.getOperationEnName());
        binding.loadingQty.setText(String.valueOf(machineData.getMachineQty()));

    }

    private void attachButtonsToListeners() {
        binding.signOffBaskets.addHoldBaskets.setOnClickListener(v -> {
            basketsType = BasketType.HOLD;
            addBasketsBottomSheetHold.setBasketList(holdBaskets);
            addBasketsBottomSheetHold.setBulk(holdBulk);
            addBasketsBottomSheetHold.setRemainingQty(getHoldSignOffRemainingQty(signOffBaskets,signOffBulk, machineQty));
            addBasketsBottomSheetHold.show(getActivity().getSupportFragmentManager(), addBasketsBottomSheetHold.getTag());
        });
        binding.signOffBaskets.addSignOffBaskets.setOnClickListener(v ->{
            basketsType = BasketType.SIGN_OFF;
            addBasketsBottomSheetOk.setBasketList(signOffBaskets);
            addBasketsBottomSheetOk.setBulk(signOffBulk);
            addBasketsBottomSheetOk.setRemainingQty(getHoldSignOffRemainingQty(holdBaskets,holdBulk, machineQty));
            addBasketsBottomSheetOk.show(getActivity().getSupportFragmentManager(), addBasketsBottomSheetHold.getTag());
        });
        binding.saveBtn.setOnClickListener(this);
    }

    private List<Basket> holdBaskets = new ArrayList<>();
    private List<Basket> signOffBaskets = new ArrayList<>();
    private boolean holdBulk = true ,signOffBulk = true;
    private BasketType basketsType ;
    private int signOffQty,holdQty;
    @Override
    public void onAddBasketsBottomSheetButtonsClicked(List<Basket> baskets, boolean isBulk,int qty) {
        if (basketsType!=null){
            if (basketsType.equals(BasketType.HOLD)){
                holdBaskets = baskets;
                holdBulk = isBulk;
                binding.signOffBaskets.holdQty.setText(String.valueOf(qty));
                holdQty = qty;
            } else {
                signOffBaskets = baskets;
                signOffBulk = isBulk;
                binding.signOffBaskets.signOffQty.setText(String.valueOf(qty));
                signOffQty = qty;
            }
            handleHoldColorsIcons();
            handleSignOffColorsIcons();
        }
    }

    private void handleHoldColorsIcons() {
        if (holdBaskets.isEmpty()){
            binding.signOffBaskets.holdText.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
            binding.signOffBaskets.addHoldBaskets.setIcon(getActivity().getResources().getDrawable(R.drawable.ic_add));
        } else {
            binding.signOffBaskets.holdText.setTextColor(getActivity().getResources().getColor(R.color.done));
            binding.signOffBaskets.addHoldBaskets.setIcon(getActivity().getResources().getDrawable(R.drawable.ic_edit));
        }
    }
    private void handleSignOffColorsIcons() {
        if (signOffBaskets.isEmpty()){
            binding.signOffBaskets.signOffText.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
            binding.signOffBaskets.addSignOffBaskets.setIcon(getActivity().getResources().getDrawable(R.drawable.ic_add));
        } else {
            binding.signOffBaskets.signOffText.setTextColor(getActivity().getResources().getColor(R.color.done));
            binding.signOffBaskets.addSignOffBaskets.setIcon(getActivity().getResources().getDrawable(R.drawable.ic_edit));
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_btn:
                if (!holdBaskets.isEmpty()){
                    int totalAddedQty = holdQty+signOffQty;
                    Log.d(TAG, "onClick: "+totalAddedQty);
                    if (totalAddedQty== machineQty){
                        if (selectedReasonId!=-1) {
                            MachineHoldData data = new MachineHoldData(
                                    USER_ID,
                                    DEVICE_SERIAL_NO,
                                    machineCode,
                                    signOffQty,
                                    signOffBaskets,
                                    signOffBulk,
                                    holdQty,
                                    holdBaskets,
                                    holdBulk,
                                    selectedReasonId,
                                    LocaleHelper.getLanguage(getContext())
                            );
                            viewModel.saveMachineHold(data);
                        } else binding.stopReason.setError(getString(R.string.please_select_stop_reason));
                    } else warningDialog(getContext(),getString(R.string.please_add_all_loading_qty_to_baskets));

                } else warningDialog(getContext(),getString(R.string.please_add_at_least_1_hold_basket));
                break;
        }
    }
}