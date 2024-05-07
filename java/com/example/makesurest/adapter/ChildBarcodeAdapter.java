package com.example.makesurest.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makesurest.R;
import com.example.makesurest.databinding.BatchItemBinding;
import com.example.makesurest.databinding.ChildDataBinding;
import com.example.makesurest.model.BarcodeTracingResponse;
import com.example.makesurest.model.BatchResponse;
import com.example.makesurest.model.ChildData;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class ChildBarcodeAdapter extends RecyclerView.Adapter<ChildBarcodeAdapter.UserViewHolder>{

    private List<ChildData> childSerialNumbers;


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChildDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.child_data, parent, false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        ChildData childSerialNumber = childSerialNumbers.get(position);
        holder.bind(childSerialNumber);


    }

    @Override
    public int getItemCount() {
        if (childSerialNumbers != null) {
            return childSerialNumbers.size();
        } else {
            return 0;
        }
    }

    public void setUserList(List<ChildData> childSerialNumbers) {
        this.childSerialNumbers = childSerialNumbers;
        notifyDataSetChanged();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private ChildDataBinding itemUserBinding;


        public UserViewHolder(@NonNull ChildDataBinding itemUserBinding) {
            super(itemUserBinding.getRoot());
            this.itemUserBinding = itemUserBinding;
        }

        public void bind(ChildData childSerialNumber) {
            itemUserBinding.setChildSerialNumber(childSerialNumber);
        }
    }


}