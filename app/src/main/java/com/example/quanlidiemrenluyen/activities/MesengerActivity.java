package com.example.quanlidiemrenluyen.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quanlidiemrenluyen.Adapters.MessengerAdapter;
import com.example.quanlidiemrenluyen.Models.Chat;
import com.example.quanlidiemrenluyen.Models.User;
import com.example.quanlidiemrenluyen.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MesengerActivity extends AppCompatActivity {
    CircleImageView profileImage;
    TextView userName;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    Intent intent;
    ImageButton imageButtonSend;
    EditText editTextSend;
    RecyclerView recyclerView;

    MessengerAdapter messengerAdapter;
    List <Chat> chats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesenger);

    profileImage = findViewById(R.id.circleImageMessenger);
    userName = findViewById(R.id.userNameMessenger);
    imageButtonSend = findViewById(R.id.btnSend);
    editTextSend = findViewById(R.id.textSend);

    recyclerView = findViewById(R.id.recyclerViewChats);
    recyclerView.setHasFixedSize(true);

    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
    layoutManager.setStackFromEnd(true);
    recyclerView.setLayoutManager(layoutManager);

    intent = getIntent();
    final String userID = intent.getStringExtra("userid");
        imageButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editTextSend.getText().toString();
                sendMesenge(firebaseUser.getUid(),userID,msg);


                editTextSend.setText("");
            }
        });
    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
    reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            User user = dataSnapshot.getValue(User.class);
            userName.setText(user.getUserName());
            if(user.getImageURL().equals("default")){
                profileImage.setImageResource(R.mipmap.ic_launcher);
            }else {
                Glide.with(MesengerActivity.this).load(user.getImageURL()).into(profileImage);
            }
            readMeseger(firebaseUser.getUid(),userID,user.getImageURL());
            }



        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

    }
    private void sendMesenge(String sender, String receiver, String mesenge){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap <String,Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("mesenger",mesenge);

        reference.child("Chats").push().setValue(hashMap);
    }
    private void readMeseger(final String id, final String userID, final String imageURL){
        chats = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chats.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if((chat.getReceiver().equals(userID)&& chat.getSender().equals(id))
                    || (chat.getSender().equals(userID)&& chat.getReceiver().equals(id))
                    ){
                        chats.add(chat);
                    }
                    messengerAdapter = new MessengerAdapter(MesengerActivity.this,chats,imageURL);
                    recyclerView.setAdapter(messengerAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
