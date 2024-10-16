package com.example.e_commerce.View.Activities;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.e_commerce.Modele.Animations;
import com.example.e_commerce.Modele.Utils;
import com.example.e_commerce.R;
import com.google.android.material.snackbar.Snackbar;

public class change_password_succes extends AppCompatActivity {

    RelativeLayout SubmitButton;
    TextView maintext, secondtext;
    ImageView mainImage;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_succes);

        SettingTheUi();
        initialisation();
        handlingAnimation();
        hadlingOnClicks();

        Snackbar.make(findViewById(R.id.succes), "Password updated successfully !", Snackbar.LENGTH_SHORT)
                .setText("Your password has been updated successfully !")
                .setTextColor(getResources().getColor(R.color.white))
                .setBackgroundTint(getResources().getColor(R.color.colorlast))
                .setAction("Got it !", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.mainyellow))
                .show();
    }

    private void hadlingOnClicks() {
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(change_password_succes.this, Login_activity.class));
                finishAffinity();
            }
        });
    }

    private void handlingAnimation() {
        Animations.FromUpToDown(mainImage);
        Animations.FromeLeftToRight(maintext);
        Animations.FromeLeftToRight1(secondtext);
        Animations.FromeRightToLeft(SubmitButton);
    }

    public void initialisation() {
        SubmitButton = findViewById(R.id.all_good_submit_button);
        maintext = findViewById(R.id.all_good_main_text);
        secondtext = findViewById(R.id.all_good_second_text);
        mainImage = findViewById(R.id.all_good_image);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void SettingTheUi(){
        // setting the keyboard
        Utils.setUpKeybaord(findViewById(R.id.succes),change_password_succes.this);

        getSupportActionBar().hide();
        this.getWindow().setStatusBarColor(this.getColor(R.color.white));

        // to change the color of the icons in status bar to dark
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }
        // to change the color of the icons in the navigation bar to dark
        getWindow().setNavigationBarColor(ContextCompat.getColor(change_password_succes.this,R.color.white)); //setting bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS);
        }
    }

}