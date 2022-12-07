package com.example.gbsbadrsf.Stoppages.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Stoppages.Model.Stoppage;
import com.example.gbsbadrsf.databinding.StoppageItemBinding;

import java.util.List;

public class StoppageListAdapter extends RecyclerView.Adapter<StoppageListAdapter.StoppageViewHolder> {
    private List<Stoppage> stoppages;
    @NonNull
    @Override
    public StoppageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StoppageItemBinding binding = StoppageItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new StoppageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StoppageViewHolder holder, int position) {
        Stoppage stoppage = stoppages.get(position);
        String location = stoppage.getFactoryName()+", "+stoppage.getWorkCenterName()+", "+stoppage.getProductionLineName()+", "+stoppage.getProductionSubLineName();
        if (stoppage.getIsMachine())
            location = location+ ", "+stoppage.getMachineFamilyName()+", "+stoppage.getMachineName()+", "+stoppage.getDieName()+", "+stoppage.getJigName();
            else
            location = location+ ", "+stoppage.getProductionStationName();
            holder.binding.stoppageName.setText(stoppage.getStoppagesNameName());
        holder.binding.stoppageLocation.setText(location.replace(",  ",""));
        holder.binding.stoppageStartDateTime.setText(stoppage.getStoppageStartDateTime());
    }

    @Override
    public int getItemCount() {
        return stoppages!=null? stoppages.size() : 0;
    }

    public void setStoppages(List<Stoppage> stoppages) {
        this.stoppages = stoppages;
        notifyDataSetChanged();
    }

    static class StoppageViewHolder extends RecyclerView.ViewHolder {
        private StoppageItemBinding binding;
        public StoppageViewHolder(@NonNull StoppageItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
