package com.example.makesurest.companyselection;

import android.content.Context;
import android.widget.Toast;

public class ItemUserClickListner {

    Context mContext;

    public ItemUserClickListner(Context mContext) {
        this.mContext = mContext;
    }

    public void onViewButtonClick(String name) {
        Toast.makeText(mContext, "You just clicked: " + name, Toast.LENGTH_SHORT).show();
    }
}
