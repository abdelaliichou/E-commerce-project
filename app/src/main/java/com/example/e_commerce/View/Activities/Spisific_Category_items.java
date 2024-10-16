package com.example.e_commerce.View.Activities;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.Controler.AllItemsAdapter;
import com.example.e_commerce.Controler.AllItemsAdapter_GridLayout;
import com.example.e_commerce.Modele.ItemsModel;
import com.example.e_commerce.Modele.Utils;
import com.example.e_commerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Spisific_Category_items extends AppCompatActivity {

    TextView header;
    RecyclerView recyclerView;
    AllItemsAdapter_GridLayout adapter;
    ProgressBar progressBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spisific_category_items);
        Setting_Ui();
        initialisation();
        settingRecyclerView();
    }

    public void initialisation() {
        progressBar = findViewById(R.id.spisific_items_progress);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.category_items_recycler);
        header = findViewById(R.id.category_name);
        header.setText(getIntent().getStringExtra("category"));
    }

    public void settingRecyclerView() {
        adapter = new AllItemsAdapter_GridLayout(getPopularItemsListFromDataBase(getIntent().getStringExtra("category")), getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Setting_Ui() {

        //Hiding action bar
        getSupportActionBar().hide();
        // setting the keyboard
        // Utils.setUpKeybaord(findViewById(R.id.parent),MainActivity.this);

        this.getWindow().setStatusBarColor(getResources().getColor(R.color.white));

        // to change the color of the icons in status bar to dark
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }
        // to change the color of the icons in the navigation bar to dark
        this.getWindow().setNavigationBarColor(getResources().getColor(R.color.white)); //setting bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS);
        }
    }
    public  ArrayList<ItemsModel> getPopularItemsListFromDataBase(String Category) {
        DatabaseReference Rot = FirebaseDatabase.getInstance().getReference();
        ArrayList<ItemsModel> list3 = new ArrayList<>();
        list3.clear();
        Rot.child("Users").child("Products").child("items").child(Category).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot category : snapshot.getChildren()) {
                    progressBar.setVisibility(View.GONE);
                    String Productname = category.getValue(ItemsModel.class).getProductName();
                    String ProductPris = category.getValue(ItemsModel.class).getPrice();
                    String ImageUrl = category.getValue(ItemsModel.class).getImageUrl();
                    ItemsModel model = new ItemsModel(Productname, ProductPris, ImageUrl);
                    list3.add(model);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
            }
        });
        return list3;
    }

}