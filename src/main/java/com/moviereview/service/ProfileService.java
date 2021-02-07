package com.moviereview.service;

import com.moviereview.exception.UserProfileNotFoundException;
import com.moviereview.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProfileService {

    @Autowired
    private UserService userService;


    private static final Map<String, Integer> userProfile;
    static {
        userProfile = new HashMap<>();
        userProfile.put("viewer", 1);
        userProfile.put("critic", 2);
        userProfile.put("expert", 3);
        userProfile.put("admin", 4);
    }

    private static final Map<String, Integer> profileThreshold;
    static {
        profileThreshold = new HashMap<>();
        profileThreshold.put("viewer", 0);
        profileThreshold.put("critic", 3);
        profileThreshold.put("expert", 10);
        profileThreshold.put("admin", 20);
    }

    private static final Map<String, String> profileUpgrade;
    static  {
        profileUpgrade = new HashMap<>();
        profileUpgrade.put("viewer", "critic");
        profileUpgrade.put("critic", "expert");
        profileUpgrade.put("expert", "admin");
        profileUpgrade.put("admin", "admin");
    }

    public Integer getWeightage(String profile) throws Exception {
        if (!userProfile.containsKey(profile)) {
            throw new UserProfileNotFoundException("user profile not found");
        }
        return userProfile.get(profile);
    }

    public void updateUserProfile(String userName) throws Exception {
        this.userService.incrementReviews(userName);
        User user = this.userService.getUser(userName);
        String nextProfile = profileUpgrade.get(user.getProfile());
        Integer currentReviews = user.getNumReviews();
        Integer nextProfileReviews = profileThreshold.get(nextProfile);
        if (currentReviews >= nextProfileReviews) {
            user.setProfile(nextProfile);
        }
    }

    public void checkProfileExists(String profile) throws UserProfileNotFoundException {
        if (!userProfile.containsKey(profile)) {
            throw new UserProfileNotFoundException("user profile " + profile + " not found");
        }
    }


}
