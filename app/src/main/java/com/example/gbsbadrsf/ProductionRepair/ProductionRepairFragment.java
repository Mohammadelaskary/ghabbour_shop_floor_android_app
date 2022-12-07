package com.example.gbsbadrsf.ProductionRepair;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;
import static com.example.gbsbadrsf.MyMethods.MyMethods.sortManufacturing;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.Model.ManufacturingDefect;
import com.example.gbsbadrsf.Model.QtyDefectsQtyDefected;
import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.ProductionRepair.Data.ProductionRepairViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentProductionRepairBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.intermec.aidc.BarcodeReadListener;

import java.util.ArrayList;
import java.util.List;

public class ProductionRepairFragment extends Fragment implements BarcodeReadListener, BarcodeReader.BarcodeListener,BarcodeReader.TriggerListener {

    FragmentProductionRepairBinding binding;
    List<ManufacturingDefect>  defectsManufacturingList = new ArrayList<>();
    ProductionRepairChildsQtyDefectsQtyAdapter adapter;
    ProductionRepairViewModel viewModel;
    private static final String SUCCESS = "Data sent successfully";

//    @Inject
//    ViewModelProviderFactory provider;
    ProgressDialog progressDialog;
    SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductionRepairBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpProgressDialog();
        initViewModel();
        barCodeReader = new SetUpBarCodeReader(this,this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
        handleEditTextFocus(binding.basketCode);
        setupRecyclerView();
//        if (viewModel.getBasketData()!=null){
//            LastMoveManufacturingBasket basketData = viewModel.getBasketData();
//            adapter.setBasketData(basketData);
//            fillData(basketData.getChildDescription(),basketData.getJobOrderName(), basketData.getOperationEnName(),basketData.getJobOrderQty());
////            getBasketDefectsManufacturing(basketData.getBasketCode());
//        }
        if (viewModel.getBasketCode()!=null){
            binding.basketCode.getEditText().setText(viewModel.getBasketCode());
            getBasketData(viewModel.getBasketCode());
        }
        addTextWatcher();
        observeGettingBasketData();
        observeGettingDefectsManufacturing();
        initViews();
    }

    String basketCode;
    private void addTextWatcher() {
        binding.basketCode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.basketCode.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.basketCode.setError(null);
            }
        });
        binding.basketCode.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    basketCode = binding.basketCode.getEditText().getText().toString().trim();
                    getBasketData(basketCode);
