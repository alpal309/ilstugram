package com.ilstugram.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Entity(name = "following")
public class Following implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private int id;

    @Expose
    private String follower, following;

    @Expose
    private int enabled;

    @ManyToOne
    @JoinColumn(name = "follower", referencedColumnName = "username", insertable = false, updatable = false)
    private User user;

    @Expose
    @OneToMany
    @JoinColumn(name = "username", referencedColumnName = "following")
    private List<User> followingList;

    public Following(){}

    public Following(String follower, String following){
        this.follower = follower;
        this.following = following;
        this.enabled = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getFollowingList() {
        return followingList;
    }

    public void setFollowingList(List<User> followingList) {
        this.followingList = followingList;
    }

    @Override
    public String toString() {
        return "Following{" +
                "id=" + id +
                ", follower='" + follower + '\'' +
                ", following='" + following + '\'' +
                ", followingList=" + Arrays.toString(followingList.toArray()) +
                ", enabled=" + enabled +
                '}';
    }
}
