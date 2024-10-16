package com.example.e_commerce.Controler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
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
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteItemsAdapter extends RecyclerView.Adapter<FavoriteItemsAdapter.ViewHolder> {

    public ArrayList<ItemsModel> items_list = new ArrayList<>();
    private Context context;
//    int  isAnimated = 0 ;


    public FavoriteItemsAdapter(ArrayList<ItemsModel> items_list, Context context) {
        this.context = context;
        this.items_list = items_list;
    }

    @NonNull
    @Override
    public FavoriteItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.populair_item_gridlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteItemsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.productName.setText(items_list.get(position).getProductName());
        holder.productPrice.setText("$" + items_list.get(position).getPrice());
        holder.likedImageEmpty.setVisibility(View.GONE);
        holder.likedImageFill.setVisibility(View.VISIBLE);
        Picasso.get().load(items_list.get(position).getImageUrl()).into(holder.productImage);
        if (Float.parseFloat(items_list.get(position).getPrice()) >= 600f){
            holder.ratting.setVisibility(View.VISIBLE);
            holder.populaire.setVisibility(View.VISIBLE);
        } else {
            holder.ratting.setVisibility(View.VISIBLE);
            holder.populaire.setVisibility(View.GONE);
        }
        holder.moreinformationButton.setOnClickListener(new View.OnClickListener() {
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

        holder.card.setAnimation(AnimationUtils.loadAnimation(context, R.anim.card_pop_up));

        holder.likedImageFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.likedImageFill.getVisibility() == View.VISIBLE) {
                    DeleteItemFromDataBase(holder.likedImageFill, holder.likedImageEmpty ,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage, likedImageFill, likedImageEmpty, ratting , populaire;
        TextView productName, productPrice;
        MaterialCardView card;
        RelativeLayout moreinformationButton ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ratting = itemView.findViewById(R.id.ratting);
            populaire = itemView.findViewById(R.id.populaire);
            productImage = itemView.findViewById(R.id.product_image);
            likedImageFill = itemView.findViewById(R.id.product_liked_fill);
            likedImageEmpty = itemView.findViewById(R.id.product_liked_empty);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            card = itemView.findViewById(R.id.card);
            moreinformationButton = itemView.findViewById(R.id.more_informations);
        }
    }

    public void DeleteItemFromDataBase(ImageView fill , ImageView empty , int position){
        DatabaseReference Root = FirebaseDatabase.getInstance().getReference();
        Root.child("FavoriteItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // searching for the items that have the same image url which means its the same object , and we delete them
                for (DataSnapshot item : snapshot.getChildren()) {
                    if (item.getValue(ItemsModel.class).getImageUrl().equals(items_list.get(position).getImageUrl())) {
                        // means that the position adapter image is the same as the one in the firebase
                        Root.child("FavoriteItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(item.getKey()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(context, items_list.get(position).getProductName()+" Deleted !", Toast.LENGTH_SHORT).show();
                                items_list.remove(items_list.get(position));
                                notifyDataSetChanged();
                                fill.setVisibility(View.GONE);
                                empty.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
