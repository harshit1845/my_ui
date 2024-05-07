package com.example.makesurest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makesurest.R;
import com.example.makesurest.databinding.BatchSelectionBinding;
import com.example.makesurest.databinding.BatchStatusSelectionBinding;
import com.example.makesurest.databinding.CompanyDialogBinding;
import com.example.makesurest.model.BatchResponse;
import com.example.makesurest.model.ResponseBatchs;
import com.example.makesurest.model.ResponseCompanyListModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class BatchStatusAdapter extends RecyclerView.Adapter<BatchStatusAdapter.UserViewHolder>{
    private List<BatchResponse> users;
    private List<BatchResponse> batchResponses;
    TextView bid;

    private OnItemClickListener listener;
    int compareValue = -1;
    @NonNull
    @Override
    public BatchStatusAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BatchStatusSelectionBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.batch_status_selection, parent, false);
        return new BatchStatusAdapter.UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BatchStatusAdapter.UserViewHolder holder, int position) {
        BatchResponse currentUser = users.get(position);
        holder.itemUserBinding.setUser(currentUser);


        String flag  =  currentUser.getFlag();



    }

    @Override
    public int getItemCount() {
        if (users != null) {
            return users.size();
        } else {
            return 0;
        }
    }

    public void setUserList(List<BatchResponse> users) {
        this.users = users;
        notifyDataSetChanged();
    }
    public BatchResponse getCurrentItemAt(int position) {
        return users.get(position);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private BatchStatusSelectionBinding itemUserBinding;
        private View view;

        private MaterialCardView cardView;
        TextView bid;

        public UserViewHolder(@NonNull BatchStatusSelectionBinding itemUserBinding) {
            super(itemUserBinding.getRoot());
            this.itemUserBinding = itemUserBinding;
            this.view = itemUserBinding.getRoot();

            bid = view.findViewById(R.id.batchStatus1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getCurrentItemAt(position));
                    }
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(
                BatchResponse user);
    }

    public void setOnItemClickListener(BatchStatusAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

}
