package com.example.gbsbadrsf.warhouse;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.ProductionMenuFragment;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentProductionMenuBinding;
import com.example.gbsbadrsf.databinding.FragmentWarehousesMainBinding;


public class WarehousesMain extends Fragment {
    FragmentWarehousesMainBinding fragmentWarehousesMainBinding;



@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    fragmentWarehousesMainBinding = FragmentWarehousesMainBinding.inflate(inflater,container,false);
           attachListeners();
          return fragmentWarehousesMainBinding.getRoot();



}

    private void attachListeners() {
        fragmentWarehousesMainBinding.closeManufacturingRejectionRequest.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_warehousesMain_to_fragment_close_manufacturing_rejection_request);

        });
        fragmentWarehousesMainBinding.closeWeldingRejectionRequest.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_warehousesMain_to_fragment_close_welding_rejection_request);

        });
        fragmentWarehousesMainBinding.closePaintRejectionRequest.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_warehousesMain_to_fragment_close_painting_rejection_request);

        });
        fragmentWarehousesMainBinding.warehouserecivingBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_warehousesMain_to_warehouseFragment);

        });




    }

    @Override
    public void onResume() {
        super.onResume();
        MyMethods.changeTitle(getString(R.string.warehouse),(MainActivity) getActivity());
    }
}