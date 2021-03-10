package com.example.haizi.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haizi.HomeActivity;
import com.example.haizi.Model.food_list_model;
import com.example.haizi.Model.food_type_model;
import com.example.haizi.R;
import com.example.haizi.restaurantHome;
import com.example.haizi.show_Notifications;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class food_list_adapter extends RecyclerView.Adapter<food_list_adapter.food_list_viewHolder> {

    private Context context;
    private List<food_list_model> list_model;

    public food_list_adapter(Context context, List<food_list_model> list_model) {
        this.context = context;
        this.list_model = list_model;
    }

    @NonNull
    @Override
    public food_list_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.foodlistcardview,parent,false);
        return new food_list_viewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull food_list_viewHolder holder, int position) {

        food_list_model uploadCurrent = list_model.get(position);
        holder.plateName.setText((uploadCurrent.getFoodName()));
        holder.plateDescription.setText((uploadCurrent.getFoodDescription()));

        //String p= Double.toString(uploadCurrent.getFoodPrice());
       // holder.platePrice.setText(p);

        holder.platePersons.setText((uploadCurrent.getFoodName()));

        Picasso.get()
                .load(uploadCurrent.getImageUri())
                .fit()
                .centerCrop().into(holder.cardImage);



    }




    @Override
    public int getItemCount() {
        return list_model.size();
    }






    public class food_list_viewHolder extends RecyclerView.ViewHolder
{
    public TextView plateName , plateDescription , platePrice , platePersons ;
    public ImageView cardImage ;

    public food_list_viewHolder(@NonNull View itemView) {
        super(itemView);
        plateName =itemView.findViewById(R.id.CardFoodName);
        plateDescription =itemView.findViewById(R.id.CardDescription);
        platePersons =itemView.findViewById(R.id.CardPersonsToEat);
        platePrice =itemView.findViewById(R.id.CardPrice);
        cardImage =itemView.findViewById(R.id.cardPlateImage);



    }
}


}
