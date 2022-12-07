package com.example.gbsbadrsf.Quality.paint.RejectionRequest;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gbsbadrsf.Model.Department;
import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.Paint.PaintSignOff.PaintSignOffPprListViewModel;
import com.example.gbsbadrsf.Quality.Data.Defect;
import com.example.gbsbadrsf.Quality.Data.RejectionReason;
import com.example.gbsbadrsf.Quality.manfacturing.RejectionRequest.DefectBottomSheet;
import com.example.gbsbadrsf.Quality.manfacturing.RejectionRequest.DefectsListAdapter;
import com.example.gbsbadrsf.Quality.manfacturing.RejectionRequest.SaveRejectionRequestBody;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.paint.Model.SaveRejectionRequestBody_Painting;
import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintRejectionRequestViewModel;
import com.example.gbsbadrsf.Quality.welding.RejectionRequest.SaveRejectionRequestBody_Welding;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentPaintRejectionRequestBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.intermec.aidc.BarcodeReadListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PaintRejectionRequestFragment extends Fragment implements BarcodeReadListener, View.OnClickListener, BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener, PaintDefectsListAdapter.SetOnItemClicked {
    private static final String GETTING_DATA_SUCCESSFULLY = "Data sent successfully";
    PaintRejectionRequestViewModel viewModel;
//    @Inject
//    ViewModelProviderFactory provider;
    public PaintRejectionRequestFragment() {
        // Required empty public constructor
    }


    public static PaintRejectionRequestFragment newInstance() {
        return new PaintRejectionRequestFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentPaintRejectionRequestBinding binding;
    SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;
    BottomSheetBehavior bottomSheetBehavior;
    PaintDefectsListAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaintRejectionRequestBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barCodeReader = new SetUpBarCodeReader(this,this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
        binding.oldBasketCode.getEditText().requestFocus();
        initViewModel();
        setUpProgressDialog();
        getDepartmentsList();
        setUpDepartmentsSpinner();
        observeGettingDepartments();
        attachButtonsToListener();
        getReasonsList();
        setUpReasonsSpinner();
        addTextWatchers();
        checkFocus();
        setUpBottomSheet();
        observeSavingRejectionRequest();
        handleEditTextFocus(binding.oldBasketCode,binding.newBasketCode);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_HIDDEN:
                        binding.disableColor.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        binding.disableColor.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                binding.disableColor.setVisibility(View.GONE);
            }
        });
    }

    private int selectedReasonId = -1;
    private ArrayAdapter<RejectionReason> reasonAdapter;
    private void setUpReasonsSpinner() {
        reasonAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,rejectionReasons);
        binding.reasonSpinner.setAdapter(reasonAdapter);
        binding.reasonSpinner.setOnItemClickListener((parent, view, position, id) -> {
            selectedReasonId = rejectionReasons.get(position).getRejectionReasonId();
        });
    }
    private List<RejectionReason> rejectionReasons = new ArrayList<>();
    private void getReasonsList() {
        viewModel.getReasonsList(USER_ID,DEVICE_SERIAL_NO);
        viewModel.getApiResponseReasonsList().observe(getViewLifecycleOwner(),apiResponseGetRejectionReasonsList -> {
            if (apiResponseGetRejectionReasonsList!=null){
                String statusMessage = apiResponseGetRejectionReasonsList.getResponseStatus().getStatusMessage();
                if (apiResponseGetRejectionReasonsList.getResponseStatus().getIsSuccess()){
                    rejectionReasons.clear();
                    rejectionReasons.addAll(apiResponseGetRejectionReasonsList.getRejectionReasonList());
                    reasonAdapter.notifyDataSetChanged();
                }
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void setUpBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.defectsListBottomSheet.getRoot());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        setUpRecyclerView();
        binding.defectsListBottomSheet.save.setOnClickListener(v->{
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        });
    }
    private void setUpRecyclerView() {
        adapter = new PaintDefectsListAdapter(getContext(),this);
        binding.defectsListBottomSheet.defectsCheckList.setAdapter(adapter);
    }
    boolean oldBasketCodeFocused,newBasketCodeFocused;
    private void checkFocus() {
        binding.oldBasketCode.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            oldBasketCodeFocused = hasFocus;
        });
        binding.newBasketCode.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            newBasketCodeFocused = hasFocus;
        });
    }

    private void observeSavingRejectionRequest() {
        viewModel.getApiResponseSaveRejectionRequestStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING)
                progressDialog.show();
            else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    private void addTextWatchers() {
        binding.rejectedQtyEdt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.rejectedQtyEdt.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.rejectedQtyEdt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.rejectedQtyEdt.setError(null);
            }
        });
        binding.oldBasketCode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.oldBasketCode.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                basketData = null;
                fillViewsData();
                binding.oldBasketCode.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.oldBasketCode.setError(null);
            }
        });
        binding.newBasketCode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.newBasketCode.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.newBasketCode.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.newBasketCode.setError(null);
            }
        });
        binding.oldBasketCode.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    oldBasketCode = binding.oldBasketCode.getEditText().getText().toString().trim();
                    getBasketData(oldBasketCode);
                    return true;
                }
                return false;
            }
        });
    }

    private void attachButtonsToListener() {
        binding.saveBtn.setOnClickListener(this);
        binding.reasonDefBtn.setOnClickListener(this);
    }

    String parentCode ="", parentDesc,jobOrderName,deviceSerial=DEVICE_SERIAL_NO,oldBasketCode,newBasketCode,basketType;
    int basketQty,jobOrderQty;
    LastMovePaintingBasket basketData;

    private void getBasketData(String oldBasketCode) {
        binding.oldBasketCode.setError(null);
        viewModel.getBasketDataViewModel(userId,deviceSerial,oldBasketCode);
        viewModel.getApiResponseBasketDataLiveData().observe(getViewLifecycleOwner(),apiResponseLastMoveWeldingBasket -> {
            String statusMessage = apiResponseLastMoveWeldingBasket.getResponseStatus().getStatusMessage();
            if (apiResponseLastMoveWeldingBasket.getResponseStatus().getIsSuccess()){
                basketData = apiResponseLastMoveWeldingBasket.getLastMovePaintingBaskets().get(0);
                binding.oldBasketCode.setError(null);
                binding.dataLayout.setVisibility(View.VISIBLE);
                basketType = basketData.getBasketType();
            } else {
                binding.oldBasketCode.setError(statusMessage);
                basketData = null;
                binding.dataLayout.setVisibility(View.GONE);
            }
            fillViewsData();
        });
    }

    private void fillViewsData() {
        if (basketData!=null) {
            binding.dataLayout.setVisibility(View.VISIBLE);
            parentCode = basketData.getParentCode();
            parentDesc = basketData.getParentDescription();
            jobOrderName = basketData.getJobOrderName();
            jobOrderQty  = basketData.getJobOrderQty();
            basketQty = basketData.getSignOffQty();
            binding.parentDesc.setText(parentDesc);
            binding.jobOrderData.jobordernum.setText(jobOrderName);
            binding.jobOrderData.Joborderqtn.setText(String.valueOf(jobOrderQty));
            if (basketQty != 0)
                binding.loadingQtyData.qty.setText(String.valueOf(basketQty));
            else
                binding.loadingQtyData.qty.setText("");
            if (basketType.equals("Rejected")){
                binding.rejectedQtyEdt.getEditText().setText(String.valueOf(basketQty));
                binding.newBasketCode.getEditText().setText(basketData.getBasketCode());
                binding.rejectedQtyEdt.getEditText().setEnabled(false);
                binding.newBasketCode.getEditText().setEnabled(false);
            } else {
                binding.rejectedQtyEdt.getEditText().setText("");
                binding.newBasketCode.getEditText().setText("");
                binding.rejectedQtyEdt.getEditText().setEnabled(true);
                binding.newBasketCode.getEditText().setEnabled(true);
            }
            getDefectsList(basketData.getOperationId());
        } else {
            binding.dataLayout.setVisibility(View.GONE);
            binding.parentDesc.setText("");
            binding.jobOrderData.jobordernum.setText("");
            binding.jobOrderData.Joborderqtn.setText("");
            binding.loadingQtyData.qty.setText("");
        }
    }

    private void observeGettingDepartments() {
        viewModel.getApiResponseDepartmentsListStatus().observe(getViewLifecycleOwner(),status -> {
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
    int departmentId = -1 ;
    ArrayAdapter<Department> spinnerAdapter;
    private void setUpDepartmentsSpinner() {
        spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,departments);
        binding.responsibleSpinner.setAdapter(spinnerAdapter);
        binding.responsibleSpinner.setOnItemClickListener((parent, view, position, id) -> {
            departmentId = departments.get(position).getDepartmentId();
            binding.newBasketCode.getEditText().requestFocus();
        });
    }

    ProgressDialog progressDialog;
    private void setUpProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.loading_3dots));
    }

    List<Department> departments = new ArrayList<>();
    int userId =USER_ID;
    private void getDepartmentsList() {
        viewModel.getDepartmentsList(userId);
        viewModel.getApiResponseDepartmentsListLiveData().observe(getViewLifecycleOwner(),apiResponseDepartmentsList -> {
            if (apiResponseDepartmentsList!=null) {
                ResponseStatus responseStatus = apiResponseDepartmentsList.getResponseStatus();
                List<Department> departmentList = apiResponseDepartmentsList.getDepartments();
                if (responseStatus.getIsSuccess()) {
                    departments.clear();
                    departments.addAll(departmentList);
                    for (Department department:departments){
                        department.setLang(LocaleHelper.getLanguage(getContext()));
                    }
                    spinnerAdapter.notifyDataSetChanged();
                } else {
                    warningDialog(getContext(),getString(R.string.failed_to_get_departments));
                }
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void initViewModel() {
//        viewModel = ViewModelProviders.of(this,provider).get(PaintRejectionRequestViewModel.class);
        viewModel = new ViewModelProvider(this).get(PaintRejectionRequestViewModel.class);

    }
    List<Defect> defectList = new ArrayList<>();
    private void getDefectsList(int operationId) {
        viewModel.getDefectsList(operationId);
        viewModel.getApiResponseDefectsListPerOperation().observe(getViewLifecycleOwner(),response -> {
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()){
                    defectList.clear();
                    defectList.addAll(response.getDefectsList());
                    adapter.setDefects(defectList);
                } else
                    warningDialog(getContext(),statusMessage);
            } else {
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
            }
        });
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.save_btn:{
                String rejectedQtyString = binding.rejectedQtyEdt.getEditText().getText().toString().trim();
                String oldBasketCode = binding.oldBasketCode.getEditText().getText().toString().trim();
                boolean emptyRejectedQty = rejectedQtyString.isEmpty()||Integer.parseInt(rejectedQtyString)==0;
                boolean validRejectedQty = false;
                if (emptyRejectedQty)
                    binding.rejectedQtyEdt.setError(getString(R.string.please_enter_the_rejected_qty));
                else {
                    validRejectedQty = Integer.parseInt(rejectedQtyString) <= basketQty;
                    if (!validRejectedQty)
                        binding.rejectedQtyEdt.setError(getString(R.string.rejected_qty_must_be_less_than_or_equal_basket_qty));
                }
                String newBasketCode = binding.newBasketCode.getEditText().getText().toString().trim();
//                String newBasketCode = "Bskt10";
                if (newBasketCode.isEmpty())
                    binding.newBasketCode.setError(getString(R.string.please_scan_or_enter_new_basket_code));
                if (oldBasketCode.isEmpty())
                    binding.newBasketCode.setError(getString(R.string.please_scan_or_enter_old_basket_code));

                if (departmentId==-1){
                    binding.responsibleDepSpin.setError(getString(R.string.please_select_a_responsibility));
                }
                if (selectedReasonId==-1){
                    binding.reason.setError(getString(R.string.please_select_a_rejection_reason));
                }
//                if (selectedIds.isEmpty())
//                    warningDialog(getContext(),"Please select at least one defect!");
                if (newBasketCode.equals(oldBasketCode)&&Integer.parseInt(rejectedQtyString)!=basketQty) {
                    binding.newBasketCode.setError(getString(R.string.please_scan_different_basket_or_add_all_basket_qty));
                } else {
                    if (!emptyRejectedQty && validRejectedQty && !newBasketCode.isEmpty() && !oldBasketCode.isEmpty() && departmentId != -1 && selectedReasonId != -1) {
                        SaveRejectionRequestBody_Painting body = new SaveRejectionRequestBody_Painting(
                                userId,
                                deviceSerial,
                                oldBasketCode,
                                newBasketCode,
                                Integer.parseInt(rejectedQtyString),
                                departmentId,
                                selectedReasonId,
                                selectedIds,
                                LocaleHelper.getLanguage(getContext()));
                        saveRejectedRequest(body);
                    }
                }
            } break;
            case R.id.reason_def_btn:
                if (!defectList.isEmpty()){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    adapter.setSelectedDefectsIds(selectedIds);
                } else {
                    warningDialog(getContext(),getString(R.string.no_stored_defects_for_this_operation));
                }
                break;
        }
    }

    private void saveRejectedRequest(SaveRejectionRequestBody_Painting body) {
        NavController navController = NavHostFragment.findNavController(this);
        binding.newBasketCode.setError(null);
        viewModel.saveRejectionRequest(body);
        viewModel.getApiResponseSaveRejectionRequestLiveData().observe(getViewLifecycleOwner(),apiResponseSaveRejectionRequest -> {
            if (apiResponseSaveRejectionRequest!=null) {
                String statusMessage = apiResponseSaveRejectionRequest.getResponseStatus().getStatusMessage();
                if (apiResponseSaveRejectionRequest.getResponseStatus().getIsSuccess()) {
//                    Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
                    showSuccessAlerter(statusMessage,getActivity());
                    navController.popBackStack();
                } else {
                    binding.newBasketCode.setError(statusMessage);
                }
            } else {
                warningDialog(getContext(),getString(R.string
                        .error_in_saving_data));
            }
        });
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            if (oldBasketCodeFocused){
                binding.oldBasketCode.getEditText().setText(scannedText);
                getBasketData(scannedText);
            } else if (newBasketCodeFocused){
                binding.newBasketCode.getEditText().setText(scannedText);
            }
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
    List<Integer> selectedIds = new ArrayList<>();
    @Override
    public void OnItemClicked(List<Integer> ids) {
        selectedIds=ids;
    }

    @Override
    public void barcodeRead(com.intermec.aidc.BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReaderInterMec.scannedData(barcodeReadEvent);
            if (oldBasketCodeFocused){
                binding.oldBasketCode.getEditText().setText(scannedText);
                getBasketData(scannedText);
            } else if (newBasketCodeFocused){
                binding.newBasketCode.getEditText().setText(scannedText);
            }
        });
    }

//    @Override
//    public void OnItemDeselected(int id) {
//        selectedIds.remove(Integer.valueOf(id));
//    }
    //    private void showDialog(String s) {
//       new AlertDialog.Builder(getContext())
//               .setMessage(s)
//               .setIcon(R.drawable.ic_round_warning)
//               .setNegativeButton("OK", new DialogInterface.OnClickListener() {
//                   @Override
//                   public void onClick(DialogInterface dialog, int which) {
//                       dialog.dismiss();
//                       binding.basketcodeEdt.requestFocus();
//                   }
//               })
//               .show();
//    }
}