package com.example.g1_admin.DBHelper;

import android.util.Log;
import android.util.MonthDisplayHelper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DBHelper {

    private static final String TAG = "DBHelper";

    // Instance  of the Firebase Database
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    // Creates a new Dish object and ads it to the Database
    // This method also allows to update all the values from a dish element
    private void addDish(String dishId, String name, String category, String description, Double price) {
        Dish dish = new Dish(dishId,name,category,description,price);
        mDatabase.child("dish").child(dishId).setValue(dish);
    }

    // Replaces the name from a single dish element
    private void replaceName(String dishId, String newName) {
        mDatabase.child("dish").child(dishId).child("name").setValue(newName);
    }

    // Replaces the category from a single dish element
    private void replaceCategory(String dishIs, String newCategory) {
        mDatabase.child("dish").child(dishIs).child("category").setValue(newCategory);
    }

    // Replaces the description from a single dish element
    private void replaceDescription(String dishId, String newDescription) {
        mDatabase.child("dish").child(dishId).child("description").setValue(newDescription);
    }

    // Replaces the price from a single dish element
    private void replacePrice(String dishId, Double newPrice) {
        mDatabase.child("dish").child(dishId).child("price").setValue(newPrice);
    }

    // Removes a values from a single dish element
    private void removeValue(String dishId, String type) {
        mDatabase.child("dish").child(dishId).child(type).removeValue();
    }

    // Removes a hole Dish element
    private void removeDish(String dishId) {
        mDatabase.child("dish").child(dishId).removeValue();
    }

    // Receives a DataSnapshot that contains the values from a specific location on the database
    private void readDataSnapShot(DatabaseReference mDishReference) {
        ValueEventListener dishListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Dish object and use the values to update the UI
                Dish dish = dataSnapshot.getValue(Dish.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDishReference.addValueEventListener(dishListener);
    }
}
