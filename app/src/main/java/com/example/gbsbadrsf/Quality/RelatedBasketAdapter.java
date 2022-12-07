package com.example.gbsbadrsf.Quality;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.databinding.RelatedBasketItemBinding;

import java.util.List;

public class RelatedBasketAdapter extends RecyclerView.Adapter<RelatedBasketAdapter.RelatedBasketViewHolder> {
    private List<Basket> baskets;
    private boolean isBulk;

    @NonNull
    @Override
    public RelatedBasketAdapter.RelatedBasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RelatedBasketItemBinding binding = RelatedBasketItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new RelatedBasketAdapter.RelatedBasketViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedBasketAdapter.RelatedBasketViewHolder holder, int position) {
        Basket basket = baskets.get(position);
        holder.binding.basketCode.setText(basket.getBasketCode());
        holder.binding.qty.setText(String.valueOf(basket.getQty()));
        if (position>0) {
            if (isBulk) {
                holder.binding.line.setVisibility(View.GONE);
                holder.binding.qty.setVisibility(View.GONE);
            } else {
                holder.binding.line.setVisibility(View.VISIBLE);
                holder.binding.qty.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return baskets==null?0: baskets.size();
    }

    public void setBaskets(List<Basket> baskets) {
        this.baskets = baskets;
        notifyDataSetChanged();
    }

    public boolean isBulk() {
        return isBulk;
    }

    public void setBulk(boolean bulk) {
        isBulk = bulk;
    }

    static class RelatedBasketViewHolder extends RecyclerView.ViewHolder {
        private RelatedBasketItemBinding binding;
        public RelatedBasketViewHolder(@NonNull RelatedBasketItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}