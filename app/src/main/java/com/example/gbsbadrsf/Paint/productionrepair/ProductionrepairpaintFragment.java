package com.example.gbsbadrsf.Paint.productionrepair;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.Productionrepaircustomdialog;
import com.example.gbsbadrsf.Productionrepairpaintcustomdialog;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.OnClick;
import com.example.gbsbadrsf.databinding.FragmentProductionrepairpaintBinding;
import com.example.gbsbadrsf.databinding.FragmentProductionrepairweBinding;
import com.example.gbsbadrsf.databinding.ProductionrepairpaintdialogBinding;
import com.example.gbsbadrsf.productionsequence.SimpleDividerItemDecoration;


public class ProductionrepairpaintFragment extends Fragment implements OnClick {


    FragmentProductionrepairpaintBinding fragmentProductionrepairpaintBinding;
    ProductionrepairpaintAdapter productionrepairpaintAdapter;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentProductionrepairpaintBinding = FragmentProductionrepairpaintBinding.inflate(inflater, container, false);

        return fragmentProductionrepairpaintBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        recyclerView = fragmentProductionrepairpaintBinding.qcnotesRv;
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
    }

    private void initViews() {

        productionrepairpaintAdapter = new ProductionrepairpaintAdapter(this);
        fragmentProductionrepairpaintBinding.qcnotesRv.setAdapter(productionrepairpaintAdapter);
        fragmentProductionrepairpaintBinding.qcnotesRv.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public void OnItemClickedListener(int position) {
        Productionrepairpaintcustomdialog mycustomDialog=new Productionrepairpaintcustomdialog();
        mycustomDialog.show(getChildFragmentManager(),"repaircustomdialoginpaint");


    }
}
