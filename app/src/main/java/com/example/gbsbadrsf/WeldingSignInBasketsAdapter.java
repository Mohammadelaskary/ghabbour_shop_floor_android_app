package com.example.gbsbadrsf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Paint.Basket;
import com.example.gbsbadrsf.data.response.Baskets;
import com.example.gbsbadrsf.databinding.SignInBasketItemBinding;

import java.util.ConcurrentModificationException;
import java.util.List;

public class WeldingSignInBasketsAdapter extends RecyclerView.Adapter<WeldingSignInBasketsAdapter.WeldingSignInBasketViewHolder> {
    private List<Baskets> baskets;
    private List<String> addedBasketsCodes;
    private Context context;

    public WeldingSignInBasketsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public WeldingSignInBasketsAdapter.WeldingSignInBasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SignInBasketItemBinding binding = SignInBasketItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new WeldingSignInBasketsAdapter.WeldingSignInBasketViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeldingSignInBasketsAdapter.WeldingSignInBasketViewHolder holder, int position) {
        Baskets basket = baskets.get(position);
        holder.binding.basketCode.setText(basket.getBasketCode());
        holder.binding.qty.setText(basket.getQty().toString());
        if (addedBasketsCodes.contains(basket.getBasketCode())){
            holder.binding.basketCode.setTextColor(context.getResources().getColor(R.color.done));
        } else {
            holder.binding.basketCode.setTextColor(context.getResources().getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return baskets==null?0: baskets.size();
    }

    public void setBaskets(List<Baskets> baskets) {
        this.baskets = baskets;
        notifyDataSetChanged();
    }

    public void setAddedBaskets(List<String> basketsCodes) {
        this.addedBasketsCodes = basketsCodes;
        notifyDataSetChanged();
    }

    static class WeldingSignInBasketViewHolder extends RecyclerView.ViewHolder {
        private SignInBasketItemBinding binding;
        public WeldingSignInBasketViewHolder(@NonNull SignInBasketItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

