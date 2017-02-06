package com.adev.swiftcontributor.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adev.swiftcontributor.R;
import com.adev.swiftcontributor.Utils.Utils;
import com.adev.swiftcontributor.model.User;
import com.adev.swiftcontributor.service.APIService;
import com.adev.swiftcontributor.service.UserService;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = "DetailsActivity";

    @BindView(R.id.user_txt_name)
    TextView userTxtName;
    @BindView(R.id.user_img_thumbnail)
    ImageView userImgThumbnail;
    @BindView(R.id.user_txt_contribution)
    TextView userTxtContribution;
    @BindView(R.id.user_txt_followers)
    TextView userTxtFollowers;
    @BindView(R.id.user_txt_following)
    TextView userTxtFollowing;
    @BindView(R.id.user_txt_localisation)
    TextView userTxtLocalisation;
    @BindView(R.id.user_txt_company)
    TextView userTxtCompany;
    @BindView(R.id.user_txt_bio)
    TextView userTxtBio;
    @BindView(R.id.user_txt_blog)
    TextView userTxtBlog;
    @BindView(R.id.user_txt_link)
    TextView userTxtLink;
    @BindView(R.id.send_mail_fab)
    FloatingActionButton sendMailFab;
    @BindView(R.id.loader_user)
    LinearLayout loaderUser;
    @BindView(R.id.swipe_refresh_user)
    SwipeRefreshLayout swipeRefreshLayout;

    private final UserService service = APIService.createService(UserService.class);
    private Realm realm;
    private User mUser;

    private String loginUser;
    private int userContribution;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfiguration);

        loaderUser.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setOnRefreshListener(this);

        if (getIntent() != null) {
            this.loginUser = getIntent().getStringExtra("login");
            this.userContribution = getIntent().getIntExtra("contribution", 0);
            fetchUser();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void fetchUser() {
        if (Utils.isOnline(this)) {
            Call<User> call = service.getContributor(loginUser);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        setUpUser(response.body());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d(TAG, "Fail User fetch : " + t.getLocalizedMessage());
                }
            });
        } else {
            setUpUser(realm.where(User.class).equalTo("login", loginUser).findFirst());
        }
    }

    private void setUpUser(User user) {
        if (user == null)
            return;

        this.mUser = user;

        realm.beginTransaction();
        mUser.setContributions(userContribution);
        realm.copyToRealmOrUpdate(this.mUser);
        realm.commitTransaction();


        userTxtName.setText(mUser.getName());

        userTxtFollowers.setText(getResources().getQuantityString(R.plurals.format_follower, mUser.getFollowers(), mUser.getFollowers()));
        userTxtFollowing.setText(getResources().getQuantityString(R.plurals.format_following, mUser.getFollowing(), mUser.getFollowing()));
        userTxtContribution.setText(getResources().getQuantityString(R.plurals.format_contribution, mUser.getContributions(), mUser.getContributions()));

        if (mUser.getLocation() != null) {
            userTxtLocalisation.setText(mUser.getLocation());
            userTxtLocalisation.setVisibility(View.VISIBLE);
        }

        if (mUser.getCompany() != null) {
            userTxtCompany.setText(mUser.getCompany());
            userTxtCompany.setVisibility(View.VISIBLE);
        }

        if (mUser.getBio() != null) {
            userTxtBio.setText(mUser.getBio());
            userTxtBio.setVisibility(View.VISIBLE);
        }

        if (mUser.getBlog() != null) {
            userTxtBlog.setVisibility(View.VISIBLE);
        }

        if (mUser.getHtml_url() != null) {
            userTxtLink.setVisibility(View.VISIBLE);
        }

        if (mUser.getEmail() != null) {
            sendMailFab.setVisibility(View.VISIBLE);
        }

        Picasso.with(userImgThumbnail.getContext())
                .load(mUser.getAvatar_url())
                .transform(new CropCircleTransformation())
                .centerCrop()
                .placeholder(R.drawable.ic_user_circle)
                .error(R.drawable.ic_user_circle)
                .noFade()
                .fit()
                .into(userImgThumbnail, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        loaderUser.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError() {
                        loaderUser.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

    }

    private void openViewLink(String url) {
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(myIntent);
    }

    private void sendMail() {
        if (mUser.getEmail() != null) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", mUser.getEmail(), null));
            startActivity(Intent.createChooser(emailIntent, getString(R.string.send_mail)));
        }
    }

    @OnClick({R.id.user_txt_followers, R.id.user_txt_following, R.id.user_txt_localisation, R.id.user_txt_blog, R.id.user_txt_link, R.id.send_mail_fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_txt_blog:
                openViewLink(mUser.getBlog());
                break;
            case R.id.user_txt_link:
                openViewLink(mUser.getHtml_url());
                break;
            case R.id.send_mail_fab:
                sendMail();
                break;
        }
    }

    @Override
    public void onRefresh() {
        fetchUser();
    }
}
