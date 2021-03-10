package com.example.haizi.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.example.haizi.Interface.ItemClickListner;
import com.example.haizi.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class cart_Holder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public ItemClickListner Hlistner;
    public TextView Hname ,Hquantity , Hprice , HCategory ;

    public cart_Holder(@NonNull View itemView) {
        super(itemView);
        Hname = itemView.findViewById(R.id.cartname);
        Hquantity = itemView.findViewById(R.id.cartquantity);
        Hprice = itemView.findViewById(R.id.cartprice);
        HCategory = itemView.findViewById(R.id.cartcategory);


    }

    @Override
    public void onClick(View v) {
Hlistner.onClick(v,getAdapterPosition(),false);
    }

    public void setHlistner(ItemClickListner hlistner) {
        Hlistner = hlistner;
    }
}
