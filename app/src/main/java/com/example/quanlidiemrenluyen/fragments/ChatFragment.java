package com.example.quanlidiemrenluyen.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanlidiemrenluyen.Adapters.UserAdapter;
import com.example.quanlidiemrenluyen.Models.Chat;
import com.example.quanlidiemrenluyen.Models.User;
import com.example.quanlidiemrenluyen.R;
import com.example.quanlidiemrenluyen.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChatFragment extends Fragment {

    RecyclerView recyclerView;
    UserAdapter adapter;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    List<User> users;
    List<String> userList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)getActivity()).setActionBarTitle("Chats");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.recyclerviewuserchat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        userList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if(chat.getSender().equals(firebaseUser.getUid())){
                        userList.add(chat.getReceiver());
                    }
                    if(chat.getReceiver().equals(firebaseUser.getUid())){
                        userList.add(chat.getSender());
                    }
                }
                readChatUser();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
    public void readChatUser(){
         users  = new ArrayList<>();
         reference = FirebaseDatabase.getInstance().getReference("Users");
         reference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 users.clear();
                 for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                     User user = snapshot.getValue(User.class);

                     for(String id: userList){
                         if(user.getId().equals(id)){
                             if(users.size()!=0){
                                 for (User u: users){
                                     if(!user.getId().equals(u.getId())){
                                         users.add(user);
                                     }
                                 }
                             }else{
                                 users.add(user);
                             }

                         }
                     }


                 }
                 adapter = new UserAdapter(getContext(),users);
                 recyclerView.setAdapter(adapter);
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });
    }

}
