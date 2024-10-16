package com.example.e_commerce.View.Activities;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.Modele.Animations;
import com.example.e_commerce.Modele.UserModel;
import com.example.e_commerce.Modele.Utils;
import com.example.e_commerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_activity extends AppCompatActivity {


    ImageView mainImage;
    TextView Login_text, secondtext, maintext;
    RelativeLayout LoginginButton;
    LinearLayout supportLinearLayout;
    TextInputLayout fullnametext, phonenumbertext, emailtext, confirmpasswordtext, passwordtext;
    String userName, userEmail, userPassword, userConfirmPassword;
    int userNumber;
    String emailPattern = "[a-zA-Z0-9._-]+@esi-sba.dz";
    String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    MaterialAlertDialogBuilder progressDialog;
    AlertDialog dialog ;
    DatabaseReference Root;
    FirebaseAuth auth;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //setting the status bar , action bar colors , keyboard interaction
        Setting_Action_Bar_Status_Bar();

        initialisation();
        handlingOnClicks();
        handlingAnimation();


    }

    private void handlingAnimation() {
        Animations.FromUpToDownSignup(mainImage);
        Animations.FromeRightToLeft(LoginginButton);
        Animations.FromeDownToUp(supportLinearLayout);
        Animations.FromeRightToLeftEditetext4(confirmpasswordtext);
        Animations.FromeRightToLeftEditetext1(emailtext);
        Animations.FromeRightToLeftEditetext(fullnametext);
        Animations.FromeRightToLeftEditetext3(passwordtext);
        Animations.FromeRightToLeftEditetext2(phonenumbertext);
        Animations.FromeLeftToRight(maintext);
        Animations.FromeLeftToRight1(secondtext);
    }

    public void initialisation() {
        LoginginButton = findViewById(R.id.login_button);
        Login_text = findViewById(R.id.go_to_login);
        secondtext = findViewById(R.id.signup_second_text);
        maintext = findViewById(R.id.signup_main_text);
        mainImage = findViewById(R.id.main_img);
        supportLinearLayout = findViewById(R.id.support_layout);
        fullnametext = findViewById(R.id.full_name_parent);
        phonenumbertext = findViewById(R.id.phone_parent);
        emailtext = findViewById(R.id.email_parent);
        confirmpasswordtext = findViewById(R.id.confirm_password_parent);
        passwordtext = findViewById(R.id.password_parent);
        progressDialog = new MaterialAlertDialogBuilder(this);
        Root = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
    }

    public void handlingOnClicks() {
        LoginginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettingUserData();
                if (userName.isEmpty()) {
                    fullnametext.getEditText().setError("Enter your name !");
                } else if ( userEmail.isEmpty()) {
                    emailtext.getEditText().setError("Enter your email !");
                } else if (!userEmail.matches(emailPattern) && !userEmail.matches(emailPattern2)) {
                    emailtext.getEditText().setError("Invalid email form !");
                } else if ( userNumber == -1 || String.valueOf(userNumber).isEmpty()) {
                    phonenumbertext.getEditText().setError("Enter your number !");
                } else if (userPassword.isEmpty()) {
                    passwordtext.getEditText().setError("Enter your password !");
                } else if (userPassword.length() < 7) {
                    passwordtext.getEditText().setError("Short password !");
                } else if (userConfirmPassword.isEmpty()){
                    confirmpasswordtext.getEditText().setError("Enter your password !");
                }  else if (!userPassword.equals(userConfirmPassword)) {
                    confirmpasswordtext.getEditText().setError("Passwords don't match !");
                } else {
                    progressDialog.setTitle("Wait a minute please !");
                    progressDialog.setMessage("We are registering you , you'll be ready in just a moment ...");
                    progressDialog.setCancelable(false);
                    progressDialog.setBackground(getResources().getDrawable(R.drawable.alert_dialog_back));
                    progressDialog.setIcon(R.drawable.ic__cloud_upload);
                    progressDialog.setCancelable(false);
                    dialog = progressDialog.show();
                    dialog.show();

                    userSignUp(userName, userEmail, userPassword, userNumber);
                }
            }
        }
        );
        Login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup_activity.this, Login_activity.class));
                finishAffinity();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Setting_Action_Bar_Status_Bar() {

        //Hiding action bar
        getSupportActionBar().hide();
        // setting the keyboard
        Utils.setUpKeybaord(findViewById(R.id.parentt), Signup_activity.this);

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
        userName = fullnametext.getEditText().getText().toString().trim();
        userEmail = emailtext.getEditText().getText().toString().trim();
        try {
            userNumber = Integer.parseInt(phonenumbertext.getEditText().getText().toString().trim());
        } catch (NumberFormatException e){
            userNumber = -1 ;
        }
        userPassword = passwordtext.getEditText().getText().toString().trim();
        userConfirmPassword = confirmpasswordtext.getEditText().getText().toString().trim();
    }

    public void userSignUp(String name, String email, String password, int number) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    UserModel user = new UserModel(name, email, number);
                    Root.child("Users").child(auth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Signup_activity.this,"Successfully Signed in !",Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                startActivity(new Intent(Signup_activity.this, MainActivity.class));
                            } else {
                                Toast.makeText(Signup_activity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    });
                } else {
                    Toast.makeText(Signup_activity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

    }

}