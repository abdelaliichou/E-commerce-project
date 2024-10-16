package com.example.e_commerce.View.Activities;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.Modele.UserModel;
import com.example.e_commerce.Modele.Utils;
import com.example.e_commerce.R;
import com.example.e_commerce.View.Fragments.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update_Activity extends AppCompatActivity {

    TextInputLayout nameLayout, phoneLayout;
    RelativeLayout updateButton;
    MaterialAlertDialogBuilder progressDialog;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Setting_Ui();
        initialisation();
        initialInformations();
        OnClicks();

    }

    public void initialisation() {
        nameLayout = findViewById(R.id.name);
        phoneLayout = findViewById(R.id.phone);
        updateButton = findViewById(R.id.update);
    }

    public void initialInformations() {
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserModel user = snapshot.getValue(UserModel.class);
                    phoneLayout.getEditText().setText("0" + String.valueOf(user.getPhonenumber()));
                    nameLayout.getEditText().setText(user.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void OnClicks() {
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckInformations();
            }
        });
    }

    public void CheckInformations() {
        if (phoneLayout.getEditText().getText().toString().isEmpty()) {
            phoneLayout.getEditText().setError("Enter phone number !");
        } else if (nameLayout.getEditText().getText().toString().isEmpty()) {
            nameLayout.getEditText().setError("Enter your name !");
        } else {
            progressDialog = new MaterialAlertDialogBuilder(Update_Activity.this);
            progressDialog.setTitle("Updating profile !");
            progressDialog.setMessage("We are updating your profile, please wait a minute !");
            progressDialog.setCancelable(false);
            progressDialog.setBackground(getResources().getDrawable(R.drawable.alert_dialog_back));
            progressDialog.setIcon(R.drawable.update_profile_image);
            dialog = progressDialog.show();
            dialog.show();

            UpdateUser(nameLayout.getEditText().getText().toString().trim(), Integer.valueOf(phoneLayout.getEditText().getText().toString().trim()));
        }
    }

    public void UpdateUser(String name , int number) {
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("phonenumber").setValue(number).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Update_Activity.this, "Profile updated succsesfully !", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                finish();
                            } else {
                                Toast.makeText(Update_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    });
                } else {
                    Toast.makeText(Update_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Setting_Ui() {

        //Hiding action bar
        getSupportActionBar().hide();
        // setting the keyboard
        Utils.setUpKeybaord(findViewById(R.id.linear),Update_Activity.this);

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