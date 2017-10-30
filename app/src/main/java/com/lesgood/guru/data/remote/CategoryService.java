package com.lesgood.guru.data.remote;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Agus on 5/9/17.
 */

public class CategoryService {
    DatabaseReference databaseRef;

    public CategoryService(){
        this.databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getCategories(){
        return databaseRef.child("categories");
    }

    public DatabaseReference getLevels(String id){
        return databaseRef.child("levels").child(id);
    }

    public DatabaseReference getSubCategories(String id){
        return databaseRef.child("subcategories").child(id);
    }
}
