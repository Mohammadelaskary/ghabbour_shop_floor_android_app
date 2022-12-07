package com.example.gbsbadrsf.Stoppages;

import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;

import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Stoppages.Adapter.StoppageListAdapter;
import com.example.gbsbadrsf.databinding.FragmentStoppagesListBinding;

public class StoppagesListFragment extends Fragment {

    private StoppagesListViewModel viewModel;

    public static StoppagesListFragment newInstance() {
        return new StoppagesListFragment();
    }

    private FragmentStoppagesListBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentStoppagesListBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    private ProgressDialog progressDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(StoppagesListViewModel.class);
        progressDialog = loadingProgressDialog(getActivity());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.GetStoppagesList();
        setUpRecyclerView();
        observeStoppagesList();
        observeStoppagesListStatus();
    }
    private StoppageListAdapter adapter;
    private void setUpRecyclerView() {
        adapter = new StoppageListAdapter();
        binding.stoppagesList.setAdapter(adapter);
    }

    private void observeStoppagesListStatus() {
        viewModel.getStoppagesListStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    progressDialog.show();
                    break;
                case SUCCESS:
                    progressDialog.hide();
                    break;
                case ERROR:
                    warningDialog(getContext(),getString(R.string.network_issue));
                    break;
            }
        });
    }

    private void observeStoppagesList() {
        viewModel.getStoppagesList().observe(getViewLifecycleOwner(),response ->{
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()){
                    adapter.setStoppages(response.getStoppages());
                } else warningDialog(getContext(),statusMessage);
            } else warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        changeTitle(getString(R.string.view_stoppages), (MainActivity) getActivity());
    }
}