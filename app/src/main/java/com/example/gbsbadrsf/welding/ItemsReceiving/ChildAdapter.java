package com.example.gbsbadrsf.welding.ItemsReceiving;

import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Model.IssuedChild;
import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.ChildItemBinding;

import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ChildViewHolder> {
    private List<LstIssuedChildParameter> issuedChildList;
    private onChildItemClicked onChildItemClicked;
    private Context context;

    public ChildAdapter(ChildAdapter.onChildItemClicked onChildItemClicked,Context context) {
        this.onChildItemClicked = onChildItemClicked;
        this.context = context;
    }

    public void setIssuedChildList(List<LstIssuedChildParameter> issuedChildList) {
        this.issuedChildList = issuedChildList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChildItemBinding binding = ChildItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ChildViewHolder(binding);
    }
    private int selectedPosition = -1;
    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
        int currentPosition = position;
        LstIssuedChildParameter child = issuedChildList.get(currentPosition);
        holder.binding.childDesc.setText(child.getChilDITEMDESC());
        holder.binding.qty.setText(child.getQuantitYISSUED());
//        if (child.getBasketCode()!=null)
//            holder.binding.basketCode.setText(child.getBasketCode());
//        else
//            holder.binding.basketCode.setText("....");
//        if (selectedPosition == currentPosition)
//            MyMethods.activateItem(holder.itemView);
//        else
//            MyMethods.deactivateItem(holder.itemView);
        holder.itemView.setOnClickListener(v -> {
//            selectedPosition = currentPosition;
//            notifyDataSetChanged();
            if (Integer.parseInt(child.getQuantitYISSUED()) > 0) {
                if (child.getIsIssued()) {
                    warningDialog(context,context.getString(R.string.child_is_already_put_in_baskets));
                } else {
                    onChildItemClicked.onItemClicked(child, position);
                }
            } else {
                warningDialog(context,context.getString(R.string.there_is_no_issued_qty_for_this_child));
            }
        });
        if (Integer.parseInt(child.getQuantitYISSUED()) > 0) {
            if (child.getIsIssued()) {
                setSavedChild(holder.binding);
            } else {
                setUnsavedChild(holder.binding);
            }
        } else {
            setNotIssuedChild(holder.binding);
        }
    }

    private void setUnsavedChild(ChildItemBinding binding) {
        binding.childDesc.setTextColor(context.getResources().getColor(R.color.done));
        binding.qty.setTextColor(context.getResources().getColor(R.color.done));
        binding.background.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        binding.seperator.setBackgroundColor(context.getResources().getColor(R.color.disable));
    }

    private void setSavedChild(ChildItemBinding binding) {
        binding.childDesc.setTextColor(context.getResources().getColor(R.color.white));
        binding.qty.setTextColor(context.getResources().getColor(R.color.white));
        binding.background.setCardBackgroundColor(context.getResources().getColor(R.color.done));
        binding.seperator.setBackgroundColor(context.getResources().getColor(R.color.white));
    }

    private void setNotIssuedChild(ChildItemBinding binding) {
        binding.childDesc.setTextColor(context.getResources().getColor(R.color.red));
        binding.qty.setTextColor(context.getResources().getColor(R.color.red));
        binding.background.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        binding.seperator.setBackgroundColor(context.getResources().getColor(R.color.disable));
    }


    @Override
    public int getItemCount() {
        return issuedChildList==null?0: issuedChildList.size();
    }

    static class ChildViewHolder extends RecyclerView.ViewHolder {
        private ChildItemBinding binding;
        public ChildViewHolder(@NonNull ChildItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public interface onChildItemClicked {
        void onItemClicked(LstIssuedChildParameter childParameter,int position);
    }
}
