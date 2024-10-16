package com.example.e_commerce.View.Fragments;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.e_commerce.Controler.AllItemsAdapter;
import com.example.e_commerce.Controler.FavoriteItemsAdapter;
import com.example.e_commerce.Modele.Utils;
import com.example.e_commerce.R;

public class FavoriteFragment extends Fragment {

    SwipeRefreshLayout refresh;
    public static FavoriteItemsAdapter adapter;
    public static RecyclerView Favoritterecycler;
    public static ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        initialisation(view);
        SettingRecyclers(view);
        setting_ui();
        refresh();
        return view;
    }

    public void refresh() {
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.items_list = Utils.getFavoriteItems();
                adapter.notifyDataSetChanged();
                refresh.setRefreshing(false);
            }
        });
    }

    public void initialisation(View view) {
        refresh = view.findViewById(R.id.refresh);
        progressBar = view.findViewById(R.id.Favorite_progress);
        progressBar.setVisibility(View.VISIBLE);
        Favoritterecycler = view.findViewById(R.id.Favorite_items);
    }

    public void SettingRecyclers(View view) {
        // setting  recycler
        adapter = new FavoriteItemsAdapter(Utils.getFavoriteItems(), view.getContext());
        Favoritterecycler.setAdapter(adapter);
        Favoritterecycler.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
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