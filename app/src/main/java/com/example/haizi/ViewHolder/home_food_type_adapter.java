package com.example.haizi.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haizi.Model.food_type_model;
import com.example.haizi.R;
import com.example.haizi.add_food_type_list;
import com.example.haizi.food_type;
import com.example.haizi.restaurantHome;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class home_food_type_adapter extends RecyclerView.Adapter<home_food_type_adapter.home_food_type_Holder>
{

    private Context contx;
    private ArrayList<food_type_model> food_type_list;
    private OnItemClickListener mListener;
    public String x;


    //the coming two methods to make a clickable cards
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(home_food_type_adapter.OnItemClickListener listener) {
        mListener = listener;
    }


    //then create constructor hold these variables


    public home_food_type_adapter(Context contx, List<food_type_model> food_type_list) {
        this.contx = contx;
        this.food_type_list = (ArrayList<food_type_model>) food_type_list;
    }

    @NonNull
    @Override
    public home_food_type_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(contx);

        View view = inflate.inflate(R.layout.home_food_type_card_view, null);

       home_food_type_Holder holder = new home_food_type_Holder(view,mListener);
        /*****/
        return  holder;


    }



    @Override
    public void onBindViewHolder(@NonNull home_food_type_adapter.home_food_type_Holder holder, int position) {
//upload class obj
        food_type_model upload = food_type_list.get(position);
        holder.cardText.setText(upload.getCardText());
        holder.cardImage.setImageDrawable(contx.getResources().getDrawable(upload.getImage()));


    }



    @Override
    public int getItemCount() {
        return food_type_list.size();
    }

    // we are inside the holder which connect the recyclerView to the UI elements which inside the cardView
    public class home_food_type_Holder extends RecyclerView.ViewHolder
    {
        public TextView cardText;
        public ImageView cardImage;

        public home_food_type_Holder(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);

            cardText = itemView.findViewById(R.id.HfoodTypeText);
            cardImage = itemView.findViewById(R.id.HcardImage);


            x=String.valueOf(cardText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
//                            String finall = cardText.toString();
//                            Intent intent = new Intent(contx, restaurantHome.class);
//                             intent.putExtra("categoryy",finall);
//                               contx.startActivity(intent);
                        }
                    }

                }
            });

 }



        }


}
