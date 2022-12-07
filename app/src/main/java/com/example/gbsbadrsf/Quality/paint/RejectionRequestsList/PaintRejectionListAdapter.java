package com.example.gbsbadrsf.Quality.paint.RejectionRequestsList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Quality.paint.Model.RejectionRequest;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.ProductionscrapLstBinding;

import java.util.List;

public class PaintRejectionListAdapter extends RecyclerView.Adapter<PaintRejectionListAdapter.ProductionRejectionListViewHolder> {
    ProductionscrapLstBinding binding;
    List<RejectionRequest> rejectionRequests;


    public PaintRejectionListAdapter() {
    }

    @NonNull
    @Override
    public ProductionRejectionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ProductionscrapLstBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ProductionRejectionListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductionRejectionListViewHolder holder, int position) {
        RejectionRequest rejectionRequest = rejectionRequests.get(position);
        String responsibleDepartment = rejectionRequest.getDepartmentEnName();
        int rejectedQty = rejectionRequest.getRejectionQty();
        String jobOrderName = rejectionRequest.getJobOrderName();
        holder.binding.parentDesc.setText(rejectionRequest.getParentDescription());
        holder.binding.rejectedQtn.setText(String.valueOf(rejectedQty));
        holder.binding.department.setText(responsibleDepartment);
        holder.binding.jobOrderName.setText(jobOrderName);
        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("rejectionRequest",rejectionRequest);
            Navigation.findNavController(view).navigate(R.id.action_fragment_paint_rejection_requests_list_to_fragment_paint_rejection_request_details,bundle);
        });
    }

    @Override
    public int getItemCount() {
        return rejectionRequests==null?0: rejectionRequests.size();
    }

    public void setRejectionRequests(List<RejectionRequest> rejectionRequests) {
        this.rejectionRequests = rejectionRequests;
        notifyDataSetChanged();
    }

    static class ProductionRejectionListViewHolder extends RecyclerView.ViewHolder{
        ProductionscrapLstBinding binding;
        public ProductionRejectionListViewHolder(@NonNull ProductionscrapLstBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
