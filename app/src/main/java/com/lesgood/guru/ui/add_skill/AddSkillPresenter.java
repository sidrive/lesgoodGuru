package com.lesgood.guru.ui.add_skill;

import android.support.annotation.NonNull;

import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.model.Category;
import com.lesgood.guru.data.model.Skill;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.CategoryService;
import com.lesgood.guru.data.remote.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Agus on 5/9/17.
 */

public class AddSkillPresenter implements BasePresenter {
    AddSkillActivity activity;
    UserService userService;
    CategoryService categoryService;
    User user;

    public AddSkillPresenter(AddSkillActivity activity, UserService userService, CategoryService categoryService, User user){
        this.activity = activity;
        this.userService = userService;
        this.categoryService = categoryService;
        this.user = user;
    }

    @Override
    public void subscribe() {
        if (user != null){
            getCategories();
        }
    }

    @Override
    public void unsubscribe() {

    }

    public void getCategories(){
        categoryService.getCategories().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Category> listCategories = new ArrayList<Category>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Category category = postSnapshot.getValue(Category.class);
                    listCategories.add(category);
                }

                activity.initCategories(listCategories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("onCancelled", "AddSkillPresenter" + databaseError.getMessage());
            }
        });
    }

    public void getSubCategories(String id){
        categoryService.getSubCategories(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Category> listCategories = new ArrayList<Category>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Category category = postSnapshot.getValue(Category.class);
                    listCategories.add(category);
                }

                activity.initSubcategories(listCategories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("onCancelled", "AddSkillPresenter" + databaseError.getMessage());
            }
        });
    }

    public void getLevels(String id){
        categoryService.getLevels(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Category> listCategories = new ArrayList<Category>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Category category = postSnapshot.getValue(Category.class);
                    listCategories.add(category);
                }

                activity.initLevel(listCategories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("onCancelled", "AddSkillPresenter" + databaseError.getMessage());
            }
        });
    }

    public void updateSkill(String uid, Skill skill){
        userService.updateSkill(uid, skill).addOnCompleteListener(
            task -> activity.successUpdateSkill());
    }

    public void updateTotalSkill(String uid, int total){
        userService.updateTotalSkill(uid, total);
    }
}
