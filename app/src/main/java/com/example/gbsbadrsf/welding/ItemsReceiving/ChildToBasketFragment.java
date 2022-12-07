package com.example.gbsbadrsf.welding.ItemsReceiving;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;
import static com.example.gbsbadrsf.welding.ItemsReceiving.ChildToBasketsBottomSheet.ISSUED_CHILD;
import static com.example.gbsbadrsf.welding.ItemsReceiving.PprAdapter.JOB_ORDER;
import static com.example.gbsbadrsf.welding.ItemsReceiving.PprAdapter.PPR;

import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.Model.IssuedChild;
import com.example.gbsbadrsf.Model.JobOrder;
import com.example.gbsbadrsf.Model.Ppr;
import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintQualityOperationViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.databinding.ChildToBasketFragmentBinding;
import com.google.gson.Gson;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ChildToBasketFragment extends Fragment implements ChildAdapter.onChildItemClicked
//        BarcodeReader.BarcodeListener,BarcodeReader.TriggerListener,
         {

    private ChildToBasketViewModel viewModel;
//    private SetUpBarCodeReader barCodeReader;
//    @Inject
//    ViewModelProviderFactory provider;
//
//    @Inject
//    Gson gson;

    public ChildToBasketFragment() {
    }

    public static ChildToBasketFragment newInstance() {
        return new ChildToBasketFragment();
    }

    private ChildToBasketFragmentBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ChildToBasketFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    private ProgressDialog progressDialog;
    private ChildToBasketsBottomSheet childToBasketsBottomSheet;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        viewModel = ViewModelProviders.of(this,provider).get(ChildToBasketViewModel.class);
        viewModel = new ViewModelProvider(this).get(ChildToBasketViewModel.class);
        progressDialog = loadingProgressDialog(getContext());
//        barCodeReader = new SetUpBarCodeReader(this,this);

    }

    private Ppr ppr;
    private JobOrder jobOrder;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getReceivedData();
        fillData();
        setUpChildRecyclerView();
        observeGettingJobOrdersIssuedChilds();
        observeStatus();
    }

    private void observeStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    progressDialog.show();
                    break;
                case ERROR:
                    progressDialog.dismiss();
                    warningDialog(getContext(),getString(R.string.network_issue_or_no_qty_issues));
                    break;
                case SUCCESS:
                    progressDialog.hide();
                    break;
            }
        });
    }



    private void setUpChildRecyclerView() {
        adapter = new ChildAdapter(this,getContext());
        binding.childList.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically(){
                return false;
            }
        };
        binding.childList.setLayoutManager(layoutManager);
    }

    private List<LstIssuedChildParameter> issuedChildList = new ArrayList<>();
    private ChildAdapter adapter;
    private void observeGettingJobOrdersIssuedChilds() {
        viewModel.jobOrdersIssuedChilds.observe(getViewLifecycleOwner(),apiResponseGetJobOrderIssuedChilds -> {
            if (apiResponseGetJobOrderIssuedChilds!=null){
                String statusMessage = apiResponseGetJobOrderIssuedChilds.getResponseStatus().getStatusMessage();
                if (apiResponseGetJobOrderIssuedChilds.getResponseStatus().getIsSuccess()){
                    issuedChildList =apiResponseGetJobOrderIssuedChilds.getLstIssuedChildParameters();
                    adapter.setIssuedChildList(issuedChildList);
                } else {
                    warningDialog(getContext(), statusMessage);
                }
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void fillData() {
        binding.parentDesc.setText(jobOrder.getParentDisplayName());
        binding.jobOrderData.jobordernum.setText(jobOrder.getJobOrderName());
        binding.jobOrderData.Joborderqtn.setText(jobOrder.getJobOrderQty().toString());
        binding.pprQty.qty.setText(ppr.getLoadingQty().toString());
    }

    private void getReceivedData() {
        if (getArguments()!=null){
            ppr = getArguments().getParcelable(PPR);
            jobOrder = getArguments().getParcelable(JOB_ORDER);
            viewModel.GetJobOrdersIssuedChilds(USER_ID,DEVICE_SERIAL_NO, jobOrder.getEntitiyId());
        }
    }

//    @Override
//    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
//        getActivity().runOnUiThread(()->{
//            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
//            if (selectedPosition!=-1){
////                binding.basketCode.getEditText().setText(scannedText);
//                LstIssuedChildParameter issuedChild = issuedChildList.get(selectedPosition);
////                issuedChild.setBasketCode(scannedText);
////                issuedChildList.remove(selectedPosition);
////                issuedChildList.add(selectedPosition,issuedChild);
////                adapter.notifyItemChanged(selectedPosition);
////                if (Integer.parseInt(issuedChild.getQuantitYISSUED())>0)
////                    if (issuedChild.getBasketCode()==null)
////                        viewModel.TransferIssuedChildToBasket(USER_ID,DEVICE_SERIAL_NO, jobOrder.getEntitiyId(), scannedText, ppr.getPprparentId(), issuedChild.getChilDITEMID());
////                    else
////                        warningDialog(getContext(),getString(R.string.this_child_has_already_added_to_basket));
////                else
////                    warningDialog(getContext(),getString(R.string.there_is_no_issued_qty_for_this_child));
//            }
//        });
//    }
//
//    @Override
//    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {
//
//    }
//
//    @Override
//    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {
//        barCodeReader.onTrigger(triggerStateChangeEvent);
//    }

    @Override
    public void onResume() {
        super.onResume();
//        barCodeReader.onResume();
    }
    private int selectedPosition = -1 ;
    @Override
    public void onItemClicked(LstIssuedChildParameter childParameter,int position) {
        if (Integer.parseInt(childParameter.getQuantitYISSUED())>0) {
            selectedPosition = position;
//        if (issuedChildList.get(position).getBasketCode()!=null)
//            binding.basketCode.getEditText().setText(issuedChildList.get(position).getBasketCode());
//        else
//            binding.basketCode.getEditText().setText("");
//        binding.basketCode.setVisibility(View.VISIBLE);
            Bundle bundle = new Bundle();
            bundle.putParcelable(PPR,ppr);
            bundle.putParcelable(JOB_ORDER,jobOrder);
            bundle.putParcelable(ISSUED_CHILD,issuedChildList.get(position));
            Navigation.findNavController(getView()).navigate(R.id.action_child_to_basket_fragment_to_child_to_basket_bottom_sheet,bundle);
        } else {
            warningDialog(getContext(),getString(R.string.there_is_no_issued_qty_for_this_child));
        }

    }
    private List<String> basketCodes = new ArrayList<>();

}