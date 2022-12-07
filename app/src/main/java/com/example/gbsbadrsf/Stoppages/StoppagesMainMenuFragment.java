package com.example.gbsbadrsf.Stoppages;

import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentStoppagesMainMenuBinding;

public class StoppagesMainMenuFragment extends Fragment {

    private StoppagesMainMenuViewModel viewModel;

    public static StoppagesMainMenuFragment newInstance() {
        return new StoppagesMainMenuFragment();
    }

    private FragmentStoppagesMainMenuBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentStoppagesMainMenuBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(StoppagesMainMenuViewModel.class);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.addStoppage.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.action_fragment_stoppages_main_menu_to_fragment_add_stoppage));
        binding.viewStoppages.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.action_fragment_stoppages_main_menu_to_fragment_stoppages_list));
        binding.machineStop.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.action_fragment_stoppages_main_menu_to_machine_hold));
    }

    @Override
    public void onResume() {
        super.onResume();
        changeTitle(getString(R.string.stoppages),(MainActivity)getActivity());
    }
}