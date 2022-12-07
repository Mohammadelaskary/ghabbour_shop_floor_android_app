package com.example.gbsbadrsf.Quality.paint.RandomQualityInception.PprListFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Quality.paint.Model.LastMovePainting;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.PaintPprItemBinding;

import java.util.List;

public class OnlineInspectionPprAdapter extends RecyclerView.Adapter<OnlineInspectionPprAdapter.PaintSignOffPprViewHolder> {
    public static final String PAINT_PPR = "ppr_paint";
    private List<LastMovePaintingOnline> pprWipPaintList;
    private OnPprItemClicked onPprItemClicked;

    public OnlineInspectionPprAdapter(OnPprItemClicked onPprItemClicked) {
        this.onPprItemClicked = onPprItemClicked;
    }

    @NonNull
    @Override
    public PaintSignOffPprViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PaintPprItemBinding binding = PaintPprItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PaintSignOffPprViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PaintSignOffPprViewHolder holder, int position) {
        holder.binding.parentDesc.setText(pprWipPaintList.get(position).getParentDescription());
        holder.binding.jobOrderQty.setText(String.valueOf(pprWipPaintList.get(position).getJobOrderQty()));
        holder.binding.signOffQty.setText(String.valueOf(pprWipPaintList.get(position).getSignOffQty()));
        holder.itemView.setOnClickListener(v-> {
            onPprItemClicked.onPprItemClicked(pprWipPaintList.get(position).getPprLoadingId());
        });
    }

    @Override
    public int getItemCount() {
        return pprWipPaintList == null?0:pprWipPaintList.size();
    }

    public void setPprWipPaintList(List<LastMovePaintingOnline> pprWipPaintList) {
        this.pprWipPaintList = pprWipPaintList;
        notifyDataSetChanged();
    }

    static class PaintSignOffPprViewHolder extends RecyclerView.ViewHolder {
        private PaintPprItemBinding binding;
        public PaintSignOffPprViewHolder(@NonNull PaintPprItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    interface OnPprItemClicked {
        void onPprItemClicked(int pprLoadingId);
    }
}
