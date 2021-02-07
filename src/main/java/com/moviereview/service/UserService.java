package com.moviereview.service;

import com.moviereview.exception.UserNotFoundException;
import com.moviereview.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserService {

    private static final Map<String, User> users = new HashMap<>();


    public User addUser(String userName) {
        User user = new User(userName, "viewer"); // TODO: make factory
        users.put(userName, user);
        return user;
    }

    public void userExists(String username) throws Exception{
        if (!users.containsKey(username)) {
            throw new UserNotFoundException("user does not exist"); // TODO: create exception class
        }
    }

    public User getUser(String userName) throws Exception {
        this.userExists(userName);
        return users.get(userName);
    }

    public void incrementReviews(String userName) throws Exception {
        this.userExists(userName);
        User user = users.get(userName);
        user.addReview();
        users.put(userName, user);
    }

    public List<User> getUsersByProfile(String profile) {
        List<User> usersByProfile = users.values()
                .stream()
                .filter(user -> profile.equals(user.getProfile()))
                .collect(Collectors.toList());
        return usersByProfile;
    }
}

