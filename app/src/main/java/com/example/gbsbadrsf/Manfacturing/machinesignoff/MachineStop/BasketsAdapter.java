package com.example.gbsbadrsf.Manfacturing.machinesignoff.MachineStop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Manfacturing.machinesignoff.Basketcodelst;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.OnBasketRemoved;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.ProductionSignoffAdapter;
import com.example.gbsbadrsf.databinding.BasketcodeLstBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BasketsAdapter extends RecyclerView.Adapter<BasketsAdapter.ProductionSignoffViewHolder> {
    public List<Basket> Basketcodelst;
    OnBasketRemoved onBasketRemoved;
    private boolean isBulk;
    public BasketsAdapter(List<Basket> basketcodelst,OnBasketRemoved onBasketRemoved,boolean isBulk) {
        this.Basketcodelst = basketcodelst;
        this.onBasketRemoved = onBasketRemoved;
        this.isBulk = isBulk;
    }

    public void setBulk(boolean bulk) {
        isBulk = bulk;
        notifyDataSetChanged();
    }

    public List<Basket> getBasketcodelst() {
        return Basketcodelst;
    }

    public void setBasketcodelst(List<Basket> basketcodelst) {
        Basketcodelst = basketcodelst;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public BasketsAdapter.ProductionSignoffViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        BasketcodeLstBinding basketcodeLstBinding = BasketcodeLstBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new BasketsAdapter.ProductionSignoffViewHolder(basketcodeLstBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BasketsAdapter.ProductionSignoffViewHolder holder, int position) {
        if (isBulk) {
            holder.basketQty.setVisibility(View.GONE);
        } else {
            holder.basketQty.setText(String.valueOf(Basketcodelst.get(position).getQty()));
            holder.basketQty.setVisibility(View.VISIBLE);
        }
        holder.basketname.setText(Basketcodelst.get(position).getBasketCode());
        holder.delete.setOnClickListener(__->{
//            Basketcodelst.remove(position);
//            notifyItemChanged(position);
            onBasketRemoved.onBasketRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return Basketcodelst.size();
    }

    class ProductionSignoffViewHolder extends RecyclerView.ViewHolder{

        TextView basketname,basketQty;
        ImageButton delete;
        public ProductionSignoffViewHolder(@NonNull BasketcodeLstBinding itemView) {
            super(itemView.getRoot());
            basketname=itemView.basketcode;
            basketQty = itemView.basketQty;
            delete = itemView.delete;
        }

    }

}
