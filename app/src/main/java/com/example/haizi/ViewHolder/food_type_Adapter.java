package com.example.haizi.ViewHolder;

//here we will create adapter and viewHolder for recycler view

import android.content.Context;
import android.content.Intent;
import android.media.Image;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haizi.Model.food_type_model;
import com.example.haizi.R;
import com.example.haizi.add_food_type_list;
import com.example.haizi.food_type;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class food_type_Adapter extends RecyclerView.Adapter<food_type_Adapter.food_type_Holder> {


    private Context contx;
    private ArrayList<food_type_model> food_type_list;
   private ONfoodListener mOnNote;
    // private OnItemClickListener mListener;
    public String x;
private food_type_model mo;

//the coming two methods to make a clickable cards
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

   // public void setOnItemClickListener(OnItemClickListener listener) {
     //   mListener = listener;
  //  }

    //then create constructor hold these variables


    public food_type_Adapter(Context contx, List<food_type_model> food_type_list, ONfoodListener oNfoodListener) {
        this.contx = contx;
        this.food_type_list = (ArrayList<food_type_model>) food_type_list;
        this.mOnNote = oNfoodListener;
    }

    @NonNull
    @Override
    public food_type_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(contx);

        View view = inflate.inflate(R.layout.food_type_card_view, null);

       food_type_Holder holder = new food_type_Holder(view,mOnNote);
       /*****/
        return  holder;


    }



    @Override
    public void onBindViewHolder(@NonNull food_type_Holder holder, int position) {
//upload class obj
        food_type_model upload = food_type_list.get(position);
        holder.cardText.setText(upload.getCardText());
        Picasso.get().load(upload.getImage()).into(holder.cardImage);




    }



    @Override
    public int getItemCount() {
        return food_type_list.size();
    }

    // we are inside the holder which connect the recyclerView to the UI elements which inside the cardView
public class food_type_Holder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
       public TextView cardText;
        public ImageView cardImage;
        ONfoodListener oNfoodListener;

        public food_type_Holder(@NonNull final View itemView, ONfoodListener oNfoodListener) {
            super(itemView);

            cardText = itemView.findViewById(R.id.foodTypeText);
            cardImage = itemView.findViewById(R.id.cardImage);
            this.oNfoodListener = oNfoodListener;

            itemView.setOnClickListener(this);

//             x=String.valueOf(cardText);
////
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onItemClick(position);
////
////                            Intent intent = new Intent(food_type_Adapter.this, add_food_type_list.class);
////                             intent.putExtra("category",foodList.get(x));
////                               startActivity(intent);
//                        }
//                    }
//
//                }
//            });

        }

        @Override
        public void onClick(View v)
        {
            oNfoodListener.onNoteClick(getLayoutPosition());
        }
    }
public interface ONfoodListener
{
    void onNoteClick(int position);
}

}
