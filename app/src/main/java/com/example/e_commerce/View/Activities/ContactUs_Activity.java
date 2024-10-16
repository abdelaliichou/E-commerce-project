package com.example.e_commerce.View.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.R;

public class ContactUs_Activity extends AppCompatActivity {

    RelativeLayout emailLayout, phoneLayout, facebook ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Setting_Ui();
        initialisation();
        OnClicks();


    }

    public void OnClicks() {
        emailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setData(Uri.parse("mailto:"));
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"abdelaliichou200@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "How was your experience in my application ?");
                email.putExtra(Intent.EXTRA_TEXT, "");
                email.setType("message/rfc822");

                if (email.resolveActivity(ContactUs_Activity.this.getPackageManager()) != null) {
                    startActivity(email);
                }
            }
        });
        phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0799792704"));
                startActivity(intent);
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/ali.ichou.31/")));
            }
        });
    }

    public void initialisation() {
        emailLayout = findViewById(R.id.email);
        phoneLayout = findViewById(R.id.phone);
        facebook = findViewById(R.id.facebook);
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Setting_Ui() {

        //Hiding action bar
        getSupportActionBar().hide();
        // setting the keyboard
        // Utils.setUpKeybaord(findViewById(R.id.parent),MainActivity.this);

        this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorlast));

        // to change the color of the icons in status bar to dark
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            this.getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
//        }
        // to change the color of the icons in the navigation bar to dark
        this.getWindow().setNavigationBarColor(getResources().getColor(R.color.colorlast)); //setting bar color
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            this.getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS);
//        }
    }
}