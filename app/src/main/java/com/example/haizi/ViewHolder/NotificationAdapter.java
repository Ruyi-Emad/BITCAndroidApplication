package com.example.haizi.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haizi.Interface.ItemClickListner;
import com.example.haizi.Model.Notifications;
import com.example.haizi.R;
import com.squareup.picasso.Picasso;

import java.nio.InvalidMarkException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
//it is notification holder not adapter name mistake 而已
public class NotificationAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView cardNotificationLines;
    public TextView cardNotificationTime;
    public ImageView cardNotificationImage;
    public ItemClickListner listner;

    public NotificationAdapter(@NonNull View itemView) {
        super(itemView);

            cardNotificationImage = itemView.findViewById(R.id.notification_photo);
            cardNotificationLines = itemView.findViewById(R.id.notifications_lines);
            cardNotificationTime = itemView.findViewById(R.id.notifications_time);

        }
    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View v)
    {
        listner.onClick(v, getAdapterPosition(), false);
    }
}





//
//public class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//    public TextView notificationLines;
//    public ImageView notificationPhoto;
//    public TextView time;
//    public TextView date;
//
//
//    public ItemClickListner listner;
//
//    public NotificationViewHolder(@NonNull View itemView) {
//        super(itemView);
//
//        notificationPhoto=(ImageView)itemView.findViewById(R.id.notificationPhoto);
//        notificationLines=(TextView)itemView.findViewById(R.id.notificationLines);
////        time=(TextView)itemView.findViewById(R.id.time);
////       date=(TextView)itemView.findViewById(R.id.date);
//
//    }
//
//
//
//    public void setItemClickListner(ItemClickListner listner)
//    {
//        this.listner = listner;
//    }
//
//
//    @Override
//    public void onClick(View view) {
//
//        listner.onClick(view,getBindingAdapterPosition(),false);
//
//    }
//}
