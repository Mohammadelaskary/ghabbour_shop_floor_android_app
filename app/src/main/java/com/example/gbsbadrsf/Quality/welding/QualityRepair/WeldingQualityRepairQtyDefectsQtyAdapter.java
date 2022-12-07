package com.example.gbsbadrsf.Quality.welding.QualityRepair;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Model.QtyDefectsQtyDefected;
import com.example.gbsbadrsf.Quality.Data.WeldingDefect;
import com.example.gbsbadrsf.Quality.paint.Model.PaintingDefect;
import com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.QtyChildQtyDefectItemBinding;

import java.util.ArrayList;
import java.util.List;

public class WeldingQualityRepairQtyDefectsQtyAdapter extends RecyclerView.Adapter<WeldingQualityRepairQtyDefectsQtyAdapter.WeldingQualityRepairQtyDefectsQtyViewHolder> {
    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList;
    List<WeldingDefect> defectsWeldingList;
    private OnDefectItemClicked onDefectItemClicked;

    @NonNull
    @Override
    public WeldingQualityRepairQtyDefectsQtyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QtyChildQtyDefectItemBinding binding = QtyChildQtyDefectItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new WeldingQualityRepairQtyDefectsQtyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeldingQualityRepairQtyDefectsQtyViewHolder holder, int position) {
        QtyDefectsQtyDefected qtyDefectsQtyDefected = qtyDefectsQtyDefectedList.get(position);
        int defectId = qtyDefectsQtyDefected.getDefectId();
        int defectedQty = qtyDefectsQtyDefected.getDefectedQty();
        int defectsQty = qtyDefectsQtyDefected.getDefectsQty();
        holder.binding.defectsQty.setText(String.valueOf(defectsQty));
        holder.binding.defectedQty.setText(String.valueOf(defectedQty));
        holder.itemView.setOnClickListener(v -> {
            ArrayList<WeldingDefect> selectedDefectsWelding = new ArrayList<>();
            for (WeldingDefect defectsWelding : defectsWeldingList) {
                if (defectsWelding.getDefectGroupId() == defectId) {
                    selectedDefectsWelding.add(defectsWelding);
                }
            }
            onDefectItemClicked.onDefectItemClicked(selectedDefectsWelding);
        });

    }

    @Override
    public int getItemCount() {
        return qtyDefectsQtyDefectedList == null ? 0 : qtyDefectsQtyDefectedList.size();
    }

    public void setQtyDefectsQtyDefectedList(List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList) {
        this.qtyDefectsQtyDefectedList = qtyDefectsQtyDefectedList;
        notifyDataSetChanged();
    }

    public void setDefectsWeldingList(List<WeldingDefect> defectsWeldingList) {
        this.defectsWeldingList = defectsWeldingList;
    }

    public void setOnDefectItemClicked(OnDefectItemClicked onDefectItemClicked) {
        this.onDefectItemClicked = onDefectItemClicked;
    }

    static class WeldingQualityRepairQtyDefectsQtyViewHolder extends RecyclerView.ViewHolder {
        QtyChildQtyDefectItemBinding binding;

        public WeldingQualityRepairQtyDefectsQtyViewHolder(@NonNull QtyChildQtyDefectItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public interface OnDefectItemClicked {
        void onDefectItemClicked(ArrayList<WeldingDefect> defectList);
    }

}