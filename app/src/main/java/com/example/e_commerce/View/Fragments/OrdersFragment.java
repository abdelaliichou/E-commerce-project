package com.example.e_commerce.View.Fragments;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

import com.example.e_commerce.Controler.Card_items_Adapter;
import com.example.e_commerce.Modele.CardItemModel;
import com.example.e_commerce.Modele.Delivery_Order_Model;
import com.example.e_commerce.Modele.Utils;
import com.example.e_commerce.R;
import com.example.e_commerce.View.Activities.AllOrders_Activity;
import com.example.e_commerce.View.Activities.Delivery_Activity;
import com.example.e_commerce.View.Activities.OrderDetails_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OrdersFragment extends Fragment {

    RelativeLayout payButton;
    SwipeRefreshLayout refresh;
    RecyclerView card_recycler;
    public static ImageView emptyImage;
    static public Card_items_Adapter adapter;
    TextView totalPrice;
    TextView totalItems;
    MaterialAlertDialogBuilder progressDialog, progressDialog2;
    AlertDialog dialog, dialog2;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    public static ProgressBar progressBar;
    BottomSheetDialog bottomSheetDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        initialisation(view);
        SettingRecycler(view);
        setting_ui();
        initializePricesTexts();
        refreshOrders();
        OnClicks();
        return view;
    }

    public void OnClicks() {
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.list.size() != 0) {
                    ConfirmationDialog();
                } else {
                    Toast.makeText(getContext(), "Your basket is empty !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void ConfirmationDialog() {
        progressDialog = new MaterialAlertDialogBuilder(HomeFragment.view.getContext());
        progressDialog.setTitle("Confirmation dialog !");
        progressDialog.setMessage("You want to send this order to the store ?");
        progressDialog.setCancelable(true);
        progressDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        progressDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SendOrder();
            }
        });
        progressDialog.setBackground(getResources().getDrawable(R.drawable.alert_dialog_back));
        progressDialog.setIcon(com.firebase.ui.auth.R.drawable.fui_ic_check_circle_black_128dp);
        progressDialog.setCancelable(true);
        dialog = progressDialog.show();
        dialog.show();
    }

    public void SendOrder() {

        progressDialog2 = new MaterialAlertDialogBuilder(HomeFragment.view.getContext());
        progressDialog2.setTitle("Wait a minute please !");
        progressDialog2.setMessage("Your order is being send to the store !");
        progressDialog2.setCancelable(false);
        progressDialog2.setBackground(getResources().getDrawable(R.drawable.alert_dialog_back));
        progressDialog2.setIcon(R.drawable.ic__cloud_upload);
        progressDialog2.setCancelable(false);
        dialog2 = progressDialog2.show();
        dialog2.show();

        RejesterOrder(); // saving this order after that we will send it to the store

    }

    public void RejesterOrder(){

        Delivery_Order_Model Order = new Delivery_Order_Model();
        String date = new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
        String time = new SimpleDateFormat("HH:mm:ss a").format(Calendar.getInstance().getTime());
        // this value is the parent name of the command and it is also the id of that command
        String pushValue = FirebaseDatabase.getInstance().getReference().push().getKey();

        Order.setTime(time);
        Order.setDate(date);
        Order.setOdrderID(pushValue);
        Order.setUserEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail().trim());
        Order.setTotalPrice(totalPrice.getText().toString().trim());
        Order.setProductsNumber(totalItems.getText().toString().trim());

        FirebaseDatabase.getInstance().getReference().child("Delivery").child("OnProcess").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(pushValue).setValue(Order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Processing the order... ", Toast.LENGTH_SHORT).show();
                    ChargeOrderWithCardItems(pushValue);
                } else {
                    Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void ChargeOrderWithCardItems(String pushValue) {
        // goes to the same precedent order that we created and charge his "orderElements" by the elements that exist in the "UserCard"

        FirebaseDatabase.getInstance().getReference().child("UserCard").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot product : snapshot.getChildren()) {
                        if (product.exists()) {
                            FirebaseDatabase.getInstance().getReference().child("Delivery").child("OnProcess").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(pushValue).child("orderElements").push().setValue(product.getValue(CardItemModel.class)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // delete all the orders from the user card after we have added them to the  "Delivery"  child
                                        root.child("UserCard").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(product.getKey()).removeValue(new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                // we have deleted the product from the card after we added it to the "Produits_Vendeus" child
                                                adapter.notifyDataSetChanged();
                                            }
                                        });
                                    } else {
                                        Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog2.dismiss();
                                    }
                                }
                            });
                        }
                    }
                    // the transfer data process is done
                    ButtomshetDialog();
                    StartDelivery(pushValue);
                    dialog2.dismiss();
                    emptyImage.setVisibility(View.VISIBLE);
                    adapter.list.clear();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                dialog2.dismiss();
            }
        });
    }

    public void StartDelivery(String OrderID){
        // the child to start the delivery , initialled with "first"
        FirebaseDatabase.getInstance().getReference().child("Delivery").child("Started").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(OrderID).child("status").setValue("first").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getActivity(), "Delivering start !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void ButtomshetDialog(){
        bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.layout_bottom_sheet);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();
        RelativeLayout CheckButton = bottomSheetDialog.findViewById(R.id.check_procces);

        CheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OrderDetails_Activity.class).putExtra("id","last_order"));
                bottomSheetDialog.dismiss();
            }
        });

    }

    public void initialisation(View view) {
        emptyImage = view.findViewById(R.id.empty);
        payButton = view.findViewById(R.id.pay);
        refresh = view.findViewById(R.id.refresh);
        progressBar = view.findViewById(R.id.card_progress);
        progressBar.setVisibility(View.VISIBLE);
        card_recycler = view.findViewById(R.id.Card_items);
        totalItems = view.findViewById(R.id.product_number);
        totalPrice = view.findViewById(R.id.products_total_price);
    }

    public void SettingRecycler(View view) {
        adapter = new Card_items_Adapter(Utils.getCardItemsList(), view.getContext() ,getActivity().getClass());
        card_recycler.setAdapter(adapter);
        card_recycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
    }

    public void setting_ui() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorgray));
        }

        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorgray));

        // to change the color of the icons in status bar to dark
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getActivity().getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }
    }

    public void initializePricesTexts() {
        root.child("UserCard").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int productsnumer = 0;
                Float allprice = 0f;
                for (DataSnapshot i : snapshot.getChildren()) {
                    if (i.exists()) {
                        // Log.d("heeeeeeeeeeeere", i.getValue(CardItemModel.class).getQuantity());
                        productsnumer = productsnumer + Integer.parseInt(i.getValue(CardItemModel.class).getQuantity());
                        allprice = allprice + Integer.parseInt(i.getValue(CardItemModel.class).getQuantity()) * Float.parseFloat(i.getValue(CardItemModel.class).getPrice());
                    }
                }
                totalItems.setText(String.valueOf(productsnumer));
                totalPrice.setText(String.valueOf(allprice));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void refreshOrders() {
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.list = Utils.getCardItemsList();
                adapter.notifyDataSetChanged();
                initializePricesTexts();
                refresh.setRefreshing(false);
            }
        });
    }

}