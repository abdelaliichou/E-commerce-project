package com.example.e_commerce.View.Activities;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.e_commerce.Controler.AllCategoriesAdapter;
import com.example.e_commerce.Controler.AllItemsAdapter;
import com.example.e_commerce.Controler.AllItemsAdapter_GridLayout;
import com.example.e_commerce.Modele.ItemsModel;
import com.example.e_commerce.Modele.Utils;
import com.example.e_commerce.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewAllItemsActivity extends AppCompatActivity {

    ProgressBar progressBar ;
    AllItemsAdapter_GridLayout adapter;
    RecyclerView categoriesrecycler ;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_items);
        //setting the status bar , action bar colors , keyboard interaction
        Setting_Ui();
        initialisation();
        settingRecyclerView();

    }

    public void initialisation(){
        categoriesrecycler = findViewById(R.id.AllCategories_recycler);
        progressBar = findViewById(R.id.all_items_progress);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void settingRecyclerView(){
        // setting first recycler
        //Bundle intent = getIntent().getExtras();
        adapter = new AllItemsAdapter_GridLayout(ItemsList(), getApplicationContext());
        categoriesrecycler.setAdapter(adapter);
        categoriesrecycler.setLayoutManager(new GridLayoutManager(this,2));
    }


    public  ArrayList<ItemsModel> ItemsList (){
        DatabaseReference Rott = FirebaseDatabase.getInstance().getReference();
        ArrayList<ItemsModel> list4 = new ArrayList<>();
        list4.clear();
        Rott.child("Users").child("Products").child("items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot category : snapshot.getChildren()){
                    for (DataSnapshot item : category.getChildren()){
                        ItemsModel model = item.getValue(ItemsModel.class);
                        list4.add(model);
                        progressBar.setVisibility(View.GONE);
                    }
                    adapter.notifyDataSetChanged();
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
            }
        });
        return  list4 ;
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
}