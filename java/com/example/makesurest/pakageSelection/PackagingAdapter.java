package com.example.makesurest.pakageSelection;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makesurest.R;

import com.example.makesurest.adapter.ProductListAdapter;
import com.example.makesurest.databinding.ItemProductBinding;
import com.example.makesurest.databinding.ProductItemBinding;
import com.example.makesurest.model.PackgingResponce;

import com.example.makesurest.model.ProductResponse;
import com.google.android.material.card.MaterialCardView;

import java.util.List;
public class PackagingAdapter extends RecyclerView.Adapter<PackagingAdapter.UserViewHolder> {

    private List<PackgingResponce> productList;
    private OnItemClickListener listener;
    private int compareValue = -1;

    public PackagingAdapter(List<PackgingResponce> productList) {
        this.productList = productList;
    }

    public interface OnItemClickListener {
        void onItemClick(PackgingResponce primaryItem, PackgingResponce secondaryItem);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        PackgingResponce currentProduct = productList.get(position);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = holder.getAdapterPosition();
                if (listener != null && currentPosition != RecyclerView.NO_POSITION) {
                    PackgingResponce primaryItem = getCurrentItemAt(currentPosition);
                    PackgingResponce secondaryItem = getNextItemAt(currentPosition);
                    listener.onItemClick(primaryItem, secondaryItem);
//                    compareValue = currentPosition;
                    notifyDataSetChanged();
                }
            }
        });

        // Set the background color based on the compareValue
//        if (compareValue == position) {
//            holder.cardView.setCardBackgroundColor(Color.parseColor("#be5051"));
//        } else {
//            holder.cardView.setCardBackgroundColor(Color.parseColor("#ffffff"));
//        }

        if (position < productList.size() - 1) {
            PackgingResponce nextProduct = productList.get(position + 1);
            holder.bindTransition(currentProduct, nextProduct);
        } else {
            holder.bindLevel(currentProduct);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size() - 1;
    }

    public void setUserList(List<PackgingResponce> users) {
        this.productList = users;
        notifyDataSetChanged();
    }

    public PackgingResponce getCurrentItemAt(int position) {
        return productList.get(position);
    }

    public PackgingResponce getNextItemAt(int position) {
        if (position + 1 < productList.size()) {
            return productList.get(position + 1);
        } else {
            return null;
        }
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private ItemProductBinding itemBinding;
        private MaterialCardView cardView;
        private TextView textView;

        public UserViewHolder(@NonNull ItemProductBinding binding) {
            super(binding.getRoot());
            itemBinding = binding;
            textView = itemBinding.levelTextView;
            cardView = itemBinding.cardView;
        }

        public void bindLevel(PackgingResponce product) {
            textView.setText(product.getLevelName());
        }

        public void bindTransition(PackgingResponce currentProduct, PackgingResponce nextProduct) {
            String transitionText = currentProduct.getLevelName() + " to " + nextProduct.getLevelName();
            textView.setText(transitionText);
        }
    }
}