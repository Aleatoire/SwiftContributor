package com.adev.swiftcontributor.service;

import com.adev.swiftcontributor.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by devandroid on 03/02/2017.
 */

public interface UserService {

    @GET("/repos/apple/swift/contributors")
    Call<List<User>> getContributors();

    @GET("/users/{user}")
    Call<User> getContributor(@Path("user") String user);

}

