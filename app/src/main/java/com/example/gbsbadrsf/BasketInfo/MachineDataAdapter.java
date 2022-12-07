package com.example.gbsbadrsf.BasketInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.MachineItemBasketWipBinding;

import java.util.List;

public class MachineDataAdapter extends RecyclerView.Adapter<MachineDataAdapter.MachineDataViewHolder> {
    List<BasketWipData> basketsWIPList;
    private Context context;
    public MachineDataAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MachineDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MachineItemBasketWipBinding binding = MachineItemBasketWipBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MachineDataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MachineDataViewHolder holder, int position) {
        BasketWipData basketsWIP = basketsWIPList.get(position);
        holder.binding.machineDesc.setText(basketsWIP.getMachineEnName());
        if (basketsWIP.getDieEnName()!=null)
            holder.binding.dieDesc.setText(basketsWIP.getDieEnName().toString());
        holder.binding.loadingQty.setText(basketsWIP.getPprLoadingQty().toString());
        if (basketsWIP.getSignOutQty()!=null) {
            holder.binding.signOffQty.setText(String.valueOf(basketsWIP.getBasketQty()));
//            holder.binding.status.setText(context.getString(R.string.done));
//            holder.binding.statusIcon.setImageResource(R.drawable.ic_done);
        } else {
            holder.binding.signOffQty.setText(R.string.pending);
//            holder.binding.status.setText(R.string.pending);
//            holder.binding.statusIcon.setImageResource(R.drawable.ic_pending);
        }
        holder.binding.operationDesc.setText(basketsWIP.getOperationEnName());
        holder.binding.operationTime.setText(basketsWIP.getOperationTime().toString());
        holder.binding.signInTime.setText(basketsWIP.getDateSignIn());
        if (basketsWIP.getSignOutQty()!=null)
            if (basketsWIP.getDateSignOut()!=null)
                holder.binding.signOffTime.setText(basketsWIP.getDateSignOut());
            else
                holder.binding.signOffTime.setText("");
        else
            holder.binding.signOffTime.setText(R.string.pending);
    }

    public void setBasketsWIPList(List<BasketWipData> basketsWIPList) {
        this.basketsWIPList = basketsWIPList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return basketsWIPList==null?0: basketsWIPList.size();
    }

    static class MachineDataViewHolder extends RecyclerView.ViewHolder{
        MachineItemBasketWipBinding binding;
        public MachineDataViewHolder(@NonNull MachineItemBasketWipBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
