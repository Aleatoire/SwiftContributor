package com.adev.swiftcontributor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adev.swiftcontributor.R;
import com.adev.swiftcontributor.UI.UserViewHolder;
import com.adev.swiftcontributor.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devandroid on 03/02/2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private List<User> mUsers;


    public UserAdapter(List<User> users) {
        this.mUsers = users;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.user_grid_item, viewGroup, false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder myViewHolder, int position) {
        User user = mUsers.get(position);
        myViewHolder.setUser(user);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

}
