package com.example.gbsbadrsf.ProductionRepair;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.Model.ManufacturingDefect;
import com.example.gbsbadrsf.Model.QtyDefectsQtyDefected;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.QtyChildQtyDefectItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ProductionRepairChildsQtyDefectsQtyAdapter extends RecyclerView.Adapter<ProductionRepairChildsQtyDefectsQtyAdapter.QtyChildQtyDefectItemViewHolder> {
    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList;
    List<ManufacturingDefect> defectsManufacturingList;
    LastMoveManufacturingBasket basketData;

    @Override
    public void onBindViewHolder(@NonNull QtyChildQtyDefectItemViewHolder holder, int position) {
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
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("selectedDefectsManufacturing",selectedDefectsManufacturing);
                Log.d("defectsListSizeBefore",defectsManufacturingList.size()+"");
                bundle.putParcelable("basketData",basketData);
                Navigation.findNavController(v).navigate(R.id.action_manufacturing_production_repair_to_fragment_manufacturing_production_defects_repair,bundle);
            });

    }


    @NonNull
    @Override
    public QtyChildQtyDefectItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QtyChildQtyDefectItemBinding binding = QtyChildQtyDefectItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new QtyChildQtyDefectItemViewHolder(binding);
    }


    @Override
    public int getItemCount() {
        return qtyDefectsQtyDefectedList==null?0: (qtyDefectsQtyDefectedList.size());
    }

    public void setQtyDefectsQtyDefectedList(List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList) {
        this.qtyDefectsQtyDefectedList = qtyDefectsQtyDefectedList;

        notifyDataSetChanged();
    }

    public void setDefectsManufacturingList(List<ManufacturingDefect> defectsManufacturingList) {
        this.defectsManufacturingList = defectsManufacturingList;
    }

    public void setBasketData(LastMoveManufacturingBasket basketData) {
        this.basketData = basketData;
    }

    static class QtyChildQtyDefectItemViewHolder extends RecyclerView.ViewHolder{
        QtyChildQtyDefectItemBinding binding;

        public QtyChildQtyDefectItemViewHolder(@NonNull QtyChildQtyDefectItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
