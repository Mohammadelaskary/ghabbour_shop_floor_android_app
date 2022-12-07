package com.example.gbsbadrsf.Quality.welding;

import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentQualityweldingBinding;


public class QualityWeldingFragment extends Fragment {
    FragmentQualityweldingBinding binding;




    public QualityWeldingFragment() {
        // Required empty public constructor
    }


    public static QualityWeldingFragment newInstance(String param1, String param2) {
        QualityWeldingFragment fragment = new QualityWeldingFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQualityweldingBinding.inflate(inflater,container,false);
        attachListeners();
        return binding.getRoot();

    }

    private void attachListeners() {
        binding.qualityOperationBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_quality_welding_fragment_to_welding_quality_operation);

        });
        binding.qualityRepairBtn.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_quality_welding_to_fragment_fragment_welding_quality_repair);

        });
        binding.qualityDecisionBtn.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_quality_welding_fragment_to_fragment_welding_quality_sign_off);

        });
//        binding.rejectionRequestsListBtn.setOnClickListener(v -> {
//
//            Navigation.findNavController(v).navigate(R.id.action_welding_menu_fragment_to_approval_rejection_requests_list);
//
//        });
        binding.rejectionRequestBtn.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_quality_welding_fragment_to_fragment_welding_rejection_request);

        });
        binding.randomQualityInspection.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_fragment_welding_quality_to_fragment_random_quality_inception);

        });
        binding.declineRejectionRequest.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_fragment_welding_quality_to_decline_rejection_request_decision_fragment);

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
        changeTitle(getString(R.string.welding),(MainActivity) getActivity());
    }
}