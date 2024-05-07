package com.example.makesurest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makesurest.R;
import com.example.makesurest.model.BatchResponse;

import java.util.ArrayList;
import java.util.List;

public class BatchResponseAdapter extends RecyclerView.Adapter<BatchResponseAdapter.MyViewHolder> implements Filterable {

    private List<String> items;
    private List<String> filteredItems;

    public BatchResponseAdapter(List<String> items) {
        this.items = items;
        this.filteredItems = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.batch_selection, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BatchResponseAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(filteredItems.get(position));
    }



    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint != null && constraint.length() > 0) {
                    List<String> filteredList = new ArrayList<>();
                    for (String item : items) {
                        if (item.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filteredList.add(item);
                        }
                    }

                    results.values = filteredList;
                    results.count = filteredList.size();
                } else {
                    results.values = items;
                    results.count = items.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredItems = (List<String>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public MyViewHolder(View view) {
            super(view);

//            textView = view.findViewById(R.id.batchNumber);
        }
    }
}