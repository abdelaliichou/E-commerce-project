package com.example.e_commerce.View.Activities;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.e_commerce.Controler.AllItemsAdapter;
import com.example.e_commerce.Controler.AllOrdersAdapter;
import com.example.e_commerce.Controler.Card_items_Adapter;
import com.example.e_commerce.Modele.Delivery_Order_Model;
import com.example.e_commerce.Modele.Utils;
import com.example.e_commerce.R;
import com.example.e_commerce.View.Fragments.OrdersFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllOrders_Activity extends AppCompatActivity {

    RecyclerView AllOrdersRecycler ;
    AllOrdersAdapter AllOrdersAdapter ;
    ProgressBar progressBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);
        Setting_Ui();
        initialisation();
        SettingRecycler();

    }

    public void initialisation(){
        progressBar = findViewById(R.id.progress);
        AllOrdersRecycler = findViewById(R.id.order_list);
    }

    public void SettingRecycler(){
        AllOrdersAdapter = new AllOrdersAdapter(AllOrdersList(), getApplicationContext());
        AllOrdersRecycler.setAdapter(AllOrdersAdapter);
        AllOrdersRecycler.setLayoutManager(new LinearLayoutManager(AllOrders_Activity.this, LinearLayoutManager.VERTICAL, false));
    }

    public ArrayList<Delivery_Order_Model> AllOrdersList(){
        ArrayList<Delivery_Order_Model> list = new ArrayList<>();
        // we will charge all the on process orders
        FirebaseDatabase.getInstance().getReference().child("Delivery").child("OnProcess").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot order : snapshot.getChildren()){
                        Delivery_Order_Model model = order.getValue(Delivery_Order_Model.class);
                        list.add(0,model);
                        AllOrdersAdapter.notifyItemInserted(0);
                    }
                    AllOrdersAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
            }
        });
        return list ;
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