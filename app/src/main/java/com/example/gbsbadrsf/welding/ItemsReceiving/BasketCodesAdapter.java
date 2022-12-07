package com.example.gbsbadrsf.welding.ItemsReceiving;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.databinding.BasketCodeItemBinding;

import java.util.List;

public class BasketCodesAdapter extends RecyclerView.Adapter<BasketCodesAdapter.BasketCodesViewHolder> {
    private List<String> basketCodes;
    private boolean saved;
    private OnBasketCodeRemoved onBasketCodeRemoved;

    public BasketCodesAdapter(List<String> basketCodes,OnBasketCodeRemoved onBasketCodeRemoved) {
        this.basketCodes = basketCodes;
        this.onBasketCodeRemoved = onBasketCodeRemoved;
    }

    @NonNull
    @Override
    public BasketCodesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BasketCodeItemBinding binding = BasketCodeItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new BasketCodesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketCodesViewHolder holder, int position) {
        String basketCode = basketCodes.get(position);
        holder.binding.basketCode.setText(basketCode);
        holder.binding.remove.setOnClickListener(__->{
            basketCodes.remove(position);
            notifyDataSetChanged();
//            onBasketCodeRemoved.onBasketCodeRemoved(position);
        });
//        if (saved){
//            holder.binding.remove.setVisibility(View.GONE);
//        }
    }

//    public void setBasketCodes(List<String> basketCodes) {
//        this.basketCodes = basketCodes;
//        notifyDataSetChanged();
//    }
//
//    public void setSaved(boolean saved) {
//        this.saved = saved;
//        notifyDataSetChanged();
//    }

    @Override
    public int getItemCount() {
        return basketCodes==null?0: basketCodes.size();
    }

    static class BasketCodesViewHolder extends RecyclerView.ViewHolder{
        BasketCodeItemBinding binding;
        public BasketCodesViewHolder(@NonNull BasketCodeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnBasketCodeRemoved{
        void onBasketCodeRemoved(int position);

    }
}
