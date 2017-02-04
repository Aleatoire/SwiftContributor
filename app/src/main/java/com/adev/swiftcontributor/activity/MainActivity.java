package com.adev.swiftcontributor.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.adev.swiftcontributor.R;
import com.adev.swiftcontributor.adapter.UserAdapter;
import com.adev.swiftcontributor.model.User;
import com.adev.swiftcontributor.service.APIService;
import com.adev.swiftcontributor.service.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private final UserService service = APIService.createService(UserService.class);
    private RecyclerView mRecyclerUser;
    private UserAdapter mAdapterUser;
    private List<User> mUsers = new ArrayList<>();


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerUser = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerUser.setLayoutManager(new GridLayoutManager(this, 2));
        fetchUsers();
    }

    private void fetchUsers() {

        Call<List<User>> call = service.getContributors();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    for (User user :
                            response.body()) {
                        Log.d(TAG, "User Login : " + user.getLogin());
                    }

                    mUsers.addAll(response.body());
                    mAdapterUser = new UserAdapter(mUsers);
                    mRecyclerUser.setAdapter(mAdapterUser);
                    mAdapterUser.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d(TAG, "Fail User fetch : " + t.getLocalizedMessage());
            }
        });
    }
}
