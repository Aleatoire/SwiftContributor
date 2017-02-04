package com.adev.swiftcontributor.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adev.swiftcontributor.R;
import com.adev.swiftcontributor.model.User;
import com.adev.swiftcontributor.service.APIService;
import com.adev.swiftcontributor.service.UserService;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "DetailsActivity";

    private final UserService service = APIService.createService(UserService.class);

    private ImageView mThumbnail;
    private TextView mName;
    private TextView mLink;
    private TextView mFollowers;

    private String loginUser;
    private User mUser;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mThumbnail = (ImageView) findViewById(R.id.user_img_thumbnail);
        mName = (TextView) findViewById(R.id.user_txt_name);
        mLink = (TextView) findViewById(R.id.user_txt_link);
        mFollowers = (TextView) findViewById(R.id.user_txt_followers);

        mLink.setOnClickListener(this);

        if (getIntent() != null) {
            this.loginUser = getIntent().getStringExtra("login");
            fetchUser();
        }

    }

    private void fetchUser() {

        Call<User> call = service.getContributor(loginUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful())
                    setUpUser(response.body());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "Fail User fetch : " + t.getLocalizedMessage());
            }
        });
    }

    private void setUpUser(User user) {
        if (user == null)
            return;

        this.mUser = user;

        mName.setText(mUser.getName());
        mFollowers.setText(mUser.getFollowers() + " Followers");

        Picasso.with(mThumbnail.getContext())
                .load(user.getAvatar_url())
                .transform(new CropCircleTransformation())
                .centerCrop()
                .placeholder(R.drawable.ic_person_black_48dp)
                .error(android.R.color.holo_red_dark)
                .noFade()
                .fit()
                .into(mThumbnail);
    }

    private void openViewLink() {
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUser.getHtml_url()));
        startActivity(myIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_txt_link:
                openViewLink();
                break;
        }
    }
}
