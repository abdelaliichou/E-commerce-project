package com.example.e_commerce.Controler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.Modele.ItemsModel;
import com.example.e_commerce.R;
import com.example.e_commerce.View.Activities.ItemMoreInfomationsActivity;
import com.example.e_commerce.View.Activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class Search_adapter extends RecyclerView.Adapter<Search_adapter.ViewHolder> implements Filterable {

    public ArrayList<ItemsModel> items_list = new ArrayList<>();
    private Context context;
    int exists = 0;


    public Search_adapter(ArrayList<ItemsModel> items_list, Context context) {
        this.context = context;
        this.items_list = items_list;
    }

    @NonNull
    @Override
    public Search_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Search_adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.productName.setText(items_list.get(position).getProductName());
        holder.productPrice.setText(items_list.get(position).getPrice());
        Picasso.get().load(items_list.get(position).getImageUrl()).into(holder.productImage);
        holder.card.setAnimation(AnimationUtils.loadAnimation(context, R.anim.card_pop_up));
        holder.go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemMoreInfomationsActivity.class) ;
                intent.putExtra("name",items_list.get(position).getProductName());
                intent.putExtra("price",items_list.get(position).getPrice());
                intent.putExtra("imageurl",items_list.get(position).getImageUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items_list.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice;
        MaterialCardView card;
        RelativeLayout go ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.photo);
            productName = itemView.findViewById(R.id.nom);
            productPrice = itemView.findViewById(R.id.prix);
            card = itemView.findViewById(R.id.card_item);
            go = itemView.findViewById(R.id.go);
//            card.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    click = getAdapterPosition();
//                    isAnime = true ;
//                    notifyDataSetChanged();
//                }
//            });
        }
    }
}
