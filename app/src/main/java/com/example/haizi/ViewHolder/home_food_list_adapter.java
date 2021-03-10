package com.example.haizi.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haizi.HomeActivity;
import com.example.haizi.Interface.ItemClickListner;
import com.example.haizi.Model.food_list_model;
import com.example.haizi.Model.food_type_model;
import com.example.haizi.R;
import com.example.haizi.admin_change_food;
import com.example.haizi.admin_change_tongzhi;
import com.example.haizi.preValent.preValent;
import com.example.haizi.restaurantHome;
import com.example.haizi.show_Notifications;
import com.example.haizi.show_product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class home_food_list_adapter  extends RecyclerView.ViewHolder implements  View.OnClickListener {
    // we are inside the holder which connect the recyclerView to the UI elements which inside the cardView

    public TextView CplateName, Cdescription, Cprice, Cpeople, HCategory;
    public ImageView CardFoodListImage;
    public ItemClickListner listner;

    public home_food_list_adapter(@NonNull final View itemView) {
        super(itemView);

        CplateName = itemView.findViewById(R.id.HLCardFoodName);
//            Cdescription = itemView.findViewById(R.id.HLCardDescription);
//            Cprice = itemView.findViewById(R.id.HLCardPrice);
//            Cpeople = itemView.findViewById(R.id.HLCardPersonsToEat);
        HCategory = itemView.findViewById(R.id.HLCardFoodCategory);

        CardFoodListImage = itemView.findViewById(R.id.HLPlateImage);
    }

    public void setItemClickListner(ItemClickListner listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View v) {
        listner.onClick(v, getAdapterPosition(), false);
    }

}
//             x=String.valueOf(HCategory);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onItemClick(position);
//
//                            Intent intent = new Intent(v.getContext(), restaurantHome.class);
//                            intent.putExtra("dish", );
//                            v.getContext().startActivity(intent);
//                        }
//                    }
//
//                }
//            });












