package com.example.e_commerce.View.Activities;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.Modele.Animations;
import com.example.e_commerce.Modele.UserModel;
import com.example.e_commerce.Modele.Utils;
import com.example.e_commerce.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class Forget_password_activity extends AppCompatActivity {

    RelativeLayout SubmitButton;
    TextView maintext, secondtext;
    ImageView mainImage;
    TextInputLayout phoneLayout;
    String userNumber;
    DatabaseReference Root;
    ProgressBar progressBar;
    UserModel oldUser = null;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        //setting the status bar , action bar colors , keyboard interaction
        Setting_Action_Bar_Status_Bar();

        initialisation();
        handlingAnimation();
        hadlingOnClicks();
    }


    private void handlingAnimation() {
        Animations.FromUpToDown(mainImage);
        Animations.FromeLeftToRight(maintext);
        Animations.FromeLeftToRight1(secondtext);
        Animations.FromeRightToLeftEditetext2(phoneLayout);
        Animations.FromeRightToLeft(SubmitButton);
    }

    public void initialisation() {
        Root = FirebaseDatabase.getInstance().getReference();
        SubmitButton = findViewById(R.id.forget_password_submit_button);
        maintext = findViewById(R.id.signup_main_text);
        mainImage = findViewById(R.id.forget_password_image);
        phoneLayout = findViewById(R.id.forget_password_email_layout);
        secondtext = findViewById(R.id.signup_second_text);
        progressBar = findViewById(R.id.progres);
    }

    public void hadlingOnClicks() {
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gettingUserData();

                if (userNumber.isEmpty()) {
                    phoneLayout.getEditText().setError("Enter your phone number !");
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    verifyingPhoneNumber(userNumber);
                }
            }
        });
    }

    private void verifyingPhoneNumber(String number) {
        Root.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // checking if the user with email exists
                for (DataSnapshot user : snapshot.getChildren()) {
                    if (String.valueOf(user.getValue(UserModel.class).getPhonenumber()) != null) {
                        if (String.valueOf(user.getValue(UserModel.class).getPhonenumber()).equals(number)) {
                            oldUser = user.getValue(UserModel.class);
                            break;
                        }
                    }
                }
                if (oldUser == null) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Forget_password_activity.this, "No account with this phone number !", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Forget_password_activity.this, "Sending message ...", Toast.LENGTH_SHORT).show();
                    Log.d("============> ", number);
                    SendOTPCode(number);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Forget_password_activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void SendOTPCode(String number) {

        progressBar.setVisibility(View.VISIBLE);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+213" + number, // algeria
                60,
                TimeUnit.SECONDS,
                Forget_password_activity.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(Forget_password_activity.this, OTP_verification_activity.class);
 // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // this is for clearing backstack activities , but here im using the finish()
                        // we are sending the code that the firebase will send to the user phone
                        intent.putExtra("Code", verificationId);
                        // we are sending the phone number just to shoe it in the screen otp screen
                        intent.putExtra("phonenumber", userNumber);
                        // we are sending the email to use it when sending the email verification
                        intent.putExtra("email", oldUser.getEmail());
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Forget_password_activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Setting_Action_Bar_Status_Bar() {

        //Hiding action bar
        getSupportActionBar().hide();
        // setting the keyboard
        Utils.setUpKeybaord(findViewById(R.id.parent), Forget_password_activity.this);

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

    public void gettingUserData() {
        userNumber = phoneLayout.getEditText().getText().toString().trim();
    }

}