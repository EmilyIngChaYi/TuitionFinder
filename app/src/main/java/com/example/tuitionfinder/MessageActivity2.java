package com.example.tuitionfinder;

import static com.example.tuitionfinder.R.id.recyclerView8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity2 extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Chat_Tuition.class));
        finish();
    }

    ImageButton sendBtn;
    EditText text_send;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Chats");
    DatabaseReference userReference = database.getReference("Users");
    FirebaseAuth fAuth;
    String uid;
    MessageAdapter messageAdapter;
    List<Chat> mChat;
    RecyclerView recyclerView;
    chatUser receiverList = new chatUser();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message2);

        //display message recyclerView
        recyclerView = findViewById(R.id.recyclerView8);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        sendBtn = findViewById(R.id.SendBtn);
        text_send = findViewById(R.id.textSend);
        fAuth = FirebaseAuth.getInstance();
        uid = fAuth.getCurrentUser().getUid();

        //pass intent to get id
        String userID = getIntent().getStringExtra("id");
        String username = getIntent().getStringExtra("name");

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = text_send.getText().toString();
                if (!msg.equals("")){
                    sendMessage(uid, userID, msg, username);
                }else{
                    Toast.makeText(MessageActivity2.this, "You can't send empty massage", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChat = new ArrayList<>();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mChat.clear();
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Chat chat = snapshot.getValue(Chat.class);
                            if (chat.getReceiver().equals(uid) && chat.getSender().equals(userID) || chat.getReceiver().equals(userID) && chat.getSender().equals(uid)){
                                mChat.add(chat);
                            }
                            messageAdapter = new MessageAdapter(MessageActivity2.this, mChat);
                            recyclerView.setAdapter(messageAdapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage (String sender, String receiver, String message, String receiverName){

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String senderName = snapshot.child(sender).child("name").getValue().toString();

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("sender", sender);
                hashMap.put("receiver", receiver);
                hashMap.put("message", message);

                reference.push().setValue(hashMap);

                DatabaseReference chatUser = database.getReference("ChatUser").child(sender);
                DatabaseReference receiverUser = database.getReference("ChatUser").child(receiver);
                chatUser.orderByChild("id").equalTo(receiver).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){

                        }else{
                            receiverList.setName(receiverName);
                            receiverList.setId(receiver);
                            chatUser.push().setValue(receiverList);

                            receiverList.setName(senderName);
                            receiverList.setId(sender);
                            receiverUser.push().setValue(receiverList);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}