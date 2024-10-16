package com.example.e_commerce.View.Activities;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.Modele.CardItemModel;
import com.example.e_commerce.Modele.ItemsModel;
import com.example.e_commerce.Modele.Utils;
import com.example.e_commerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class ItemMoreInfomationsActivity extends AppCompatActivity {

    RelativeLayout addToCard ;
    ImageView image, heartfull, heartempty;
    MaterialCardView plusImage, minusImage , color1 , color2,color3,color4,color5;
    TextView product_name, product_price, sommeText, Total_somme;
    DatabaseReference Root ;
    int somme = 1;
    int exists = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_more_infomations);
        Setting_Ui();
        initialisation();
        LoadItem();
        HandelignOnClicks();
        HandelingHeartClicks();
        Utils.Setting_initial_liked_button(heartfull,heartempty,getIntent().getStringExtra("imageurl"));
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void HandelingHeartClicks() {
        heartempty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (heartempty.getVisibility() == View.VISIBLE) {
                    ChargingItemInFireBase(heartfull, heartempty);
                }
            }
        });
        heartfull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (heartfull.getVisibility() == View.VISIBLE) {
                    DeletingItemFromFireBase(heartfull, heartempty);
                }
            }
        });
    }

    public void HandelignOnClicks() {
        addToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductToCardFireBase();
            }
        });
        plusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                somme++;
                sommeText.setText(String.valueOf(somme));
                Total_somme.setText("Total : $" + somme * Float.parseFloat(getIntent().getStringExtra("price")));
            }
        });
        minusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (somme <= 1) {
                    sommeText.setText("1");
                    somme = 1;
                    Total_somme.setText("Total : $" + Float.parseFloat(getIntent().getStringExtra("price")));
                } else {
                    somme--;
                    sommeText.setText(String.valueOf(somme));
                    Total_somme.setText("Total : $" + somme * Float.parseFloat(getIntent().getStringExtra("price")));
                }
            }
        });
        color1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color1.setStrokeColor(getResources().getColor(R.color.mainyellow));
                color1.setStrokeWidth(5);
                color2.setStrokeWidth(0);
                color3.setStrokeWidth(0);
                color4.setStrokeWidth(0);
                color5.setStrokeWidth(0);
            }
        });
        color2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color2.setStrokeColor(getResources().getColor(R.color.colorlast));
                color2.setStrokeWidth(5);
                color1.setStrokeWidth(0);
                color3.setStrokeWidth(0);
                color4.setStrokeWidth(0);
                color5.setStrokeWidth(0);
            }
        });
        color3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color3.setStrokeColor(getResources().getColor(R.color.colorlast));
                color3.setStrokeWidth(5);
                color2.setStrokeWidth(0);
                color1.setStrokeWidth(0);
                color4.setStrokeWidth(0);
                color5.setStrokeWidth(0);
            }
        });
        color4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color4.setStrokeColor(getResources().getColor(R.color.colorlast));
                color4.setStrokeWidth(5);
                color2.setStrokeWidth(0);
                color3.setStrokeWidth(0);
                color1.setStrokeWidth(0);
                color5.setStrokeWidth(0);
            }
        });
        color5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color5.setStrokeColor(getResources().getColor(R.color.colorlast));
                color5.setStrokeWidth(5);
                color2.setStrokeWidth(0);
                color3.setStrokeWidth(0);
                color4.setStrokeWidth(0);
                color1.setStrokeWidth(0);
            }
        });
    }

    public void addProductToCardFireBase(){
        String time , date ;
        Calendar calDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM/dd/yyyy");
        date = currentDate.format(calDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        time = currentTime.format(calDate.getTime());

        CardItemModel product = new CardItemModel(getIntent().getStringExtra("name"), // name
                date,
                time
                ,getIntent().getStringExtra("price"), // price
                String.valueOf(somme*Float.parseFloat(getIntent().getStringExtra("price"))), // total price
                String.valueOf(somme), // quantity
                getIntent().getStringExtra("imageurl")); // image url


        Root.child("UserCard").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ItemMoreInfomationsActivity.this, getIntent().getStringExtra("name")+" added to card !", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ItemMoreInfomationsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void initialisation() {
        Root = FirebaseDatabase.getInstance().getReference();
        addToCard = findViewById(R.id.add_to_card);
        image = findViewById(R.id.image_product);
        product_name = findViewById(R.id.info);
        product_price = findViewById(R.id.price);
        sommeText = findViewById(R.id.somme);
        minusImage = findViewById(R.id.minus_button);
        plusImage = findViewById(R.id.add_button);
        color1 = findViewById(R.id.color1);
        color2 = findViewById(R.id.color2);
        color3 = findViewById(R.id.color3);
        color4 = findViewById(R.id.color4);
        color5 = findViewById(R.id.color5);
        heartfull = findViewById(R.id.heart_fill);
        heartempty = findViewById(R.id.heart_empty);
        Total_somme = findViewById(R.id.total_somme);
        Total_somme.setText("Total : $" + getIntent().getStringExtra("price"));
    }

    public void LoadItem() {
        Picasso.get().load(getIntent().getStringExtra("imageurl")).into(image);
        product_price.setText(getIntent().getStringExtra("price"));
        product_name.setText(getIntent().getStringExtra("name"));
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Setting_Ui() {

        //Hiding action bar
        getSupportActionBar().hide();
        // setting the keyboard
        Utils.setUpKeybaord(findViewById(R.id.more_info), ItemMoreInfomationsActivity.this);

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

    public void ChargingItemInFireBase(ImageView fill, ImageView empty) {
        // charging this item in freebase
        Root.child("FavoriteItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // looping throw the firebase children and incrementing the value of the exist variable every time we found  similar items to the one that we want to add to the favorite list
                for (DataSnapshot i : snapshot.getChildren()) {
                    if (i.getValue(ItemsModel.class).getImageUrl().equals(getIntent().getStringExtra("imageurl"))) {
                        exists = 1;
                        // break;
                    }
                }
                // seeing if the item that we trying to add is already added or note , if note we add it (by verifying the exists variable )
                if (exists == 0) {
                    // there is no item like this added to the favorite , so we add it
                    Root.child("FavoriteItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().setValue(new ItemsModel(getIntent().getStringExtra("name"), getIntent().getStringExtra("price"), getIntent().getStringExtra("imageurl"), false)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ItemMoreInfomationsActivity.this, getIntent().getStringExtra("name") + " added to favorite !", Toast.LENGTH_SHORT).show();
                                // setting the heart to full , means that this item is from the favorites now
                               // MainActivity.chipNavigationBar.showBadge(R.id.Fav);
                                empty.setVisibility(View.GONE);
                                fill.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    // means that this element already exists in the firebase favorite list , so we don't add it
                } else {
                    exists = 0;
                    Toast.makeText(ItemMoreInfomationsActivity.this, getIntent().getStringExtra("name") + " already exists !", Toast.LENGTH_SHORT).show();
                    // this item is already in the favorite list , so in case the heart wasn't full according to some problem , we make it full to
                    // assure that this element is a favorite item
                    //MainActivity.chipNavigationBar.showBadge(R.id.Fav);
                    empty.setVisibility(View.GONE);
                    fill.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void DeletingItemFromFireBase(ImageView fill, ImageView empty) {

        Root.child("FavoriteItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // searching for the items that have the same image url which means its the same object , and we delete them
                for (DataSnapshot item : snapshot.getChildren()) {
                    if (item.getValue(ItemsModel.class).getImageUrl().equals(getIntent().getStringExtra("imageurl"))) {
                        // means that the position adapter image is the same as the one in the firebase
                        Root.child("FavoriteItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(item.getKey()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(ItemMoreInfomationsActivity.this, getIntent().getStringExtra("name") + " deleted !", Toast.LENGTH_SHORT).show();
                                //sett the heart to empty , means that we have deleted it to our favorite list
                                MainActivity.bottomBar.setBadgeAtTabId(R.id.Fav, new AnimatedBottomBar.Badge());
                                fill.setVisibility(View.GONE);
                                empty.setVisibility(View.VISIBLE);
                                exists = 0;
                            }
                        });
                    }
                }
                //sett the heart to empty , means that we have removed it to from our favorite list , even if we haven't find its similar in the database
                MainActivity.bottomBar.setBadgeAtTabId(R.id.Fav, new AnimatedBottomBar.Badge());
                fill.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}
