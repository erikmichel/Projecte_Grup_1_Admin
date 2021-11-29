package com.example.g1_admin.DBHelper;

import android.util.Log;
import android.util.MonthDisplayHelper;

import com.example.g1_admin.Model.Dish;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DBHelper {

    private static final String TAG = "DBHelper";

    // Instance  of the Firebase Database
    DatabaseReference mDatabase;

    public DBHelper(DatabaseReference mDatabase) {
        this.mDatabase = mDatabase;
    }

    // Creates a new Dish object and ads it to the Database
    // This method also allows to update all the values from a dish element

    public void addDish(String name, String category, String description, Double price) {

        //mDatabase = mDatabase.child("dish");

        /*DatabaseReference pushedPostRef = mDatabase.child("dish").push();

        String dishId = pushedPostRef.getKey();
        Log.i("testDB", "" + dishId);
        */

        Dish dish = new Dish("TEST",category,name,description,price);
        mDatabase.child("dish").child("TEST").setValue(dish);
    }

    // Replaces the name from a single dish element
    public void replaceName(Integer dishId, String newName) {
        mDatabase.child("dish").child(String.valueOf(dishId)).child("name").setValue(newName);
    }

    // Replaces the category from a single dish element
    public void replaceCategory(Integer dishIs, String newCategory) {
        mDatabase.child("dish").child(String.valueOf(dishIs)).child("category").setValue(newCategory);
    }

    // Replaces the description from a single dish element
    public void replaceDescription(Integer dishId, String newDescription) {
        mDatabase.child("dish").child(String.valueOf(dishId)).child("description").setValue(newDescription);
    }

    // Replaces the price from a single dish element
    public void replacePrice(Integer dishId, Double newPrice) {
        mDatabase.child("dish").child(String.valueOf(dishId)).child("price").setValue(newPrice);
    }

    // Removes a values from a single dish element
    public void removeValue(Integer dishId, String type) {
        mDatabase.child("dish").child(String.valueOf(dishId)).child(type).removeValue();
    }

    // Removes a hole Dish element
    public void removeDish(Integer dishId) {
        mDatabase.child("dish").child(String.valueOf(dishId)).removeValue();
    }

    // Receives a DataSnapshot that contains the values from a specific location on the database
    public void readDataSnapShot(DatabaseReference mDishReference) {
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
