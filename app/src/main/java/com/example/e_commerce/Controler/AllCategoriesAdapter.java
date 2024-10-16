package com.example.e_commerce.Controler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.Modele.CategoryModel;
import com.example.e_commerce.R;
import com.example.e_commerce.View.Activities.Spisific_Category_items;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllCategoriesAdapter extends RecyclerView.Adapter<AllCategoriesAdapter.ViewHolder> {
    int isclicked = -1;
    private ArrayList<CategoryModel> Category_list = new ArrayList<>();
    private Context context;

    public AllCategoriesAdapter(ArrayList<CategoryModel> tasks_list, Context context) {
        this.context = context;
        this.Category_list = tasks_list;
    }
    @NonNull
    @Override
    public AllCategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_categories_layoutitem, parent, false);
        return new AllCategoriesAdapter.ViewHolder(view);
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull AllCategoriesAdapter.ViewHolder holder, int position) {
        holder.Category_name.setText(Category_list.get(position).getCategory());
        Picasso.get().load(Category_list.get(position).getImageUrl()).into(holder.Category_image);
//        Animations.FromeRightToLeftCard(holder.cardView);
        if (position == isclicked) { // the clicked item
            holder.cardView.setStrokeWidth(6);
            holder.cardView.setStrokeColor(R.color.colorfirst);
            holder.backEffect.setBackground(null);
            holder.Category_name.setTextColor(context.getResources().getColor(R.color.colorfirst));

        } else { // all the not clicked items
            holder.cardView.setStrokeWidth(0);
            holder.backEffect.setBackgroundColor(context.getResources().getColor(R.color.beckeffect));
            holder.Category_name.setTextColor(context.getResources().getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return Category_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView Category_image ;
        TextView Category_name ;
        MaterialCardView cardView;
        RelativeLayout backEffect;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Category_image = itemView.findViewById(R.id.AllCategories_image);
            Category_name = itemView.findViewById(R.id.AllCategories_Text);
            cardView = itemView.findViewById(R.id.card_parent);
            backEffect = itemView.findViewById(R.id.backeffect);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isclicked = getAdapterPosition();
                    Intent intent = new Intent(context, Spisific_Category_items.class);
                    intent.putExtra("category",Category_list.get(getAdapterPosition()).getCategory());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
