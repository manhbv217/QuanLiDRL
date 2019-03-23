package com.example.quanlidiemrenluyen.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quanlidiemrenluyen.Models.User;
import com.example.quanlidiemrenluyen.R;
import com.example.quanlidiemrenluyen.activities.MesengerActivity;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter <UserAdapter.ViewHolder>{
    private Context mContext;
    private List<User> mUser;

    public UserAdapter(Context context, List < User> mUsers){
        this.mContext = context;
        this.mUser = mUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item,viewGroup,false);

        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            final User user = mUser.get(i);

           viewHolder.username.setText(user.getUserName());

        if(user.getImageURL().equals("default")){
            viewHolder.profileImage.setImageResource(R.mipmap.ic_launcher);

        }else {
            Glide.with(mContext).load(user.getImageURL()).into(viewHolder.profileImage);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MesengerActivity.class);
                intent.putExtra("userid",user.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView profileImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            profileImage = itemView.findViewById(R.id.profile_image);


        }
    }
}
