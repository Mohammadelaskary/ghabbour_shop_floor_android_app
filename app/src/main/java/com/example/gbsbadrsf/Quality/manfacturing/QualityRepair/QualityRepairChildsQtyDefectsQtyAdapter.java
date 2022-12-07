package com.example.gbsbadrsf.Quality.manfacturing.QualityRepair;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.Model.ManufacturingDefect;
import com.example.gbsbadrsf.Model.QtyDefectsQtyDefected;
import com.example.gbsbadrsf.Quality.Data.DefectsManufacturing;
import com.example.gbsbadrsf.Quality.Data.WeldingDefect;
import com.example.gbsbadrsf.Quality.paint.Model.PaintingDefect;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.QtyChildQtyDefectItemBinding;

import java.util.ArrayList;
import java.util.List;

public class QualityRepairChildsQtyDefectsQtyAdapter extends RecyclerView.Adapter<QualityRepairChildsQtyDefectsQtyAdapter.QualityRepairChildsQtyDefectsQtyViewHolder> {
    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList;
    List<ManufacturingDefect> defectsManufacturingList;
    private OnDefectItemClicked onDefectItemClicked;
    @NonNull
    @Override
    public QualityRepairChildsQtyDefectsQtyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QtyChildQtyDefectItemBinding binding = QtyChildQtyDefectItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new QualityRepairChildsQtyDefectsQtyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull QualityRepairChildsQtyDefectsQtyViewHolder holder, int position) {
        QtyDefectsQtyDefected qtyDefectsQtyDefected = qtyDefectsQtyDefectedList.get(position);
        int defectManufacturingId = qtyDefectsQtyDefected.getDefectId();
        int defectedQty = qtyDefectsQtyDefected.getDefectedQty();
        int defectsQty  = qtyDefectsQtyDefected.getDefectsQty();
        holder.binding.defectsQty.setText(String.valueOf(defectsQty));
        holder.binding.defectedQty.setText(String.valueOf(defectedQty));
        holder.itemView.setOnClickListener(v -> {
            ArrayList<ManufacturingDefect> selectedDefectsManufacturing = new ArrayList<>();
            for (ManufacturingDefect defectsManufacturing:defectsManufacturingList){
                if (defectsManufacturing.getDefectGroupId()==defectManufacturingId){
                    selectedDefectsManufacturing.add(defectsManufacturing);
                }
            }
            onDefectItemClicked.onDefectItemClicked(selectedDefectsManufacturing);
        });

    }

    @Override
    public int getItemCount() {
        return qtyDefectsQtyDefectedList==null?0: qtyDefectsQtyDefectedList.size();
    }

    static class QualityRepairChildsQtyDefectsQtyViewHolder extends RecyclerView.ViewHolder{
        QtyChildQtyDefectItemBinding binding;
        public QualityRepairChildsQtyDefectsQtyViewHolder(@NonNull QtyChildQtyDefectItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setQtyDefectsQtyDefectedList(List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList) {
        this.qtyDefectsQtyDefectedList = qtyDefectsQtyDefectedList;
        notifyDataSetChanged();
    }

    public void setDefectsManufacturingList(List<ManufacturingDefect> defectsManufacturingList) {
        this.defectsManufacturingList = defectsManufacturingList;
    }

    public void setOnDefectItemClicked(OnDefectItemClicked onDefectItemClicked) {
        this.onDefectItemClicked = onDefectItemClicked;
    }

    public interface OnDefectItemClicked {
        void onDefectItemClicked(ArrayList<ManufacturingDefect> defectList);
    }
}
