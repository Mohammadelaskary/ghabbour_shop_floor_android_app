package com.example.gbsbadrsf.Quality.paint;

import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentQualityPaintMainBinding;
import com.example.gbsbadrsf.databinding.FragmentQualityweldingBinding;


public class QualityPaintFragment extends Fragment {
    FragmentQualityPaintMainBinding binding;




    public QualityPaintFragment() {
        // Required empty public constructor
    }


    public static QualityPaintFragment newInstance() {
        return new QualityPaintFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQualityPaintMainBinding.inflate(inflater,container,false);
        attachListeners();
        return binding.getRoot();

    }

    private void attachListeners() {
        binding.qualityOperationBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_fragment_paint_quality_to_fragment_paint_quality_ppr_list);

        });
        binding.qualityRepairBtn.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_fragment_paint_quality_to_fragment_paint_quality_repair);

        });
        binding.qualityDecisionBtn.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_fragment_paint_quality_to_fragment_paint_quality_sign_off);

        });
//        binding.rejectionRequestsListBtn.setOnClickListener(v -> {
//
//            Navigation.findNavController(v).navigate(R.id.action_painting_menu_fragment_to_approval_rejection_requests_list);
//
//        });
        binding.rejectionRequestBtn.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_fragment_paint_quality_to_fragment_paint_rejection_request);

        });
        binding.randomQualityInspection.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_fragment_paint_quality_to_fragment_online_inspection_ppr_list);

        });
        binding.declineRejectionRequest.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_fragment_paint_quality_to_decline_rejection_request_decision_fragment);

        });
        if (MainActivity.userInfo.getProductionControl()){
            binding.qualityOperationBtn.setEnabled(false);
            binding.qualityRepairBtn.setEnabled(false);
            binding.declineRejectionRequest.setEnabled(false);
            binding.rejectionRequestsListBtn.setEnabled(true);
            binding.rejectionRequestBtn.setEnabled(false);
            binding.randomQualityInspection.setEnabled(false);
        } else {
            binding.qualityOperationBtn.setEnabled(true);
            binding.qualityRepairBtn.setEnabled(true);
            binding.declineRejectionRequest.setEnabled(true);
            binding.rejectionRequestsListBtn.setEnabled(false);
            binding.rejectionRequestBtn.setEnabled(true);
            binding.randomQualityInspection.setEnabled(true);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        changeTitle(getString(R.string.paint),(MainActivity) getActivity());
    }
}