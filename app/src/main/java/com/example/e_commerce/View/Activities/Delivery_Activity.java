package com.example.e_commerce.View.Activities;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.e_commerce.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Delivery_Activity extends AppCompatActivity {

    MaterialCardView cardone, cardsecond, cardthird, cardfour, cardfive, doneButton;
    ImageView imgone, imgsecond, imgthird, imgfour, imgfive, doneimage, sendimage;
    LinearLayout linearone, linearsecond, linearthird, linearfour;
    TextView textone, textsecond, textthird, textfour, textfive, doneText;
    BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        Setting_Action_Bar_Status_Bar();
        initialisation();
        HandelingDelivery();
        OnClick();

    }

    public void HandelingDelivery() {

        FirebaseDatabase.getInstance().getReference().child("Delivery").child("Started").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getIntent().getStringExtra("id")).child("status").addValueEventListener(new ValueEventListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String status = snapshot.getValue(String.class);
                    switch (status) {
                        case "first":
                            cardone.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardsecond.setCardBackgroundColor(getResources().getColor(R.color.white));
                            cardthird.setCardBackgroundColor(getResources().getColor(R.color.white));
                            cardfour.setCardBackgroundColor(getResources().getColor(R.color.white));
                            cardfive.setCardBackgroundColor(getResources().getColor(R.color.white));
                            imgone.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgsecond.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.edite_text_hint_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgthird.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.edite_text_hint_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgfour.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.edite_text_hint_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgfive.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.edite_text_hint_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                            linearone.setVisibility(View.VISIBLE);
                            linearsecond.setVisibility(View.GONE);
                            linearthird.setVisibility(View.GONE);
                            linearfour.setVisibility(View.GONE);
                            textone.setTextColor(getResources().getColor(R.color.deliveryText));
                            textsecond.setTextColor(getResources().getColor(R.color.edite_text_hint_color));
                            textthird.setTextColor(getResources().getColor(R.color.edite_text_hint_color));
                            textfour.setTextColor(getResources().getColor(R.color.edite_text_hint_color));
                            textfive.setTextColor(getResources().getColor(R.color.edite_text_hint_color));
                            doneButton.setClickable(false);
                            doneText.setText("On process...");
                            doneimage.setVisibility(View.GONE);
                            sendimage.setVisibility(View.VISIBLE);

                            break;
                        case "second":
                            cardone.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardsecond.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardthird.setCardBackgroundColor(getResources().getColor(R.color.white));
                            cardfour.setCardBackgroundColor(getResources().getColor(R.color.white));
                            cardfive.setCardBackgroundColor(getResources().getColor(R.color.white));
                            imgone.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgsecond.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgthird.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.edite_text_hint_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgfour.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.edite_text_hint_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgfive.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.edite_text_hint_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                            linearone.setVisibility(View.GONE);
                            linearsecond.setVisibility(View.VISIBLE);
                            linearthird.setVisibility(View.GONE);
                            linearfour.setVisibility(View.GONE);
                            textone.setTextColor(getResources().getColor(R.color.deliveryText));
                            textsecond.setTextColor(getResources().getColor(R.color.deliveryText));
                            textthird.setTextColor(getResources().getColor(R.color.edite_text_hint_color));
                            textfour.setTextColor(getResources().getColor(R.color.edite_text_hint_color));
                            textfive.setTextColor(getResources().getColor(R.color.edite_text_hint_color));
                            doneButton.setClickable(false);
                            doneText.setText("On process...");
                            doneimage.setVisibility(View.GONE);
                            sendimage.setVisibility(View.VISIBLE);

                            break;
                        case "third":
                            cardone.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardsecond.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardthird.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardfour.setCardBackgroundColor(getResources().getColor(R.color.white));
                            cardfive.setCardBackgroundColor(getResources().getColor(R.color.white));
                            imgone.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgsecond.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgthird.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgfour.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.edite_text_hint_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgfive.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.edite_text_hint_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                            linearone.setVisibility(View.GONE);
                            linearsecond.setVisibility(View.GONE);
                            linearthird.setVisibility(View.VISIBLE);
                            linearfour.setVisibility(View.GONE);
                            textone.setTextColor(getResources().getColor(R.color.deliveryText));
                            textsecond.setTextColor(getResources().getColor(R.color.deliveryText));
                            textthird.setTextColor(getResources().getColor(R.color.deliveryText));
                            textfour.setTextColor(getResources().getColor(R.color.edite_text_hint_color));
                            textfive.setTextColor(getResources().getColor(R.color.edite_text_hint_color));
                            doneButton.setClickable(false);
                            doneText.setText("On process...");
                            doneimage.setVisibility(View.GONE);
                            sendimage.setVisibility(View.VISIBLE);

                            break;
                        case "forth":
                            cardone.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardsecond.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardthird.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardfour.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardfive.setCardBackgroundColor(getResources().getColor(R.color.white));
                            imgone.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgsecond.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgthird.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgfour.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgfive.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.edite_text_hint_color), android.graphics.PorterDuff.Mode.MULTIPLY);
                            linearone.setVisibility(View.GONE);
                            linearsecond.setVisibility(View.GONE);
                            linearthird.setVisibility(View.GONE);
                            linearfour.setVisibility(View.VISIBLE);
                            textone.setTextColor(getResources().getColor(R.color.deliveryText));
                            textsecond.setTextColor(getResources().getColor(R.color.deliveryText));
                            textthird.setTextColor(getResources().getColor(R.color.deliveryText));
                            textfour.setTextColor(getResources().getColor(R.color.deliveryText));
                            textfive.setTextColor(getResources().getColor(R.color.edite_text_hint_color));
                            doneButton.setClickable(false);
                            doneText.setText("On process...");
                            doneimage.setVisibility(View.GONE);
                            sendimage.setVisibility(View.VISIBLE);

                            break;
                        case "fifth":
                            cardone.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardsecond.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardthird.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardfour.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            cardfive.setCardBackgroundColor(getResources().getColor(R.color.colorlast));
                            imgone.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgsecond.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgthird.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgfour.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                            imgfive.setColorFilter(ContextCompat.getColor(Delivery_Activity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                            linearone.setVisibility(View.GONE);
                            linearsecond.setVisibility(View.GONE);
                            linearthird.setVisibility(View.GONE);
                            linearfour.setVisibility(View.VISIBLE);
                            textone.setTextColor(getResources().getColor(R.color.deliveryText));
                            textsecond.setTextColor(getResources().getColor(R.color.deliveryText));
                            textthird.setTextColor(getResources().getColor(R.color.deliveryText));
                            textfour.setTextColor(getResources().getColor(R.color.deliveryText));
                            textfive.setTextColor(getResources().getColor(R.color.deliveryText));
                            doneText.setText("Finish");
                            doneimage.setVisibility(View.VISIBLE);
                            sendimage.setVisibility(View.GONE);
                            doneButton.setClickable(true);
                            try {
                                ButtomshetDialog();
                                Log.d("Bottom No problem","Nooo probleeeem");
                            }
                            catch (WindowManager.BadTokenException e) {
                                // fuck you
                                Log.d("Bottom problem","here is the probleeeeem");
                            }
                            break;

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Delivery_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ButtomshetDialog() {
        bottomSheetDialog = new BottomSheetDialog(Delivery_Activity.this, R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.delivery_done_buttom_sheet);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();
        RelativeLayout CheckButton = bottomSheetDialog.findViewById(R.id.check_procces);

        CheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });

    }

    public void OnClick() {
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Delivery_Activity.this, MainActivity.class));
                finishAffinity();
            }
        });
    }

    public void initialisation() {
        doneimage = findViewById(R.id.done_image);
        sendimage = findViewById(R.id.send_houre);
        doneText = findViewById(R.id.text_button);
        textfive = findViewById(R.id.textfive);
        textfour = findViewById(R.id.textfour);
        textthird = findViewById(R.id.textthird);
        textsecond = findViewById(R.id.textsecond);
        textone = findViewById(R.id.textone);
        cardone = findViewById(R.id.one);
        cardsecond = findViewById(R.id.second);
        cardthird = findViewById(R.id.third);
        cardfour = findViewById(R.id.four);
        cardfive = findViewById(R.id.five);
        imgone = findViewById(R.id.imgone);
        imgsecond = findViewById(R.id.imgsecond);
        imgthird = findViewById(R.id.imgthird);
        imgfour = findViewById(R.id.imgfour);
        imgfive = findViewById(R.id.imgfive);
        linearone = findViewById(R.id.linearone);
        linearsecond = findViewById(R.id.linearsecond);
        linearthird = findViewById(R.id.linearthird);
        linearfour = findViewById(R.id.linearfour);
        doneButton = findViewById(R.id.done);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Setting_Action_Bar_Status_Bar() {

        //Hiding action bar
        getSupportActionBar().hide();
        // setting the keyboard
        // Utils.setUpKeybaord(findViewById(R.id.succes), Delivery_Activity.this);

        this.getWindow().setStatusBarColor(Color.WHITE);

        // to change the color of the icons in status bar to dark
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }
        // to change the color of the icons in the navigation bar to dark
        this.getWindow().setNavigationBarColor(Color.WHITE); //setting bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS);
        }
    }
}