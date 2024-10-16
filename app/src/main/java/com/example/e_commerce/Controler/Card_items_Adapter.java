package com.example.e_commerce.Controler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.Modele.CardItemModel;
import com.example.e_commerce.R;
import com.example.e_commerce.View.Activities.MainActivity;
import com.example.e_commerce.View.Activities.OrderDetails_Activity;
import com.example.e_commerce.View.Fragments.OrdersFragment;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firestore.v1.StructuredQuery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Card_items_Adapter extends RecyclerView.Adapter<Card_items_Adapter.ViewHolder> {
    public ArrayList<CardItemModel> list;
    Context context;
    MaterialAlertDialogBuilder progressDialog;
    AlertDialog dialog;
    Class activity ;
    Class OrderDetails_Activity = com.example.e_commerce.View.Activities.OrderDetails_Activity.class;


    public Card_items_Adapter(ArrayList<CardItemModel> list, Context context , Class activity) {
        this.list = list;
        this.context = context;
        this.activity = activity ;
    }

    @NonNull
    @Override
    public Card_items_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (activity == OrderDetails_Activity){
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_receipt, parent, false));
        } else {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Card_items_Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.nameText.setText(list.get(position).getName() + "..");
        holder.priceText.setText(list.get(position).getPrice());
        holder.total_items_Text.setText("Total: " + list.get(position).getQuantity());
        holder.dateText.setText(list.get(position).getDate());
        holder.timeText.setText(list.get(position).getTime());
        holder.card.setAnimation(AnimationUtils.loadAnimation(context, R.anim.card_pop_up));
        if (activity != OrderDetails_Activity){
            if (Float.parseFloat(list.get(position).getPrice()) >= 600f){
                holder.ratting.setVisibility(View.GONE);
                holder.populaire.setVisibility(View.VISIBLE);
            } else {
                holder.ratting.setVisibility(View.VISIBLE);
                holder.populaire.setVisibility(View.GONE);
            }
        }
        Picasso.get().load(list.get(position).getImageurl()).into(holder.image);
        // to not give the user the ability to delete an order from the OrderDelivery_Activity because the order has already been sent
        if (activity != OrderDetails_Activity){
            holder.card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    progressDialog = new MaterialAlertDialogBuilder(context);
                    progressDialog.setTitle("Confirmation text !");
                    progressDialog.setMessage("Do you want to delete this product from your card ?");
                    progressDialog.setCancelable(true);
                    progressDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DeleteItemFromCard(list.get(position).getDate(), list.get(position).getTime(), position);
                            dialog.dismiss();
                        }
                    });
                    progressDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialog.dismiss();
                        }
                    });
                    progressDialog.setBackground(context.getResources().getDrawable(R.drawable.alert_dialog_back));
                    progressDialog.setIcon(R.drawable.delete);
                    progressDialog.setCancelable(false);
                    dialog = progressDialog.show();
                    dialog.show();
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (OrdersFragment.emptyImage != null) {
            if (list.size() == 0) {
                OrdersFragment.emptyImage.setVisibility(View.VISIBLE);
            } else {
                OrdersFragment.emptyImage.setVisibility(View.GONE);
            }
        }
        if (OrdersFragment.progressBar != null){
            if (list.size() == 0) {
                OrdersFragment.progressBar.setVisibility(View.GONE);
            }
        }
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image , ratting , populaire;
        TextView dateText, timeText, total_items_Text, priceText, nameText;
        MaterialCardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ratting = itemView.findViewById(R.id.ratting);
            populaire = itemView.findViewById(R.id.populaire);
            card = itemView.findViewById(R.id.card_item);
            image = itemView.findViewById(R.id.item_image);
            dateText = itemView.findViewById(R.id.date);
            timeText = itemView.findViewById(R.id.time);
            total_items_Text = itemView.findViewById(R.id.total_items);
            priceText = itemView.findViewById(R.id.item_price);
            nameText = itemView.findViewById(R.id.item_name);
        }
    }

    public void DeleteItemFromCard(String date, String time, int position) {
        DatabaseReference Root = FirebaseDatabase.getInstance().getReference();
        Root.child("UserCard").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // searching for the items that have the same image url which means its the same object , and we delete them
                for (DataSnapshot item : snapshot.getChildren()) {
                    if (item.getValue(CardItemModel.class).getTime().equals(time) && item.getValue(CardItemModel.class).getDate().equals(date)) {
                        Root.child("UserCard").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(item.getKey()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(context, list.get(position).getName() + " Deleted from card!", Toast.LENGTH_SHORT).show();
                                list.remove(list.get(position));
                                notifyDataSetChanged();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
