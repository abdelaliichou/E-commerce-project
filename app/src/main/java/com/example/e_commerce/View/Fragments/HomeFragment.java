package com.example.e_commerce.View.Fragments;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.example.e_commerce.Controler.AllItemsAdapter;
import com.example.e_commerce.Controler.categories_adapter;
import com.example.e_commerce.Modele.Animations;
import com.example.e_commerce.Modele.ItemsModel;
import com.example.e_commerce.Modele.Utils;
import com.example.e_commerce.R;
import com.example.e_commerce.View.Activities.SearchActivity;
import com.example.e_commerce.View.Activities.ViewAllCategoriesActivity;
import com.example.e_commerce.View.Activities.ViewAllItemsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class HomeFragment extends Fragment {
    ImageView searchImage , bigImage;
    public static ImageSlider imageSlider;
    TextView HeaderText, popularItemsText, secondHeaderText, CategoriesText, SuggestedItemsText, viewAllText1, viewAllText2, viewAllText3;
    FirebaseAuth auth;
    DatabaseReference Root;
    MaterialCardView parent_slide_card;
    public static RecyclerView recyclerone, recyclersecond, recyclerthird;
    public static categories_adapter adapter;
    public static AllItemsAdapter adapter3, adapter2;
    static View view;
    public static ProgressBar progressBar1, progressBar2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initialisation(view);
        handlingOnClicks();
        SettingUpImageSlider();
        SettingRecyclers(view);
        SettingAnimation();
        setting_ui();
        SettingHeaderTextUser();

        return view;
    }

    private void SettingAnimation() {
        Animations.FromeRightToLeftCard(parent_slide_card);
        Animations.FromeLeftToRight(HeaderText);
        Animations.FromeLeftToRight(popularItemsText);
        Animations.FromeLeftToRight(CategoriesText);
        Animations.FromeLeftToRight(SuggestedItemsText);
        Animations.FromRightToLeft1(viewAllText1);
        Animations.FromRightToLeft1(viewAllText2);
        Animations.FromRightToLeft1(viewAllText3);
        Animations.FromeLeftToRight1(secondHeaderText);

    }

    public void initialisation(View view) {
        bigImage = view.findViewById(R.id.second_big_image);
        searchImage = view.findViewById(R.id.search);
        progressBar1 = view.findViewById(R.id.populaire_items_progress);
        progressBar2 = view.findViewById(R.id.all_items_progress);
        progressBar2.setVisibility(View.VISIBLE);
        auth = FirebaseAuth.getInstance();
        HeaderText = view.findViewById(R.id.header_text);
        popularItemsText = view.findViewById(R.id.popular_items_text);
        secondHeaderText = view.findViewById(R.id.second_header);
        CategoriesText = view.findViewById(R.id.category_text);
        SuggestedItemsText = view.findViewById(R.id.suggested_items_text);
        viewAllText1 = view.findViewById(R.id.first_view_all_text);
        viewAllText2 = view.findViewById(R.id.second_view_all_text);
        viewAllText3 = view.findViewById(R.id.third_view_all_text);
        imageSlider = view.findViewById(R.id.slider);
        parent_slide_card = view.findViewById(R.id.parent_card);
        Root = FirebaseDatabase.getInstance().getReference();
        recyclerone = view.findViewById(R.id.first_recycler);
        recyclersecond = view.findViewById(R.id.second_recyclerview);
        recyclerthird = view.findViewById(R.id.third_recycler);}

    public void handlingOnClicks() {
        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {
                Toast.makeText(getActivity().getApplication(), "Clicked on " + i, Toast.LENGTH_SHORT).show();
            }
        });
        viewAllText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ViewAllCategoriesActivity.class));
            }
        });
        viewAllText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ViewAllItemsActivity.class));
            }
        });
        viewAllText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ViewAllItemsActivity.class));
            }
        });
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
    }

    public void SettingUpImageSlider() {
       // Picasso.get().load("https://images.unsplash.com/photo-1556740714-a8395b3bf30f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80").into(bigImage);
        imageSlider.setImageList(Utils.GetSlideList(), ScaleTypes.CENTER_CROP);
    }

    public static void SettingRecyclers(View view) {

        // setting first recycler
        adapter = new categories_adapter(Utils.CategoriesList(), view.getContext());
        recyclerone.setAdapter(adapter);
        recyclerone.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        // setting second recycler
        adapter2 = new AllItemsAdapter(Utils.initialPopularItemsList(), view.getContext());
        recyclersecond.setAdapter(adapter2);
        recyclersecond.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        //setting third recycler
        adapter3 = new AllItemsAdapter(Utils.AllItemsList(), view.getContext());
        recyclerthird.setAdapter(adapter3);
        recyclerthird.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

    }

    public void SettingHeaderTextUser() {
//        GoogleSignInAccount sign = GoogleSignIn.getLastSignedInAccount(HomeFragment.view.getContext());
//        if (sign != null) {
//            // means that the user has signed in using google
//            HeaderText.setText(sign.getDisplayName());
//        }
        Root.child("Users").child(auth.getCurrentUser().getUid()).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue(String.class) != null) {
                    HeaderText.setText("Hello " + snapshot.getValue(String.class));
                } else {
                    HeaderText.setText("Hello user");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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