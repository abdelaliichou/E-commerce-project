package com.example.e_commerce.View.Fragments;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.e_commerce.Modele.Animations;
import com.example.e_commerce.Modele.UserModel;
import com.example.e_commerce.R;
import com.example.e_commerce.View.Activities.AllOrders_Activity;
import com.example.e_commerce.View.Activities.ContactUs_Activity;
import com.example.e_commerce.View.Activities.Delivery_Activity;
import com.example.e_commerce.View.Activities.Login_activity;
import com.example.e_commerce.View.Activities.OrderDetails_Activity;
import com.example.e_commerce.View.Activities.Update_Activity;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;


public class ProfileFragment extends Fragment {
    MaterialCardView DeliveryProccesButton, OrdersHistoryButton, updateButton, contactLayout, LogoutButton;
    TextView name, email;
    MaterialAlertDialogBuilder progressDialog;
    AlertDialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initialisation(view);
        handlingOnClicks();
        setting_ui();
        initiale_elemets();
        SetAnimations(view);
        return view;
    }

    public void SetAnimations(View view) {
        Animations.FromeLeftToRight(view.findViewById(R.id.info));
        Animations.FromeRightToLeftCardd(view.findViewById(R.id.parent));
        Animations.FromeRightToLeftCard1(view.findViewById(R.id.localisation));
        Animations.FromeRightToLeftCard2(view.findViewById(R.id.emaillayout));
        Animations.FromeRightToLeftCard3(view.findViewById(R.id.phone));
        Animations.FromeRightToLeftCard4(view.findViewById(R.id.contact));
        Animations.FromeRightToLeftCard5(view.findViewById(R.id.logout));
    }

    public void initiale_elemets() {
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel user = snapshot.getValue(UserModel.class);
                name.setText(user.getName());
                email.setText(user.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void initialisation(View view) {
        DeliveryProccesButton = view.findViewById(R.id.localisation);
        OrdersHistoryButton = view.findViewById(R.id.emaillayout);
        updateButton = view.findViewById(R.id.phone);
        LogoutButton = view.findViewById(R.id.logout);
        contactLayout = view.findViewById(R.id.contact);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
    }

    public void handlingOnClicks() {

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getActivity(), Update_Activity.class));
            }
        });

        OrdersHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AllOrders_Activity.class));
            }
        });

        contactLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getActivity(), ContactUs_Activity.class));
            }
        });

        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new MaterialAlertDialogBuilder(getContext());
                progressDialog.setTitle("Déconnecter ?");
                progressDialog.setMessage("Vous êtes sûr que vous voulez déconnecter ?");
                progressDialog.setCancelable(true);
                progressDialog.setBackground(getResources().getDrawable(R.drawable.alert_dialog_back));
                progressDialog.setIcon(R.drawable.ic_logout);
                progressDialog.setPositiveButton("oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Paper.book().destroy();
                                FirebaseAuth.getInstance().signOut();
                                getActivity().finishAffinity();
                                startActivity(new Intent(view.getContext(), Login_activity.class));
                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // dismiss the orders
                                dialog.dismiss();
                            }
                        });
                progressDialog.setCancelable(false);
                dialog = progressDialog.show();
                dialog.show();
            }
        });

        DeliveryProccesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OrderDetails_Activity.class).putExtra("id","last_order"));
            }
        });

    }

    public void setting_ui() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.white));
        }

        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.white));

        // to change the color of the icons in status bar to dark
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getActivity().getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }
    }
}