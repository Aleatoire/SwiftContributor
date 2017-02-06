package com.adev.swiftcontributor.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.adev.swiftcontributor.R;
import com.adev.swiftcontributor.Utils.Utils;
import com.adev.swiftcontributor.adapter.UserAdapter;
import com.adev.swiftcontributor.model.User;
import com.adev.swiftcontributor.service.APIService;
import com.adev.swiftcontributor.service.UserService;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private final UserService service = APIService.createService(UserService.class);
    private Realm realm;
    private RecyclerView mRecyclerUser;
    private LinearLayout mLoader;
    private UserAdapter mAdapterUser;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerUser = (RecyclerView) findViewById(R.id.recyclerView);
        mLoader = (LinearLayout) findViewById(R.id.loader_contributor);

        GridLayoutManager glm = new GridLayoutManager(this, 2);
        glm.setInitialPrefetchItemCount(8);
        mRecyclerUser.setLayoutManager(glm);
        mRecyclerUser.setItemAnimator(new DefaultItemAnimator());

        realm = Realm.getDefaultInstance();

        getUser();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void getUser() {
        if (Utils.isOnline(this)) {
            getUsersFromAPI();
        } else {
            getUsersFromDB();
        }
    }

    private void getUsersFromAPI() {
        Call<List<User>> call = service.getContributors();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {

                    realm.beginTransaction();
                    List<User> realmUsers = realm.copyToRealmOrUpdate(response.body());
                    realm.commitTransaction();

                    setupRecycler(realmUsers);
                    mLoader.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d(TAG, "Fail User fetch : " + t.getLocalizedMessage());
            }
        });
    }

    private void getUsersFromDB() {
        RealmResults<User> users = realm.where(User.class).findAll();

        setupRecycler(users);
        mLoader.setVisibility(View.GONE);
    }

    private void setupRecycler(List<User> users) {
        mAdapterUser = new UserAdapter(users);
        mRecyclerUser.setAdapter(mAdapterUser);
        mAdapterUser.notifyDataSetChanged();
        mRecyclerUser.invalidate();
    }
}
