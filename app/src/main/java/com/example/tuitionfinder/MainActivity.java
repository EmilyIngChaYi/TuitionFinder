package com.example.tuitionfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface{

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Login.class));
    }

    FirebaseDatabase database;
    DatabaseReference reference, ref;
    FirebaseAuth fAuth;
    String userID;
    TextView userEmail;
    SearchView search;
    ImageButton sortBtn;
    Button chatBtn, addBtn, userProfileBtn;
    RecyclerView recyclerView;
    ArrayList<TuitionList> list;
    MyAdapter adapter;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userEmail = findViewById(R.id.userEmail);
        search = findViewById(R.id.search);
        search.clearFocus();
        sortBtn = findViewById(R.id.sortBtn);
        chatBtn = findViewById(R.id.chatBtn);
        addBtn = findViewById(R.id.addBtn);
        userProfileBtn = findViewById(R.id.userProfile);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        //recycle view
        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ref = database.getReference("Tuition Center Lists");
        list = new ArrayList<>();
        adapter = new MyAdapter(this,list, this);
        recyclerView.setAdapter(adapter);

        //recyclerView
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    TuitionList tuitionList = dataSnapshot.getValue(TuitionList.class);
                    list.add(tuitionList);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //search bar
        if (search != null){
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }

        //retrieve the email address of user and display on the top right corner
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String email = snapshot.child(userID).child("email").getValue().toString();
                userEmail.setText(email);

                //if user is student then cannot see the add button
                String userId = snapshot.child(userID).child("userType").getValue().toString();
                if (userId.matches("Tuition Center")){
                    addBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //click the user email on the top right corner will direct to user profile
        userEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),UserProfile.class));
                finish();
            }
        });

        //sort ascending order; tuition name
        sortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (i == 0){
                    Collections.reverse(list);
                    i++;
                }else{
                    Collections.sort(list, new Comparator<TuitionList>() {
                        @Override
                        public int compare(TuitionList tuitionList, TuitionList t1) {
                            return tuitionList.tuitionName.compareToIgnoreCase(t1.tuitionName);
                        }
                    });
                    i--;
                }
                adapter.notifyDataSetChanged();
            }
        });

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Chat_Tuition.class));
                finish();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),addTuitionCenter.class));
                finish();
            }
        });

        userProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),UserProfile.class));
                finish();
            }
        });
    }

    // able to search through tuition center list with tuitionName, location, or state.
    private void search(String str) {
        ArrayList<TuitionList> myList = new ArrayList<>();
        for (TuitionList object: list){
            if (object.getTuitionName().toLowerCase().contains(str.toLowerCase())){
                myList.add(object);
            }
            if (object.getTuitionAddress().toLowerCase().contains(str.toLowerCase())){
                myList.add(object);
            }
            if (object.getState().toLowerCase().contains(str.toLowerCase())){
                myList.add(object);
            }
        }
        adapter = new MyAdapter(this,myList, this);
        recyclerView.setAdapter(adapter);
    }

    // able to click on the recyclerView item and pass the tuition center name
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, TuitionCenterDetails.class);
        intent.putExtra("tuitionName",list.get(position).getTuitionName());

        startActivity(intent);
    }
}