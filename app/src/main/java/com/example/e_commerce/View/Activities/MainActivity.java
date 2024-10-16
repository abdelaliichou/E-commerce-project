package com.example.e_commerce.View.Activities;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowInsetsController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.e_commerce.R;
import com.example.e_commerce.View.Fragments.FavoriteFragment;
import com.example.e_commerce.View.Fragments.HomeFragment;
import com.example.e_commerce.View.Fragments.OrdersFragment;
import com.example.e_commerce.View.Fragments.ProfileFragment;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {
    public static AnimatedBottomBar bottomBar;

    HomeFragment homeFragment;
    OrdersFragment ordersFragment;
    ProfileFragment profileFragment;
    FavoriteFragment favoriteFragment;
    Fragment fragment;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setting the status bar , action bar colors , keyboard interaction
        Setting_Ui();

        initialisation();
        onclicks();
    }

    public void initialisation(){
        bottomBar = findViewById(R.id.buttom);
        homeFragment = new HomeFragment();
        ordersFragment = new OrdersFragment();
        favoriteFragment = new FavoriteFragment();
        profileFragment = new ProfileFragment();
    }

    public void onclicks() {
        bottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {

            }

            @Override
            public void onTabSelected(int i, @Nullable AnimatedBottomBar.Tab tab, int i1, @NonNull AnimatedBottomBar.Tab tab1) {
                fragment = null;
                switch (tab1.getId()) {
                    case R.id.Home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.Fav:
                        fragment = new FavoriteFragment();
                        bottomBar.clearBadgeAtTabId(R.id.Fav);
                        break;
                    case R.id.Pay:
                        fragment = new OrdersFragment();
                        break;
                    case R.id.User:
                        fragment = new ProfileFragment();
                        break;
                }
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            //.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                            .replace(R.id.frame, fragment)
                            .commit();
                }
            }
        });

    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Setting_Ui() {

        // to put the first fragment in the frame layout in the moment that we entered the app
        getSupportFragmentManager().beginTransaction()
                //.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.frame, new HomeFragment())
                .commit();

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