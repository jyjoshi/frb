package com.example.frb.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frb.R;
import com.example.frb.adapters.CartAdapter;
import com.example.frb.models.Bill;
import com.example.frb.models.FoodQuantity;
import com.example.frb.models.MenuItem;
import com.example.frb.models.OrderedItem;
import com.example.frb.models.UserDB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AdminCartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    String phoneNumber;
    ArrayList<String> foodName;
    ArrayList<Integer> quantity;
    ArrayList<Integer> price;
    ArrayList<Integer> result;
    String transactionId;
    String time;
    TextView costDisplay;
    DatabaseReference root;
    DatabaseReference billRoot;
    ArrayList<FoodQuantity> itemsOrdered;
    private EditText address;
    private String stringAddress;

    int totalCost;
    Integer token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cart);

        phoneNumber = "100";
        costDisplay = findViewById(R.id.total_cost);
        recyclerView = findViewById(R.id.recyclerview_view_order);

        foodName = new ArrayList<>();
        quantity = new ArrayList<>();
        price = new ArrayList<>();
        result = new ArrayList<>();
        itemsOrdered = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartAdapter = new CartAdapter(foodName, quantity, price, result);
        recyclerView.setAdapter(cartAdapter);
        String stringForCostDisplay = "Total : " + totalCost + " Rs";
        costDisplay.setText(stringForCostDisplay);

        root = FirebaseDatabase.getInstance("https://canteen-management-systems-20a8c.asia-southeast1.firebasedatabase.app/").getReference();

        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshotChildren : snapshot.child("temp").child(phoneNumber).getChildren()) {
                    FoodQuantity temp = snapshotChildren.getValue(FoodQuantity.class);
                    if (temp.getQuantity() != 0) {
                        foodName.add(temp.getFoodName());
                        quantity.add(temp.getQuantity());
                        Log.i("FQ Add", "Added : to foodName " + foodName + " Quantitiy" + quantity);
                        for (DataSnapshot snapshot1 : snapshot.child("Menu").getChildren()) {
                            MenuItem temp2 = snapshot1.getValue(MenuItem.class);
                            if (temp.getFoodName().equals(temp2.getName())) {
                                //Retrieving the price and calculating result for the elements that have been added to the cart
                                price.add(Integer.parseInt(temp2.getPrice()));
                                result.add(temp.getQuantity() * Integer.parseInt(temp2.getPrice()));

                                //Display the total cost of all the items added to the cart
                                totalCost = sum(result);
                                String stringForCostDisplay = "Total : " + totalCost + " Rs";
                                costDisplay.setText(stringForCostDisplay);


                                Log.i("Price Add", "Added Price : " + price + " Price * Qty " + result + " Total Bill is :" + totalCost);


                                //Setting the items in the RecyclerView.
                                cartAdapter = new CartAdapter(foodName, quantity, price, result);
                                recyclerView.setAdapter(cartAdapter);

                            /*DatabaseReference totemp2 = FirebaseDatabase.getInstance("https://canteen-management-systems-20a8c.asia-southeast1.firebasedatabase.app/").getReference().child("temp2");
                            for(int i=0; i<foodName.size(); i++){
                                totemp2.child(String.valueOf(i+1)).setValue(new MenuItem(foodName.get(i), String.valueOf(price.get(i)), String.valueOf(quantity.get(i)), String.valueOf(result.get(i))));
                            }*/

                            }
                        }
                    } else {
                        root.child("temp").child(phoneNumber).child(temp.getFoodName()).removeValue();
                    }

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void adminPlaceOrder(View view) {
        //Storing time when order was placed
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        time = dtf.format(now);

        //Generating transaction id
        transactionId = root.push().getKey();
        billRoot = FirebaseDatabase.getInstance("https://canteen-management-systems-20a8c.asia-southeast1.firebasedatabase.app/").getReference().child("Bill");

        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DatabaseReference toOrderedItems = FirebaseDatabase.getInstance("https://canteen-management-systems-20a8c.asia-southeast1.firebasedatabase.app/").getReference().child("OrderedItems");
                for (DataSnapshot snapshot1 : snapshot.child("temp").child(phoneNumber).getChildren()) {
                    Log.i("TAG", "INSIDE TEMPORDERITEMS");
                    String name = snapshot1.getKey();
                    FoodQuantity val = snapshot1.getValue(FoodQuantity.class);
                    int qty = val.getQuantity();
                    for (DataSnapshot snapshot11 : snapshot.child("Menu").getChildren()) {
                        MenuItem tempItem = snapshot11.getValue(MenuItem.class);
                        if (tempItem.getName().equals(name)) {
                            Log.i("TAG", "ITEM FOUND IN MENU");
                            String price = tempItem.getPrice();
                            String result = String.valueOf(Integer.parseInt(price) * qty);
                            OrderedItem ob = new OrderedItem(transactionId, name, String.valueOf(qty), price, result);
                            String key = toOrderedItems.push().getKey();
                            toOrderedItems.child(key).setValue(ob);
                            break;
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                token = snapshot.child("Token").getValue(Integer.class) + 1;
                if (token.equals(10000)) {
                    token = 1001;
                }
                root.child("Token").setValue(token);
                UserDB temp = snapshot.child("Users").child(phoneNumber).getValue(UserDB.class);
                Log.d("Phone Number", phoneNumber);
                {
                    Log.i("TAG ", "Student");
                    Bill bill = new Bill(time, String.valueOf(totalCost), transactionId, phoneNumber, token);
                    billRoot.child(transactionId).setValue(bill);
                }
                root.child("Status").child(transactionId).setValue(Integer.toString(0)); // Set the status regardless of who ordered to 0
                Toast.makeText(getApplicationContext(), "Order Placed", Toast.LENGTH_LONG).show();
                try {
                    TimeUnit.SECONDS.sleep(2);
                    root.child("temp").child(phoneNumber).removeValue();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Intent intent = new Intent(this, AdminHomeActivity.class);
        startActivity(intent);

        }
    public int sum(ArrayList<Integer> x) {
        int total = 0;
        for (int i : x) {
            total += i;
        }
        return total;
    }
}