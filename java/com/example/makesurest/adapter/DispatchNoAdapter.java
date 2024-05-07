package com.example.makesurest.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.makesurest.R;
import com.example.makesurest.databinding.DispatchItemBinding;
import com.example.makesurest.model.DispatchNumberResponce;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class DispatchNoAdapter extends RecyclerView.Adapter<DispatchNoAdapter.UserViewHolder>{
    private List<DispatchNumberResponce> users;
    private DispatchNoAdapter.OnItemClickListener listener;
    int compareValue = -1;
    @NonNull
    @Override
    public DispatchNoAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        DispatchItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.dispatch_item, parent, false);
        return new DispatchNoAdapter.UserViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull DispatchNoAdapter.UserViewHolder holder, int position) {
        DispatchNumberResponce currentUser = users.get(position);
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

//                compareValue = position;
                notifyDataSetChanged();
            }
        });

////         Set the background color of the item based on the compareValue.
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

    public void setUserList(List<DispatchNumberResponce> users) {
        this.users = users;
        notifyDataSetChanged();
    }
    public DispatchNumberResponce getCurrentItemAt(int position) {
        return users.get(position);
    }


    public class UserViewHolder extends RecyclerView.ViewHolder {
        private DispatchItemBinding itemUserBinding;
        private MaterialCardView cardView;


        public UserViewHolder(@NonNull DispatchItemBinding itemUserBinding) {
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
        void onItemClick(DispatchNumberResponce user);
    }

    public void setOnItemClickListener(DispatchNoAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

}

