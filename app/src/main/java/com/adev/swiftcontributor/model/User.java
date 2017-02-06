package com.adev.swiftcontributor.model;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by devandroid on 03/02/2017.
 */

public class User extends RealmObject {


    @PrimaryKey
    private int id;
    private String login;
    private String avatar_url;
    private String html_url;
    @Ignore
    private String type;
    @Ignore
    private boolean site_admin;
    private String name;
    private String company;
    private String blog;
    private int contributions;
    private String location;
    private String email;
    @Ignore
    private String bio;
    private int followers;
    private int following;
    private String updated_at;

    //region Getter Setter
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSite_admin() {
        return site_admin;
    }

    public void setSite_admin(boolean site_admin) {
        this.site_admin = site_admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public int getContributions() {
        return contributions;
    }

    public void setContributions(int contributions) {
        this.contributions = contributions;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void updateUser(User user) {

        if (!getLogin().equals(user.getLogin()))
            return;

        if (!getEmail().equals(user.getEmail())) {
            this.email = user.getEmail();
        }
        if (!getHtml_url().equals(user.getHtml_url())) {
            this.html_url = user.getHtml_url();
        }
        if (!getBlog().equals(user.getBlog())) {
            this.blog = user.getBlog();
        }
        if (!getAvatar_url().equals(user.getAvatar_url())) {
            this.avatar_url = user.getAvatar_url();
        }
        if (!getBio().equals(user.getBio())) {
            this.bio = user.getBio();
        }
        if (!getCompany().equals(user.getCompany())) {
            this.company = user.getCompany();
        }
        if (!getLocation().equals(user.getLocation())) {
            this.location = user.getLocation();
        }
        if (!getName().equals(user.getName())) {
            this.name = user.getName();
        }
        if (!getType().equals(user.getType())) {
            this.type = user.getType();
        }
        if (!getUpdated_at().equals(user.getUpdated_at())) {
            this.updated_at = user.getUpdated_at();
        }
        if(getContributions() != user.getContributions()){
            this.contributions = user.getContributions();
        }
        if(getFollowers() != user.getFollowers()){
            this.followers = user.getFollowers();
        }
        if(getFollowing() != user.getFollowing()){
            this.following = user.getFollowing();
        }

    }
    //endregion
}
