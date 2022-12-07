package com.example.gbsbadrsf.Paint;

import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentPaintMenuBinding;


public class PaintMenuFragment extends Fragment {
FragmentPaintMenuBinding binding;

    public PaintMenuFragment() {
        // Required empty public constructor
    }


    public static PaintMenuFragment newInstance(String param1, String param2) {
        PaintMenuFragment fragment = new PaintMenuFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaintMenuBinding.inflate(inflater,container,false);
        attachListeners();
        return binding.getRoot();
    }

    private void attachListeners() {
        binding.machineloadingBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_paintMenuFragment_to_paintdstation);

        });
        binding.paintwipBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_paintMenuFragment_to_paintwipFragment);

        });
        binding.colorverificationBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_paintMenuFragment_to_colorverificationFragment);

        });
        binding.productionrepairBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_paint_menu_fragment_to_fragment_paint_production_repair);

        });
        binding.rejectionRequestBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_paint_menu_fragment_to_fragment_paint_rejection_request);

        });

        binding.paintSignOff.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_paintMenuFragment_to_ppr_wip_paint);

        });
        binding.approvalRejectionRequest.setOnClickListener(v->{
            Navigation.findNavController(getView()).navigate(R.id.action_fragment_paint_production_to_fragment_paint_rejection_requests_list);
        });
        binding.addWorkers.setOnClickListener(__->Navigation.findNavController(getView()).navigate(R.id.action_paintMenuFragment_to_fragment_add_station_workers));

    }

    @Override
    public void onResume() {
        super.onResume();
        changeTitle(getString(R.string.paint),(MainActivity) getActivity());
    }
}