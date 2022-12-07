package com.example.gbsbadrsf.welding;

import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentWeldingMenuBinding;


public class WeldingMenuFragment extends Fragment {
FragmentWeldingMenuBinding binding;


    public WeldingMenuFragment() {
        // Required empty public constructor
    }


    public static WeldingMenuFragment newInstance(String param1, String param2) {
        WeldingMenuFragment fragment = new WeldingMenuFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeldingMenuBinding.inflate(inflater,container,false);
        attachListeners();
        return binding.getRoot();

    }

    private void attachListeners() {
        binding.machineloadingBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_weldingMenuFragment_to_welding_production_sequence);

        });
        binding.machinesignoffBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_weldingMenuFragment_to_signoffweFragment);

        });
        binding.addWorkers.setOnClickListener(__->Navigation.findNavController(getView()).navigate(R.id.action_weldingMenuFragment_to_fragment_add_station_workers));
//        fragmentWeldingMenuBinding.baskettransferBtn.setOnClickListener(__ -> {
//
//            Navigation.findNavController(getView()).navigate(R.id.action_weldingMenuFragment_to_baskettransferweFragment);
//
//        });

        binding.weldingwipBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_weldingMenuFragment_to_weldingwip);

        });
        binding.rejectionRequestsList.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_fragment_quality_main_to_fragment_welding_rejection_requests_list);

        });
        binding.productionRepairBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_welding_menu_fragment_to_fragment_welding_production_repair);

        });
        binding.createRejectionRequest.setOnClickListener(__->{
            Navigation.findNavController(getView()).navigate(R.id.action_quality_welding_fragment_to_fragment_welding_rejection_request);
        });
        binding.itemsReceiving.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.action_quality_welding_fragment_to_items_receiving_fragment));
        binding.counting.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.action_quality_welding_fragment_to_fragment_welding_counting));
//        binding.approvalRejectionRequest.setOnClickListener(v->{
//            Navigation.findNavController(getView()).navigate(R.id.action_welding_menu_fragment_to_approval_rejection_requests_list);
//        });


    }

    @Override
    public void onResume() {
        super.onResume();
        changeTitle(getString(R.string.welding),(MainActivity)getActivity());
    }
}