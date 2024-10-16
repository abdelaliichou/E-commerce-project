package com.example.e_commerce.View.Activities;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.Controler.Card_items_Adapter;
import com.example.e_commerce.Modele.CardItemModel;
import com.example.e_commerce.Modele.Delivery_Order_Model;
import com.example.e_commerce.R;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderDetails_Activity extends AppCompatActivity {

    TextView email, date, time, totalPrice, totalNumber, id;
    RecyclerView OrderRecyclerView;
    Card_items_Adapter OrderAdapter;
    ProgressBar progressBar;
    MaterialCardView checkProcessButton;
    Class OrderDetails_Activity = com.example.e_commerce.View.Activities.OrderDetails_Activity.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Setting_Ui();
        initialisation();
        OnClicks();
        SetInitialInformations();
        SettingRecycler();

    }

    public void OnClicks() {
        checkProcessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().getStringExtra("id").equals("last_order")){
                    startActivity(new Intent(com.example.e_commerce.View.Activities.OrderDetails_Activity.this, Delivery_Activity.class).putExtra("id",id.getText().toString().trim()));
                } else {
                    startActivity(new Intent(com.example.e_commerce.View.Activities.OrderDetails_Activity.this, Delivery_Activity.class).putExtra("id",getIntent().getStringExtra("id")));
                }
              //  finish();
            }
        });
    }

    public void SetInitialInformations() {
        if (getIntent().getStringExtra("id").equals("last_order")) {
            FirebaseDatabase.getInstance().getReference().child("Delivery").child("OnProcess").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // even that we have a single last element , but we need to iterate using the for loop else it won't work
                        for (DataSnapshot item : snapshot.getChildren()) {
                            Delivery_Order_Model model = item.getValue(Delivery_Order_Model.class);
                            id.setText(model.getOdrderID());
                            email.setText(model.getUserEmail());
                            date.setText(model.getDate());
                            time.setText(model.getTime());
                            totalNumber.setText(model.getProductsNumber());
                            totalPrice.setText(model.getTotalPrice());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        } else {
            FirebaseDatabase.getInstance().getReference().child("Delivery").child("OnProcess").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getIntent().getStringExtra("id")).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Delivery_Order_Model model = snapshot.getValue(Delivery_Order_Model.class);
                            id.setText(model.getOdrderID());
                            email.setText(model.getUserEmail());
                            date.setText(model.getDate());
                            time.setText(model.getTime());
                            totalNumber.setText(model.getProductsNumber());
                            totalPrice.setText(model.getTotalPrice());
                            progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        startActivity(new Intent(com.example.e_commerce.View.Activities.OrderDetails_Activity.this,MainActivity.class));
//        finishAffinity();
    }

    public void initialisation() {
        checkProcessButton = findViewById(R.id.process);
        progressBar = findViewById(R.id.progress);
        email = findViewById(R.id.email);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        totalPrice = findViewById(R.id.products_total_price);
        totalNumber = findViewById(R.id.total_products);
        id = findViewById(R.id.id);
        OrderRecyclerView = findViewById(R.id.order_list);
    }

    public void SettingRecycler() {
        if (getIntent().getStringExtra("id").equals("last_order")) {
            OrderAdapter = new Card_items_Adapter(getAllOrderProducts("last"), getApplicationContext(), OrderDetails_Activity);
        } else {
            OrderAdapter = new Card_items_Adapter(getAllOrderProducts("id"), getApplicationContext(), OrderDetails_Activity);
        }
        OrderRecyclerView.setAdapter(OrderAdapter);
        OrderRecyclerView.setLayoutManager(new LinearLayoutManager(OrderDetails_Activity.this, LinearLayoutManager.VERTICAL, false));
    }

    public ArrayList<CardItemModel> getAllOrderProducts(String id) {
        ArrayList<CardItemModel> liste = new ArrayList<>();
        if (id.equals("last")) {
            // means that we came from OrdersFragment bottom sheet , or from the check last order in the profile fragment ==> we don't have the id of the order so we get the last order
            FirebaseDatabase.getInstance().getReference().child("Delivery").child("OnProcess").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot order : snapshot.getChildren()) {
                            Delivery_Order_Model model = order.getValue(Delivery_Order_Model.class);
                            // we can also user model = order.getKey() , and use it in the firebase path below
                            FirebaseDatabase.getInstance().getReference().child("Delivery").child("OnProcess").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(model.getOdrderID()).child("orderElements").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        for (DataSnapshot cardItem : snapshot.getChildren()) {
                                            CardItemModel model = cardItem.getValue(CardItemModel.class);
                                            liste.add(model);
                                        }
                                        OrderAdapter.notifyDataSetChanged();
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        } else {
            // means that we came from the order recycler view directly ==> we have the order id
            FirebaseDatabase.getInstance().getReference().child("Delivery").child("OnProcess").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getIntent().getStringExtra("id")).child("orderElements").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot cardItem : snapshot.getChildren()) {
                            CardItemModel model = cardItem.getValue(CardItemModel.class);
                            liste.add(model);
                        }
                        OrderAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
        return liste;
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