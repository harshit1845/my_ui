package com.example.makesurest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makesurest.R;
import com.example.makesurest.model.ChildData;

import java.util.List;

public class ChildDataAdapter extends RecyclerView.Adapter<ChildDataAdapter.ChildDataViewHolder> {

    private List<ChildData> childDataList;

    public ChildDataAdapter(List<ChildData> childDataList) {
        this.childDataList = childDataList;
    }

    @NonNull
    @Override
    public ChildDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the child item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_barcode_list_itm, parent, false);
        return new ChildDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildDataViewHolder holder, int position) {
        ChildData childData = childDataList.get(position);

        // Bind child data to view holder elements
        holder.childSerialNoTextView.setText(childData.getChildSerialNo());
        holder.childProductNameTextView.setText(childData.getChildProductName());
        // ... set other child data fields
    }

    @Override
    public int getItemCount() {
        return childDataList.size();
    }

    public class ChildDataViewHolder extends RecyclerView.ViewHolder {

        TextView childSerialNoTextView, childProductNameTextView; // ... other child data fields

        public ChildDataViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize view holder elements
            childSerialNoTextView = itemView.findViewById(R.id.productName);
            childProductNameTextView = itemView.findViewById(R.id.unit_name);
            // ... initialize other child data fields
        }
    }
}
