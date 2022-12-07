package com.example.gbsbadrsf.AddWorkers;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.databinding.WorkerItemLayoutBinding;

import java.util.List;

public class SelectedWorkersAdapter extends RecyclerView.Adapter<SelectedWorkersAdapter.WorkerViewHolder> {
    private List<Worker> workersList;
    private WorkerItemDeleted workerItemDeleted;

    public SelectedWorkersAdapter(List<Worker> workersList) {
        this.workersList = workersList;
    }


    @NonNull
    @Override
    public WorkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WorkerItemLayoutBinding binding = WorkerItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new WorkerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerViewHolder holder, int position) {
        Worker worker = workersList.get(position);
        holder.binding.workerName.setText(worker.getWorkerArName());
        holder.binding.workerCode.setText(worker.getWorkerCode());
    }

    @Override
    public int getItemCount() {
        return workersList==null?0: workersList.size();
    }

    public void removeItem(int position) {
        workersList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Worker item, int position) {
        workersList.add(position, item);
        notifyItemInserted(position);
    }

    static class WorkerViewHolder extends RecyclerView.ViewHolder {
        WorkerItemLayoutBinding binding;
        public WorkerViewHolder(@NonNull WorkerItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    interface WorkerItemDeleted{
        void OnWorkerItemDeleted (Worker worker);
    }
}
