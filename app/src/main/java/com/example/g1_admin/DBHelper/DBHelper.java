package com.example.g1_admin.DBHelper;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.g1_admin.Model.Category;
import com.example.g1_admin.Model.Order;
import com.example.g1_admin.Model.Dish;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DBHelper {

    private static final String TAG = "DBHelper";

    // Instance  of the Firebase Database
    DatabaseReference mDatabase;

    ArrayList<Order> orders = new ArrayList<>();

    ArrayList<String> categories = new ArrayList<>();

    public DBHelper(DatabaseReference mDatabase) {
        this.mDatabase = mDatabase;
    }

    // Creates a new Dish object and adds it to the Database
    // This method also allows to update all the values from a dish element
    public void addDish(String name, String imageName, String category, String description, Double price) {

        //mDatabase = mDatabase.child("dish");

        DatabaseReference pushedPostRef = mDatabase.child("dish").push();

        String dishId = pushedPostRef.getKey();
        Log.i("testDB", "" + dishId);


        Dish dish = new Dish(dishId,imageName,category,name,description,price);
        mDatabase.child("dish").child(category).child(dishId).setValue(dish);
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

    // Add promotion to dish
    public void addPromotion(String categoria, String id, String promotionDate, String discount) {
        mDatabase.child("dish").child(categoria).child(id).setValue(promotionDate);
        mDatabase.child("dish").child(categoria).child(id).setValue(discount);
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

    // Creates a new Dish object and adds it to the Database
    // This method also allows to update all the values from a dish element
    public void addCategory(String name, String imageName) {

        DatabaseReference pushedPostRef = mDatabase.child("category").push();

        String categoryId = pushedPostRef.getKey();
        Log.i("testDB", "" + categoryId);


        Category category = new Category(name, categoryId, imageName);
        mDatabase.child("category").child(categoryId).setValue(category);
    }

    // Returns an ArrayList of the Orders nodes from Firebase
    public ArrayList<Order> getOrders() {

        mDatabase.child("orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Gets the child's from Order
                    for (DataSnapshot ds: snapshot.getChildren()) {

                        // Gets the child's from the Order child's
                        for (int i = 0; i < ds.getChildrenCount(); i++) {

                            int id = Integer.parseInt(ds.child(String.valueOf(i)).child("id").getValue().toString());
                            String name = ds.child(String.valueOf(i)).child("name").getValue().toString();
                            double price = Double.parseDouble(ds.child(String.valueOf(i)).child("price").getValue().toString());

                            Order order = new Order(ds.getKey(), i, id, name, price);

                            //Log.i("command", order.getName());

                            orders.add(order);

                        }
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Do nothing
            }
        });

        return orders;
    }

    // Returns an ArrayList of the Orders nodes from Firebase
    public ArrayList<String> getCategories() {

        mDatabase.child("category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    categories.clear();
                    // Gets the child's from Order
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        String name = ds.child("categoryName").getValue().toString();

                        categories.add(name);
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Do nothing
            }
        });

        return categories;
    }
}
