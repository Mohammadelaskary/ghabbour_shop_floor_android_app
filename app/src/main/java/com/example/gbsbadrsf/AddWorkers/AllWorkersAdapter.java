package com.example.gbsbadrsf.AddWorkers;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gbsbadrsf.databinding.WorkerItemLayoutBinding;
import java.util.ArrayList;
import java.util.List;

public class AllWorkersAdapter extends RecyclerView.Adapter<AllWorkersAdapter.WorkerViewHolder> implements Filterable {
    private List<Worker> workersList;
    private List<Worker> fullWorkersList ;
    private WorkerItemClicked workerItemClicked;

    public AllWorkersAdapter(WorkerItemClicked workerItemClicked) {
        this.workerItemClicked = workerItemClicked;
    }

    public void setWorkersList(List<Worker> workersList) {
        this.workersList = workersList;
        fullWorkersList = new ArrayList<>(workersList);
        notifyDataSetChanged();
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
        holder.itemView.setOnClickListener(v -> workerItemClicked.OnWorkerItemClicked(worker));
    }

    @Override
    public int getItemCount() {
        return workersList==null?0: workersList.size();
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if(charSequence == null | charSequence.length() == 0){
                    filterResults.count = fullWorkersList.size();
                    filterResults.values = fullWorkersList;

                }else{
                    String searchChr = charSequence.toString().toLowerCase();

                    List<Worker> resultData = new ArrayList<>();

                    for(Worker worker: fullWorkersList){
                        if(worker.getWorkerArName().toLowerCase().contains(searchChr)||worker.getWorkerCode().toLowerCase().contains(searchChr)){
                            resultData.add(worker);
                        }
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;

                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                workersList = (List<Worker>) filterResults.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }

    static class WorkerViewHolder extends RecyclerView.ViewHolder {
        WorkerItemLayoutBinding binding;
        public WorkerViewHolder(@NonNull WorkerItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    interface WorkerItemClicked{
        void OnWorkerItemClicked (Worker worker);
    }
}
