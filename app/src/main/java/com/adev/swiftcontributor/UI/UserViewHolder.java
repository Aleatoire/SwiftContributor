package com.adev.swiftcontributor.UI;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adev.swiftcontributor.activity.DetailsActivity;
import com.adev.swiftcontributor.R;
import com.adev.swiftcontributor.model.User;
import com.squareup.picasso.Picasso;

public class UserViewHolder extends RecyclerView.ViewHolder {

    private TextView loginTxtView;
    private ImageView thumbnailImgView;
    private User mUser;

    public UserViewHolder(View itemView) {
        super(itemView);

        thumbnailImgView = (ImageView) itemView.findViewById(R.id.img_thumbnail_user);
        loginTxtView = (TextView) itemView.findViewById(R.id.txt_login_user);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thumbnailImgView.getContext(), DetailsActivity.class);
                intent.putExtra("login", mUser.getLogin());
                intent.putExtra("contributions", mUser.getContributions());
                thumbnailImgView.getContext().startActivity(intent);
            }
        });
    }

    public void setUser(User user) {
        if (user == null)
            return;

        this.mUser = user;

        loginTxtView.setText(user.getLogin());
        Picasso.with(thumbnailImgView.getContext())
                .load(user.getAvatar_url())
                .centerCrop()
                .placeholder(R.drawable.ic_user)
                .error(R.drawable.ic_user)
                .noFade()
                .fit()
                .into(thumbnailImgView);
    }
}
