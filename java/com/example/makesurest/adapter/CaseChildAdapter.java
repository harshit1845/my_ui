package com.example.makesurest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makesurest.R;
import com.example.makesurest.databinding.AdapterCaseChildBinding;

import java.util.ArrayList;
import java.util.List;


public class CaseChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    List<String> qrCodes = new ArrayList<>();
    AdapterCaseChildBinding binding;


    public CaseChildAdapter(Context context, List<String> qrCodes) {
        this.context = context;
        this.qrCodes = qrCodes;
    }

    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        AdapterCaseChildBinding binding = DataBindingUtil.inflate(inflater, R.layout.adapter_case_child,group,false);
        return new ViewHolder(binding);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(LayoutInflater.from(context),parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(qrCodes.get(position));
    }

    @Override
    public int getItemCount() {
        return qrCodes.size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        AdapterCaseChildBinding binding;

        public ViewHolder(AdapterCaseChildBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void setData(String data){
            if (data!=null){
                binding.qrCodeTv.setText(data);
            }


        }

    }
}