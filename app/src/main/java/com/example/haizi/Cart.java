package com.example.haizi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haizi.Model.cart;
import com.example.haizi.ViewHolder.cart_Holder;
import com.example.haizi.preValent.preValent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cart extends AppCompatActivity
{
    private RecyclerView CrecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView textTotal;
    private String user = "";
    private DatabaseReference CartRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        CrecyclerView =  findViewById(R.id.cartRecycler);
        CrecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        CrecyclerView.setLayoutManager(layoutManager);
        CartRef = FirebaseDatabase.getInstance().getReference()
                .child("cart");
         user = preValent.onLineUser.getPhone();

        textTotal = (TextView) findViewById(R.id.Total);




    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<cart> option =
                new FirebaseRecyclerOptions.Builder<cart>()
                        .setQuery(CartRef.child("User View").child(user)
                                .child("Order"), cart.class)
                        .build();


        FirebaseRecyclerAdapter<cart, cart_Holder> adapter =
                new FirebaseRecyclerAdapter<cart, cart_Holder>(option) {
                    @Override
                    protected void onBindViewHolder(@NonNull cart_Holder holder, int position, @NonNull final cart model)
                    {
                        holder.Hname.setText(model.getPlateName());
                        holder.Hprice.setText(model.getPrice());
                        //holder.HCategory.setText(model.getCategory());
                        holder.Hquantity.setText(model.getQuantity());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                CharSequence options[] = new CharSequence[]
                                        {
                                                "修改",
                                                "删除"
                                        };

                                AlertDialog.Builder builder = new AlertDialog.Builder(Cart.this);
                                builder.setTitle("Cart Options:");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(which==0)
                                        {
                                            Intent intent = new Intent(Cart.this,show_product.class);
                                            intent.putExtra("dish",model.getPid());
                                            startActivity(intent);
                                        }
                                        if(which ==1)
                                        {
                                            CartRef.child("User View")
                                                    .child(preValent.onLineUser.getPhone())
                                                    .child("Order")
                                                    .child(model.getPid())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task)
                                                        {
                                                            if(task.isSuccessful())
                                                            {
                                                                Toast.makeText(Cart.this,"删除成功",Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(Cart.this,restaurantHome.class);

                                                                startActivity(intent);
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public cart_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_card_view, parent, false);
                        cart_Holder holder = new cart_Holder(view);
                        return holder;
                    }
                };

        CrecyclerView.setAdapter(adapter);
        // adapter.startListening();

    }
}