//                    getBasketDefectsManufacturing(basketCode);
                    return true;
                }
                return false;
            }
        });
    }

    private void observeGettingDefectsManufacturing() {
        viewModel.getDefectsManufacturingListStatus().observe(getViewLifecycleOwner(),status -> {
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
    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList = new ArrayList<>();
//    private void getBasketDefectsManufacturing(String basketCode) {
//        viewModel.getDefectsManufacturingViewModel(basketCode);
//        viewModel.getDefectsManufacturingListLiveData().observe(getViewLifecycleOwner(),apiResponseDefectsManufacturing -> {
//            if (apiResponseDefectsManufacturing!=null) {
//                ResponseStatus responseStatus = apiResponseDefectsManufacturing.getResponseStatus();
//                String statusMessage = responseStatus.getStatusMessage();
//                if (statusMessage.equals(SUCCESS)) {
//                    if (apiResponseDefectsManufacturing.getData() != null) {
//                        defectsManufacturingList.clear();
//                        List<ManufacturingDefect> defectsManufacturingListLocal = apiResponseDefectsManufacturing.getData();
//                        defectsManufacturingList.addAll(defectsManufacturingListLocal);
//                        adapter.setDefectsManufacturingList(defectsManufacturingList);
//                        qtyDefectsQtyDefectedList = groupDefectsById(defectsManufacturingList);
//                        String defectedQty = calculateDefectedQty(qtyDefectsQtyDefectedList);
//                        binding.defectedData.qty.setText(defectedQty);
//                    }
//                } else {
//                    binding.defectedData.qty.setText("");
//                    qtyDefectsQtyDefectedList.clear();
//                }
//            } else {
//                binding.defectedData.qty.setText("");
//                qtyDefectsQtyDefectedList.clear();
////                Toast.makeText(getContext(), "Error in getting data!", Toast.LENGTH_SHORT).show();
//                warningDialog(getContext(),"Error in getting data!");
//            }
//            adapter.setQtyDefectsQtyDefectedList(qtyDefectsQtyDefectedList);
//            adapter.notifyDataSetChanged();
//        });
//    }
    public List<QtyDefectsQtyDefected> groupDefectsById(List<ManufacturingDefect> defectsManufacturingListLocal) {
        List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedListLocal = new ArrayList<>();
        int id = -1 ;
        MyMethods.sortManufacturing(defectsManufacturingListLocal);
        Log.d("defectsLength",qtyDefectsQtyDefectedList.size()+"");
        for (ManufacturingDefect defectsManufacturing:defectsManufacturingListLocal){
            if (defectsManufacturing.getDefectGroupId()!=id&&!defectsManufacturing.getIsRejectedQty()){
                int currentId = defectsManufacturing.getDefectGroupId();
                int defectedQty = defectsManufacturing.getQtyDefected();
                QtyDefectsQtyDefected qtyDefectsQtyDefected = new QtyDefectsQtyDefected(currentId,defectedQty,getDefectsQty(currentId));
                qtyDefectsQtyDefectedListLocal.add(qtyDefectsQtyDefected);
                id = currentId;
            }
        }
        return qtyDefectsQtyDefectedListLocal;
    }

    private int getDefectsQty(int currentId) {
        int defectNo = 0;
        for (ManufacturingDefect defectsManufacturing:defectsManufacturingList){
            if (defectsManufacturing.getDefectGroupId()==currentId)
                defectNo++;
        }
        return  defectNo;
    }


    private String calculateDefectedQty(List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList) {
        int sum = 0;
        for (QtyDefectsQtyDefected qtyDefectsQtyDefected : qtyDefectsQtyDefectedList){
            sum +=qtyDefectsQtyDefected.getDefectedQty();
        }
        return String.valueOf(sum);
    }
    private void setUpProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getContext().getString(R.string.loading_3dots));
        progressDialog.setCancelable(false);
    }

    private void observeGettingBasketData() {
        viewModel.getApiResponseBasketDataStatus().observe(getViewLifecycleOwner(),status -> {
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

    private void initViewModel() {
//        viewModel = ViewModelProviders.of(this,provider).get(ProductionRepairViewModel.class);
        viewModel = new ViewModelProvider(this).get(ProductionRepairViewModel.class);

    }
    LastMoveManufacturingBasket basketData = new LastMoveManufacturingBasket();
    String childDesc,childCode = "",operationName,jobOrderName,totalDefectedQty,totalRejectedQty;
    int jobOrderQty;

    private void getBasketData(String basketCode) {
        viewModel.getBasketDataViewModel(USER_ID,DEVICE_SERIAL_NO,basketCode);
        viewModel.getApiResponseBasketDataLiveData().observe(getViewLifecycleOwner(),apiResponseLastMoveManufacturingBasket -> {
            if (apiResponseLastMoveManufacturingBasket!=null) {
                ResponseStatus responseStatus = apiResponseLastMoveManufacturingBasket.getResponseStatus();
                String statusMessage = responseStatus.getStatusMessage();
                if (responseStatus.getIsSuccess()) {
                    basketData = apiResponseLastMoveManufacturingBasket.getLastMoveManufacturingBaskets().get(0);
                    adapter.setBasketData(basketData);
                    childDesc = basketData.getChildDescription();
                    childCode = basketData.getChildCode();
                    operationName = basketData.getOperationEnName();
                    jobOrderName = basketData.getJobOrderName();
                    jobOrderQty = basketData.getJobOrderQty();
                    totalDefectedQty = basketData.getTotalQtyDefected();
                    totalRejectedQty = basketData.getTotalQtyRejected();
                    binding.basketCode.setError(null);
                    defectsManufacturingList.clear();
                    List<ManufacturingDefect> defectsManufacturingListLocal = apiResponseLastMoveManufacturingBasket.getManufacturingDefects();
                    defectsManufacturingList.addAll(defectsManufacturingListLocal);
                    adapter.setDefectsManufacturingList(defectsManufacturingList);
                    qtyDefectsQtyDefectedList = groupDefectsById(defectsManufacturingList);
                  if (basketData.getBasketType().equals("Defected")){
                        adapter.setQtyDefectsQtyDefectedList(qtyDefectsQtyDefectedList);
                        int basketQty = basketData.getSignOffQty();
                        String basketDefectedQty = basketQty + "/" + calculateDefectedQty(qtyDefectsQtyDefectedList);
                        binding.basketQtyLayout.qty.setText(basketDefectedQty);
                    } else {
                        adapter.setQtyDefectsQtyDefectedList(new ArrayList<>());
//                        int basketQty = basketData.getSignOffQty();
//                        String basketDefectedQty = basketQty + "/" + calculateDefectedQty(qtyDefectsQtyDefectedList);
                        binding.basketQtyLayout.qty.setText(String.valueOf(basketData.getSignOffQty()));
                    }
                    binding.dataLayout.setVisibility(View.VISIBLE);
                } else {
                    childDesc = "";
                    childCode = "";
                    operationName = "";
                    binding.dataLayout.setVisibility(View.GONE);
                    binding.basketCode.setError(statusMessage);
                }
            } else {
                childDesc = "";
                childCode = "";
                operationName = "";
                binding.dataLayout.setVisibility(View.GONE);
                binding.basketCode.setError(getString(R.string.error_in_getting_data));
            }
            fillData(childDesc,jobOrderName,operationName,jobOrderQty);
        });
    }

    private void fillData(String childDesc, String jobOrderName, String operationName,int jobOrderQty) {
        binding.childDesc.setText(childDesc);
        binding.operation.setText(operationName);
        binding.jobOrderData.jobordernum.setText(jobOrderName);
//        if (totalDefectedQty)
        binding.jobOrderData.Joborderqtn.setText(String.valueOf(jobOrderQty));
    }


    private void setupRecyclerView() {
        adapter = new ProductionRepairChildsQtyDefectsQtyAdapter();
        binding.defectsDetailsList.setAdapter(adapter);
    }

    private void initViews() {

    }


    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            binding.basketCode.getEditText().setText(scannedText);
            getBasketData(scannedText.trim());
//            getBasketDefectsManufacturing(scannedText.trim());
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
        changeTitle(getString(R.string.manfacturing),(MainActivity) getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        barCodeReader.onPause();
        barCodeReaderInterMec.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        basketCode = binding.basketCode.getEditText().getText().toString().trim();
        viewModel.setBasketCode(basketCode);
    }

    @Override
    public void barcodeRead(com.intermec.aidc.BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReaderInterMec.scannedData(barcodeReadEvent);
            binding.basketCode.getEditText().setText(scannedText);
            getBasketData(scannedText.trim());
//            getBasketDefectsManufacturing(scannedText.trim());
        });
    }
}
