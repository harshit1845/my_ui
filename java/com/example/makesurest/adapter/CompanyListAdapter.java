package com.example.makesurest.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makesurest.R;
import com.example.makesurest.databinding.CompanyDialogBinding;
import com.example.makesurest.model.ResponseCompanyListModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class CompanyListAdapter extends RecyclerView.Adapter<CompanyListAdapter.UserViewHolder>{
    private List<ResponseCompanyListModel> users;
    private OnItemClickListener listener;
    int compareValue = -1;
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CompanyDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.company_dialog, parent, false);
        return new UserViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull CompanyListAdapter.UserViewHolder holder, int position) {
        ResponseCompanyListModel currentUser = users.get(position);
        holder.itemUserBinding.setUser(currentUser);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(getCurrentItemAt(position));
                }

                // Change the background color of the selected item.
//                if (compareValue == position) {
//                    holder.cardView.setCardBackgroundColor(Color.parseColor("#be5051"));
//                } else {
//                    holder.cardView.setCardBackgroundColor(Color.parseColor("#ffffff"));
//                }
//
//                compareValue = position;
                notifyDataSetChanged();
            }
        });

        // Set the background color of the item based on the compareValue.
//        if (compareValue == position) {
//            holder.cardView.setCardBackgroundColor(Color.parseColor("#be5051"));
//        } else {
//            holder.cardView.setCardBackgroundColor(Color.parseColor("#ffffff"));
//        }
    }

    @Override
    public int getItemCount() {
        if (users != null) {
            return users.size();
        } else {
            return 0;
        }
    }

    public void setUserList(List<ResponseCompanyListModel> users) {
        this.users = users;
        notifyDataSetChanged();
    }
    public ResponseCompanyListModel getCurrentItemAt(int position) {
        return users.get(position);
    }


    public class UserViewHolder extends RecyclerView.ViewHolder {
        private CompanyDialogBinding itemUserBinding;
        private MaterialCardView cardView;


        public UserViewHolder(@NonNull CompanyDialogBinding itemUserBinding) {
            super(itemUserBinding.getRoot());
            this.itemUserBinding = itemUserBinding;
            cardView = itemUserBinding.cardView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getCurrentItemAt(position));
//                        compareValue = position;


                    }
                }

            });


        }

    }

    public interface OnItemClickListener {
        void onItemClick(ResponseCompanyListModel user);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}

