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

    /**
     * Return the list of swift contributors
     *
     * @return list of user
     */
    @GET("/repos/apple/swift/contributors")
    Call<List<User>> getContributors();

    /**
     * Return the queried contributor
     *
     * @return selected user
     */
    @GET("/users/{user}")
    Call<User> getContributor(@Path("user") String user);

}

